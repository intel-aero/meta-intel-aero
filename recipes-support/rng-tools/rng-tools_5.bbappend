inherit systemd

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI += "file://rngd.service"

do_install_append() {
        install -d "${D}${sysconfdir}/systemd/system"
        install -m 0644 ${WORKDIR}/rngd.service ${D}${sysconfdir}/systemd/system/rngd.service
}

SYSTEMD_SERVICE_${PN} = "rngd.service"