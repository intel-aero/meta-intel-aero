DESCRIPTION = "Extra nodes and plugins for <a href="http://wiki.rot.org/mavros">MAVROS</a>"

LICENSE = "BSD | GPLv3 | LGPLv3"
LIC_FILES_CHKSUM = "file://package.xml;beginline=12;endline=14;md5=9b511d4c606b1a23e454d3260818d003"

DEFAULT_PREFERENCE = "1"

MAVROS_RUN_AND_BUILD_DEPENDS = " \
     roscpp \
     tf2-ros \
     tf \
     geometry-msgs \
     mavros-msgs \
     sensor-msgs \
     std-msgs \
     visualization-msgs \
     urdf \
     image-transport \
     mavros \
 "

DEPENDS = "\
    cmake-modules \
    cv-bridge \
    ${MAVROS_RUN_AND_BUILD_DEPENDS} \
"

RDEPENDS_${PN} = "\
    ${MAVROS_RUN_AND_BUILD_DEPENDS} \ 
"

require mavros.inc

ROS_PKG_SUBDIR = "mavros_extras"
