SUMMARY = "ModemManager is a daemon controlling broadband devices/connections"
DESCRIPTION = "ModemManager is a DBus-activated daemon which controls mobile broadband (2G/3G/4G) devices and connections"
HOMEPAGE = "http://www.freedesktop.org/wiki/Software/ModemManager/"
LICENSE = "GPLv2 & LGPLv2.1"
LIC_FILES_CHKSUM = " \
    file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
    file://COPYING.LIB;md5=4fbd65380cdd255951079008b364516c \
"

inherit autotools gettext gtk-doc systemd

DEPENDS = "glib-2.0 libmbim udev dbus-glib"

SRC_URI = "http://www.freedesktop.org/software/ModemManager/ModemManager-${PV}.tar.xz"
SRC_URI[md5sum] = "03242732a8dbaccd52100b7e7bb1998e"
SRC_URI[sha256sum] = "d951eaffbe513bcde54f90602c6c423a49593f656d6485ba95f6fcb4bc1e8003"

S = "${WORKDIR}/ModemManager-${PV}"

CFLAGS_append += "-O0"

EXTRA_OECONF = " \
        --disable-static \
        --enable-shared \
        --disable-nls \
        --disable-rpath \
        --enable-more-warnings \
        --disable-gtk-doc \
        --with-gnu-ld \
        --without-qmi \
        --with-polkit=none \
        --enable-introspection=no \
"
FILES_${PN} += " \
    ${datadir}/icons \
    ${libdir}/ModemManager \
    ${systemd_unitdir}/system \
    ${datadir}/dbus-1 \
"

FILES_${PN}-dev += " \
    ${libdir}/ModemManager/*.la \
"

FILES_${PN}-staticdev += " \
    ${libdir}/ModemManager/*.a \
"

FILES_${PN}-dbg += "${libdir}/ModemManager/.debug"

SYSTEMD_SERVICE_${PN} = "ModemManager.service"
# no need to start on boot - dbus will start on demand
SYSTEMD_AUTO_ENABLE = "disable"
