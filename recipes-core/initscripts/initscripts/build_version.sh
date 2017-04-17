#!/bin/sh
# This script will:
# a) Read and store BIOS version

chattr -i /etc/bios_version
dmesg | grep DMI > /etc/bios_version
chmod 444 /etc/bios_version
chattr +i /etc/bios_version
