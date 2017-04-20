#!/bin/sh

echo "autostart-supplicant"

case "$1" in
        start)
                echo "autostart-supplicant called with start"

                # Stop hostapd if previously running.
                /etc/init.d/autostart-hostapd.sh stop

                # Need to enable RFKILL for wlan0
                rfkill unblock wlan

                # get wlan MAC@ and use it for P2P device name.
                MAC=$(ifconfig -a | grep wlan | sed -e 's/.*HWaddr\s//' |tr -d ':[:space:]')
                sed "s/^device_name=.*/device_name=Aero-${MAC}/" /etc/p2p_supplicant.conf > /tmp/p2p_supplicant.conf

                # Start supplicant
                start-stop-daemon -S -x /usr/sbin/wpa_supplicant -- -iwlan0 -Dnl80211 -c/etc/wpa_supplicant.conf -m/tmp/p2p_supplicant.conf -dd &

                # Start DHCP client
                sleep 2
                start-stop-daemon -S -x /sbin/dhclient -- -4 wlan0 &
                ;;
        stop)
                echo "autostart-supplicant called with stop"
                # Stop DHCP client and supplicant.
                killall wpa_supplicant
                killall dhclient
                ifconfig wlan0 0.0.0.0
                ifconfig wlan0 down
                ;;
esac

