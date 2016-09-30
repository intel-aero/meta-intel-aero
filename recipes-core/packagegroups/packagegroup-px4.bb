SUMMARY = "PX4 related packages"
DESCRIPTION = "This packagegroup lists dependencies and packages needed \
		for PX4 related functionality"
LICENSE = "MIT"

inherit packagegroup

PR = "r0"

RDEPENDS_${PN} = "\
		python-pyserial \
		python-argparse \
		python-json \
		pymavlink \
		"
