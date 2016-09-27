"""
  userapp
  AirMapSDK - Example

  Created by AirMap Team on 6/28/16.
  Copyright (c) 2016 AirMap, Inc. All rights reserved.
"""

from airmap.connect import Connect
from airmap.airdefs import Startup, Advisory, Weather, Notify, Public
from airmap.statusAPI import Status
from airmap.flightAPI import Flight
from airmap.telemetryAPI import Telemetry
import gps
import socket
import time
import sys

curMode = Startup.Drone.State.connect
test = True
lat = None
lon = None
xapikey = {"Content-Type":"application/json; charset=utf-8","X-API-Key":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjcmVkZW50aWFsX2lkIjoiY3JlZGVudGlhbHxHTk5nbUxuaTlYM1p3UlRYTU9sMnFmS0o1Z0xLIiwiYXBwbGljYXRpb25faWQiOiJhcHBsaWNhdGlvbnxuOW41QmtZc0JhNkFvM3NBUkpHeXlVYWxZUUVZIiwib3JnYW5pemF0aW9uX2lkIjoiZGV2ZWxvcGVyfDJ6b2JiN3loeGVZNHFrQzNQUngwWkhLTXoyMzgiLCJpYXQiOjE0NzExMjY1NDJ9.v4STUtbJa3uJZFsJLpWZRgUYoyz1X6BxKW8kokerjCg"}


if test:
	lat = '34.013252'
	lon = '-118.499112'
	alt = '101.3'
	ground_speed = '10.8'
	heading = '84.6'
	barometer = '28.4'
	print "GPS test mode enabled..."
else:
	try:
    		#Access GPS
		gpsReady = False
    		gpsd = gps.gps(mode=gps.WATCH_ENABLE)

		while not gpsReady:
			print "Waiting for GPS..."
			# Read the GPS state
			gpsd.next()

			# Wait for GPS lock
			if (gpsd.valid & gps.LATLON_SET) != 0:
				lat = str(gpsd.fix.latitude)
				lon = str(gpsd.fix.longitude)
				gpsReady = True #breakout
            
	except socket.error:
    		print "Error: gpsd service does not seem to be running, plug in USB GPS, run fake-gps-data.sh or run set 'test' flag"
    		sys.exit(1)

airconnect = Connect()
airstatus = Status()
airflight = Flight()
airtelemetry = Telemetry()
airconnect.set_Timeout(16)
airconnect.set_XAPIKey(xapikey)

#airconnect.get_CIDID()

Ret = airconnect.connect()

if Ret:
	if airstatus.get_status(lat,lon,Weather.on):
		flightStatus = airstatus.get_StatusColor()
		print flightStatus
		maxDistance = airstatus.get_MaxDistance()
		print maxDistance

		statusCode = airstatus.get_StatusCode()
		print statusCode

		temperature = airstatus.get_Temperature()
		print temperature

		visibility = airstatus.get_Visibility()
		print visibility

		humidity = airstatus.get_Humidity()
		print humidity

		condition = airstatus.get_Condition()
		print condition

		precipitation = airstatus.get_Precipitation()
		print precipitation

		windGusting = airstatus.get_WindGusting()
		print windGusting

		windSpeed = airstatus.get_WindSpeed()
		print windSpeed

		windHeading = airstatus.get_WindHeading()
		print windHeading

		Ret = airstatus.cmd_ProcessAdvisories()
		print Ret

		Advisories = airstatus.get_Advisories()
		try:
			xIndex = 0
			while True:
				thisAdvisory = Advisories[xIndex]
				try:
					yIndex = 0
					while True:
						if str(Advisories[xIndex].properties[yIndex].prop_value)[0] == '[':
							print str(Advisories[xIndex].properties[yIndex].prop_name)
							try:
								zIndex = 0;
								while True:
									print str(Advisories[xIndex].properties[yIndex].prop_value[zIndex].prop_name) + " : " + str(Advisories[xIndex].properties[yIndex].prop_value[zIndex].prop_value)
									zIndex += 1
							except:
								pass
						else:
							print str(Advisories[xIndex].properties[yIndex].prop_name) + " : " + str(Advisories[xIndex].properties[yIndex].prop_value)
						yIndex += 1
				except:
					pass 
				print xIndex
				xIndex += 1
		except:
			print "Finished Advisory Print Task..."

		oneAdvisory = airstatus.get_Advisory(3)
		yIndex = 0
		try:
			while True:
				if str(oneAdvisory.properties[yIndex].prop_value)[0] == '[':
					print str(oneAdvisory.properties[yIndex].prop_name)
					try:
						zIndex = 0;
						while True:
							print str(oneAdvisory.properties[yIndex].prop_value[zIndex].prop_name) + " : " + str(oneAdvisory.properties[yIndex].prop_value[zIndex].prop_value)
							zIndex += 1
					except:
						pass
				else:
					print str(oneAdvisory.properties[yIndex].prop_name) + " : " + str(oneAdvisory.properties[yIndex].prop_value)
				yIndex += 1
		except:
			print "Advisory Complete..."


		airconnect.get_SecureToken()

		flightID = airflight.create_FlightPoint (2,lat,lon,Public.on,Notify.on)
		myPilotID = airflight.get_PilotID()

		airflight.end_Flight(flightID)
		#airflight.delete_Flight(flightID)

		response = airtelemetry.post_Telemetry(flightID,lat,lon,alt,ground_speed,heading,barometer)
		print response

		airflight.get_FlightList(myPilotID)
		airflight.cmd_KillFlights(myPilotID)


