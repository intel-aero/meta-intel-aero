DESCRIPTION = "Fully functional image for Intel Aero platform"
LICENSE = "MIT"

IMAGE_FEATURES += "package-management ssh-server-openssh x11-base"

inherit core-image

IMAGE_INSTALL += "gstreamer1.0 gst-player \
				gstreamer-vaapi-1.0 libva va-intel libva-intel-driver \
				gstreamer1.0-plugins-base gstreamer1.0-plugins-good \
				gstreamer1.0-plugins-bad \
				gstreamer1.0-meta-base gstreamer1.0-rtsp-server \
				jam-stapl \
				aero-watchdog \
				aero-utils \
				px4-fw \
				mavlink-router \
				efibootmgr \
				"

# Add /etc/os-release
IMAGE_INSTALL += "os-release"

IMAGE_INSTALL += "openssh-sftp-server"

# increase entropy right after boot so hostapd succeeds authentication
IMAGE_INSTALL += "rng-tools"

#Camera Streaming Daemon support
IMAGE_INSTALL += "libavahi-client libavahi-glib"

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
IMAGE_INSTALL_append = " mavros realsense-camera"

# Enable ros comm packagegroup
IMAGE_INSTALL_append = " packagegroup-ros-comm"

# PX4
IMAGE_INSTALL_append = " packagegroup-px4"

# Platform configurations
APPEND += "console=ttyS0,115200n8 console=tty1"
GRUB_TIMEOUT = "3"

# librealsense
IMAGE_INSTALL += "librealsense"
IMAGE_INSTALL += "librealsense-graphical-examples"

addtask create_link after do_rootfs before do_image
addtask create_os_version_file after do_rootfs before do_image

do_create_link() {
	ln -s lib ${WORKDIR}/rootfs/lib64
}

do_create_os_version_file() {
	echo "v1.2" > ${WORKDIR}/rootfs/etc/os_version
	chmod 444 ${WORKDIR}/rootfs/etc/os_version
}
