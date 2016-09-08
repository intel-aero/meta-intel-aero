SUMMARY = "Watchdog daemon application for Aero platform"
LICENSE = "GPLv2"
LICENSE_PATH = "${S}"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

SRC_URI = "file://aero-watchdog.c"

S = "${WORKDIR}"

do_compile() {
    ${CC} ${CFLAGS} ${LDFLAGS} aero-watchdog.c -o aero-watchdog
}

do_install() {
    install -d ${D}${bindir}/
    install -m 0750 ${WORKDIR}/aero-watchdog ${D}${bindir}/
}
