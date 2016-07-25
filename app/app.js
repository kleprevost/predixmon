'use strict';
var appname = 'My Awesome Microapp';

var express = require('express');
//var rp = require('request-promise');
var errors = require('request-promise/errors');
var serveStatic = require('serve-static');
var proxy = require('express-request-proxy');
var debug = require('debug')('app');
var app = express();
var router = express.Router();

var endPoints ={};
var assetPath;
var userPath;
var vcap;

if(process.env.VCAP_SERVICES){
  vcap= JSON.parse(process.env.VCAP_SERVICES);
}


if(vcap){
  var userProvided = vcap["user-provided"];
  if(userProvided){
    userProvided.forEach(function (arrayItem){
      endPoints[arrayItem.name] = arrayItem.credentials.uri;
    });
  }
}


if(endPoints && endPoints["apm-ext-microservice-team26"]){
  assetPath = "https://apm-ext-microservice-team26.run.aws-usw02-pr.ice.predix.io/v1";
}
else {
  assetPath = "https://apm-ext-microservice-team26.run.aws-usw02-pr.ice.predix.io/v1";
}

//app.use(serveStatic('public'));
function setCacheControl(res, path) {
    res.setHeader('Cache-Control', 'public, max-age=604800');
}
app.use('/', serveStatic('public', {
	setHeaders: setCacheControl
}));

// Note: If you're not running behind an app hub, you'll need to add an
//       'Authorization' and tenant headers to any authenticated service requests.
//       For example, if you set an environment variables called
//       'AUTHTOKEN' and TENANT you need to get Headers as below:
function getHeaders() {
   if(process.env.AUTHTOKEN && process.env.TENANT) {
       let headers = {
         'Authorization': process.env.AUTHTOKEN,
         'tenant': process.env.TENANT
        }
        console.log('Headers:', headers);
        return headers;
   }
   console.log('Headers: null');
    return null;
}
let myHeaders = getHeaders();

app.use('/api/*', (req, res, next) => {
  proxy({
    url: assetPath + '/*',
    headers: myHeaders,
    timeout: parseInt(req.headers.timeout) || 3600000
  })(req, res, next);
});

app.use(function (req, res, next) {
  console.log('page not found - request url', req.hostname + ':' + port + req.originalUrl);
  res.render('not-found.html');
});

app.use(function (err, req, res, next) {
  console.log('error - request url', req.hostname + ':' + port + req.originalUrl);
  console.error('err', err);
  res.render('error.html');
});

// Need to let CF set the port if we're deploying there.
var port = process.env.PORT || 9000;
app.listen(port);
console.log(appname + ' started on port ' + port);
