"""
  flightAPI
  AirMapSDK

  Created by AirMap Team on 6/28/16.
  Copyright (c) 2016 AirMap, Inc. All rights reserved.
"""

# flightAPI.py -- Flight API functions

import traceback
import httplib
import urllib
import simplejson as json
import ssl
import time
import datetime
import socket
from airdefs import Advisory, Advisories, Properties, Globals
import os
import subprocess
import traceback

class Flight:
	
	os = __import__('os')
	connection = None
	headers = None
	thisGlobals = Globals()

	def __init__(self):
		pass

	def get_FlightList(self, pilotID):
		connection = httplib.HTTPSConnection(Globals.httpsAddr, Globals.httpsPort, timeout=Globals.timeOut)
		headers = Globals.xapikey
		try:
        		connection.request('GET', '/flight/v2/?pilot_id='+str(pilotID)+'&enhance=true', '', headers)
        		result = connection.getresponse().read()
			parsed_json = json.loads(result)
        		#print(result)
			flight_collection = parsed_json['data']['results']
			#print flight_collection
			return flight_collection
		except:
			traceback.print_exc()

	def cmd_KillFlights(self, pilotID):
		connection = httplib.HTTPSConnection(Globals.httpsAddr, Globals.httpsPort, timeout=Globals.timeOut)
		headers = Globals.xapikey
		try:
        		connection.request('GET', '/flight/v2/?pilot_id='+str(pilotID)+'&enhance=true', '', headers)
        		result = connection.getresponse().read()
			parsed_json = json.loads(result)
        		#print(result)
			flight_collection = parsed_json['data']['results']
			#print flight_collection
			for flights in flight_collection:
				endFlight = flights['id']
				#destroy flight
				print "deleting {}".format(endFlight)
				try:
					connectFlight = httplib.HTTPSConnection(Globals.httpsAddr, Globals.httpsPort, timeout=Globals.timeOut)
					headers = Globals.xapikey
					headers['Authorization'] = "Bearer {}".format(Globals.myToken)
					connectFlight.request('POST', '/flight/v2/{}/delete'.format(endFlight), '', headers)
        				result = connectFlight.getresponse().read()
        				#print(result)
				except:
        				print "Kill Flights Error..."
					traceback.print_exc()
		except:
			traceback.print_exc()

	def get_PilotID(self):
		if Globals.pilotIDValid == True:
			return Globals.pilot_id
		else:
			return False

	def create_FlightPoint(self, time, lat, lon, public, notify):
		startTime = datetime.datetime.now()
		endTime = startTime + datetime.timedelta(0,(time*60))
		startTime = startTime.isoformat() + "-07:00"
		endTime = endTime.isoformat() + "-07:00"
		try:
			connectFlight = httplib.HTTPSConnection(Globals.httpsAddr, Globals.httpsPort, timeout=Globals.timeOut)
			headers = Globals.xapikey
			headers['Authorization'] = "Bearer {}".format(Globals.myToken)
			connectFlight.request('POST', '/flight/v2/point', json.dumps({"latitude":float(lat),"longitude":float(lon),"max_altitude":100,"start_time":"{}".format(startTime),"end_time":"" + endTime + "","public":bool(public),"notify":bool(notify)}), headers)
        		result = connectFlight.getresponse().read()
        		#Globals.strPrint(self.thisGlobals,result)
			try:
				parsed_json = json.loads(result)
				parsed_status = parsed_json['status']
				Globals.pilot_id = parsed_json['data']['pilot_id']
				Globals.pilotIDValid = True
				#Globals.strPrint (self.thisGlobals,Globals.pilot_id)
			except:
				Globals.strPrint (self.thisGlobals,"Pilot ID not found...Retry!")
				Globals.strPrint (self.thisGlobals,result)
				return False
			if parsed_status != "success":
				return False
			Globals.myFlightID = parsed_json['data']['id']	
		except:
        		print "Create Flight Error..."
			traceback.print_exc()

		return Globals.myFlightID

	def end_Flight(self, flightID):
		try:
			connectFlight = httplib.HTTPSConnection(Globals.httpsAddr, Globals.httpsPort, timeout=Globals.timeOut)
			headers = Globals.xapikey
			headers['Authorization'] = "Bearer {}".format(Globals.myToken)
			connectFlight.request('POST', '/flight/v2/{}/end'.format(flightID), '', headers)
        		result = connectFlight.getresponse().read()
			parsed_json = json.loads(result)
        		parsed_status = parsed_json['status']
			if parsed_status != "success":
				return False
			else:
				return True
		except:
        		print "End Flight Error..."
			traceback.print_exc()

	def delete_Flight(self, flightID):
		try:
			connectFlight = httplib.HTTPSConnection(Globals.httpsAddr, Globals.httpsPort, timeout=Globals.timeOut)
			headers = Globals.xapikey
			headers['Authorization'] = "Bearer {}".format(Globals.myToken)
			connectFlight.request('POST', '/flight/v2/{}/delete'.format(flightID), '', headers)
        		result = connectFlight.getresponse().read()
			parsed_json = json.loads(result)
			parsed_status = parsed_json['status']
        		if parsed_status != "success":
				return False
			else:
				return True
		except:
        		print "End Flight Error..."
			traceback.print_exc()


