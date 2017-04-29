# Intel Aero Machine kernel support
KBRANCH_intel-aero ?= "standard/intel/base"
SRCREV_machine_intel-aero ?= "2cc78e92f40522d8b5f278b7099c1ae657947749"
COMPATIBLE_MACHINE_intel-aero = "intel-aero"
LINUX_VERSION_intel-aero = "4.4.60"

KERNEL_EXTRA_FEATURES = ""
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI = "git://git.yoctoproject.org/linux-yocto-4.4.git;name=machine;branch=${KBRANCH};"

# List of configs to enable in kernel .config
SRC_URI += "file://defconfig"

# List of binary files
SRC_URI += "file://shisp_2401a0_v21.bin \
			"
# List of patches to apply
SRC_URI += " \
	file://0002-media-Add-support-for-RW10-pixel-format-detection.patch \
	file://0003-thermal-add-cherryview-support-to-soc-dts.patch \
	file://0004-dma-dw-Allow-driver-usage-on-platforms-without-HCLK.patch \
	file://0005-dma-dw-must-initialize-dma-channel-in-each-transacti.patch \
	file://0006-dma-dw-enable-CherryTrail-LPDMA.patch \
	file://0007-dma-dw-remove-shutdown-hook.patch \
	file://0008-BACKPORT-spi-Let-drivers-translate-ACPI-DeviceSelect.patch \
	file://0009-BACKPORT-spi-pxa2xx-Move-chip-select-control-bits-in.patch \
	file://0010-BACKPORT-spi-pxa2xx-Translate-ACPI-DeviceSelection-t.patch \
	file://0011-BACKPORT-spi-pxa2xx-Add-support-for-both-chip-select.patch \
	file://0012-usb-otg-add-cherryview-support.patch \
	file://0013-pmic-intel-port-whiskey-cove-driver.patch \
	file://0014-regulator-whiskey_cove-implements-WhiskeyCove-pmic-V.patch \
	file://0015-regulator-whiskey_cove-fixup-to-build-with-gcc-6.patch \
	file://0016-pmic-whiskeycove-add-vqmmc-regulator-for-SD-host-vol.patch \
	file://0017-temp-atomisp-support.patch \
	file://0018-atomisp-Fixups-to-build-with-gcc-6.patch \
	file://0019-OV8858-add-sensor-sources-to-cloudsrest-platform.patch \
	file://0020-kernel-Adding-support-for-HW-flip-using-AtomISP.patch \
	file://0021-kernel-bug-fix-for-intermittent-green-patches-during.patch \
	file://0022-ov8858-fix-return-logic-on-ov8858_s_ctrl.patch \
	file://0023-ov8858-fix-regulator-management-on-ov8858_s_ctrl.patch \
	file://0024-ov7251-remove-early-return-statement.patch \
	file://0025-intel-mid-split-keyboard-gpio-SFI-implementation-fro.patch \
	file://0026-input-soc_button_array-add-debounce-parameter-to-the.patch \
	file://0027-acpi-Workaround-for-not-registering-CAN-controller.patch \
	file://0028-Temporarily-remove-BXT-PMIC-driver.patch \
	"

# SRC_URI += " \
# 	file://0001-Add-support-to-RealSense-camera-formats.patch \
# 	"

do_install_append() {
	install -d ${D}/lib/firmware
	install -m 0777 ${WORKDIR}/shisp_2401a0_v21.bin ${D}/lib/firmware
}
