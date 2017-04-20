#!/bin/sh

echo "autostart-hostapd"

case "$1" in
        start)
                echo "autostart-hostapd called with start"

                # Stop wpa_supplicant if previously running.
                /etc/init.d/autostart-supplicant.sh stop

                # get wlan MAC@ and use it for Soft-AP SSID.
                MAC=$(ifconfig -a | grep wlan | sed -e 's/.*HWaddr\s*//' | tr -d ':[:space:]')
                sed "s/^ssid=.*/ssid=Aero-${MAC}/" /etc/hostapd.conf > /tmp/hostapd.conf

                # Need to enable RFKILL for wlan0
                rfkill unblock wlan

                # Start softAP
                start-stop-daemon -S -x /usr/local/bin/hostapd -- -B -f /var/log/hostapd.log /tmp/hostapd.conf

                # Start DHCP server
                sleep 1
                ifconfig wlan0 192.168.8.1
                start-stop-daemon -S -x /usr/sbin/udhcpd -- -S /etc/udhcpd.conf
                ;;
        stop)
                echo "autostart-hostapd called with stop"
                # Stop DHCP server and softAP.
                killall udhcpd
                killall hostapd
                ifconfig wlan0 0.0.0.0
                ifconfig wlan0 down
                ;;
esac

