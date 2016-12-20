#!/bin/sh

red=437
green=397
blue=403

# Check if GPIO's are already exported
if [ ! -e /sys/class/gpio/gpio$red ]; then
	echo $red > /sys/class/gpio/export
fi
if [ ! -e /sys/class/gpio/gpio$green ]; then
	echo $green > /sys/class/gpio/export
fi
if [ ! -e /sys/class/gpio/gpio$blue ]; then
	echo $blue > /sys/class/gpio/export
fi

# Set direction
echo out > /sys/class/gpio/gpio$red/direction
echo out > /sys/class/gpio/gpio$blue/direction
echo out > /sys/class/gpio/gpio$green/direction

echo 0 > /sys/class/gpio/gpio$green/value
echo 1 > /sys/class/gpio/gpio$red/value
echo 1 > /sys/class/gpio/gpio$blue/value
