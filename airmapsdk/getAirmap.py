"""
  getAirmap
  AirMapSDK - github download

  Created by AirMap Team on 6/28/16.
  Copyright (c) 2016 AirMap, Inc. All rights reserved.
"""

import requests
import sys

#print sys.argv[1]
#print sys.argv[2]

try:
	r = requests.get('https://github.com/'+sys.argv[1]+'/'+sys.argv[2]+'/archive/master.zip')
	#print r
	#print r.headers
	#print r.headers['content-disposition']
	#print r.content
	#print r.text

	tmpFilename = r.headers['content-disposition']
	filename = tmpFilename.split('=')
	#print filename[1]

	filename = filename[1]

	f = open(filename,'wb')
	#f.write(r.text.encode('utf8'))
	f.write(r.content)
except:
	print "Usage: python getAirmap.py gituser gitrepository"

print "Success..."


