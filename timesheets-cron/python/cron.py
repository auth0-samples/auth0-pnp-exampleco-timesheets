def main():
	import json, urllib, urllib2
	
	# Configuration Values
	DOMAIN = "YOUR-AUTH0-DOMAIN" # Update with your Auth0 Domain
	AUDIENCE = "YOUR-API-IDENTIFIER" # Update with the Identifier of your API
	CLIENT_ID = "YOUR_CLIENT_ID" # Update with the Client ID of your Non Interactive Client
	CLIENT_SECRET = "YOUR_CLIENT_SECRET" # Update with the Client Secret of your Non Interactive Client
	API_URL = "http://localhost:8080/timesheets/upload"
	GRANT_TYPE = "client_credentials" # OAuth 2.0 flow to use
	
	# Get an access token from Auth0
	base_url = "https://{domain}".format(domain=DOMAIN)
	data = urllib.urlencode([('client_id', CLIENT_ID),
	                       ('client_secret', CLIENT_SECRET),
	                       ('audience', AUDIENCE),
	                       ('grant_type', GRANT_TYPE)])
	req = urllib2.Request(base_url + "/oauth/token", data)
	response = urllib2.urlopen(req)
	oauth = json.loads(response.read())
	access_token = oauth['access_token']

	#Post new timesheet to API
	timesheet = {'user_id': '007',
				'date': '2017-05-10T17:40:20.095Z',
				'project': 'StoreZero',
				'hours': 5}
	req = urllib2.Request(API_URL, data = json.dumps(timesheet))
	req.add_header('Authorization', 'Bearer ' + access_token)
	req.add_header('Content-Type', 'application/json')
	
	try: 
	    response = urllib2.urlopen(req)
	    res = json.loads(response.read())
	    print res['message']
	except urllib2.HTTPError, e:
	    print 'HTTPError = ' + str(e.code) + ' ' + str(e.reason)
	except urllib2.URLError, e:
	    print 'URLError = ' + str(e.reason)
	except urllib2.HTTPException, e:
	    print 'HTTPException'
	except Exception:
	    print 'Generic Exception'


# Standard boilerplate to call the main() function.
if __name__ == '__main__':
    main()