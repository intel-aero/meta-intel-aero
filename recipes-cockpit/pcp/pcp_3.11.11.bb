require pcp.inc
#inherit perlnative
#inherit python3native

# NOTE: the following prog dependencies are unknown, ignoring: gtar gzip pkgmk xmlto lzma qshape md5sum pod2man publican git makedepend qmake-qt4 xconfirm true gmake xz dblatex hdiutil rpm bzip2 which mkinstallp dtrace seinfo qmake-qt5 gawk dlltool rpmbuild dpkg makepkg qmake echo
# NOTE: unable to map the following pkg-config dependencies: libmicrohttpd libsystemd-journal
#       (this is based on recipes that have previously been built and packaged)
# NOTE: the following library dependencies are unknown, ignoring: nspr gen ibumad regex sasl2 pfm nss papi ibmad
#       (this is based on recipes that have previously been built and packaged)
DEPENDS += "bison-native flex-native pcp-native cairo zlib ncurses readline libx11 avahi openssl"

EXTRA_OECONF += "--with-group=root --with-user=root"

export PCP_DIR = "${BASE_WORKDIR}/${BUILD_SYS}/${PN}-native/${PV}-${PR}/recipe-sysroot-native"
export DIST_ROOT = "${D}"

# Copy perl lib
#cp ${RECIPE_SYSROOT}/usr/lib/perl/5.24.1/CORE/libperl.so ${RECIPE_SYSROOT}/usr/lib

do_install_append() {
  rmdir ${D}/var/run/pcp
  rmdir ${D}/var/run
}

inherit systemd
SYSTEMD_AUTO_ENABLE_${PN} = "enable"
SYSTEMD_SERVICE_${PN} = "pmcd.service"

FILES_${PN} += "${datadir}/bash-completion \
  ${datadir}/pcp-gui \
  ${datadir}/bash-completion \
  ${datadir}/zsh \
  ${systemd_unitdir}/system/pmcd.service \
  ${systemd_unitdir}/system/pmie.service \
  ${systemd_unitdir}/system/pmlogger.service \
  ${systemd_unitdir}/system/pmproxy.service \
  ${libdir}/zabbix \
  /run \
 "

# pcp-testsuite package
PACKAGES =+ "${PN}-testsuite"
SUMMARY_${PN}-testsuite = "Performance Co-Pilot (PCP) test suite"
DESCRIPTION_${PN}-testsuite = "Quality assurance test suite for Performance Co-Pilot (PCP)"
RDEPENDS_${PN}-testsuite = "${PN}"
FILES_${PN}-testsuite = "/var/lib/pcp/testsuite"

# pcp-manager package
PACKAGES =+ "${PN}-manager"
SUMMARY_${PN}-manager =  "Performance Co-Pilot (PCP) manager daemon"
DESCRIPTION_${PN}-manager = "\
An optional daemon (pmmgr) that manages a collection of pmlogger and \
pmie daemons, for a set of discovered local and remote hosts running \
the performance metrics collection daemon (pmcd).  It ensures these \
daemons are running when appropriate, and manages their log rotation \
needs.  It is an alternative to the cron-based pmlogger/pmie service \
scripts."
RDEPENDS_${PN}-manager = "${PN}"
FILES_${PN}-manager = "${systemd_unitdir}/system/pmmgr.service \
                       ${datadir}/pcp/lib/pmmgr\
                       ${sysconfdir}/pcp/pmmgr"
SYSTEMD_SERVICE_${PN}-manager = "pmmgr.service"

