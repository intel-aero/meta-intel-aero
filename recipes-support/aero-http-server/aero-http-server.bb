FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
LICENSE = "GPLv2"
LICENSE_PATH = "${S}"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
inherit systemd

SRC_URI += "file://aero-http-server.sh \
            file://aero-http-server.service \
           " 
 

FILES_${PN} += "${bindir}/aero-http-server.sh"
               

do_install() {
        install -d ${D}${localstatedir}/www/hosted_files
        install -d ${D}${systemd_unitdir}/system
        install -d ${D}${bindir}
        install -m 0644 ${WORKDIR}/aero-http-server.service ${D}${systemd_unitdir}/system
        install -m 0755 ${WORKDIR}/aero-http-server.sh ${D}${bindir}
        

}

SYSTEMD_SERVICE_${PN} += "aero-http-server.service"
          
