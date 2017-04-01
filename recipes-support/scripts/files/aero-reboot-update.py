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
import os
import os.path
import struct
import subprocess
import sys
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

mountpoint = '/sys/firmware/efi/efivars'
efivar = os.path.join(mountpoint, 'BootNext-8be4df61-93ca-11d2-aa0d-00e098032b8c')
efivar_check = os.path.join(mountpoint, 'BootCurrent-8be4df61-93ca-11d2-aa0d-00e098032b8c')
usb_entry = 0x2001

def die(s):
    print(s, file=sys.stderr)
    sys.exit(1)

def mount_efivarfs():
    subprocess.check_call('mount -t efivarfs efivarfs '.split() + [mountpoint])

if os.path.ismount(mountpoint) and not os.path.isfile(efivar_check):
    print("%s is a mountpoint but %s is not present" % (mountpoint, efivar_check))
    try:
        subprocess.check_call(['umount', mountpoint])
    except:
        die('Could not umount %s' % mountpoint)

try:
    mount_efivarfs()
except:
    die('Could not mount efivarfs')

with os.fdopen(os.open(efivar, os.O_WRONLY | os.O_CREAT, 0o644), 'w') as f:
    buf = struct.pack('<Ih', 0x07, usb_entry)
    f.write(buf)

timeout = 5
print('Setup done to boot from USB.\nRebooting in %d seconds.\nPress ^C to cancel.' % timeout)

for i in range(timeout):
    time.sleep(1)
    print('.')
print('\n\n')

subprocess.call(['reboot'])
