CRIPTION = "Image containing only needed packages for production"

IMAGE_FEATURES += "package-management ssh-server-dropbear x11-base"

LICENSE = "MIT"

inherit core-image

IMAGE_INSTALL += ""

# Tests applications
IMAGE_INSTALL += "packagegroup-core-tools"

