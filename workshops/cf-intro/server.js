var express = require('express');                       //je wilt express gebruiken
var app = express();                                    // je sla express in variable app op
var http = require('http').Server(app);
var io = require('socket.io')(http);

app.use(express.static('public'));                      //verwijzen waar bestanden staan

var sockets = [];                                       //hier staan de gegevens van een gebruiker op als ze net verbonden zijn

io.on('connection', function (socket) {                 //dat activeert wanneer klant verbonden wordt. io wilt verbinden zeggen
    console.log('New connection: ' + socket);           //socket is browser
    sockets.push(socket);                               //server stuurt bericht naar de klant
    socket.on('disconnect', function () {
        console.log('Socket ' + socket + ' disconnected!');                 //klant verlaat
        //sockets(socket);
    });
    socket.on('chat', function (chatBericht) {                           //als function(chatBericht) wordt geactiveerd, dan wordt tussen de {} uitgevoerd
        console.log('Chat from socket ' + socket + ': ' + chatBericht);     //in command line verschijn "chat from socket .naam. : .bericht."
        for (var eenSocket in sockets) {
            eenSocket.emit('chat', chatBericht);                            //een variable eensocket stuurt berichten naar verschillende klanten
        }
    });
});

http.listen(3000, function () {                                             //je kiest de poort 3000
    console.log('listening on *:3000');                                     //in command line verschijn string 'listening on *:3000'
});