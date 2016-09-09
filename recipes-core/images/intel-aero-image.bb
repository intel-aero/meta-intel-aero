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
				"

# Build tools
IMAGE_INSTALL += "packagegroup-core-buildessential"

# Tests applications
IMAGE_INSTALL += "packagegroup-core-tools"

# OpenCV
IMAGE_INSTALL += "opencv"

# Platform configurations
APPEND += "console=tty1 console=ttyS0,115200n8"
GRUB_TIMEOUT = "3"
