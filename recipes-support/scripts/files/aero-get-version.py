#!/usr/bin/env python

##############################################################################
# This script gets current versions of various software/firmware on Aero board.
# It reads current BIOS, OS, FPGA, and Airmap versions and prints on console.
#
# Author: Mandeep Bansal <mandeep.bansal@intel.com>
#      Pranav Tipnis <pranav.tipnis@intel.com>
#
############################################################################

import commands
import os.path

def main():
    print "\n"
    BIOS_version()
    OS_version()
    AirMap_version()
    FPGA_version()
    print "\n"

def FPGA_version():
    print "FPGA_VERSION =",
    try:
        # FPGA read incorrectly returns 0x0 when we read first time after boot.
        # Hence, we will read it twice every time we want to find FPGA version.
        commands.getstatusoutput("/usr/bin/spi_xfer -b 1 -c 1 -d 00 -w 2")
        status, output = commands.getstatusoutput("/usr/bin/spi_xfer -b 1 -c 1 -d 00 -w 2")
        if status != 0:
            print "unknown"
        else:
            lines = output.split('\n')
            words = lines[4].split()
            print words[1]
    except:
        print "unknown"

def AirMap_version():
    print "AIRMAP_VERSION =",
    try:
        status, output = commands.getstatusoutput("python /etc/airmap/AirMapSDK-Embedded/getver.py")
        if status != 0:
            print "unknown"
        else:
            print output
    except:
        print "unknown"

def OS_version(rootdir='/'):
    version = None
    try:
        with open(os.path.join(rootdir, 'etc', 'os-release'), 'r') as f:
            for line in f:
                if line.startswith('PRETTY_NAME='):
                    version = line.split('PRETTY_NAME=')[1].strip('"').strip()
                    if version:
                        break
    except:
        pass

    if not version:
        version = 'unknown'

    print "OS_VERSION = %s" % version

def BIOS_version():
    try:
        f = open("/etc/bios_version", "r")
        words = f.readline().split()

        for word in words:
            if "Aero-" in word:
                print "BIOS_VERSION = " + word
    except:
        print "BIOS_VERSION = unknown"


if __name__ == "__main__":
    main()
