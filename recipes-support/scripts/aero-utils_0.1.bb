SUMMARY = "Intel Aero Utils to control misc features"
LICENSE = "GPLv2"
LICENSE_PATH = "${S}"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

RDEPENDS_${PN} += "bash"
RDEPENDS_${PN} += "python"

PR = "r1"

SRC_URI = "file://aero-led-ctrl \
                file://aero-fan-ctrl \
                file://aerofc-update.sh \
                file://px_uploader.py \
                file://aero-get-version.py \
                file://aero-reboot-update.py \
		"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${bindir}

    install aero-led-ctrl ${D}${bindir}
    ln -s aero-led-ctrl ${D}{bindir}/led_ctrl

    install aero-fan-ctrl ${D}${bindir}
    ln -s aero-fan-ctrl ${D}{bindir}/fan_ctrl

    install -d ${D}${sbindir}
    install -m 0755 ${WORKDIR}/aerofc-update.sh ${D}${sbindir}
    ln -s aerofc-update.sh ${D}${sbindir}/aerofc_update.sh

    install -m 0755 ${WORKDIR}/px_uploader.py ${D}${sbindir}

    install -m 0755 ${WORKDIR}/aero-get-version.py ${D}${sbindir}
    ln -s aero-get-version.py ${D}${sbindir}/get_aero_version.py

    install -m 0755 ${WORKDIR}/aero-reboot-update.py ${D}${sbindir}
}
