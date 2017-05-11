DESCRIPTION = "Route Mavlink packets between endpoints"
DEPENDS = "python python-future"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=93888867ace35ffec2c845ea90b2e16b"

SRCREV = "2f62ec7bfe3d220240b42b2a3d920d3fd684a53b"
SRC_URI = "gitsm://git@github.com/01org/mavlink-router.git;protocol=https;branch=master"
SRC_URI += "file://mavlink-routerd"
SRC_URI += "file://main.conf"

S = "${WORKDIR}/git"

inherit autotools pythonnative pkgconfig update-rc.d systemd

PACKAGECONFIG ??= "${@bb.utils.filter('DISTRO_FEATURES', 'systemd', d)}"
PACKAGECONFIG[systemd] = "--enable-systemd --with-systemdsystemunitdir=${systemd_unitdir}/system/,--disable-systemd"

do_compile_prepend () {
    export PYTHONPATH="${PKG_CONFIG_SYSROOT_DIR}/usr/lib/python2.7/site-packages/"
}

do_install_append () {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'sysvinit', 'true', 'false', d)}; then
        install -d ${D}${sysconfdir}/init.d
        install -m 0755 ${WORKDIR}/mavlink-routerd ${D}${sysconfdir}/init.d
        ln -s ./mavlink-routerd ${D}${sysconfdir}/init.d/mavlink-routerd.sh
    fi

    install -d ${D}${sysconfdir}/mavlink-router/config.d
    install -m 0644 ${WORKDIR}/main.conf ${D}${sysconfdir}/mavlink-router/main.conf

    install -d ${D}/var/lib/mavlink-router/
}

SYSTEMD_SERVICE_${PN} = "mavlink-router.service"
INITSCRIPT_NAME = "mavlink-routerd"
INITSCRIPT_PARAMS = "defaults 71"
