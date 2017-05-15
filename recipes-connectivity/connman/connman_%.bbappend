FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://main.conf"
SRC_URI += "file://firstboot-connman.service"
SRC_URI += "file://firstboot-connman-setup"

SYSTEMD_SERVICE_${PN} += "firstboot-connman.service"

do_install_append() {
    install -D -m 0644 ${WORKDIR}/main.conf ${D}${sysconfdir}/connman/main.conf

    install -D -m 0755 ${WORKDIR}/firstboot-connman-setup ${D}/usr/sbin/firstboot-connman-setup
    install -D -m 0644 ${WORKDIR}/firstboot-connman.service ${D}${systemd_unitdir}/system
}
