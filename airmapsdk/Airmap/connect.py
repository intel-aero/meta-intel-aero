"""
  connect
  AirMapSDK

  Created by AirMap Team on 6/28/16.
  Copyright (c) 2016 AirMap, Inc. All rights reserved.
"""

# connect.py -- Connect API functions

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

class Connect:
	
	os = __import__('os')
	"""OS access

 	:notes: Run sys commands 
    	"""
	connection = None
	"""Connection instance

 	:notes: HTTPS access variable 
    	"""
	headers = None
	"""Connection headerssecurity and format

 	:notes: Security and format headers
    	"""
	thisGlobals = Globals()
	"""Global parameter access

 	:notes: Endpoint address, ports, token, id(s)
    	"""

	def __init__(self):
		"""Initialize connect find os set ssl context, get IP address

    		:param None:
    		:returns: None

		:todo: Add ifconfig command to try to get ip address
    		"""

		try:
			if self.os.name == 'nt':
				ssl.create_default_context()
			elif self.os.name == 'posix':
				ssl._create_default_https_context = ssl._create_unverified_context
			else:
				ssl._create_default_https_context = ssl._create_unverified_context

		except Exception,e:
			pass

		try:
			print socket.gethostname()
			thisIP = "IP Address: " + socket.gethostbyname(socket.gethostname())
			Globals.strPrint (self.thisGlobals, thisIP)
			
		except Exception,e:
			Globals.strPrint (self.thisGlobals, "No IP Found...")

	def set_Timeout(self, time_out):
		"""Set https request timeout time

    		:param time_out: Timeout time in seconds
    		:returns: True - Success, False - Fail
    		"""
		try:
			Globals.timeOut = time_out
			return True
		except:
			return False

	def set_XAPIKey(self, xapikey):
		"""Set https request timeout time

    		:param xapikey: X-API-Key from developers account
    		:returns: True - Success, False - Fail
    		"""
		try:
			Globals.xapikey = xapikey
			return True
		except:
			return False
	
	def get_CIDID(self):
		"""Retrieve CID from mmcblk0

    		:param: None
    		:returns: CID otherwise False
    		"""
		try:
			Globals.thisCID = subprocess.check_output('cat /sys/block/mmcblk0/device/cid', shell=True)
			return Globals.thisCID
		except:
			traceback.print_exc()
			return False


	def connect(self):
		"""Connect to service

    		:param: None
    		:returns: True - if connected otherwise False
    		"""
		Globals.AirConnected = False
		try:
			self.connection = httplib.HTTPSConnection(Globals.httpsAddr, Globals.httpsPort, timeout=Globals.timeOut)
			self.headers = Globals.xapikey
			Globals.AirConnected = True	
		except Exception,e:
			Globals.strPrint (self.thisGlobals, "Not Connected... Check Device...")
		return Globals.AirConnected

	def get_SecureToken(self):
		"""Retrieve security token and refresh

    		:param: None
    		:returns: Token if successful otherwise False

		:todo: Remove hardcoded token and add token from https endpoint based on CID
    		"""
		try:
			connectAuth0 = httplib.HTTPSConnection(Globals.keyAddr, Globals.httpsPort, timeout=Globals.timeOut)
			headers = Globals.xapikey
			connectAuth0.request('POST', '/delegation', json.dumps({"refresh_token":"ezKrfuSeSD8DA7w2Dq7gqsL10sYuKdVEXA6BIIJLEAJQh","grant_type":"urn:ietf:params:oauth:grant-type:jwt-bearer","client_id":"2iV1XSfdLJNOfZiTZ9JGdrNHtcNzYstt","api_type":"app"}), headers)
        		result = connectAuth0.getresponse().read()
        		#print(result)
			parsed_json = json.loads(result)
			Globals.myToken = parsed_json['id_token']
			#print Globals.myToken
		except:
        		print "OAuth2 Error..."
			traceback.print_exc()
        		return False
