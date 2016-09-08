#!/bin/sh
# Description: Startup the watchdog daemon 

if [ -x /usr/bin/aero-watchdog ]; then 
  /usr/bin/aero-watchdog &
fi
