DESCRIPTION = "Camera Streaming Daemon is a software daemon to handle video streaming for drones in general"
DEPENDS = "avahi gstreamer1.0 gstreamer1.0-rtsp-server glib-2.0 python python-future librealsense"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=93888867ace35ffec2c845ea90b2e16b"

SRCREV = "aa675bc0877e5f2331ef301d91eb05b8edaf8223"
SRC_URI = "gitsm://git@github.com/01org/camera-streaming-daemon.git;protocol=https;branch=master"
SRC_URI += "file://csd"
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
	install -m 0755 ${WORKDIR}/csd ${D}${sysconfdir}/init.d
    fi

    install -d ${D}${sysconfdir}/csd/
    install -m 0644 ${WORKDIR}/main.conf ${D}${sysconfdir}/csd/
}

SYSTEMD_SERVICE_${PN} = "csd.service"
INITSCRIPT_NAME = "csd"
INITSCRIPT_PARAMS = "defaults 72"
