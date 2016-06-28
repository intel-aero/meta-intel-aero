FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# List of configs to enable in kernel .config
SRC_URI += "file://fbefi.cfg \
			file://lpss.cfg \
			file://mmc.cfg \
			file://pinctrl.cfg \
			file://debug.cfg \
			file://socdts.cfg \
			file://dma.cfg \
			"
# List of patches to apply
SRC_URI += "file://0001-thermal-add-cherryview-support-to-soc-dts.patch \
			file://0002-dma-dw-Allow-driver-usage-on-platforms.patch \
			file://0003-dma-dw-must-initialize-dma-channel.patch \
			file://0004-dma-dw-delelte-unused-var.patch \
			file://0005-dma-dw-enable-CherryTrail.patch \
			file://0006-dma-dw-remove-shutdown-ho.patch \
			"
