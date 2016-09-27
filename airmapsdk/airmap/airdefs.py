"""
  airdefs
  AirMapSDK

  Created by AirMap Team on 6/28/16.
  Copyright (c) 2016 AirMap, Inc. All rights reserved.
"""

class Startup:
	"""Startup configuration

	:notes:
    	"""
	class Drone:
		"""Drone information (ID, Location, Status)

    		"""
		def enum(*sequential, **named):
    			enums = dict(zip(sequential, range(len(sequential))), **named)
    			reverse = dict((value, key) for key, value in enums.iteritems())
    			enums['reverse_mapping'] = reverse
			return type('Enum', (), enums)

		State = enum(connect='connect', prearm='prearm', gpslock='gpslock', airstatus='airstatus', arm='arm', takeoff='takeoff')

class Advisory:
	"""Advisoy information (Pre-Flight, In-Flight)

	:notes: Advisory status color list
    	"""
	class Color:
		"""Flight status color code

    		"""
		def enum(*sequential, **named):
    			enums = dict(zip(sequential, range(len(sequential))), **named)
    			reverse = dict((value, key) for key, value in enums.iteritems())
    			enums['reverse_mapping'] = reverse
			return type('Enum', (), enums)

		Colors = enum(gray='gray', green='green', yellow='yellow', red='red')
		"""Color code
		
		:param gray: Disbabled
		:param green: Go
		:param yellow: Advise
		:param red: NoFly
    		"""

class Globals:
	"""Global settings

	:notes: Session parametes address, port, timeot, api key, token, pilot id, flight id
    	"""

	httpsAddr = 'api.airmap.io'
	keyAddr = 'sso.airmap.io'
	telemetryAddr = 'api-aero-telemetry.airmap.com'
	httpsPort = 443
	httpPort = 80
	timeOut = 18
	AirConnected = False
	thisCID = None
	xapikey = None
	myToken = None
	pilot_id = None
	myFlightID = None
	pilotIDValid = False

	def strPrint(self, data):
		"""Information send to console

    		:param data: information to send to console
    		:returns: None
    		"""
    		print str(data) + "\r"

	def dbgPrint(self, data):
		"""Debug send to console

		:param data: Debug or information to send to console
        	:returns: None
        	"""
		if __name__ == '__main__':
			print str(data) + "\r"

class Properties():
	"""Name to Value Keypairs

	"""
	def __init__(self, prop_name, prop_value):
		"""Properties key pair

    		:param prop_name: Pair data name
		:param prop_value: Pair data value
    		:returns: None
    		"""
        	self.prop_name = prop_name
		self.prop_value = prop_value

class Advisories():
	"""Adivosry group list

	:notes: Distance, date of last update, name, city, color(status), country, longitude, properties, state, latitude, type, id
	"""
	def __init__(self, distance, last_updated, name, city, color, country, longitude, properties, state, latitude, type, id):
		"""Advisories key pair

		:param distance: Distance to neareast advisory
		:param last_updated: Advisory date
		:param name: Advisory name
		:param city: Advisory city
		:param color: Green-ok, Yellow-Advise, Red-NoFly
		:param country: Advisory country
		:param longitude: Advisory location longitude
		:param properties: Advisory information
		:param state: Advisory state
		:param latitude: Advisory location latitude
		:param type: Advisory type
		:param id: Advisory unique ID
    		:returns: None
    		"""
        	self.distance = distance
        	self.last_updated = last_updated
		self.name = name
		self.city = city
		self.color = color
		self.country = country
		self.longitude = longitude
		self.properties = properties
		self.state = state
		self.latitude = latitude
		self.type = type
		self.id = id

class Requirement:
	"""Notification requirments contact key pair list

    	"""
	def enum(*sequential, **named):
    		enums = dict(zip(sequential, range(len(sequential))), **named)
    		reverse = dict((value, key) for key, value in enums.iteritems())
    		enums['reverse_mapping'] = reverse
		return type('Enum', (), enums)

	State = enum(digital='digital', phone='phone')

class Weather:
	"""Weather control parameters

	:param on: Enable weather data
	:param off: Disable weather data
    	"""
	on="true"
	off="false"

class Public:
	"""Make flight public

	:param on: Public flight
	:param off: Private flight
    	"""
	on=True
	off=False

class Notify:
	"""Enable notifications

	:param on: Enable notifications
	:param off: Disable notifications
    	"""
	on=True
	off=False

