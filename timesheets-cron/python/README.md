# Python Cron

This folder includes the server process implementation using Python.

In order to keep this sample simple, and focus on the authentication and authorization part of the implementation, a single hard-coded timesheet entry is sent to the API. Also, the result is printed in the console, something you wouldn't do with a server running process.

## Prerequisites

- Auth0 account
- Python 2.7.10

## Set the configuration values

You should set the following values at the `cron.py`:

- `YOUR-AUTH0-DOMAIN`: Set this to the value of your Auth0 Domain. You can retrieve it from the *Settings* of your Client at the [Auth0 Dashboard](https://manage.auth0.com/#/clients).

- `YOUR-API-IDENTIFIER`: Set this to the value of your API Identifier. You can retrieve it from the *Settings* of your API at the [Auth0 Dashboard](https://manage.auth0.com/#/apis).

- `YOUR_CLIENT_ID`: Set this to the value of your Auth0 Client's Id. You can retrieve it from the *Settings* of your Client at the [Auth0 Dashboard](https://manage.auth0.com/#/clients).

- `YOUR_CLIENT_SECRET`: Set this to the value of your Auth0 Client's Secret. You can retrieve it from the *Settings* of your Client at the [Auth0 Dashboard](https://manage.auth0.com/#/clients).


## Deploy & Run

Make sure that the API is running. If you using our [sample Node API](https://github.com/auth0-samples/auth0-pnp-abc-timesheets/tree/master/timesheets-api/node), open a terminal, navigate to the API's directory (`node`) and run:

```
node server
```

To run the python process, navigate to its directory and run:

```
python cron.py
```

## Test

We assume that you already have an Auth0 account and you have configured your API in the dashboard.

Once you run the python process, and provided that the API is running and the access token is valid, you will get the following response:

```text
Timesheet created for Employee: 007
```

If the access token is not valid, the response will be:

```text
Missing or invalid token
```

If the API is not running or there is a problem reaching it, the output can be:

```text
URLError = [Errno 61] Connection refused
```

or

```text
HTTPError = 404 Not Found
```

or some other generic exception.
