SUMMARY = "NetworkManager"
SECTION = "net/misc"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=cbbffd568227ada506640fe950a4823b"

DEPENDS = "libnl dbus dbus-glib udev wireless-tools nss util-linux ppp modemmanager libndp libsoup-2.4 libnewt bluez5"

inherit gnome gettext systemd

SRC_URI = " \
    ${GNOME_MIRROR}/NetworkManager/${@gnome_verdir("${PV}")}/NetworkManager-${PV}.tar.xz \
    file://0001-don-t-try-to-run-sbin-dhclient-to-get-the-version-nu.patch \
    file://0001-configure.ac-Check-only-for-libsystemd-not-libsystem.patch \
    file://0006-fix_missing_concheck.patch \
    file://0007-add-nm-dhcp-client-conf.patch \
"
#    0.9.8.10  
#	file://0005-event_msg_rcv_from_nlh.patch 
#"
#    file://0003-allow_mm_7.990.patch 
#    file://0004-revert_back_capabilities.patch 
 

SRC_URI[md5sum] = "96a5fb710b1f56e0c1c11fc43ddcf24b"
SRC_URI[sha256sum] = "e01e6acab673e82211e78a574d0950e992fe16ee98898e0fd4acb31984492688"


S = "${WORKDIR}/NetworkManager-${PV}"

#orig
#EXTRA_OECONF = " \
#    --enable-ifupdown \
#    --disable-ifcfg-rh \
#    --disable-ifnet \
#    --disable-ifcfg-suse \
#    --with-netconfig \
#    --with-crypto=nss \
#    --disable-more-warnings \
#    --with-dhclient=${base_sbindir}/dhclient \
#    --with-iptables=${sbindir}/iptables \
#    --with-tests \
#    --with-dnsmasq=${bindir}/dnsmasq \
#"

#from working 9.8.0
EXTRA_OECONF = " \
       --disable-static \
        --enable-shared \
        --disable-nls \
        --disable-rpath \
        --disable-qt \
        --disable-ppp \
        --disable-wimax \
        --disable-polkit \
        --enable-more-warnings \
        --disable-gtk-doc \
        --disable-vala \
        --without-docs \
        --with-gnu-ld \
        --with-session-tracking=none \
        --with-dhclient=${base_sbindir}/dhclient \
        --with-dhcpcd=no \
        --with-resolvconf=no \
        --with-iptables=${sbindir}/iptables \
        --enable-ifupdown \
        --with-modem-manager-1 \
        --disable-ifcfg-rh \
        --disable-ifcfg-suse \
        --disable-ifnet \
        --without-netconfig \
        --without-resolvconf \
        --enable-introspection=no \
"



PACKAGECONFIG ??= "${@base_contains('DISTRO_FEATURES','systemd','systemd','consolekit',d)}"
PACKAGECONFIG[systemd] = " \
    --with-systemdsystemunitdir=${systemd_unitdir}/system --with-session-tracking=systemd --enable-polkit, \
    --without-systemdsystemunitdir, \
    polkit \
"
# consolekit is not picked by shlibs, so add it to RDEPENDS too
PACKAGECONFIG[consolekit] = "--with-session-tracking=consolekit,,consolekit,consolekit"
PACKAGECONFIG[concheck] = "--enable-concheck,--disable-concheck,libsoup-2.4"

# Work around dbus permission problems since we lack a proper at_console
do_install_prepend() {
    sed -i 's:deny send_destination:allow send_destination:g' ${S}/src/org.freedesktop.NetworkManager.conf
    sed -i 's:deny send_destination:allow send_destination:g' ${S}/callouts/nm-dispatcher.conf
    sed -i 's:deny send_destination:allow send_destination:g' ${S}/callouts/nm-dhcp-client.conf
    sed -i 's:deny send_destination:allow send_destination:g' ${S}/callouts/nm-avahi-autoipd.conf
}

do_install_append () {
    install -d ${D}${sysconfdir}/dbus-1/event.d
    # Additional test binaries
    install -d ${D}${bindir}
    install -m 0755 ${B}/test/.libs/libnm* ${D}${bindir}

    # Install an empty VPN folder as nm-connection-editor will happily segfault without it :o.
    # With or without VPN support built in ;).
    install -d ${D}${sysconfdir}/NetworkManager/VPN

    rm -rf "${D}${localstatedir}/run"
    rmdir --ignore-fail-on-non-empty "${D}${localstatedir}"
}

PACKAGES =+ "libnmutil libnmglib libnmglib-vpn ${PN}-tests ${PN}-bash-completion"

FILES_libnmutil += "${libdir}/libnm-util.so.*"
FILES_libnmglib += "${libdir}/libnm-glib.so.*"
FILES_libnmglib-vpn += "${libdir}/libnm-glib-vpn.so.*"

FILES_${PN} += " \
    ${libexecdir} \
    ${libdir}/pppd/*/nm-pppd-plugin.so \
    ${libdir}/NetworkManager/*.so \
    ${datadir}/polkit-1 \
    ${datadir}/dbus-1 \
    ${base_libdir}/udev/* \
    ${systemd_unitdir}/system/NetworkManager-wait-online.service \
"

RRECOMMENDS_${PN} += "iptables dnsmasq"
RCONFLICTS_${PN} = "connman"
RDEPENDS_${PN} = " \
    wpa-supplicant \
    dhcp-client \
    ${@base_contains('COMBINED_FEATURES', '3gmodem', 'ppp', '', d)} \
"

FILES_${PN}-dbg += " \
    ${libdir}/NetworkManager/.debug/ \
    ${libdir}/pppd/*/.debug/ \
"

FILES_${PN}-dev += " \
    ${datadir}/NetworkManager/gdb-cmd \
    ${libdir}/pppd/*/*.la \
    ${libdir}/NetworkManager/*.la \
"

FILES_${PN}-tests = " \
    ${bindir}/nm-tool \
    ${bindir}/libnm-glib-test \
    ${bindir}/nm-online \
"

FILES_${PN}-bash-completion = "${datadir}/bash-completion"

SYSTEMD_SERVICE_${PN} = "NetworkManager.service"
