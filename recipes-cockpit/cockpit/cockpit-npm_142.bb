# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# NOTE: multiple licenses have been detected; they have been separated with &
# in the LICENSE value for now since it is a reasonable assumption that all
# of the licenses apply. If instead there is a choice between the multiple
# licenses then you should change the value to separate the licenses with |
# instead of &. If there is any doubt, check the accompanying documentation
# to determine which situation is applicable.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   COPYING.node
#   tools/debian/copyright.template
#   tools/debian/copyright
#   node_modules/qunit-tap/GPL-LICENSE.txt
#   node_modules/patternfly/LICENSE.txt
#   node_modules/d3/LICENSE
#   node_modules/qunitjs/LICENSE.txt
#
LICENSE = "LGPLv2.1 & Unknown & MIT & Apache-2.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c \
                    file://COPYING.node;md5=efd1f7702b8f545c82eda53a8d66370f \
                    file://tools/debian/copyright.template;md5=81adae6c924b23681adb7820e3a67483 \
                    file://tools/debian/copyright;md5=df9002510fa96f863530013e22d6ba5b \
                    file://src/bridge/mock-resource/system/cockpit/test/sub/COPYING;md5=4fbd65380cdd255951079008b364516c \
                    file://src/bridge/mock-resource/system/cockpit/test-priority/sub/COPYING;md5=4fbd65380cdd255951079008b364516c \
                    file://node_modules/term.js-cockpit/LICENSE;md5=2e14c05df0961c2bc6b1be030283a78f \
                    file://node_modules/qunit-tap/GPL-LICENSE.txt;md5=2c1778696d3ba68569a0352e709ae6b7 \
                    file://node_modules/qunit-tap/MIT-LICENSE.txt;md5=8484ee6e0e2edaebeb686297725f2ccb \
                    file://node_modules/redux/LICENSE.md;md5=a0e9a029c575a47069a637f579057679 \
                    file://node_modules/angular-gettext/LICENSE;md5=60e7c3f2d184ae4d83a8b355a89bc387 \
                    file://node_modules/kubernetes-topology-graph/COPYING;md5=4fbd65380cdd255951079008b364516c \
                    file://node_modules/jquery-flot/LICENSE.txt;md5=5bc600a435aadbd7dcde045ccb3208bf \
                    file://node_modules/patternfly/LICENSE.txt;md5=3984e2d7fa71d8d63cfd44ee8d7e801a \
                    file://node_modules/react-lite-cockpit/LICENSE;md5=204e7ce13043865f768c3956ba96f4e5 \
                    file://node_modules/bootstrap-datepicker/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
                    file://node_modules/c3/LICENSE;md5=4fd647cbdc543cda8a8bdf39c341f519 \
                    file://node_modules/d3/LICENSE;md5=9a43e3ae9eeb6821b99b41158d0b08cd \
                    file://node_modules/bootstrap/LICENSE;md5=86047de20b327af606bc29b1174c4b14 \
                    file://node_modules/qunitjs/LICENSE.txt;md5=88e6b088e1bdb95427eca76a90663e3b \
                    file://node_modules/angular-bootstrap-npm/LICENSE;md5=30204bcbaa0e17a488330b8a81e2292a \
                    file://node_modules/moment/LICENSE;md5=03554c9c0d266101dd2e4b0aa8425230 \
                    file://node_modules/registry-image-widgets/COPYING;md5=4fbd65380cdd255951079008b364516c \
                    file://node_modules/mustache/LICENSE;md5=b15e1e0022f11f78c2a3af0722e2855a \
                    file://node_modules/kubernetes-container-terminal/COPYING;md5=4fbd65380cdd255951079008b364516c"

SRC_URI = "https://github.com/cockpit-project/cockpit/releases/download/${PV}/cockpit-${PV}.tar.xz"
SRC_URI[md5sum] = "475d45abaa7944d4e09529423cb77acb"
SRC_URI[sha256sum] = "34728ce97836854de0c51c8244e95a4db695454a6326b72b0f1a033406399659"

# NOTE: the following prog dependencies are unknown, ignoring: krb5-config xsltproc msgcat intltool-update msgmerge msgfmt msggrep usermod intltool-merge newusers sudo ssh-agent chpasswd phantomjs chcon ssh-add intltool-extract go xmlto pkexec xgettext
# NOTE: unable to map the following pkg-config dependencies: libssh_threads libssh
#       (this is based on recipes that have previously been built and packaged)
# NOTE: the following library dependencies are unknown, ignoring: ssh gssapi_krb5 pam
#       (this is based on recipes that have previously been built and packaged)
DEPENDS = "pcp systemd"

# NOTE: if this software is not capable of being built in a separate build directory
# from the source, you should replace autotools with autotools-brokensep in the
# inherit line
inherit pkgconfig gettext autotools

# Specify any options you want to pass to the configure script using EXTRA_OECONF:
EXTRA_OECONF = ""

