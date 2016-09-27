"""
  telemetryAPI
  AirMapSDK

  Created by AirMap Team on 6/28/16.
  Copyright (c) 2016 AirMap, Inc. All rights reserved.
"""
# telemetryAPI.py -- Flight API functions

import traceback
import httplib
import urllib
import simplejson as json
import ssl
import time
import datetime
import socket
from airdefs import Globals
import os
import subprocess
import traceback

class Telemetry:
	
	os = __import__('os')
	connection = None
	headers = None
	thisGlobals = Globals()

	def __init__(self):
		pass

	def put_Telemetry(self, flightID, lat, lon, alt, ground_speed, heading, barometer):
		connectTelemetry = httplib.HTTPSConnection(Globals.telemetryAddr, Globals.httpsPort, timeout=Globals.timeOut)
		headers = Globals.xapikey
		print "..............................................."
		try:
        		connectTelemetry.request('PUT', '/livemap/telemetry/{}'.format(flightID), json.dumps({"lat":float(lat),"lon":float(lon),"alt":float(alt),"timestamp":"{}".format(datetime.datetime.now()),"gs_ms":float(ground_speed),"true":float(heading),"baro":float(barometer)}), headers)
        		result = connectTelemetry.getresponse().read()
			#print result			
			parsed_json = json.loads(result)
			#print parsed_json
			return parsed_json
		except:
			#traceback.print_exc()
			print "No response from telemetry..."

	def post_Telemetry(self, flightID, lat, lon, alt, ground_speed, heading, barometer):
		connectTelemetry = httplib.HTTPSConnection(Globals.telemetryAddr, Globals.httpsPort, timeout=Globals.timeOut)
		headers = Globals.xapikey
		print "..............................................."
		try:
        		connectTelemetry.request('POST', '/livemap/telemetry/{}'.format(flightID), json.dumps({"lat":float(lat),"lon":float(lon),"alt":float(alt),"timestamp":"{}".format(datetime.datetime.now()),"gs_ms":float(ground_speed),"true":float(heading),"baro":float(barometer)}), headers)
        		result = connectTelemetry.getresponse().read()
			#print result			
			parsed_json = json.loads(result)
			#print parsed_json
			return parsed_json
		except:
			#traceback.print_exc()
			print "No response from telemetry..."

