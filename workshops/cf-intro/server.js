var expressAPI = require('express'); // Express API inladen
var httpAPI = require('http'); // HTTP API inladen
var socketioAPI = require('socket.io'); // socket IO API inladen

var app = expressAPI();  // je slaat een nieuwe express app in variable app op
var httpServer = httpAPI.Server(app); // creeer een nieuwe HTTP server voor de express app
var io = socketioAPI(httpServer); // creeer een nieuwe socket IO object voor deze HTTP server

app.use(expressAPI.static('public'));                      // verwijzen waar publieke bestanden staan - zijn voor iedereen toegankelijk

var sockets = [];                                       // hier staat de client informatie in (IP, poort, socket, etc)
var gamedata = {                                        // gamedata is een dataobject ---> hij heeft 3 objecten namelijk players, keuzes, winner
    players: [],
    keuzes: [],
    winner: undefined                                   // heeft nog geen waarde
};
var chatMessages = [];                                  // hier worden berichten opgeslagen

io.on('connection', function (socket) {                 // dat activeert wanneer klant verbonden wordt. io wilt input/output zeggen
    console.log('New connection: ' + socket);           // socket is een browser connectie met de server
    sockets.push(socket);                               // in de array van sockets voegen we de nieuwe socket toe. push wilt zetten zeggen
    socket.on('disconnect', function () {
        console.log('Socket ' + socket + ' disconnected!');                 // klant sluit de browser of gaat naar een andere website
    });

    socket.on('naam', function(name){
        console.log('nieuwe bezoeker');
        emitToAll('netVerbonden', 'nieuwe bezoeker: ' + name);
    });

    socket.on('play', function (name) {             // socket.on --> server wacht tot speler op een 'PLAY' drukt
        console.log('Player ' + name + ' wants to play!');
        if (gamedata.players.length < 2) {              // er mogen maximum 2 spelers zijn
            gamedata.players.push(name);                // voeg de speler zijn naam toe aan de lijst van spelers
            for (var i = 0; i < sockets.length; i++) {  // stuur naar alle connecties een bericht dat een speler is toegevoegd
                if (gamedata.players.length == 1) {     // speler 1 is net toegevoegd
                    sockets[i].emit('play', {           // als speler 1 er is, stuur naar alle connecties --> naam, number
                        name: name,                     // je hebt je naam ingetypt in client.js
                        number: 1,
                        self: sockets[i] == socket      // je kan zelf ook je name en number zien
                    });
                } else {                                // speler 2 is net toegevoegd
                    sockets[i].emit('play', {           // laat name, number van speler 2 zien aan alle connecties
                        name: name,
                        number: 2,
                        self: sockets[i] == socket      // speler 2 kan zelf zijn name en number zien
                    });
                }
            }
        } else {
            console.log('Player ' + name + ' cannot play because there are already 2 players!'); // als er in de array players al 2 namen heeft. dan staat die text in de console
        }
    });
    socket.on('chat', function (chatBericht) {                           // als we van de socket een 'chat' bericht krijgen, dan wordt de onderstaande functie uitgevoerd
        console.log('Chat from socket ' + socket + ': ' + chatBericht);     //in command line verschijn "chat from socket .naam. : .bericht."
        chatMessages.push(chatBericht);                                     // berichten worden opgeslagen in de variable chatMessage, push wilt zeggen zetten
        for (var i = 0; i < sockets.length; i++) {
            sockets[i].emit('chat', chatBericht);                            //een variable eensocket stuurt berichten naar verschillende klanten
        }
    });
    socket.on('gamedata', function (data) {                           // als we van de socket een 'gamedata' bericht krijgen, dan wordt de onderstaande functie uitgevoerd
        console.log('Game data ontvangen van speler ' + data.player.number + ': ' + data.keuze);     // in command line verschijn "chat from socket .naam. : .bericht."
        gamedata.keuzes[data.player.number - 1] = data.keuze; // wordt opgeslagen in gamedata -> welke speler keuze heeft gemaakt(schaar, steen,...) en -1 -> omdat array met 0 begint
        if (gamedata.keuzes[0] != undefined && gamedata.keuzes[1] != undefined) {           // keuzes mogen niet leeg zijn
            gamedata.winner = determineWinner(gamedata.keuzes[0], gamedata.keuzes[1]) ? 0 : 1;
            emitToAll('gamedata', gamedata);
            console.log('Winner: ' + (gamedata.winner ? 'Speler 1' : 'Speler 2'));      // vraagteken wilt zeggen wie wint het

            console.log('reset game');
            gamedata = {                                        // gamedata is een dataobject ---> hij heeft 3 objecten namelijk players, keuzes, winner
                players: [],
                keuzes: [],
                winner: undefined                                   // heeft nog geen waarde
            };
        }
    });
});

httpServer.listen(3000, function () {                                             //je kiest de poort 3000
    console.log('listening on *:3000');                                     //in command line verschijn string 'listening on *:3000'
});


function emitToAll(messageType, message) {            //je kan nu altijd emitToAll gebruiken in plaats van sockets.emit() --> gemakkelijker
    for (var i = 0; i < sockets.length; i++) {
        sockets[i].emit(messageType, message);
    }
}

function determineWinner(choice1, choice2) {
    if (choice1 == choice2) {                               //gelijk
        return undefined;
    }
    switch (choice1) {
        case 1:
            return choice2 == 3 || choice2 == 4;
        case 2:
            return choice2 == 1 || choice2 == 5;
        case 3:
            return choice2 == 2 || choice2 == 4;
        case 4:
            return choice2 == 2 || choice2 == 5;
        case 5:
            return choice2 == 1 || choice2 == 3;
        default:
        {
            console.log(choice1 + 'is an invalid choice!');
            for (var i = 0; i < sockets.length; i++) {
                sockets[i].emit('gamedata', choice1 + 'is an invalid choice!');
            }
            chatMessages.push(choice1 + 'is an invalid choice!');
            emitToAll('gamedata', choice1 + 'is an invalid choice!');
            return undefined;

        }
    }
}


