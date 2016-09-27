Airmap -- Airspace Management For Drones.

AirMapSDK is a Python library for interfacing with the `Airmap <https://developer.airmap.io>`_ API.

.. image:: ../_images/air-map-logo.png

.. image:: ../_images/airmap-flow.png

**Pre-Requistes:**

| Airmap developer account
| X-API-Key from your account
| GPS data (fake gps included)
| OAuth token
| xmlrpclib.py
| pip
| getAirmap.py (copy to /home/root/installs directory or similar)
| python requests package

**Install Requests**

| Insert USB drive with requests-master.zip
| sudo mount /dev/sda1 /mnt
| cp /mnt/requests-master.zip /home/root/new_dir
| cd /home/root/new_dir
| unzip requests-master.zip
| cd requests-master
| sudo python setup.py install

**Wi-Fi Connectivity**

| ifconfig (check for wlan0 or LTE ethX)
| cd /etc
| sudo wpa_passphrase yourSSID yourPassphrase > wpa-Aero.conf
| sudo wpa_supplicant -i wlan0 -c /etc/wpa-Aero.conf &
| sudo dhclient wlan0

**Install simplejson**

| cd /home/root
| mkdir installs
| cd installs
| python getAirmap.py simplejson simplejson
| unzip simplejson-master.zip
| cd simplejson-master
| sudo python setup.py install

**Install AirMapSDK**

| cd /home/root
| mkdir Installs
| cd installs
| python getAirmap.py airmapinc EmbeddedSDK
| unzip EmbeddedSDK-master.zip
| cd EmbeddedSDK-master

**User Example**

| cd /home/root/Installs/EmbeddedSDK-master
| python userapp.py


**API Example**

| (connect) airmap.connect.set_XAPIKey(xapikey) - Set your X-API-Key from your Airmap account
| (connect) airmap.connect.get_CIDID() - Retrieve CID information
| (connect) airmap.connect.connect() - Check internet connection and endpoint status Returns: True if ready
| (statusAPI) airmap.statusAPI.get_status(lat,lon,Weather.on) - Given position retreive airspace data
| (statusAPI) Parse status information including weather, advisories, and maximum flight bounds (see statusAPI documentation)
| (user) Process required notifications if needed
| (connect) airmap.connect.get_SecureToken() - get security token
| (flightAPI) airmap.flightAPI.create_FlightPoint (flight time,lat,lon,publicflight?,sendnotifications?) - Setup the flight and flight time Returns flightID
| (flightAPI) airmap.flightAPI.get_PilotID() - Returns pilotID for this account
| (flightAPI) airmap.flightAPI.end_Flight(flightID) - End the flight specified by flight ID
| (flightAPI) airmap.flightAPI.delete_Flight(flightID) - Delete flight described by flightID
| (flightAPI) airmap.flightAPI.get_FlightList(pilotID) - Get all flights for pilotID
| (flightAPI) airmap.flightAPI.cmd_KillFlights(pilotID) - Delete all flights under specified pilotID



Documentation can be found at https://developer.airmap.io

Source code can be found at https://github.com/...

Airmap is a trademark of Airmap, Inc.

