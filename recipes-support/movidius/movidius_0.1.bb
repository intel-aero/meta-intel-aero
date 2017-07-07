SUMMARY = "Movidius udev rules"
LICENSE = "MIT"
LICENSE_PATH = "${S}"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3da9cfbcb788c80a0384361b4de20420"
PR = "r0"

SRC_URI = "file://LICENSE\
        file://97-movidius.rules \
        "

S = "${WORKDIR}"

do_install() {
    install -d ${D}${sysconfdir}/udev/rules.d
    install -m 0755 ${S}/97-movidius.rules ${D}${sysconfdir}/udev/rules.d
}
