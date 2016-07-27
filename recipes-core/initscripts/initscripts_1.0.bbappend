FILESEXTRAPATHS_prepend := "${THISDIR}/initscripts:"

SRC_URI += "file://ds4.sh \
		"

do_install_append () {
	install -m 0755 ${WORKDIR}/ds4.sh ${D}${sysconfdir}/init.d

	ln -sf ../init.d/ds4.sh ${D}${sysconfdir}/rc1.d/S10ds4
	ln -sf ../init.d/ds4.sh ${D}${sysconfdir}/rc2.d/S10ds4
	ln -sf ../init.d/ds4.sh ${D}${sysconfdir}/rc3.d/S10ds4
	ln -sf ../init.d/ds4.sh ${D}${sysconfdir}/rc4.d/S10ds4
	ln -sf ../init.d/ds4.sh ${D}${sysconfdir}/rc5.d/S10ds4
}
