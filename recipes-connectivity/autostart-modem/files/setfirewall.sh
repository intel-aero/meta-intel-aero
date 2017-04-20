#! /bin/bash
# Copyright (c) 2016
#
# Author: Johannes Fischer <johannes.fischer@intel.com>
#
# This is a script to set a very simple firewall
# features:
#   remote access is available on port 22 to the local aero board
#   NAT is supported, so a client connected to IF_LOCAL will be able to go to the wwan interface, 
#       so having Internet access
# handles FILTER and NAT tables, kernel and youcto config needs to support IPv4 netfilter setup 
# and has to provide the iptables command with NAT support.
#
# This script will get called from NetworkManager dispatch script when modem connection/PDN gets enabled.
#
# usage setfirewall.sh start|stop IF_PUBLIC
set +e

IPTABLES="/usr/sbin/iptables"

# Local interface
#IF_LOCAL=usb0
#IP_LOCAL=192.168.7.2
#NET_LOCAL=192.168.7.0/24

# Alternative Local Interface
IF_LOCAL=wlan0
IP_LOCAL=192.168.8.1
NET_LOCAL=192.168.8.0/24

# Public interface, WWAN interface
IF_PUBLIC=$2
IP_PUBLIC=`ip addr show dev $IF_PUBLIC | grep "inet "| sed "s/^[a-z ]*\([0-9\.]*\).*/\1/"`
NET_PUBLIC=`echo $IP_PUBLIC | cut -d "." -f1-3`.0/24 

echo "setting firewall $1 for IF $IF_PUBLIC, IP $IP_PUBLIC, NET $NET_PUBLIC" > /dev/console
logger "setting firewall $1 for IF $IF_PUBLIC, IP $IP_PUBLIC, NET $NET_PUBLIC"

startFW() {
	# Enable IPv4 routing
	echo 1 > /proc/sys/net/ipv4/ip_forward

	# what shall never leave towards Internet, no IRC chats, it is too easy to leak info by this
	/usr/sbin/iptables -A INPUT -p tcp --dport irc -j DROP
	/usr/sbin/iptables -A INPUT -p udp --dport irc -j DROP
	/usr/sbin/iptables -A INPUT -p tcp --dport ircd -j DROP
	/usr/sbin/iptables -A INPUT -p udp --dport ircd -j DROP
	/usr/sbin/iptables -A INPUT -p tcp --dport ircs -j DROP
	/usr/sbin/iptables -A INPUT -p udp --dport ircs -j DROP

	# What traffic shall go out from where
	/usr/sbin/iptables -A FORWARD -i $IF_PUBLIC -o $IF_LOCAL -m state --state ESTABLISHED,RELATED -j ACCEPT
	/usr/sbin/iptables -A FORWARD -i $IF_PUBLIC -o $IF_LOCAL -j ACCEPT

	# Allow local loopback operation
	/usr/sbin/iptables -A INPUT -i lo -j ACCEPT
	/usr/sbin/iptables -A OUTPUT -o lo -j ACCEPT

	# Open the local interfaces to allow all traffic
	/usr/sbin/iptables -A INPUT -i $IF_LOCAL -j ACCEPT
	/usr/sbin/iptables -A OUTPUT -o $IF_LOCAL -j ACCEPT
    # to be sure, again
	#/usr/sbin/iptables -A INPUT -i lo -j ACCEPT
	#/usr/sbin/iptables -A OUTPUT -o lo -j ACCEPT

	# Copnfigure the public interfaces so it accepts some traffic, also set the NAT to the local interface
	/usr/sbin/iptables -A INPUT -i $IF_PUBLIC -m state --state ESTABLISHED,RELATED -j ACCEPT
	/usr/sbin/iptables -A OUTPUT -o $IF_PUBLIC -j ACCEPT
	/usr/sbin/iptables -t nat -A POSTROUTING -s $NET_LOCAL -o $IF_PUBLIC -j SNAT --to $IP_PUBLIC

	# allow some kind of ICMP (ping etc) always
	/usr/sbin/iptables -A INPUT -p icmp --icmp-type 0 -j ACCEPT
	/usr/sbin/iptables -A INPUT -p icmp --icmp-type 3 -j ACCEPT
	/usr/sbin/iptables -A INPUT -p icmp --icmp-type 11 -j ACCEPT
	/usr/sbin/iptables -A INPUT -p icmp --icmp-type 8 -j ACCEPT

	# allow remote access by ssh on port 22
	/usr/sbin/iptables -A INPUT -i $IF_PUBLIC -p tcp --dport 22 -j ACCEPT

	# we dont want to answer to DHCP (bootp) from the outside
	/usr/sbin/iptables -A INPUT -i $IF_PUBLIC -p udp --sport bootps -j DROP

	# finally lets actively reject connection requests from ouside, normal behaviour
	/usr/sbin/iptables -A INPUT -p tcp -j REJECT --reject-with tcp-reset
}

stopFW() {
  /usr/sbin/iptables -A INPUT -i $IF_LOCAL -j ACCEPT
  /usr/sbin/iptables -A OUTPUT -o $IF_LOCAL -j ACCEPT
  /usr/sbin/iptables -A INPUT -i lo -j ACCEPT
  /usr/sbin/iptables -A OUTPUT -o lo -j ACCEPT
}

initFW()
{
  /usr/sbin/iptables -P INPUT DROP
  /usr/sbin/iptables -P OUTPUT DROP
  /usr/sbin/iptables -P FORWARD DROP

  /usr/sbin/iptables -F -t nat
  /usr/sbin/iptables -F -t mangle
  /usr/sbin/iptables -F -t filter
  /usr/sbin/iptables -X
}

echo "Configure Firewall at interface $2"


case "$1" in
	start)
		initFW
		startFW
		;;

	stop)
		initFW
		stopFW
		;;

	restart)
		$0 stop
		$0 start
		;;

	*)
		;;
esac


