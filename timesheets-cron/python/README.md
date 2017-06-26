# Python Cron

## Overview

This sample application is part of the Auth0 [Architecture Scenarios](https://auth0.com/docs/architecture-scenarios). For more information about implementing this application, please read the following Architecture Scenario document:

* [Server + API](https://auth0.com/docs/architecture-scenarios/application/server-api)

## Implementation

This folder includes the server process implementation using Python.

In order to keep this sample simple, and focus on the authentication and authorization part of the implementation, a single hard-coded timesheet entry is sent to the API. Also, the result is printed in the console, something you wouldn't do with a server running process.

## Prerequisites

- Auth0 account
- Python 2.7.10

## Set the configuration values

You should set the following values at the `cron.py`:

- `DOMAIN`: Set this to the value of your Auth0 Domain. You can retrieve it from the *Settings* of your Client at the [Auth0 Dashboard](https://manage.auth0.com/#/clients).

- `API_IDENTIFIER`: Set this to the value of your API Identifier. You can retrieve it from the *Settings* of your API at the [Auth0 Dashboard](https://manage.auth0.com/#/apis).

- `CLIENT_ID`: Set this to the value of your Auth0 Client's Id. You can retrieve it from the *Settings* of your Client at the [Auth0 Dashboard](https://manage.auth0.com/#/clients).

- `CLIENT_SECRET`: Set this to the value of your Auth0 Client's Secret. You can retrieve it from the *Settings* of your Client at the [Auth0 Dashboard](https://manage.auth0.com/#/clients).


## Deploy & Run

Make sure that the API is running. If you using our [sample Node API](https://github.com/auth0-samples/auth0-pnp-abc-timesheets/tree/master/timesheets-api/node), open a terminal, navigate to the API's directory (`node`) and run:

```
node server
```

> Refer to the [README](../../timesheets-api/node/README.md) for the server implementation for more information on running that sample

To run the python process, navigate to its directory and run:

```
python cron.py
```

## Test

We assume that you already have an Auth0 account and you have configured your API in the dashboard.

Once you run the python process, and provided that the API is running and the access token is valid, you will get a response similar to the following:

```text
Created timesheet 114 for employee 007
```

If the access token is not valid, the response will be:

```text
Missing or invalid token
```

If the API is not running or there is a problem reaching it, the output can be:

```text
URLError = [Errno 10061] No connection could be made because the target machine actively refused it
```

or

```text
HTTPError = 404 Not Found
```

or some other generic exception.
