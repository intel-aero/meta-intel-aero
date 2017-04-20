SECTION = "userland"
SUMMARY = "User space script to be run at boot in order to auto enable wpa_supplicant"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

SRC_URI = " \
           file://autostart-supplicant.sh \
"

S = "${WORKDIR}/autostart-supplicant-${PV}"

FILES_${PN} = "${sysconfdir}/init.d/autostart-supplicant.sh \
"

do_install() {
    install -d ${D}${sysconfdir}/init.d/
    install -m 0755 ${WORKDIR}/autostart-supplicant.sh ${D}${sysconfdir}/init.d/
}

