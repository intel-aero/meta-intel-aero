#!/bin/sh
# short-description: Start power DS4 by enabling regulator with GPIO
# long-descriptioni: The script initializes ds4 (enable power) when 
# booting to enable driver enumeration once system is ready.

echo 405 > /sys/class/gpio/export
echo out > /sys/class/gpio/gpio405/direction
echo 1 > /sys/class/gpio/gpio405/value
