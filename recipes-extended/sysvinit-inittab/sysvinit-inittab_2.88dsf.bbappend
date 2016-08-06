do_install_append() {
        echo >> ${D}${sysconfdir}/inittab
        echo "# Start another terminal on ttyS1" >> ${D}${sysconfdir}/inittab
        echo "S1:12345:respawn:/bin/start_getty 115200 ttyS1" >> ${D}${sysconfdir}/inittab
}
