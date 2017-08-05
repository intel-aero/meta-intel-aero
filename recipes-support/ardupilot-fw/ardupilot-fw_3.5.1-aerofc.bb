SUMMARY = "Ardupilot firmware binary"
LICENSE = "GPL-3.0"
LICENSE_PATH = "${S}"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=119ebfbc4f71a5bac2dee9acd46a18fd"
PR = "r0"

SRC_URI = "file://LICENSE.md\
		file://arducopter-aerofc-v1.px4 \
		"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${sysconfdir}/aerofc
    install -d ${D}${sysconfdir}/aerofc/ardupilot
    install -m 0755 ${S}/arducopter-aerofc-v1.px4 ${D}${sysconfdir}/aerofc/ardupilot
    install -m 0644 ${S}/LICENSE.md ${D}${sysconfdir}/aerofc/ardupilot
}
