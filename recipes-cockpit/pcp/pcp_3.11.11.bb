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

inherit systemd
SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE_${PN} = "pmcd.service"

FILES_${PN} += "${datadir}/bash-completion \
  ${datadir}/pcp-gui \
  ${datadir}/perl5 \
  ${datadir}/bash-completion \
  ${datadir}/vendor_perl \
  ${datadir}/zsh \
  ${base_libdir}/systemd/system \
  ${libdir}/zabbix \
  /run \
  /run/pcp \
  "

# QA tests package
PACKAGES =+ "${PN}-testsuite"
SUMMARY_${PN}-testsuite = "Quality assurance test suite for Performance Co-Pilot (PCP)"
RDEPENDS_${PN}-testsuite = "${PN}"
FILES_${PN}-testsuite = "/var/lib/pcp/testsuite"

