FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI = "file://init-install-efi.sh"

do_install() {
	install -m 0755 ${WORKDIR}/init-install-efi.sh ${D}/install-efi.sh
}
