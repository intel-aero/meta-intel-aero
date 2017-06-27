SUMMARY = "Collision avoidance test application."
HOMEPAGE = "https://github.com/01org/collision-avoidance-library"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=93888867ace35ffec2c845ea90b2e16b"

DEPENDS = "glm librealsense python-future libcoav"

SRCREV = "${AUTOREV}"
SRC_URI = "gitsm://git@github.com/01org/collision-avoidance-library.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

FILES_${PN} += "${systemd_system_unitdir}/coav-control.service"

inherit cmake pythonnative

EXTRA_OECMAKE = "-DWITH_TOOLS=ON -DWITH_REALSENSE=ON"

do_configure_prepend() {
    export PYTHONPATH="${PKG_CONFIG_SYSROOT_DIR}/usr/lib/python2.7/site-packages/"
}

do_install () {
    chrpath -d ${B}/tools/coav-control/coav-control
    install -d ${D}${bindir}/
    install -m 0755 ${B}/tools/coav-control/coav-control ${D}${bindir}/
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${B}/tools/coav-control/coav-control.service ${D}${systemd_system_unitdir}/
}
