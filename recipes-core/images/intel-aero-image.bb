DESCRIPTION = "Fully functional image for Intel Aero platform"
LICENSE = "MIT"

IMAGE_FEATURES += "package-management ssh-server-dropbear x11-base"

inherit core-image

IMAGE_INSTALL += "gstreamer1.0 gst-player \
				gstreamer-vaapi-1.0 libva va-intel libva-intel-driver \
				gstreamer1.0-plugins-base gstreamer1.0-plugins-good \
				gstreamer1.0-plugins-bad \
				gstreamer1.0-meta-base gstreamer1.0-rtsp-server \
				jam-stapl \
				aero-watchdog \
				aero-utils \
				"

# Build tools
IMAGE_INSTALL += "packagegroup-core-buildessential"

# Tests applications
IMAGE_INSTALL += "packagegroup-core-tools"

# AirMap
IMAGE_INSTALL += "packagegroup-airmap"

# OpenCV
IMAGE_INSTALL += "opencv"

# Dronekit
IMAGE_INSTALL_append = " dronekit-python"

# MavROS
IMAGE_INSTALL_append = " mavros"

# PX4
IMAGE_INSTALL_append = " packagegroup-px4"

# Platform configurations
APPEND += "console=ttyS1,115200n8 console=tty1"
GRUB_TIMEOUT = "3"

# librealsense
IMAGE_INSTALL += "librealsense"
