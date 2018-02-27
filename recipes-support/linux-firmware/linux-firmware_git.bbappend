SRC_URI += "file://iwlwifi-8000C-19.ucode"
PACKAGES =+ "${PN}-iwlwifi-8000c"
FILES_${PN}-iwlwifi-8000c   = "${nonarch_base_libdir}/firmware/iwlwifi-8000C-*.ucode"

do_install_append() {
	install -m 0644 ${WORKDIR}/iwlwifi-8000C-19.ucode ${D}/lib/firmware/
}
