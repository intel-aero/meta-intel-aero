#! /bin/sh

case "$1" in
  start)
	systemctl start mavlink-router
	;;
  stop)
	systemctl stop mavlink-router
	;;
  restart)
	systemctl restart mavlink-router
	;;
  status)
	systemctl status mavlink-router
	;;
  *)
	echo "Usage: mavlink-routerd { start | stop | status | restart }" >&2
	exit 1
	;;
esac

exit 0
