FILESEXTRAPATHS_prepend := "${THISDIR}/initscripts:"

SRC_URI += "file://ds4.sh \
	    file://aero-wd.sh \
	    file://mavlink_bridge.sh \
	    file://mavlink_bridge.py \
	   "

do_install_append () {
	install -m 0755 ${WORKDIR}/ds4.sh ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/aero-wd.sh ${D}${sysconfdir}/init.d

	install -m 0755 ${WORKDIR}/mavlink_bridge.sh ${D}${sysconfdir}/init.d
	install -d ${D}${sbindir}
	install -m 0755 ${WORKDIR}/mavlink_bridge.py ${D}${sbindir}

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

	ln -sf ../init.d/mavlink_bridge.sh ${D}${sysconfdir}/rc1.d/S71mavlink_bridge
	ln -sf ../init.d/mavlink_bridge.sh ${D}${sysconfdir}/rc2.d/S71mavlink_bridge
	ln -sf ../init.d/mavlink_bridge.sh ${D}${sysconfdir}/rc3.d/S71mavlink_bridge
	ln -sf ../init.d/mavlink_bridge.sh ${D}${sysconfdir}/rc4.d/S71mavlink_bridge
	ln -sf ../init.d/mavlink_bridge.sh ${D}${sysconfdir}/rc5.d/S71mavlink_bridge
}
