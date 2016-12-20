DESCRIPTION = "Route Mavlink packets between endpoints"
DEPENDS = "python python-pip python-future"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=93888867ace35ffec2c845ea90b2e16b"

SRCREV = "${AUTOREV}"
SRC_URI = "gitsm://git@github.com/01org/mavlink-router.git;protocol=https;branch=master"
SRC_URI += "file://mavlink-routerd.sh"
# Hack to fix build, python should also look for python module in build/tmp/sysroots/intel-aero/usr/lib/python2.7
SRC_URI += "file://0001-Fix-Yocto-build.patch"

S = "${WORKDIR}/git"

inherit autotools pythonnative

do_install_append () {
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/mavlink-routerd.sh ${D}${sysconfdir}/init.d

	install -d ${D}${sysconfdir}/rc1.d
	ln -sf ../init.d/mavlink-routerd.sh ${D}${sysconfdir}/rc1.d/S71mavlink-routerd
	install -d ${D}${sysconfdir}/rc2.d
	ln -sf ../init.d/mavlink-routerd.sh ${D}${sysconfdir}/rc2.d/S71mavlink-routerd
	install -d ${D}${sysconfdir}/rc3.d
	ln -sf ../init.d/mavlink-routerd.sh ${D}${sysconfdir}/rc3.d/S71mavlink-routerd
	install -d ${D}${sysconfdir}/rc4.d
	ln -sf ../init.d/mavlink-routerd.sh ${D}${sysconfdir}/rc4.d/S71mavlink-routerd
	install -d ${D}${sysconfdir}/rc5.d
	ln -sf ../init.d/mavlink-routerd.sh ${D}${sysconfdir}/rc5.d/S71mavlink-routerd
}
