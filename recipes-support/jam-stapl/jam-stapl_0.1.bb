SUMMARY = "JAM STAPL binary (32-bit, static) for Aero platform"
LICENSE = "Proprietary"
LICENSE_PATH = "${S}"
LIC_FILES_CHKSUM = "file://LICENSE;md5=6b0f12176240274ef13991165033c662"
PR = "r1"

SRC_URI = "file://jam \
		file://LICENSE\
		file://aero-compute-board.jam \
		file://aero-rtf.jam \
		file://aero-rtf-recovery.jam \
		"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install jam ${D}${bindir}

    install -d ${D}${sysconfdir}/fpga/
    install ${S}/aero-compute-board.jam ${D}${sysconfdir}/fpga
    install ${S}/aero-rtf.jam ${D}${sysconfdir}/fpga
    install ${S}/aero-rtf-recovery.jam ${D}${sysconfdir}/fpga
}
