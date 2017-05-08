interface AuthConfig {
  clientID: string;
  domain: string;
  callbackURL: string;
  apiUrl: string;
}

export const AUTH_CONFIG: AuthConfig = {
  clientID: 'GPHUxX0z26sOGWvsfBvtssXIP0zpYRTk',
  domain: 'auth0pnp.auth0.com',
  callbackURL: 'http://localhost:4200/callback',
  apiUrl: 'https://api.abcinc.com/timesheets'
};
