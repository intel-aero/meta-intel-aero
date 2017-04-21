SUMMARY = "Utilities to power on/off and reset the modem"
SECTION = "modem"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

inherit update-rc.d

SRC_URI = " \
    file://modem_off \
    file://modem_on \
    file://modem_reset \ 
	file://modem-enable \
"
INITSCRIPT_PARAMS = "start 20 2 3 4 5 . stop 20 0 1 6 ."
INITSCRIPT_NAME = "modem-enable"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${sbindir}
    install -m 0755 modem_off ${D}${sbindir}
    install -m 0755 modem_on ${D}${sbindir}
    install -m 0755 modem_reset ${D}${sbindir}
    install -d ${D}${sysconfdir}/init.d/
    install -m 0755 ${WORKDIR}/modem-enable ${D}${sysconfdir}/init.d/modem-enable
}
