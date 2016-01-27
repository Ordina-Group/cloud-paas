var app = require('express')();
var http = require('http').Server(app);

app.get('/', function (request, response) {
    var naam = request.query.naam;
    response.send('<h1>Hello ' + naam + '</h1>');
});

http.listen(3000, function () {
    console.log('listening on *:3000');
});
