var express = require('express');
var app = express();
var http = require('http').Server(app);
var io = require('socket.io')(http);

app.use(express.static('public'));

var sockets = [];

io.on('connection', function (socket) {
    console.log('New connection: ' + socket);
    sockets.push(socket);
    socket.on('disconnect', function () {
        console.log('Socket ' + socket + ' disconnected!');
        //sockets(socket);
    });
    socket.on('chat', function (chatBericht) {
        console.log('Chat from socket ' + socket + ': ' + chatBericht);
        for (var eenSocket in sockets) {
            eenSocket.emit('chat', chatBericht);
        }
    });
});

http.listen(3000, function () {
    console.log('listening on *:3000');
});