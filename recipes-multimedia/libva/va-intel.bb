DESCRIPTION = "Video Acceleration Add-ons for Intel BSPs"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

S = "${WORKDIR}"

PR = "r1"

def map_valibs(d):
    # The intel implementation requires the libva-intel-driver package
    if bb.utils.contains('MACHINE_FEATURES', 'va-impl-intel', "1", "0", d) == "1":
       return "libva libva-intel-driver"
    # All meta-intel video acceleration requires libva
    return "libva"

VA_IMPL = "${@map_valibs(d)}"

PACKAGES = "\
    va-intel \
    "

ALLOW_EMPTY_va-intel = "1"

RDEPENDS_va-intel = " \
    ${VA_IMPL} \
    "

COMPATIBLE_HOST = '(i.86|x86_64).*-linux*'
