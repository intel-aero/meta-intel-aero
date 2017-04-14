#!/usr/bin/python
# -*- coding: utf-8 -*-
#
# This program is free software; you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation; version 2 of the License.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program; if not, see
# http://www.gnu.org/licenses/old-licenses/gpl-2.0.html.
"""
Copyright (C) 2017  Intel Corporation. All rights reserved.
"""

from __future__ import print_function
import argparse
import os
import os.path
import shutil
import struct
import subprocess
import signal
import sys
import tempfile
import time

# Reboot Aero board instructing UEFI to use the USB Drive as next boot target
# If for some reason the boot fails, just rebooting again (removing the power,
# hard shutdown, etc) will allow the user to boot in the current system again
#
# This needs the efivarfs filesystem from Linux Kernel. If it's not currently
# mounted it will be mounted on the usual place,
# /sys/firmware/efi/efivars
#
# It's a poor's man implementation of the basic functionality in
# efibootmgr --bootnext <bootentry>. It's done this way to be able to reboot
# a system that doesn't have efibootmgr

efi_mountpoint = '/sys/firmware/efi/efivars'
aero_update_dir = None
efivar = os.path.join(efi_mountpoint, 'BootNext-8be4df61-93ca-11d2-aa0d-00e098032b8c')
efivar_check = os.path.join(efi_mountpoint, 'BootCurrent-8be4df61-93ca-11d2-aa0d-00e098032b8c')
usb_entry = 0x2001
cur_version = None
new_version = None

def is_possible_usb_pathname(s):
    return s.startswith('sd')

def cleanup_mountpoints():
    global aero_update_dir
    global efi_mountpoint

    if efi_mountpoint:
        subprocess.call(['umount', '-R', efi_mountpoint])
        efi_mountpoint = None

    if aero_update_dir:
        try:
            subprocess.check_call(['umount', '-R', os.path.join(aero_update_dir, 'rootfs')])
            subprocess.check_call(['umount', '-R', os.path.join(aero_update_dir, 'iso')])

            # now remove the temporary update directory, which is just below the iso directory
            shutil.rmtree(os.path.join(aero_update_dir))
            aero_update_dir = None
        except:
            pass

def die(s):
    print(s, file=sys.stderr)
    cleanup_mountpoints()
    sys.exit(1)

def sig_handler(signum, frame):
    sys.stderr.flush()
    sys.stdout.flush()
    die("\nUpdate canceled")

def mount_usbdrive(usbdrive):
    global aero_update_dir

    aero_update_dir = tempfile.mkdtemp(prefix='aero-update-')
    iso_mountpoint = os.path.join(aero_update_dir, 'iso')
    rootfs_mountpoint = os.path.join(aero_update_dir, 'rootfs')
    os.makedirs(iso_mountpoint)
    os.makedirs(rootfs_mountpoint)

    usbdrive_part1 = usbdrive + '1'
    subprocess.check_call(['mount', '-o', 'ro', usbdrive_part1, iso_mountpoint])
    subprocess.check_call(['mount', '-o', 'ro,loop', os.path.join(iso_mountpoint, 'rootfs.img'), rootfs_mountpoint])

def mount_efivarfs():
    global efi_mountpoint

    if os.path.ismount(efi_mountpoint):
        if os.path.isfile(efivar_check):
            # mountpoint in place and file exists, do nothing
            return

        print("%s is a mountpoint but %s is not present" % (efi_mountpoint, efivar_check))
        try:
            subprocess.check_call(['umount', efi_mountpoint])
        except:
            die('Could not umount %s' % efi_mountpoint)

    try:
        subprocess.check_call('mount -t efivarfs efivarfs '.split() + [efi_mountpoint])
    except:
        die('Could not mount efivarfs')

