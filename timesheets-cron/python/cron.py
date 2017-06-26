def main():
    import json
    import urllib
    import urllib2
    import httplib

    # Configuration Values
    domain = "DOMAIN" # Your Auth0 Domain
    api_identifier = "API_IDENTIFIER" # API Identifier of your API
    client_id = "CLIENT_ID" # Client ID of your Non Interactive Client
    client_secret = "CLIENT_SECRET" # Client Secret of your Non Interactive Client
    api_url = "http://localhost:8080/timesheets/upload"
    grant_type = "client_credentials" # OAuth 2.0 flow to use

    # Get an access token from Auth0
    base_url = "https://{domain}".format(domain=domain)
    data = urllib.urlencode({'client_id': client_id,
                             'client_secret': client_secret,
                             'audience': api_identifier,
                             'grant_type': grant_type})
    req = urllib2.Request(base_url + "/oauth/token", data, headers={"Accept": "application/json"})
    response = urllib2.urlopen(req)
    resp_body = response.read()
    oauth = json.loads(resp_body)
    access_token = oauth['access_token']

    # Post new timesheet to API
    timesheet = {'user_id': '007',
                            'date': '2017-05-10T17:40:20.095Z',
                            'project': 'StoreZero',
                            'hours': 5}
    req = urllib2.Request(api_url, data=json.dumps(timesheet))
    req.add_header('Authorization', 'Bearer ' + access_token)
    req.add_header('Content-Type', 'application/json')

    try:
        response = urllib2.urlopen(req)
        res = json.loads(response.read())
        print 'Created timesheet ' + str(res['id']) + ' for employee ' + str(res['user_id'])
    except urllib2.HTTPError, e:
        print 'HTTPError = ' + str(e.code) + ' ' + str(e.reason)
    except urllib2.URLError, e:
        print 'URLError = ' + str(e.reason)
    except httplib.HTTPException, e:
        print 'HTTPException'
    except Exception, e:
        print 'Generic Exception' + str(e)


# Standard boilerplate to call the main() function.
if __name__ == '__main__':
    main()
