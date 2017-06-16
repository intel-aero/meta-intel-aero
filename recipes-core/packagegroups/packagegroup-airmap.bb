SUMMARY = "AirMap tools"
LICENSE = "MIT"

inherit packagegroup

PR = "r0"

RDEPENDS_${PN} = "\
		python-cryptography \
		AirMapSDK-Embedded \
		python-paho-mqtt \
		python-requests \
		python-json \
		python-cryptography \
		python-pyyaml \
		python-dev \
		libgps \
		libgpsd \
		gpsd-conf \
		gpsd-gpsctl \
		gps-utils \
		protobuf \
		ntp \
		"
