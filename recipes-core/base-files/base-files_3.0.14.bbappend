FILESEXTRAPATHS_prepend := "${THISDIR}/base-files:"
SRC_URI += "file://media-sdcard.mount"
SRC_URI += "file://media-sdcard.automount"

# override default volatile to suppress var/log link creation
volatiles = "tmp"

do_install_append() {
	# enable mount of the SDCard in /media/sdcard when inserted
	install -d ${D}${systemd_unitdir}/system
	install -c -m 0644 ${WORKDIR}/media-sdcard.mount ${D}${systemd_unitdir}/system
	install -c -m 0644 ${WORKDIR}/media-sdcard.automount ${D}${systemd_unitdir}/system
	# Enable the service
	install -d ${D}${sysconfdir}/systemd/system/local-fs.target.wants
	ln -sf ${systemd_unitdir}/system/media-sdcard.automount \
		${D}${sysconfdir}/systemd/system/local-fs.target.wants/media-sdcard.automount
}

FILES_${PN} += "${base_libdir}/systemd/system/*.mount"
FILES_${PN} += "${base_libdir}/systemd/system/*.automount"
FILES_${PN} += "${sysconfdir}/systemd/system/local-fs.target.wants/*.automount"
