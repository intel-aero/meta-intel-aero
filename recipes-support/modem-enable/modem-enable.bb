SUMMARY = "Utilities to power on/off and reset the modem"
SECTION = "modem"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

SRC_URI = " \
    file://modem_off \
    file://modem_on \
    file://modem_reset \ 
	file://modem-enable \
"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${sbindir}
    install -m 0755 modem_off ${D}${sbindir}
    install -m 0755 modem_on ${D}${sbindir}
    install -m 0755 modem_reset ${D}${sbindir}
    install -d ${D}${sysconfdir}/init.d/
    install -d ${D}${sysconfdir}/rc0.d/
    install -d ${D}${sysconfdir}/rc1.d/
    install -d ${D}${sysconfdir}/rc2.d/
    install -d ${D}${sysconfdir}/rc3.d/
    install -d ${D}${sysconfdir}/rc4.d/
    install -d ${D}${sysconfdir}/rc5.d/
    install -d ${D}${sysconfdir}/rc6.d/
    install -m 0755 ${WORKDIR}/modem-enable ${D}${sysconfdir}/init.d/
    ln -sf ../init.d/modem-enable      ${D}${sysconfdir}/rc0.d/K20modem-enable
    ln -sf ../init.d/modem-enable      ${D}${sysconfdir}/rc1.d/K20modem-enable
    ln -sf ../init.d/modem-enable      ${D}${sysconfdir}/rc2.d/S20modem-enable
    ln -sf ../init.d/modem-enable      ${D}${sysconfdir}/rc3.d/S20modem-enable
    ln -sf ../init.d/modem-enable      ${D}${sysconfdir}/rc4.d/S20modem-enable
    ln -sf ../init.d/modem-enable      ${D}${sysconfdir}/rc5.d/S20modem-enable
    ln -sf ../init.d/modem-enable      ${D}${sysconfdir}/rc6.d/k20modem-enable
    
}
