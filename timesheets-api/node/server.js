// set dependencies
const Express = require('express');
const jwt = require('express-jwt');
const jwksRsa = require('jwks-rsa');
const bodyParser = require('body-parser');

// Initialize the app
const app = new Express();
app.use(jwt({
  // Dynamically provide a signing key based on the kid in the header and the singing keys provided by the JWKS endpoint
  secret: jwksRsa.expressJwtSecret({
    cache: true,
    rateLimit: true,
    jwksRequestsPerMinute: 5,
    jwksUri: `https://{YOUR_AUTH0_DOMAIN}/.well-known/jwks.json`
  }),

  // Validate the audience and the issuer
  audience: '{YOUR_API_IDENTIFIER}',
  issuer: 'https://{YOUR_AUTH0_DOMAIN}/',
  algorithms: [ 'RS256' ]
}));

//middleware to check scopes
const checkPermissions = function(req, res, next){
	switch(req.path){
		case '/timesheet':{
			var permissions = ['create:timesheets'];
			for(var i = 0; i < permissions.length; i++){
				if(req.user.scope.includes(permissions[i])){
					next();
				} else {
					res.status(403).send({message:'Forbidden'});
				}
			}
			break;
		}
	}
}

//enable the use of the checkPermissions middleware
app.use(checkPermissions);

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
	//print the posted data
	console.log(JSON.stringify(req.body, null, 2));

	//send the response
	res.status(201).send({message:"Timesheet created for " + req.body.user_type + ": " + req.body.user_id});
})


// launch the API Server at localhost:8080
app.listen(8080);
