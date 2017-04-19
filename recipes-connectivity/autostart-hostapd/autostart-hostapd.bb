SECTION = "userland"
SUMMARY = "User space script to be run at boot in order to auto enable hostapd"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

SRC_URI = " \
           file://autostart-hostapd.sh \
"

S = "${WORKDIR}/autostart-hostapd-${PV}"

FILES_${PN} = "${sysconfdir}/init.d/autostart-hostapd.sh \
               ${sysconfdir}/rc0.d/K20autostart-hostapd.sh \
               ${sysconfdir}/rc1.d/K20autostart-hostapd.sh \
               ${sysconfdir}/rc2.d/S20autostart-hostapd.sh \
               ${sysconfdir}/rc3.d/S20autostart-hostapd.sh \
               ${sysconfdir}/rc4.d/S20autostart-hostapd.sh \
               ${sysconfdir}/rc5.d/S20autostart-hostapd.sh \
               ${sysconfdir}/rc6.d/k20autostart-hostapd.sh \
"

do_install() {
    install -d ${D}${sysconfdir}/init.d/
    install -d ${D}${sysconfdir}/rc0.d/
    install -d ${D}${sysconfdir}/rc1.d/
    install -d ${D}${sysconfdir}/rc2.d/
    install -d ${D}${sysconfdir}/rc3.d/
    install -d ${D}${sysconfdir}/rc4.d/
    install -d ${D}${sysconfdir}/rc5.d/
    install -d ${D}${sysconfdir}/rc6.d/
    install -m 0755 ${WORKDIR}/autostart-hostapd.sh ${D}${sysconfdir}/init.d/
    ln -sf ../init.d/autostart-hostapd.sh      ${D}${sysconfdir}/rc0.d/K20autostart-hostapd.sh
    ln -sf ../init.d/autostart-hostapd.sh      ${D}${sysconfdir}/rc1.d/K20autostart-hostapd.sh
    ln -sf ../init.d/autostart-hostapd.sh      ${D}${sysconfdir}/rc2.d/S20autostart-hostapd.sh
    ln -sf ../init.d/autostart-hostapd.sh      ${D}${sysconfdir}/rc3.d/S20autostart-hostapd.sh
    ln -sf ../init.d/autostart-hostapd.sh      ${D}${sysconfdir}/rc4.d/S20autostart-hostapd.sh
    ln -sf ../init.d/autostart-hostapd.sh      ${D}${sysconfdir}/rc5.d/S20autostart-hostapd.sh
    ln -sf ../init.d/autostart-hostapd.sh      ${D}${sysconfdir}/rc6.d/k20autostart-hostapd.sh
}

