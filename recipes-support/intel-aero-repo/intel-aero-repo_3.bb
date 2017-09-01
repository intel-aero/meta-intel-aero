SUMMARY = "Intel Aero package repository"
LICENSE = "MIT"
LICENSE_PATH = "${S}"
SRC_URI = "file://intel-aero.repo \
	   file://LICENSE \
          "

LIC_FILES_CHKSUM = "file://LICENSE;md5=3da9cfbcb788c80a0384361b4de20420"

S = "${WORKDIR}"
PR = "r1"

do_install() {
        install -d ${D}${sysconfdir}/yum.repos.d
        install -m 0644 ${WORKDIR}/intel-aero.repo ${D}${sysconfdir}/yum.repos.d/
}
