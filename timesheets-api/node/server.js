const express = require('express');
const app = express();
const jwt = require('express-jwt');
const jwtAuthz = require('express-jwt-authz');
const jwksRsa = require('jwks-rsa');
const cors = require('cors');
const bodyParser = require('body-parser');
const timesheets = require('./fakeData').timesheets();
require('dotenv').config();

if (!process.env.AUTH0_DOMAIN || !process.env.AUTH0_AUDIENCE) {
  throw 'Make sure you have AUTH0_DOMAIN, and AUTH0_AUDIENCE in your .env file';
}

app.use(cors());

const checkJwt = jwt({
  // Dynamically provide a signing key based on the kid in the header and the singing keys provided by the JWKS endpoint.
  secret: jwksRsa.expressJwtSecret({
    cache: true,
    rateLimit: true,
    jwksRequestsPerMinute: 5,
    jwksUri: `https://${process.env.AUTH0_DOMAIN}/.well-known/jwks.json`
  }),

  // Validate the audience and the issuer.
  audience: process.env.AUTH0_AUDIENCE,
  issuer: `https://${process.env.AUTH0_DOMAIN}/`,
  algorithms: ['RS256']
});

// enable the use of request body parsing middleware
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
  extended: true
}));

// create timesheets API endpoint
app.post('/timesheet', checkJwt, jwtAuthz(['create:timesheets']), function(req, res){
  //print the posted data
  console.log(JSON.stringify(req.body, null, 2));

  //send the response
  res.status(201).send({message:"Timesheet created for " + req.body.user_type + ": " + req.body.user_id});
});

// create timesheets API endpoint
app.get('/timesheet', checkJwt, function(req, res){
  //send the response
  res.status(200).send(timesheets);
});

// launch the API Server at localhost:8080
app.listen(8080);
console.log('Listening on http://localhost:8080');