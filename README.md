# Auth0 Server + API Sample
This is the sample project for the Server + API Patterns & Practices document.

In this scenario we will build a Timesheet API for a fictitious company named ABC Inc. The API is meant to allow various clients to add timesheet entries for an employee or a contractor.

The sample includes:
- An [API](https://github.com/auth0-samples/auth0-pnp-abc-timesheets/tree/master/timesheets-api) with a `POST /timesheet` endpoint, used to add timesheet entries.
- A Cron job that adds timesheets using the `POST /timesheet` endpoint and an Access Token retrieved by Auth0, following the client's authentication.

The implementation will use Auth0 and the OAuth 2.0 Client Credentials Grant flow.

In order to use this sample you need to [sign up](https://auth0.com/signup) for you free Auth0 account.

## What is Auth0?

Auth0 helps you to:

* Add authentication with [multiple authentication sources](https://docs.auth0.com/identityproviders), either social like **Google, Facebook, Microsoft Account, LinkedIn, GitHub, Twitter, Box, Salesforce, among others**, or enterprise identity systems like **Windows Azure AD, Google Apps, Active Directory, ADFS or any SAML Identity Provider**.
* Add authentication through more traditional **[username/password databases](https://docs.auth0.com/mysql-connection-tutorial)**.
* Add support for **[linking different user accounts](https://docs.auth0.com/link-accounts)** with the same user.
* Support for generating signed [JSON Web Tokens](https://docs.auth0.com/jwt) to call your APIs and **flow the user identity** securely.
* Analytics of how, when and where users are logging in.
* Pull data from other sources and add it to the user profile, through [JavaScript rules](https://docs.auth0.com/rules).

## Create a free account in Auth0

1. Go to [Auth0](https://auth0.com) and click Sign Up.
2. Use Google, GitHub or Microsoft Account to login.

## Issue Reporting

If you have found a bug or if you have a feature request, please report them at this repository issues section. Please do not report security vulnerabilities on the public GitHub issue tracker. The [Responsible Disclosure Program](https://auth0.com/whitehat) details the procedure for disclosing security issues.

## Author

[Auth0](auth0.com)

## License
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
