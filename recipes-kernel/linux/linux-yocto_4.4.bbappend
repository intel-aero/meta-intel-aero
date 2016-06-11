FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# List of configs to enable in kernel .config
SRC_URI += "file://fbefi.cfg \
			file://lpss.cfg \
			file://mmc.cfg \
			file://pinctrl.cfg \
			file://debug.cfg \
			file://socdts.cfg \
			"
# List of patches to apply
SRC_URI += "file://0001-thermal-add-cherryview-support-to-soc-dts.patch \
			"