def check_usbdrive():
    # possible external drives: /dev/sd*
    blk_links = list(filter(is_possible_usb_pathname, os.listdir('/sys/block')))
    blk_devices = []
    for l in blk_links:
        b = os.path.join('/dev/', l)
        if os.path.exists(b):
            blk_devices.append(b)

    if len(blk_devices) == 0:
        die("No USB device found. Please connect a USB drive with update image.\nRefusing to reboot.")

    # Do not allow with more than one device connected
    if len(blk_devices) > 1:
        print("More than 1 external usb drives detected:")
        for b in blk_devices:
            print("\t", b)
        die("You could be left stuck on bios to select one of them. Refusing to reboot.")

    try:
        mount_usbdrive(blk_devices[0])
    except:
        die("USB device '%s' doesn't look like an update image.\nRefusing to reboot." % blk_devices[0])

    try:
        with open('/etc/os_version', 'r') as f:
            cur_version = f.read().strip()
    except:
        try:
            # assume it's an old version and use /proc/version
            with open('/proc/version', 'r') as f:
                cur_version = 'Before v1.00.04 [%s]' % (f.read().strip())
        except:
            # user may have a custom kernel or we don't know what happened:
            # don't block the reboot
            cur_version = '(Unknown)'

    try:
        with open(os.path.join(aero_update_dir, 'rootfs', 'etc', 'os_version'), 'r') as f:
            new_version = f.read().strip()
    except:
        try:
            with open(os.path.join(aero_update_dir, 'rootfs', 'etc', 'os-release'), 'r') as f:
                name = ''
                version = ''
                for line in f:
                    if line.startswith('NAME='):
                        name = line.split('NAME=')[1].strip('"').strip()
                    elif line.startswith('VERSION='):
                        version = line.split('VERSION=')[1].strip('"').strip()

                    if name and version:
                        break
                if name:
                    new_version = '%s %s' % (name, version)
                else:
                    new_version = version
                new_version.strip()
        except:
            new_version = None

    if not new_version:
        die("USB device '%s' contains update image but no version information was found.\nRefusing to reboot." % blk_devices[0])

    print('Current OS version: ', cur_version)
    print('New OS version: ', new_version)

def notify_leds():
    # leave RGB LED as blue
    LED_RED=437
    LED_GREEN=397
    LED_BLUE=403
    leds = [ LED_RED, LED_GREEN, LED_BLUE ]

    for l in leds:
        # gpio may already be exported
        try:
            with open('/sys/class/gpio/export', 'w') as f:
                f.write('%d' % l)
        except:
            pass

        with open('/sys/class/gpio/gpio%d/direction' % l, 'w') as f:
            f.write('out')

    with open('/sys/class/gpio/gpio%d/value' % LED_RED, 'w') as f:
        f.write('1')
    with open('/sys/class/gpio/gpio%d/value' % LED_GREEN, 'w') as f:
        f.write('1')
    with open('/sys/class/gpio/gpio%d/value' % LED_BLUE, 'w') as f:
        f.write('0')


def main():
    parser = argparse.ArgumentParser(description='Helper tool to reboot Aero into update image')
    parser.add_argument('-f', '--force', action='store_true',
                        help='disable sanity checks if there\'s update media connected')
    args = parser.parse_args()

    # always cleanup ourselves on exit
    signal.signal(signal.SIGINT, sig_handler)

    mount_efivarfs()

    if not args.force:
        check_usbdrive()
        print('\n')

    with os.fdopen(os.open(efivar, os.O_WRONLY | os.O_CREAT, 0o644), 'w') as f:
        buf = struct.pack('<Ih', 0x07, usb_entry)
        f.write(buf)

    cleanup_mountpoints()

    timeout = 5
    print('Setup done to boot from USB.')
    print('Rebooting in %d seconds.' % timeout)
    print('Press ^C to cancel.')

    for i in range(timeout):
        time.sleep(1)
        print('.')

    signal.signal(signal.SIGINT, signal.SIG_IGN)
    notify_leds()
    subprocess.call(['reboot'])

if __name__ == "__main__":
    main()
