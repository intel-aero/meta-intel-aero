FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
LICENSE = "GPLv2"
LICENSE_PATH = "${S}"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
inherit systemd 

SRC_URI += "file://aero-http-server.py \
            file://aero-http-server.service \
            file://aero-http-server.socket \
            file://camera-def-rs-rgb.xml \
           "

FILES_${PN} += "${bindir}/aero-http-server.py \
                ${systemd_unitdir}/system/aero-http-server.socket \
               "
SYSTEMD_AUTO_ENABLE="enable"

do_install() {
        install -d ${D}${localstatedir}/http
        install -d ${D}${systemd_unitdir}/system
        install -d ${D}${bindir}
        install -m 0644 ${WORKDIR}/aero-http-server.service ${D}${systemd_unitdir}/system
        install -m 0644 ${WORKDIR}/aero-http-server.socket ${D}${systemd_unitdir}/system
        install -m 0755 ${WORKDIR}/aero-http-server.py ${D}${bindir}
        install -m 0644 ${WORKDIR}/camera-def-rs-rgb.xml ${D}${localstatedir}/http
}

SYSTEMD_SERVICE_${PN} += "aero-http-server.service"
          
