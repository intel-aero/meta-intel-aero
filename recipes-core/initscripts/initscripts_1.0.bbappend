FILESEXTRAPATHS_prepend := "${THISDIR}/initscripts:"

SRC_URI += "file://ds4.sh \
	    file://aero-wd.sh \
	    file://init_start.sh \
	    file://init_complete.sh \
	    file://build_version.sh \
	   "

do_install_append () {
	install -m 0755 ${WORKDIR}/ds4.sh ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/aero-wd.sh ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/init_start.sh ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/init_complete.sh ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/build_version.sh ${D}${sysconfdir}/init.d

	ln -sf ../init.d/init_start.sh ${D}${sysconfdir}/rc1.d/S00init_start
	ln -sf ../init.d/init_start.sh ${D}${sysconfdir}/rc2.d/S00init_start
	ln -sf ../init.d/init_start.sh ${D}${sysconfdir}/rc3.d/S00init_start
	ln -sf ../init.d/init_start.sh ${D}${sysconfdir}/rc4.d/S00init_start
	ln -sf ../init.d/init_start.sh ${D}${sysconfdir}/rc5.d/S00init_start

	ln -sf ../init.d/ds4.sh ${D}${sysconfdir}/rc1.d/S10ds4
	ln -sf ../init.d/ds4.sh ${D}${sysconfdir}/rc2.d/S10ds4
	ln -sf ../init.d/ds4.sh ${D}${sysconfdir}/rc3.d/S10ds4
	ln -sf ../init.d/ds4.sh ${D}${sysconfdir}/rc4.d/S10ds4
	ln -sf ../init.d/ds4.sh ${D}${sysconfdir}/rc5.d/S10ds4

	ln -sf ../init.d/aero-wd.sh ${D}${sysconfdir}/rc1.d/S10aero-wd
	ln -sf ../init.d/aero-wd.sh ${D}${sysconfdir}/rc2.d/S10aero-wd
	ln -sf ../init.d/aero-wd.sh ${D}${sysconfdir}/rc3.d/S10aero-wd
	ln -sf ../init.d/aero-wd.sh ${D}${sysconfdir}/rc4.d/S10aero-wd
	ln -sf ../init.d/aero-wd.sh ${D}${sysconfdir}/rc5.d/S10aero-wd

	ln -sf ../init.d/build_version.sh ${D}${sysconfdir}/rc1.d/S90build_version
	ln -sf ../init.d/build_version.sh ${D}${sysconfdir}/rc2.d/S90build_version
	ln -sf ../init.d/build_version.sh ${D}${sysconfdir}/rc3.d/S90build_version
	ln -sf ../init.d/build_version.sh ${D}${sysconfdir}/rc4.d/S90build_version
	ln -sf ../init.d/build_version.sh ${D}${sysconfdir}/rc5.d/S90build_version

	ln -sf ../init.d/init_complete.sh ${D}${sysconfdir}/rc1.d/S99init_complete
	ln -sf ../init.d/init_complete.sh ${D}${sysconfdir}/rc2.d/S99init_complete
	ln -sf ../init.d/init_complete.sh ${D}${sysconfdir}/rc3.d/S99init_complete
	ln -sf ../init.d/init_complete.sh ${D}${sysconfdir}/rc4.d/S99init_complete
	ln -sf ../init.d/init_complete.sh ${D}${sysconfdir}/rc5.d/S99init_complete
}
