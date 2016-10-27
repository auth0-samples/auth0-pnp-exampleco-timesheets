'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.JwksClient = undefined;

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _debug = require('debug');

var _debug2 = _interopRequireDefault(_debug);

var _request = require('request');

var _request2 = _interopRequireDefault(_request);

var _ArgumentError = require('./errors/ArgumentError');

var _ArgumentError2 = _interopRequireDefault(_ArgumentError);

var _JwksError = require('./errors/JwksError');

var _JwksError2 = _interopRequireDefault(_JwksError);

var _SigningKeyNotFoundError = require('./errors/SigningKeyNotFoundError');

var _SigningKeyNotFoundError2 = _interopRequireDefault(_SigningKeyNotFoundError);

var _utils = require('./utils');

var _wrappers = require('./wrappers');

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var JwksClient = exports.JwksClient = function () {
  function JwksClient(options) {
    var _this = this;

    _classCallCheck(this, JwksClient);

    this.getSigningKey = function (kid, cb) {
      _this.logger('Fetching signing key for \'' + kid + '\'');

      _this.getSigningKeys(function (err, keys) {
        if (err) {
          return cb(err);
        }

        var key = keys.find(function (k) {
          return k.kid === kid;
        });
        if (key) {
          return cb(null, key);
        } else {
          _this.logger('Unable to find a signing key that matches \'' + kid + '\'');
          return cb(new _SigningKeyNotFoundError2.default('Unable to find a signing key that matches \'' + kid + '\''));
        }
      });
    };

    this.options = _extends({ rateLimit: false, cache: false, strictSsl: true }, options);
    this.logger = (0, _debug2.default)('jwks');

    // Initialize wrappers.
    if (this.options.rateLimit) {
      this.getSigningKey = (0, _wrappers.rateLimitSigningKey)(this, options);
    }
    if (this.options.cache) {
      this.getSigningKey = (0, _wrappers.cacheSigningKey)(this, options);
    }
  }

  _createClass(JwksClient, [{
    key: 'getKeys',
    value: function getKeys(cb) {
      var _this2 = this;

      this.logger('Fetching keys from \'' + this.options.jwksUri + '\'');
      (0, _request2.default)({ json: true, uri: this.options.jwksUri, strictSSL: this.options.strictSsl }, function (err, res) {
        if (err || res.statusCode < 200 || res.statusCode >= 300) {
          _this2.logger('Failure:', res && res.body || err);
          if (res) {
            return cb(new _JwksError2.default(res.body && (res.body.message || res.body) || res.statusMessage || 'Http Error ' + res.statusCode));
          }
          return cb(err);
        }

        _this2.logger('Keys:', res.body.keys);
        return cb(null, res.body.keys);
      });
    }
  }, {
    key: 'getSigningKeys',
    value: function getSigningKeys(cb) {
      var _this3 = this;

      this.getKeys(function (err, keys) {
        if (err) {
          return cb(err);
        }

        if (!keys || !keys.length) {
          return cb(new _JwksError2.default('The JWKS endpoint did not contain any keys'));
        }

        var signingKeys = keys.filter(function (key) {
          return key.use === 'sig' && key.kty === 'RSA' && key.kid && (key.x5c && key.x5c.length || key.n && key.e);
        }).map(function (key) {
          if (key.x5c && key.x5c.length) {
            return { kid: key.kid, nbf: key.nbf, publicKey: (0, _utils.certToPEM)(key.x5c[0]) };
          } else {
            return { kid: key.kid, nbf: key.nbf, rsaPublicKey: (0, _utils.rsaPublicKeyToPEM)(key.n, key.e) };
          }
        });

        if (!signingKeys.length) {
          return cb(new _JwksError2.default('The JWKS endpoint did not contain any signing keys'));
        }

        _this3.logger('Signing Keys:', signingKeys);
        return cb(null, signingKeys);
      });
    }
  }]);

  return JwksClient;
}();