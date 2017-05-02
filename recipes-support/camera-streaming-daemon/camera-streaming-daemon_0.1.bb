DESCRIPTION = "Camera Streaming Daemon is a software daemon to handle video streaming for drones in general"
DEPENDS = "avahi gstreamer1.0 gstreamer1.0-rtsp-server glib-2.0"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=93888867ace35ffec2c845ea90b2e16b"

SRCREV = "d2451ab9b52e4e035b387fe18ab864b7ae66e938"
SRC_URI = "gitsm://git@github.com/01org/camera-streaming-daemon.git;protocol=https;branch=master"
SRC_URI += "file://csd"
SRC_URI += "file://main.conf"

S = "${WORKDIR}/git"

inherit autotools pkgconfig update-rc.d

INITSCRIPT_PARAMS = "start 72 2 3 4 5 . stop 72 0 1 6 ."
INITSCRIPT_NAME = "csd"

do_install_append () {
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/csd ${D}${sysconfdir}/init.d

	install -d ${D}${sysconfdir}/csd/
	install -m 0644 ${WORKDIR}/main.conf ${D}${sysconfdir}/csd/
}
