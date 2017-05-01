DEPENDS += "python python-future"

SRC_URI = "git://github.com/mavlink/mavlink-gbp-release.git;branch=release/kinetic/mavlink"

inherit pythonnative

do_configure_prepend () {
    export PYTHONPATH="${STAGING_LIBDIR}/python2.7/site-packages/"
}
