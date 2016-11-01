SUMMARY = "PX4 firmware binary"
LICENSE = "BSD-3-Clause"
LICENSE_PATH = "${S}"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=868b4eea242622c0f210eb9a2c13f6d5"
PR = "r0"

SRC_URI = "file://LICENSE.md\
		file://nuttx-aerofc-v1-default.px4 \
		"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${sysconfdir}/px4-fw
    install -m 0755 ${S}/nuttx-aerofc-v1-default.px4 ${D}${sysconfdir}/px4-fw
    install -m 0644 ${S}/LICENSE.md ${D}${sysconfdir}/px4-fw
}
