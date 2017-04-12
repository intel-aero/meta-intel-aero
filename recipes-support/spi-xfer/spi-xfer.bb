DESCRIPTION = "spidev device access"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

SRC_URI = "git://github.com/intel-aero/sample-apps.git;protocol=https"
SRCREV = "b655ddce86e11d21af4bc556e23854e1eb4dfc8b"
S = "${WORKDIR}/git"

SRC_FILE = "${WORKDIR}/git/spidev-app/spi_xfer.c"

do_compile() {
    ${CC} spidev-app/spi_xfer.c -o spi_xfer
}

do_install() {
    install -d ${D}${bindir}/
    install spi_xfer ${D}${bindir}/
}
