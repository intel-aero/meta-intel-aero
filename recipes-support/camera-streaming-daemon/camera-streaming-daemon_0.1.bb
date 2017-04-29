DESCRIPTION = "Camera Streaming Daemon is a software daemon to handle video streaming for drones in general"
DEPENDS = "avahi gstreamer1.0 gstreamer1.0-rtsp-server glib-2.0"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=93888867ace35ffec2c845ea90b2e16b"

SRCREV = "${AUTOREV}"
SRC_URI = "gitsm://git@github.com/01org/camera-streaming-daemon.git;protocol=https;branch=master"
SRC_URI += "file://csd.sh"
SRC_URI += "file://main.conf"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

do_install_append () {
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/csd.sh ${D}${sysconfdir}/init.d

	install -d ${D}${sysconfdir}/csd/
	install -m 0555 ${WORKDIR}/main.conf ${D}${sysconfdir}/csd/

	install -d ${D}${sysconfdir}/rc1.d
	ln -sf ../init.d/csd.sh ${D}${sysconfdir}/rc1.d/S72csd
	install -d ${D}${sysconfdir}/rc2.d
	ln -sf ../init.d/csd.sh ${D}${sysconfdir}/rc2.d/S72csd
	install -d ${D}${sysconfdir}/rc3.d
	ln -sf ../init.d/csd.sh ${D}${sysconfdir}/rc3.d/S72csd
	install -d ${D}${sysconfdir}/rc4.d
	ln -sf ../init.d/csd.sh ${D}${sysconfdir}/rc4.d/S72csd
	install -d ${D}${sysconfdir}/rc5.d
	ln -sf ../init.d/csd.sh ${D}${sysconfdir}/rc5.d/S72csd
}
