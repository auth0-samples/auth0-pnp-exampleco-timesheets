# Node.js API

This folder includes the API implementation using Node.js and the [Express](http://expressjs.com/) framework.

## Prerequisites

- Auth0 account
- Node.js v6.6.0

## Deploy & Run
Open a terminal, navigate to the API's directory (`node`) and run:

```
node server
```

## Set the configuration values

You should set the following values at the `server.js`:

- `YOUR-AUTH0-DOMAIN`: Set this to the value of your Auth0 Domain. You can retrieve it from the *Settings* of your Client at the [Auth0 Dashboard](https://manage.auth0.com/#/clients).
- `YOUR-API-IDENTIFIER`: Set this to the value of your API Identifier. You can retrieve it from the *Settings* of your API at the [Auth0 Dashboard](https://manage.auth0.com/#/apis).

## Test

We assume that you already have an Auth0 account and you have configured your API in the dashboard.

### Get an Access Token

To ask Auth0 for tokens for any of your authorized client applications, perform a POST operation to the `https://YOUR-AUTH-DOMAIN/oauth/token` endpoint with a payload in the following format:

```json
{
  audience: "{YOUR_API_IDENTIFIER}",
  grant_type: "client_credentials",
  client_id: "{APP_CLIENT_ID}",
  client_secret: "{APP_CLIENT_SECRET}"
}
```

The response payload will be in the following format:

```json
{
  "access_token": "eyJ0eXAiOiJKV1QiKLPhbGciOiJSUzI1NiIsImtpZCI6Ik1qUXlOVFEyTURoR09FVkdNelV4TjBRNU16WkZRMEUxUWpRMk5qVkdSREE0UlVaRU16Y3hNZyJ9.eyJpc3MiOiJodHRwczovL2F1dGgwcG5wLmF1dGgwLmNvbS8iLCJzdWIiOiJzbXJuTnRjYTQ5WjJXdGRDTmRUWGdIekdaeGR4R0xQdUBjbGllbnLsIiwiYXVkIjoiaHR0cHM6Ly9hcGkuYWJjaW5jLmNvbS90aW1lc2hlZXRzIiwiZXhwIjoxNDc3MDI0NzMyLCJpYXQiOjE0NzY5ODg3MzIsInNjb3BlIjoiY3JlYXRlOnRpbWVzaGVldHMifQ.q4Ir77AXNXw0FDHFwzgwSiKMuLWRz7crhOaGE3kQg3wW6YuCiqT0GusKWr2YDDYJPDplgZ11G3l0UXjIFG_0h64JiZEDpHl9v1HwG6UGA9LBDsbS5579xayXcNUTQEcFKI_OFurfwxlgx_1V1QN-JK-SX_XnimBQLUU5X9fZHkNYvn6PMRt-9bZkxza6zMjSqO08Ng-Z8DuJ36BgOyTgkqjbYZrsX0ZJAn6NfrKVMEexBA9vCvIRA7ClSVLSoNB9hulh-S8Hkb21HJrQF5rKl8-YFB5K-sMrilwqjq1aA213yq9PH9kZgURJqR0g-0Rr1g9jR6m180-iAhej8xxN4w",
  "token_type": "Bearer"
}
```

### Invoke the API

To invoke the API start the node process and make a `POST` request to `http://localhost:8080/timesheet`.

You should add an `Authorization` header with the value `Bearer eyJ0eXAiOiJKV1QiKLPhbGciOiJSUzI1NiIsImtpZCI6Ik1qUXlOVFEyTURoR09FVkdNelV4TjBRNU16WkZRMEUxUWpRMk5qVkdSREE0UlVaRU16Y3hNZyJ9.eyJpc3MiOiJodHRwczovL2F1dGgwcG5wLmF1dGgwLmNvbS8iLCJzdWIiOiJzbXJuTnRjYTQ5WjJXdGRDTmRUWGdIekdaeGR4R0xQdUBjbGllbnLsIiwiYXVkIjoiaHR0cHM6Ly9hcGkuYWJjaW5jLmNvbS90aW1lc2hlZXRzIiwiZXhwIjoxNDc3MDI0NzMyLCJpYXQiOjE0NzY5ODg3MzIsInNjb3BlIjoiY3JlYXRlOnRpbWVzaGVldHMifQ.q4Ir77AXNXw0FDHFwzgwSiKMuLWRz7crhOaGE3kQg3wW6YuCiqT0GusKWr2YDDYJPDplgZ11G3l0UXjIFG_0h64JiZEDpHl9v1HwG6UGA9LBDsbS5579xayXcNUTQEcFKI_OFurfwxlgx_1V1QN-JK-SX_XnimBQLUU5X9fZHkNYvn6PMRt-9bZkxza6zMjSqO08Ng-Z8DuJ36BgOyTgkqjbYZrsX0ZJAn6NfrKVMEexBA9vCvIRA7ClSVLSoNB9hulh-S8Hkb21HJrQF5rKl8-YFB5K-sMrilwqjq1aA213yq9PH9kZgURJqR0g-0Rr1g9jR6m180-iAhej8xxN4w`

The body payload should be in the following format:

```json
{
	'user_type': 'Employee',
	'user_id': '007',
	'year': 2016,
	'week': 24,
	'project': 'StoreZero',
	'hours': 40
}
```

You should get a response like the following:

``json
{
  "message": "Timesheet created for Employee: 007"
}
```