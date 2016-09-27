"""
  statusAPI
  AirMapSDK

  Created by AirMap Team on 6/28/16.
  Copyright (c) 2016 AirMap, Inc. All rights reserved.
"""
# statusAPI.py -- Status API functions

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

class Status:
	
	os = __import__('os')
	connection = None
	headers = None
	status_json = None
	localAdvisories = []
	localProperties = []
	localLevelDown = []
	thisGlobals = Globals()
	

	def levelDown(self, data):
		self.localLevelDown = []
		for Level in data:
			#print Level
			for entries in Level:
    				self.localLevelDown.append(Properties(entries, Level[entries]))
				#print str(entries) + " : " + str(Level[entries])

	def __init__(self):
		pass

	def get_status(self,gps_lat,gps_lon,weather):
		if Globals.AirConnected:
			try:
				self.connection = httplib.HTTPSConnection(Globals.httpsAddr, Globals.httpsPort, timeout=Globals.timeOut)
				self.headers = Globals.xapikey
				self.connection.request('GET', '/status/v2/point?latitude='+gps_lat+'&longitude='+gps_lon+'&weather='+weather, '', self.headers)
        			result = self.connection.getresponse().read()
				self.status_json = json.loads(result)
				Globals.strPrint (self.thisGlobals,self.status_json)
				return (self.status_json['status'] == "success")
			except Exception,e:
				Globals.strPrint (self.thisGlobals,"Error: Request Timeout..")
				Globals.strPrint(self.thisGlobals,str(e))
			
			return False
				
		else:
			Globals.strPrint (self.thisGlobals,"Not Connected")

		return False

	def get_StatusColor(self):
		try:
			return self.status_json['data']['advisory_color']
		except:
			return False

	def get_StatusCode(self):
		try:
			return Advisory.Color.Colors.reverse_mapping[self.status_json['data']['advisory_color']]
		except:
			return False

	def get_MaxDistance(self):
		try:
			return self.status_json['data']['max_safe_distance']
		except:
			return False

	def get_Temperature(self):
		try:
			return self.status_json['data']['weather']['temperature']
		except:
			return False

	def get_Visibility(self):
		try:
			return self.status_json['data']['weather']['visibility']
		except:
			return False

	def get_Humidity(self):
		try:
			return self.status_json['data']['weather']['humidity']
		except:
			return False

	def get_Condition(self):
		try:
			return self.status_json['data']['weather']['condition']
		except:
			return False

	def get_Precipitation(self):
		try:
			return self.status_json['data']['weather']['precipitation']
		except:
			return False

	def get_WindGusting(self):
		try:
			return self.status_json['data']['weather']['wind']['gusting']
		except:
			return False

	def get_WindSpeed(self):
		try:
			return self.status_json['data']['weather']['wind']['speed']
		except:
			return False

	def get_WindHeading(self):
		try:
			return self.status_json['data']['weather']['wind']['heading']
		except:
			return False

	def cmd_ProcessAdvisories(self):
		advIndex = 0
		for Advise in self.status_json['data']['advisories']:
			#propIndex = 0
			self.localProperties = []
			for Property in Advise['properties']:
				if str(Advise['properties'][Property])[0] == '[':
					self.levelDown (Advise['properties'][Property])
					self.localProperties.append(Properties(Property,self.localLevelDown ))
				else:
					self.localProperties.append(Properties(Property, Advise['properties'][Property]))
				#print self.localProperties[propIndex].prop_name
				#print self.localProperties[propIndex].prop_value
				#propIndex += 1
			self.localAdvisories.append(Advisories(Advise['distance'],Advise['last_updated'],Advise['name'],Advise['city'],
			Advise['color'],Advise['country'],Advise['longitude'],self.localProperties,Advise['state'],Advise['latitude'],Advise['type'],Advise['id']))
			#print self.localAdvisories[advIndex].city
			#print Advise['distance']
			advIndex += 1
		return True

	def get_Advisories(self):
		return self.localAdvisories

	def get_Advisory(self, id):
		try:
			return self.localAdvisories[id]
		except:
			return False


