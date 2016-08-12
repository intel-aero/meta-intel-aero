CRIPTION = "Image containing only needed packages for production"

IMAGE_FEATURES += "package-management ssh-server-dropbear x11-base"

LICENSE = "MIT"

inherit core-image

IMAGE_INSTALL += "gstreamer1.0 gst-player \
				gstreamer-vaapi-1.0 libva va-intel libva-intel-driver \
				gstreamer1.0-plugins-base gstreamer1.0-plugins-good \
				gstreamer1.0-plugins-bad \
				gstreamer1.0-meta-base gstreamer1.0-rtsp-server \
				jam-stapl \
				"

# Tests applications
IMAGE_INSTALL += "packagegroup-core-tools"

