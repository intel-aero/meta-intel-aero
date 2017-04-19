FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://p2p_supplicant.conf \
           "

do_configure_prepend () {
    sed -e 's/#CONFIG_P2P=y/CONFIG_P2P=y/' \
        -i ${WORKDIR}/defconfig
}

do_install_append() {
	install -m 600 ${WORKDIR}/p2p_supplicant.conf ${D}${sysconfdir}/p2p_supplicant.conf
}
