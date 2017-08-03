FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://firstboot-networkmanager.service"
SRC_URI += "file://firstboot-networkmanager-setup"
SRC_URI += "file://NetworkManager.conf"

PACKAGECONFIG += "modemmanager"
PACKAGECONFIG += "ppp"
PACKAGECONFIG += "wifi"
PACKAGECONFIG += "systemd"

DEPENDS += "nss"

SYSTEMD_SERVICE_${PN} += "firstboot-networkmanager.service"

do_install_append() {
    install -D -m 0755 ${WORKDIR}/firstboot-networkmanager-setup ${D}/usr/sbin
    install -D -m 0644 ${WORKDIR}/firstboot-networkmanager.service ${D}${systemd_unitdir}/system
    install -D -m 0644 ${WORKDIR}/NetworkManager.conf ${D}${sysconfdir}/NetworkManager
}