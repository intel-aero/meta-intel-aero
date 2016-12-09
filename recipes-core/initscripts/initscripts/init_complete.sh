#!/bin/sh

red=437
blue=403
green=397

if [ ! -e /sys/class/gpio/gpio$red ]; then
        echo $red > /sys/class/gpio/export
fi
if [ ! -e /sys/class/gpio/gpio$green ]; then
        echo $green > /sys/class/gpio/export
fi
if [ ! -e /sys/class/gpio/gpio$blue ]; then
        echo $blue > /sys/class/gpio/export
fi

echo 0 > /sys/class/gpio/gpio$red/value
echo 0 > /sys/class/gpio/gpio$blue/value
echo 1 > /sys/class/gpio/gpio$green/value
echo $red > /sys/class/gpio/unexport
echo $blue > /sys/class/gpio/unexport
