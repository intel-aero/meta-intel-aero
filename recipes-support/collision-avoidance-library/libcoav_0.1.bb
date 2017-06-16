SUMMARY = "Intel Colision Avoidance Library"
AUTHOR = "Intel Corporation"
SECTION = "libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=93888867ace35ffec2c845ea90b2e16b"

DEPENDS = "glm python-future librealsense"

SRCREV = "${AUTOREV}"
SRC_URI = "gitsm://github.com/01org/collision-avoidance-library.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

inherit cmake pythonnative

EXTRA_OECMAKE = "-DWITH_REALSENSE=ON -DWITH_GAZEBO=OFF -DWITH_TOOLS=OFF -DWITH_VDEBUG=OFF"

PACKAGES = "${PN} ${PN}-dev ${PN}-dbg"

FILES_${PN} += "\
    ${libdir}/*.so.* \
"

FILES_${PN}-dev += "\
    ${libdir}/*.so \
    ${includedir}/${PN} \
"

FILES_${PN}-dbg += "${bindir}/.debug"

# avoid packaging error
INSANE_SKIP_${PN} = "ldflags"

do_configure_prepend() {
    export PYTHONPATH="${PKG_CONFIG_SYSROOT_DIR}/usr/lib/python2.7/site-packages/"
}
