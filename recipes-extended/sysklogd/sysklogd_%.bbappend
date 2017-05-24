inherit systemd

SYSTEMD_SERVICE_${PN}_remove = "syslogd.service"

do_install_append() {
	rm -f ${D}${systemd_unitdir}/system/syslogd.service
}
