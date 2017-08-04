SUMMARY = "Cockpit plugins for Aero platform"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"
PR = "r1"

DEPENDS = "cockpit"

PV = "0.1+git${SRCPV}"
SRCREV = "15a693cbbc40b96fdf289dbf97078260de2bf215"
SRC_URI = "git://github.com/intel-aero/aero-cockpit-plugins"

S = "${WORKDIR}/git"

do_configure() {
}

do_compile() {
}

do_install() {
    cd ${S}
    make V=1 DEST=${D} install
}

FILES_${PN} = "${datadir}"
