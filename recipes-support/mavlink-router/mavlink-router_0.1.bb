DESCRIPTION = "Route Mavlink packets between endpoints"
DEPENDS = "python python-future"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=93888867ace35ffec2c845ea90b2e16b"

SRCREV = "7196d3d827ac3f1e4e53f45c5a6e8bf8998c4ec1"
SRC_URI = "gitsm://git@github.com/01org/mavlink-router.git;protocol=https;branch=master"
SRC_URI += "file://mavlink-routerd.sh"
SRC_URI += "file://main.conf"

S = "${WORKDIR}/git"

inherit autotools pythonnative pkgconfig systemd

PACKAGECONFIG ??= "${@bb.utils.filter('DISTRO_FEATURES', 'systemd', d)}"
PACKAGECONFIG[systemd] = "--enable-systemd --with-systemdsystemunitdir=${systemd_unitdir}/system/,--disable-systemd"

do_compile_prepend () {
    export PYTHONPATH="${PKG_CONFIG_SYSROOT_DIR}/usr/lib/python2.7/site-packages/"
}

do_install_append () {
    install -D -m 0755 ${WORKDIR}/mavlink-routerd.sh ${D}${sysconfdir}/init.d/mavlink-routerd.sh

    install -d ${D}${sysconfdir}/mavlink-router/config.d
    install -m 0644 ${WORKDIR}/main.conf ${D}${sysconfdir}/mavlink-router/main.conf

    install -d ${D}/var/lib/mavlink-router/
}

# we don't want to remove init script to retain backward compatibility since this is
# called from external scripts
python rm_sysvinit_initddir (){
}

SYSTEMD_SERVICE_${PN} = "mavlink-router.service"
