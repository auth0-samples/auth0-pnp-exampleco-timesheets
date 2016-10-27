'use strict';

var _JwksClient = require('./JwksClient');

var _errors = require('./errors');

var errors = _interopRequireWildcard(_errors);

var _hapi = require('./integrations/hapi');

var _express = require('./integrations/express');

function _interopRequireWildcard(obj) { if (obj && obj.__esModule) { return obj; } else { var newObj = {}; if (obj != null) { for (var key in obj) { if (Object.prototype.hasOwnProperty.call(obj, key)) newObj[key] = obj[key]; } } newObj.default = obj; return newObj; } }

module.exports = function (options) {
  return new _JwksClient.JwksClient(options);
};

module.exports.ArgumentError = errors.ArgumentError;
module.exports.JwksError = errors.JwksError;
module.exports.JwksRateLimitError = errors.JwksRateLimitError;
module.exports.SigningKeyNotFoundError = errors.SigningKeyNotFoundError;

module.exports.expressJwtSecret = _express.expressJwtSecret;
module.exports.hapiJwt2Key = _hapi.hapiJwt2Key;