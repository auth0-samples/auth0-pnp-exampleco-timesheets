# Angular Single Page Application

This folder includes the Single Page Application (SPA) implementation using [Angular](https://angular.io/).

## Prerequisites

- Auth0 account
- [Node Package Manager (NPM)](https://docs.npmjs.com/cli/version)

## Set the configuration values

Rename the `auth0-variables.ts.example` file in the `src/app/auth` folder to `auth0-variables.ts` Once you have renamed the file you should set the following values in your new `auth0-variables.ts` file:

- `{DOMAIN}`: Set this to the value of your Auth0 Domain. You can retrieve it from the *Settings* of your Client at the [Auth0 Dashboard](https://manage.auth0.com/#/clients).
- `{CLIENT_ID}`: Set this to the value for your Auth0 Client. You can retrieve it from the *Settings* of your Client at the [Auth0 Dashboard](https://manage.auth0.com/#/clients).
- `{API_IDENTIFIER}`: Set this to the value of your API Identifier. You can retrieve it from the *Settings* of your API at the [Auth0 Dashboard](https://manage.auth0.com/#/apis).

## Deploy & Run

To test this application, you will need to also configure and run the corresponding API. Please see the [README.md](../../timesheets-api/node/README.md) for the API for instructions on how to configure and run the API.

Once the API is running, you can open a terminal window to the folder in which this README.md is (`/timesheets-spa/angular`) and install the required packages for the Angular SPA by running:

```
npm i
```

Once the packages are installed, you can then run the Angular app:

```
npm start
```

The application will be served at `http://localhost:4200`.

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

