const faker = require('faker');
const _ = require('lodash');

exports.timesheets = function() {
    return _.times(100, function(n) {
            return {
                id: n,
                user_type: 'Employee',
                user_id: faker.random.number({min:1, max:20}),
                year: 2016,
                week: faker.random.number({min:1, max:52}),
                project: faker.random.arrayElement(['StoreZero']),
                hours: faker.random.number({min:25, max:50})
            }
        })
}