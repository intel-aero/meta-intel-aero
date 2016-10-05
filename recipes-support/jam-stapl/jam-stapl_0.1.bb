SUMMARY = "JAM STAPL binary (32-bit, static) for Aero platform"
LICENSE = "Proprietary"
LICENSE_PATH = "${S}"
LIC_FILES_CHKSUM = "file://LICENSE;md5=6b0f12176240274ef13991165033c662"
PR = "r0"

# Relax QA checks for binary form, jam binary is compiled statically,
# the code is limited to function on 32-bit only, until it is fixed
# we need to silence QA for arch check.
INSANE_SKIP_${PN} += "arch"
INSANE_SKIP_${PN}-dbg += "arch"

SRC_URI = "file://jam \
		file://LICENSE\
		file://aerofc_fpga.jam \
		"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install jam ${D}${bindir}
    install -d ${D}${sysconfdir}
    install ${S}/aerofc_fpga.jam ${D}${sysconfdir}
}
