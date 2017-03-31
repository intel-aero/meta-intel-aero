#! /bin/sh

### BEGIN INIT INFO
# Provides:        Camera Streaming Daemon
# Required-Start:  $network
# Required-Stop:   $network
# Default-Start:   2 3 4 5
# Default-Stop:
### END INIT INFO

# Source function library.
. /etc/init.d/functions

# Functions to do individual actions
start(){
	 /usr/bin/csd &
}
stop(){
	kill `ps | grep -m 1 'csd' | awk '{print $1}'`
}
status(){
	ps | grep -m 1 camera-streaming-daemon
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
	echo "Usage: csd.sh { start | stop | status | restart }" >&2
	exit 1
	;;
esac

exit 0
