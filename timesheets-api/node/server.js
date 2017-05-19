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

// Enable CORS
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

// Batch upload endpoint
app.post('/timesheets/upload', checkJwt, jwtAuthz(['batch:upload']), function(req, res){
  var timesheet = req.body;

  // determine id for new timesheet
  var max = Math.max(...timesheets.map(elt => elt.id))
  timesheet.id = max + 1;

  // append the timesheet
  timesheets.push(req.body);

  //send the response
  res.status(201).send(timesheet);
});

// create timesheets API endpoint
app.post('/timesheets', checkJwt, jwtAuthz(['create:timesheets']), function(req, res){
  var timesheet = req.body;

  // determine id for new timesheet
  var max = Math.max(...timesheets.map(elt => elt.id))
  timesheet.id = max + 1;
  timesheet.user_id = req.user.sub;

  // append the timesheet
  timesheets.push(req.body);

  //send the response
  res.status(201).send(timesheet);
});

// create timesheets API endpoint
app.get('/timesheets', checkJwt, jwtAuthz(['read:timesheets']), function(req, res) {
  // Get timesheet entries for this user
  var userEntries = timesheets.filter(entry => entry.user_id === req.user.sub);

  //send the response
  res.status(200).send(userEntries);
});

// launch the API Server at localhost:8080
app.listen(8080);
console.log('Listening on http://localhost:8080');