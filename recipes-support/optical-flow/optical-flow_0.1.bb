DESCRIPTION = "Aero Optical flow"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=51a9bc5c11b77b05102ae28a479cf829"

DEPENDS = "opencv"
RDEPENDS_${PN} = "opencv"

SRCREV = "${AUTOREV}"
SRC_URI = "gitsm://git@github.com/intel-aero/aero-optical-flow.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

FILES_${PN} += "${systemd_unitdir}/aero-optical-flow.service"

inherit cmake systemd

do_install () {
    install -d ${D}${bindir}/
    install -m 0755 ${B}/aero-optical-flow ${D}${bindir}/

    install -d ${D}${systemd_unitdir}
    install -m 0644 ${B}/aero-optical-flow.service ${D}${systemd_unitdir}/
}
