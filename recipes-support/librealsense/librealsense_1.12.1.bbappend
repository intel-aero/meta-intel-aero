do_install_append () {
        install -d ${D}${prefix}/local/bin
        install -m 0755 ${S}/config/usb-R200-in ${D}${prefix}/local/bin
        install -m 0755 ${S}/config/usb-R200-in_udev ${D}${prefix}/local/bin
}

FILES_${PN} += "\
        ${prefix}/local/bin/* \
"
