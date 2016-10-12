SUMMARY = "Intel Aero Utils to control misc features"
LICENSE = "GPLv2"
LICENSE_PATH = "${S}"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

PR = "r0"

SRC_URI = "file://led_ctrl \
		file://fan_ctrl \
		"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}
    install led_ctrl ${D}${bindir}
    install fan_ctrl ${D}${bindir}
}
