// set dependencies
var express = require('express');
var app = express();
var jwt = require('express-jwt');
var rsaValidation = require('auth0-api-jwt-rsa-validation');


// validate the access token
var jwtCheck = jwt({
	secret: rsaValidation(),
	algorithms: ['RS256'],
  	issuer: "https://auth0pnp.auth0.com/", // https://${YOUR-AUTH0-DOMAIN}/
  	audience: 'https://api.abcinc.com/timesheets' // should match the identifier you gave to your API
});


// enable the use of the jwtCheck middleware
app.use(jwtCheck);


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

	//create the timesheet

	//send the response
	res.status(200).send({message:'Timesheet created'});
})


// launch the API Server at localhost:8080
app.listen(8080);