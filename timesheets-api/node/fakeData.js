const faker = require('faker');
const _ = require('lodash');

exports.timesheets = function() {
    return _.times(100, function(n) {
            return {
                id: n,
                user_id: faker.internet.email(),
                date: faker.date.recent(),
                project: faker.random.arrayElement(['StoreZero', 'Auth0 Dashboard']),
                hours: faker.random.number({min:4, max:8}),
                approved: false
            }
        })
}