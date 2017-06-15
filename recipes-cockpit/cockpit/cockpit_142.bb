SUMMARY = "A user interface for Linux servers"
DESCRIPTION = "Cockpit runs in a browser and can manage your network of GNU/Linux machines"

LICENSE = "LGPLv2.1 & GPLv2 & Apache-2.0 & MIT & Unknown"
LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c \
                    file://COPYING.node;md5=efd1f7702b8f545c82eda53a8d66370f"

SRC_URI = "https://github.com/cockpit-project/cockpit/releases/download/${PV}/cockpit-${PV}.tar.xz"
SRC_URI[md5sum] = "475d45abaa7944d4e09529423cb77acb"
SRC_URI[sha256sum] = "34728ce97836854de0c51c8244e95a4db695454a6326b72b0f1a033406399659"

inherit gettext pkgconfig autotools systemd

#S = "${WORKDIR}/git"

EXTRA_AUTORECONF = "-I tools"
EXTRA_OECONF = "--with-cockpit-user=root --with-cockpit-group=root --disable-doc"

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE_${PN} = "cockpit.service"

# Avoid warnings "file XXX is owned by uid 1001, which is the same as the user running bitbake. This may be due to host contamination"
INSANE_SKIP_${PN} += "host-user-contaminated"

FILES_${PN} += "${libdir}/firewalld \
                ${libdir}/security \
                ${datadir}/appdata \
                ${systemd_unitdir}/system/${PN}.socket \
                "

DEPENDS += "glib-2.0-native intltool-native"
DEPENDS += "systemd gettext gtk+ json-glib polkit krb5 libssh libpam pcp"





