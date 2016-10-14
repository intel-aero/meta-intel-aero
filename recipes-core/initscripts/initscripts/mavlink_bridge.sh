#! /bin/sh

### BEGIN INIT INFO
# Provides:        mavlink_bridge
# Required-Start:  $network
# Required-Stop:   $network
# Default-Start:   2 3 4 5
# Default-Stop:
### END INIT INFO

# Source function library.
. /etc/init.d/functions

# Functions to do individual actions
start(){
	mavlink_bridge.py 192.168.1.2 &
}
stop(){
	kill `ps | grep -m 1 'mavlink_bridge.py' | awk '{print $1}'`
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
	ps | grep -m 1 mavlink_bridge.py
	;;
  *)
	echo "Usage: mavlink_bridge { start | stop | status | restart }" >&2
	exit 1
	;;
esac

exit 0
