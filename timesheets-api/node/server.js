// set dependencies
var express = require('express');
var app = express();
var jwt = require('express-jwt');
var rsaValidation = require('auth0-api-jwt-rsa-validation');
var bodyParser = require('body-parser'); // if you get errors try: npm install body-parser


// validate the access token
var jwtCheck = jwt({
	secret: rsaValidation(),
	algorithms: ['RS256'],
  	issuer: "https://YOUR-AUTH0-DOMAIN/", // Update with your Auth0 Domain
  	audience: 'YOUR-API-IDENTIFIER' // Update with the Identifier of your API
});


// enable the use of the jwtCheck middleware
app.use(jwtCheck);

// enable the use of request body parsing middleware
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
  extended: true
}));


// return error message for unauthorized requests
app.use(function (err, req, res, next) {
	if (err.name === 'UnauthorizedError') {
    	res.status(401).json({message:'Missing or invalid token'});
  	}
});


// create timesheets API endpoint
app.post('/timesheet', function(req, res){
	//check if the token includes scope -> create:timesheets
	var permissions = ['create:timesheets'];
	for(var i = 0; i < permissions.length; i++){
		if(!req.user.scope.includes(permissions[i])){
			res.status(403).send({message:'Forbidden'});
		}
	}

	//print the posted data
	console.log(JSON.stringify(req.body, null, 2));

	//send the response
	res.status(201).send({message:"Timesheet created for " + req.body.user_type + ": " + req.body.user_id});
})


// launch the API Server at localhost:8080
app.listen(8080);
