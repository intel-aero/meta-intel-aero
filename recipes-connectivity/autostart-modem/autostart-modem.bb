SECTION = "modem"
SUMMARY = "User space script to be run at boot in order to activate the modem connection"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

SRC_URI = " \
    file://NetworkManager \
    file://02wwan \
    file://setfirewall.sh \
    file://Modemlink \
    file://NetworkManager.conf \
"

S = "${WORKDIR}/autostart-modem-${PV}"

RDEPENDS_${PN} += "bash"

FILES_${PN} = "${sysconfdir}/init.d/NetworkManager \
               ${sysconfdir}/rc0.d/K20NetworkManager \
               ${sysconfdir}/rc1.d/K20NetworkManager \
               ${sysconfdir}/rc2.d/S20NetworkManager \
               ${sysconfdir}/rc3.d/S20NetworkManager \
               ${sysconfdir}/rc4.d/S20NetworkManager \
               ${sysconfdir}/rc5.d/S20NetworkManager \
               ${sysconfdir}/rc6.d/k20NetworkManager \
               /etc/NetworkManager/dispatcher.d/02wwan \
               /etc/NetworkManager/system-connections/Modemlink \
               /etc/NetworkManager/NetworkManager.conf \
	       /usr/bin/setfirewall.sh \
"

do_install() {
    install -d ${D}${sysconfdir}/init.d/
    install -d ${D}${sysconfdir}/rc0.d/
    install -d ${D}${sysconfdir}/rc1.d/
    install -d ${D}${sysconfdir}/rc2.d/
    install -d ${D}${sysconfdir}/rc3.d/
    install -d ${D}${sysconfdir}/rc4.d/
    install -d ${D}${sysconfdir}/rc5.d/
    install -d ${D}${sysconfdir}/rc6.d/
    install -m 0755 ${WORKDIR}/NetworkManager ${D}${sysconfdir}/init.d/
    ln -sf ../init.d/NetworkManager      ${D}${sysconfdir}/rc0.d/K20NetworkManager
    ln -sf ../init.d/NetworkManager      ${D}${sysconfdir}/rc1.d/K20NetworkManager
    ln -sf ../init.d/NetworkManager      ${D}${sysconfdir}/rc2.d/S20NetworkManager
    ln -sf ../init.d/NetworkManager      ${D}${sysconfdir}/rc3.d/S20NetworkManager
    ln -sf ../init.d/NetworkManager      ${D}${sysconfdir}/rc4.d/S20NetworkManager
    ln -sf ../init.d/NetworkManager      ${D}${sysconfdir}/rc5.d/S20NetworkManager
    ln -sf ../init.d/NetworkManager      ${D}${sysconfdir}/rc6.d/k20NetworkManager
    install -m 0700 -d ${D}/etc/NetworkManager/
    install -m 0700 -d ${D}/etc/NetworkManager/dispatcher.d
    install -m 0700 -d ${D}/etc/NetworkManager/system-connections
    install -m 0744 ${WORKDIR}/02wwan ${D}/etc/NetworkManager/dispatcher.d/
    install -m 0700 ${WORKDIR}/Modemlink ${D}/etc/NetworkManager/system-connections/
    install -m 0700 ${WORKDIR}/NetworkManager.conf ${D}/etc/NetworkManager/
    install -d ${D}/usr/
    install -d ${D}/usr/bin/
    install -m 0755  ${WORKDIR}/setfirewall.sh ${D}/usr/bin

}

