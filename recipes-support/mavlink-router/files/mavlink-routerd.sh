#! /bin/sh

### BEGIN INIT INFO
# Provides:        MAVLink Router
# Required-Start:  $network
# Required-Stop:   $network
# Default-Start:   2 3 4 5
# Default-Stop:
### END INIT INFO

# Source function library.
. /etc/init.d/functions

# Functions to do individual actions
start(){
	 /usr/bin/mavlink-routerd -b 1500000 -e 192.168.7.255 -e 192.168.1.255 /dev/ttyS1 &
}
stop(){
	kill `ps | grep -m 1 'mavlink-routerd' | awk '{print $1}'`
}
status(){
	ps | grep -m 1 mavlink-routerd
}

case "$1" in
  start)
	start
	;;
  stop)
	stop
	;;
  restart)
	stop
	start
	;;
  status)
	status
	;;
  *)
	echo "Usage: mavlink-routerd { start | stop | status | restart }" >&2
	exit 1
	;;
esac

exit 0
