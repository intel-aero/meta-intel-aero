SUMMARY = "Admin interface for Linux machines"
DESCRIPTION = "Cockpit makes it easy to administer your GNU/Linux servers via a web browser"

LICENSE = "LGPLv2.1 & GPLv2 & Apache-2.0 & MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c"

SRC_URI  = "file://cockpit.pam"
SRC_URI += "https://github.com/cockpit-project/cockpit/releases/download/${PV}/cockpit-${PV}.tar.xz"
SRC_URI[md5sum] = "af79651d3aea2e6140bb313961d7fad0"
SRC_URI[sha256sum] = "87c67a61ab1e2cb9105a776ed89a18feb92675eb60ddf083b6c8ecf960b7546e"

inherit gettext pkgconfig autotools systemd distro_features_check

REQUIRED_DISTRO_FEATURES = "pam"

EXTRA_AUTORECONF = "-I tools"
EXTRA_OECONF = "--with-cockpit-user=root \
                --with-cockpit-group=root \
                --with-branding=default \
                --disable-ssh \
                --disable-doc \
               "

PACKAGECONFIG ?= ""
PACKAGECONFIG[pcp] = "--enable-pcp,--disable-pcp,pcp"

SYSTEMD_SERVICE_${PN} = "cockpit.socket"

# Avoid warnings "file XXX is owned by uid 1001, which is the same as the user running bitbake. This may be due to host contamination"
INSANE_SKIP_${PN} += "host-user-contaminated"

PACKAGES =+ "${PN}-bridge ${PN}-pcp ${PN}-docker ${PN}-ws ${PN}-system ${PN}-dashboard"

FILES_${PN} += "${libdir}/firewalld \
                ${libdir}/security \
                ${datadir}/appdata \
                ${systemd_unitdir}/system/${PN}.socket \
                "

FILES_${PN}-pcp =+ "${libexecdir}/cockpit-pcp \
                    ${datadir}/cockpit/pcp \
                    ${localstatedir}/lib/pcp/config/pmlogconf/tools/cockpit \
                    "

FILES_${PN}-bridge =+ "${bindir}/cockpit-bridge \
                       ${datadir}/cockpit/base1 \
                       ${libexec}/cockpit-askpass \
                       "
FILES_${PN}-docker =+ "${datadir}/cockpit/docker "

FILES_${PN}-ws =+ "${libexecdir}/cockpit-session \
                   ${libexecdir}/cockpit-ws \
                   ${sbindir}/remotectl \
                   ${datadir}/cockpit/branding \
                   ${datadir}/cockpit/static \
                   "
FILES_${PN}-system =+ "${datadir}/cockpit/systemd \
                       ${datadir}/cockpit/users \
                       ${datadir}/cockpit/shell \
                       "

FILES_${PN}-dashboard =+ "${datadir}/cockpit/dashboard \
                          ${datadir}/cockpit/ssh \
                          ${libexecdir}/cockpit-ssh \
                          "

DEPENDS += "glib-2.0-native intltool-native"
DEPENDS += "systemd gettext gtk+ json-glib polkit krb5 libpam"

do_install_append() {
    pkgdatadir=${datadir}/cockpit

    # fix up install location of these files
    cp -al ${D}${pkgdatadir}/dist/* ${D}/${pkgdatadir}
    rm -rf ${D}${pkgdatadir}/dist

    # remove unwanted artifacts
    rm -rf ${D}${pkgdatadir}/branding/{centos,debian,fedora,kubernetes,registry,rhel,ubuntu}

    rm -rf ${D}${pkgdatadir}/kdump
    rm -rf ${D}${pkgdatadir}/kubernetes
    rm -rf ${D}${pkgdatadir}/machines
    rm -rf ${D}${pkgdatadir}/networkmanager
    rm -rf ${D}${pkgdatadir}/ostree
    rm -rf ${D}${pkgdatadir}/packagekit
    rm -rf ${D}${pkgdatadir}/playground
    rm -rf ${D}${pkgdatadir}/realmd
    rm -rf ${D}${pkgdatadir}/selinux
    rm -rf ${D}${pkgdatadir}/sosreport
    rm -rf ${D}${pkgdatadir}/ssh
    rm -rf ${D}${pkgdatadir}/storaged
    rm -rf ${D}${pkgdatadir}/subscriptions
    rm -rf ${D}${pkgdatadir}/tuned

    chmod 4750 ${D}${libexecdir}/cockpit-session

    install -d "${D}${sysconfdir}/pam.d"
    install -p -m 644 ${WORKDIR}/cockpit.pam ${D}${sysconfdir}/pam.d/cockpit
}
