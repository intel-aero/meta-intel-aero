FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PACKAGECONFIG_append = " networkd"

SRC_URI += "file://99-usb0.network"

do_install_append() {
    install -D -m 0644 ${WORKDIR}/99-usb0.network ${D}${sysconfdir}/systemd/network/99-usb0.network
}
