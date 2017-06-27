SUMMARY = "Add Airspace Data Access"

DESCRIPTION = "The AirMap airspace management system facilitates \
	regulation conformity and notification compliance based on current location. \
	Additionally, AirMap provides weather data, flight telemetry endpoint, \
	and live alerts based on updated drone location"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM="file://License.txt;md5=d515a6d920f942dc31ac8432e8ce4b71"

RDEPENDS_${PN} += "python-requests python-json libgps libgpsd gpsd-conf gpsd-gpsctl gps-utils python-paho-mqtt python-cryptography protobuf ntp python-pyyaml python-dev"

SRC_URI = "https://github.com/airmap/AirMapSDK-Embedded/archive/v${PV}.tar.gz"
SRC_URI[md5sum] = "f9759f1cd9157d9150c20a981f818ef1"

do_install() {
	install -d ${D}/etc/airmap/
	cp -rf ${WORKDIR}/${PN}-${PV}/ ${D}/etc/airmap/${PN}
	chmod a+x ${D}/etc/airmap/${PN}
}

FILES_${PN} += "/etc/airmap/*"
