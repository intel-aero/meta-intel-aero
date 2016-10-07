# Intel Aero Machine kernel support
KBRANCH_intel-aero = "standard/base"
KMACHINE_intel-aero ?= "common-pc-64"
SRCREV_machine_intel-aero ?= "3d2455f9da30f923c6bd69014fad4cc4ea738be6"
COMPATIBLE_MACHINE_intel-aero = "intel-aero"
LINUX_VERSION_intel-aero = "4.4.3"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# List of configs to enable in kernel .config
SRC_URI += "file://lpss.cfg \
			file://mmc.cfg \
			file://pinctrl.cfg \
			file://debug.cfg \
			file://socdts.cfg \
			file://dma.cfg \
			file://drone-code.cfg \
			file://acmmbim.cfg \
			file://nat.cfg \
			file://spi.cfg \
			file://usbotg.cfg \
			file://regulator.cfg \
			file://camera.cfg \
			file://wdt.cfg \
			"

# List of binarie files
SRC_URI += "file://shisp_2401a0_v21.bin \
			"


# List of patches to apply
SRC_URI += "file://0001-thermal-add-cherryview-support-to-soc-dts.patch \
			file://0002-dma-dw-Allow-driver-usage-on-platforms.patch \
			file://0003-dma-dw-must-initialize-dma-channel.patch \
			file://0004-dma-dw-delelte-unused-var.patch \
			file://0005-dma-dw-enable-CherryTrail.patch \
			file://0006-dma-dw-remove-shutdown-ho.patch \
			file://0007-spi-Let-drivers-translate-ACPI-DeviceSelection-to-su.patch \
			file://0008--spi-pxa2xx-Move-chip-select-control-bits-into-lpss_c.patch \
			file://0009--spi-pxa2xx-Translate-ACPI-DeviceSelection-to-Linux-c.patch \
			file://0010--spi-pxa2xx-Add-support-for-both-chip-selects-on-Inte.patch \
			file://0011-usb-otg-add-cherryview-support.patch \
			file://0012-pmic-intel-port-whiskey-cove-driver.patch \
			file://0013-temp-atomisp-support.patch \
			file://0014-OV8858-add-sensor-sources-to-cloudsrest-platform.patch \
			file://0015-kernel-Adding-support-for-HW-flip-using-AtomISP.patch \
			file://0016-kernel-bug-fix-for-intermittent-green-patches.patch \
			"

do_install_append() {
			install -d ${D}/lib/firmware
			install -m 0777 ${WORKDIR}/shisp_2401a0_v21.bin ${D}/lib/firmware
}
