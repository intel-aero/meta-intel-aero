FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://main.conf"
SRC_URI += "file://firstboot-connman.service"
SRC_URI += "file://firstboot-connman-setup"

# temporary patch to avoid using first IP addresses when tethering
SRC_URI += "file://0001-ippool-don-t-use-first-8-blocks-of-16-bit-addressing.patch"

SYSTEMD_SERVICE_${PN} += "firstboot-connman.service"

do_install_append() {
    install -D -m 0644 ${WORKDIR}/main.conf ${D}${sysconfdir}/connman/main.conf

    install -D -m 0755 ${WORKDIR}/firstboot-connman-setup ${D}/usr/sbin/firstboot-connman-setup
    install -D -m 0644 ${WORKDIR}/firstboot-connman.service ${D}${systemd_unitdir}/system
}
