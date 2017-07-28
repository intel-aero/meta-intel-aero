DESCRIPTION = "Fully functional image for Intel Aero platform"
LICENSE = "MIT"

IMAGE_FSTYPES = "iso"

IMAGE_FEATURES += "package-management"
IMAGE_FEATURES += "ssh-server-openssh"
IMAGE_FEATURES += "x11-base"
IMAGE_FEATURES += "splash"

inherit core-image

# full version of command line tools, not the busybox ones
IMAGE_INSTALL += "packagegroup-core-full-cmdline"
IMAGE_INSTALL += "wget"

IMAGE_INSTALL += "packagegroup-docker"

IMAGE_INSTALL += "gstreamer1.0 \
				gstreamer1.0-vaapi libva va-intel libva-intel-driver \
				gstreamer1.0-plugins-base gstreamer1.0-plugins-good \
				gstreamer1.0-plugins-bad \
				gstreamer1.0-meta-base gstreamer1.0-rtsp-server \
				"

# Drone relate projects
IMAGE_INSTALL += "camera-streaming-daemon"
IMAGE_INSTALL += "mavlink-router"
IMAGE_INSTALL += "libcoav coav-control"
IMAGE_INSTALL += "jam-stapl"
IMAGE_INSTALL += "px4-fw"
IMAGE_INSTALL += "ardupilot-fw"
# AirMap
IMAGE_INSTALL += "packagegroup-airmap"
# PX4
IMAGE_INSTALL += "packagegroup-px4"

# Allow to enumerate movidius devices
IMAGE_INSTALL += "movidius"

# Allow to easily copy files to/from host
IMAGE_INSTALL += "rsync"

# Add /etc/os-release
IMAGE_INSTALL += "os-release"

# increase entropy right after boot so hostapd succeeds authentication
IMAGE_INSTALL += "rng-tools"

# Build tools
IMAGE_INSTALL += "packagegroup-core-buildessential"

# Development/Debug tools
IMAGE_INSTALL += "valgrind"

# Tests applications
IMAGE_INSTALL += "packagegroup-core-tools"

# OpenCV
IMAGE_INSTALL += "opencv"

# librealsense
IMAGE_INSTALL += "librealsense"
IMAGE_INSTALL += "librealsense-graphical-examples"

# connectivity
IMAGE_INSTALL += "connman connman-client"
IMAGE_INSTALL += "openssh-sftp-server"

IMAGE_INSTALL += "hostapd"

# dev-tools
IMAGE_INSTALL += "dmidecode"

# Init Scripts
IMAGE_INSTALL += "notify-led"
# Init Scripts missing conversion to systemd
IMAGE_INSTALL += "initscripts-aero"

# cockpit projects
IMAGE_INSTALL += "cockpit"
IMAGE_INSTALL += "cockpit-ws"
IMAGE_INSTALL += "cockpit-bridge"
IMAGE_INSTALL += "cockpit-docker"
IMAGE_INSTALL += "cockpit-system"
IMAGE_INSTALL += "cockpit-dashboard"

# repo metadata
IMAGE_INSTALL += "intel-aero-repo"

enable_repo() {
		sed -i 's/enabled=0/enabled=1/' ${IMAGE_ROOTFS}/etc/yum.repos.d/intel-aero.repo
}

ROOTFS_POSTPROCESS_COMMAND += "enable_repo; "

addtask create_link after do_rootfs before do_image

do_create_link() {
	ln -s lib ${WORKDIR}/rootfs/lib64
}
BAD_RECOMMENDATIONS_pn-intel-aero-image = "busybox-syslog"
