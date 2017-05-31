SUMMARY = "This is a python implementation of the MAVLink protocol. "
HOMEPAGE = "http://qgroundcontrol.org/mavlink/"

LICENSE = "LGPLv3"
LIC_FILES_CHKSUM = "file://README.txt;md5=a350cb83bd19d7a1d9a3096d58cd5220"

SRC_URI = "https://pypi.python.org/packages/bd/8b/0c139436eae7dd2ed5a1feeee9dd384da4e432e9971485eca8fc316ddf86/${PN}-${PV}.zip"
SRC_URI[md5sum] = "5c96a9d4ff5bf8f6e5d4ab9b0533fc70"

S = "${WORKDIR}/${PN}-${PV}"

inherit setuptools

