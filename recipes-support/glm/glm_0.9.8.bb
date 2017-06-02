# glm.bb
SUMMARY = "OpenGL Mathematics (GLM)."
DESCRIPTION = "C++ mathematics library for graphics software based on the GLSL spec."
HOMEPAGE = "https://github.com/g-truc/glm"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://copying.txt;md5=4431606d144252143c9c3df384a74cad"

SRCREV = "${AUTOREV}"
SRC_URI = "gitsm://git@github.com/g-truc/glm.git;protocol=https;branch=0.9.8"

S = "${WORKDIR}/git"

inherit cmake

FILES_${PN}-dev += "${libdir}/cmake"
