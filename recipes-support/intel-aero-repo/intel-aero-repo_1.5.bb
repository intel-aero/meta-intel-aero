inherit allarch

SUMMARY = "Intel Aero package repository"
LICENSE = "MIT"
LICENSE_PATH = "${S}"
SRC_URI = "file://LICENSE"

S = "${WORKDIR}"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3da9cfbcb788c80a0384361b4de20420"

INHIBIT_DEFAULT_DEPS = "1"

do_fetch[noexec] = "1"
do_unpack[noexec] = "1"
do_patch[noexec] = "1"
do_configure[noexec] = "1"

REPO_FIELDS = "name baseurl gpgcheck enabled"

name= "Intel Aero"
baseurl= "https://download.01.org/aero/repo/${DISTRO_VERSION}/"
gpgcheck= "1"
enabled= "0"


python do_compile () {
    import shutil    
    with open(d.expand('${B}/intel-aero.repo'), 'w') as f:
        f.write('[Main]\n')
        for field in d.getVar('REPO_FIELDS').split():
            value = d.getVar(field)
            if value:
                f.write('{0}={1}\n'.format(field, value))
}
do_compile[vardeps] += "${REPO_FIELDS}"

do_install () {
        install -d ${D}${sysconfdir}/yum.repos.d
        install -m 0644 intel-aero.repo ${D}${sysconfdir}/yum.repos.d/
}

