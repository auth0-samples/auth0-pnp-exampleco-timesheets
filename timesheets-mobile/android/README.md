# Android Application

## Overview

This sample application is part of the Auth0 [Architecture Scenarios](https://auth0.com/docs/architecture-scenarios). For more information about implementing this application, please read the following Architecture Scenario document:

* [Mobile + API](https://auth0.com/docs/architecture-scenarios/application/mobile-api)

## Implementation

This folder includes the Mobile Application implementation using [Android](https://developer.android.com/index.html) which connects to the [Node.JS API implementation](https://github.com/auth0-samples/auth0-pnp-exampleco-timesheets/tree/master/timesheets-api/node).

## Prerequisites

* Auth0 account ([sign up here!](https://auth0.com/signup))
* [Android Studio](https://developer.android.com/studio/index.html)
* [Nexus 5X virtual device running Marshmallow (API 23) Android 6.0](https://developer.android.com/studio/run/managing-avds.html)
* [Node.JS API implementation](https://github.com/auth0-samples/auth0-pnp-exampleco-timesheets/tree/master/timesheets-api/node)

## Set the configuration values

Update the following configuration values found in the `strings.xml` resource (`/src/main/res/values/strings.xml`):

* `<string name="auth0_domain">...</string>`: Set this to the value of your Auth0 Domain. You can retrieve it from the *Settings* of your Client at the [Auth0 Dashboard](https://manage.auth0.com/#/clients).
* `<string name="auth0_client_id">...</string>`: Set this to the value for your Auth0 Client. You can retrieve it from the *Settings* of your Client at the [Auth0 Dashboard](https://manage.auth0.com/#/clients).

## Start the Node.JS API

Follow the instructions for setting up the [Node.JS API implementation](https://github.com/auth0-samples/auth0-pnp-exampleco-timesheets/tree/master/timesheets-api/node).

After installing the required packages and configuring the `.env` file, start the server by navigating to the Node.JS API folder in your terminal and running `node server`.

## Create Android Virtual Device

In Android Studio, create a Nexus 5X virtual device running Marshmallow (API 23) Android 6.0. For more information on creating Android Virtual Devices, check out the [Create and Manage Virtual Devices](https://developer.android.com/studio/run/managing-avds.html) page on the Android developer documentation.

## Run the application

Open the Mobile App implementation folder in Android Studio. Install the required dependencies by clicking the Synchronize button in the top left corner of Android Studio.

After the dependencies are installed, click the Run button at the top of Android Studio and select the Nexus 5X API 23 device from the list of available virtual devices.

Once the application has loaded on the emulator you can log in as a user ([managing users with the management dashboard](https://auth0.com/docs/user-profile#manage-users-using-the-management-dashboard)), create and view timesheet entries stored on the Node.JS API as well as view your user profile.

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