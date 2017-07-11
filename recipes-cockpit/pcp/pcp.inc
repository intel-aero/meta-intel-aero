SUMMARY = "System-level performance monitoring and performance management"
HOMEPAGE = "http://www.pcp.io"
SECTION =  "Applications/System"

LICENSE = "GPLv2+ & LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=37ab75b580d5aad4ada04260efa3702f \
                    "

SRC_URI = "git://github.com/performancecopilot/pcp;protocol=https"
SRC_URI += "file://0001-perl-Perl-support-is-now-optional-enabled-by-default.patch"
SRC_URI += "file://config.linux"


SRCREV = "a0f7d4413cf3b5dbd675441c45ddf52011de34f9"
S = "${WORKDIR}/git"

inherit pkgconfig autotools-brokensep

# Specify any options you want to pass to the configure script using EXTRA_OECONF:
CACHED_CONFIGUREVARS = "PACKAGE_DISTRIBUTION=arch"
EXTRA_OECONF = "--without-python --without-python3 --without-perl --without-qt --without-qt3d"
EXTRA_OEMAKE = ""

do_configure_prepend() {
    cp ${WORKDIR}/config.linux ${B}
    export SED=${TMPDIR}/hosttools/sed
}
