FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://main.conf"

do_install_append() {
    install -D -m 0644 ${WORKDIR}/main.conf ${D}${sysconfdir}/connman/main.conf
}
