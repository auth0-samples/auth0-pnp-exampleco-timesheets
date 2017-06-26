# Node.js API

## Overview

This sample application is part of the Auth0 [Architecture Scenarios](https://auth0.com/docs/architecture-scenarios). For more information about implementing this application, please read either of the following Architecture Scenario documents:

* [Server + API](https://auth0.com/docs/architecture-scenarios/application/server-api)
* [SPA + API](https://auth0.com/docs/architecture-scenarios/application/spa-api)

## Implementation

This folder includes the API implementation using Node.js and the [Express](http://expressjs.com/) framework.

## Prerequisites

- Auth0 account
- Node.js v6.6.0

## Set the configuration values

Rename the `.env.example` file to `.env`. Once you have renamed the file you should set the following values in this file:

- `{DOMAIN}`: Set this to the value of your Auth0 Domain. You can retrieve it from the *Settings* of your Client at the [Auth0 Dashboard](https://manage.auth0.com/#/clients).
- `{API_IDENTIFIER}`: Set this to the value of your API Identifier. You can retrieve it from the *Settings* of your API at the [Auth0 Dashboard](https://manage.auth0.com/#/apis).

## Deploy & Run

Open a terminal and navigate to the folder in which this README.md is (`/timesheets-api/node`). Install the required packages for the Angular SPA by running:

```
npm i
```

Once the packages are installed, you can then run the server:

```
node server
```

## Test

We assume that you already have an Auth0 account and you have configured your API in the dashboard.

You can test the API in conjunction with either the CRON client (located in `/timesheets-cron/python`) or the SPA client (located in `/timesheets-spa/angular`). Alternatively you can test it using a tool with which you can make HTTP requests (such as Postman or CURL) but following the steps below.

### Get an Access Token

To ask Auth0 for tokens for any of your authorized client applications, perform a POST operation to the `https://YOUR-AUTH-DOMAIN/oauth/token` endpoint with a payload in the following format:

```json
{
  "audience": "{API_IDENTIFIER}",
  "grant_type": "client_credentials",
  "client_id": "{CLIENT_ID}",
  "client_secret": "{CLIENT_SECRET}"
}
```

The response payload will be in the following format:

```json
{
  "access_token": "eyJ0eXAiOiJKV1QiKLPhbGciOiJSUzI1NiIsImtpZCI6Ik1qUXlOVFEyTURoR...",
  "token_type": "Bearer"
}
```

### Invoke the API

To invoke the API start the node process and make a `POST` request to `http://localhost:8080/timesheet`.

You should add an `Authorization` header with the value `Bearer eyJ0eXAiOiJKV1QiKLPhbGciOiJSUzI1NiIsImtpZCI6Ik1qUXlOVFEyTURoR...`

The body payload should be in the following format:

```json
{
	"user_type": "Employee",
	"user_id": "007",
	"year": 2016,
	"week": 24,
	"project": "StoreZero",
	"hours": 40
}
```

You should get a response like the following:

```json
{
  "message": "Timesheet created for Employee: 007"
}
```

## What is Auth0?

Auth0 helps you to:

* Add authentication with [multiple authentication sources](https://docs.auth0.com/identityproviders), either social like **Google, Facebook, Microsoft Account, LinkedIn, GitHub, Twitter, Box, Salesforce, amont others**, or enterprise identity systems like **Windows Azure AD, Google Apps, Active Directory, ADFS or any SAML Identity Provider**.
* Add authentication through more traditional **[username/password databases](https://docs.auth0.com/mysql-connection-tutorial)**.
* Add support for **[linking different user accounts](https://docs.auth0.com/link-accounts)** with the same user.
* Support for generating signed [Json Web Tokens](https://docs.auth0.com/jwt) to call your APIs and **flow the user identity** securely.
* Analytics of how, when and where users are logging in.
* Pull data from other sources and add it to the user profile, through [JavaScript rules](https://docs.auth0.com/rules).

## Create a free Auth0 account

1. Go to [Auth0](https://auth0.com/signup) and click Sign Up.
2. Use Google, GitHub or Microsoft Account to login.

## Issue Reporting

If you have found a bug or if you have a feature request, please report them at this repository issues section. Please do not report security vulnerabilities on the public GitHub issue tracker. The [Responsible Disclosure Program](https://auth0.com/whitehat) details the procedure for disclosing security issues.

## Author

[Auth0](auth0.com)

## License

This project is licensed under the MIT license. See the [LICENSE](LICENSE.txt) file for more info.

