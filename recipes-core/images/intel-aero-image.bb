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
				camera-streaming-daemon \
				"

# Allow to easily copy files to/from host
IMAGE_INSTALL += "rsync"

# Handle power button through ACPI
IMAGE_INSTALL += "eee-acpi-scripts"

# Add /etc/os-release
IMAGE_INSTALL += "os-release"

IMAGE_INSTALL += "openssh-sftp-server"

# increase entropy right after boot so hostapd succeeds authentication
IMAGE_INSTALL += "rng-tools"

# Build tools
IMAGE_INSTALL += "packagegroup-core-buildessential"

# Development/Debug tools
IMAGE_INSTALL += "valgrind"

# Tests applications
IMAGE_INSTALL += "packagegroup-core-tools"

# AirMap
IMAGE_INSTALL += "packagegroup-airmap"

# OpenCV
IMAGE_INSTALL += "opencv"

# PX4
IMAGE_INSTALL_append = " packagegroup-px4"

# librealsense
IMAGE_INSTALL += "librealsense"
IMAGE_INSTALL += "librealsense-graphical-examples"

#connectivity
IMAGE_INSTALL += "backport-iwlwifi"
IMAGE_INSTALL += "linux-firmware-iwlwifi-8000c"
IMAGE_INSTALL += "hostapd"
IMAGE_INSTALL += "autostart-hostapd"
IMAGE_INSTALL += "autostart-supplicant"

# Platform configurations
APPEND += "console=ttyS0,115200n8 console=tty1"
GRUB_TIMEOUT = "3"

# LTE MODEM
IMAGE_INSTALL += "glibc-gconvs glibc-utils glibc-gconv-iso8859-1 modemmanager \
	rpm icon-naming-utils libtool libndp libnl libinput \
	libxdmcp networkmanager autostart-modem modem-enable \
"
addtask create_link after do_rootfs before do_image

do_create_link() {
	ln -s lib ${WORKDIR}/rootfs/lib64
}
