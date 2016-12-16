SUMMARY = "Add Airspace Data Access"

DESCRIPTION = "Description: The AirMap airspace management system facilitates \
	regulation conformity and notification compliance based on current location. \
	Additionally, AirMap provides weather data, flight telemetry endpoint, \
	and live alerts based on updated drone location"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d9bb3515869c0f426cb8441c899ae7f5"

RDEPENDS_${PN} += "python-json libgps libgpsd gpsd-conf gpsd-gpsctl gps-utils python-paho-mqtt"

SRC_URI = "file://requests-master.zip \
	file://AirMapSDK-Embedded.zip \
	"

S = "${WORKDIR}/requests-master"

inherit setuptools

do_install_append() {
	install -d ${D}/etc/airmap/
	cp -rf ${WORKDIR}/AirMapSDK-Embedded/ ${D}/etc/airmap/
	chmod a+x ${D}/etc/airmap/AirMapSDK-Embedded
}

FILES_${PN} += "/etc/airmap/*"
