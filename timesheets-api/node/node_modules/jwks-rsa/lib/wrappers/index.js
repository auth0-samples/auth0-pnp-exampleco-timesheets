'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.rateLimitSigningKey = exports.cacheSigningKey = undefined;

var _cache = require('./cache');

var _cache2 = _interopRequireDefault(_cache);

var _rateLimit = require('./rateLimit');

var _rateLimit2 = _interopRequireDefault(_rateLimit);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.cacheSigningKey = _cache2.default;
exports.rateLimitSigningKey = _rateLimit2.default;