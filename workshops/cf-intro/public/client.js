var socket = io();
var player = {};
$('#chatFormulier').submit(function () {                // als submit geactiveerd word dan wordt tussen {} uitgevoerd
    var chatVeld = $('#chatBericht');
    socket.emit('chat', chatVeld.val());       // stuurt berichten naar de server
    chatVeld.val(''); // maakt het chatveld leeg
    return false;
});
$('#playFormulier').submit(function () {                // als submit geactiveerd word dan wordt tussen {} uitgevoerd
    var naamVeld = $('#naam');
    socket.emit('play', naamVeld.val());              // stuurt berichten naar de server
    naamVeld.hide();
    return false;// dit moet je bijtypen als je submit gebruikt, om aan te duiden dat het formulier niet opnieuw moet worden opgestuurd.
});


socket.on('play', function(data) {
    var chatPanel = $('gesprek');
    var chatMessages = $('messages');
    chatMessages.append($('<li>').text(data));
});


socket.on('chat', function (data) {                  // we krijgen een chatbericht van de server --> {}
    var chatPanel = $('.gesprek');
    var chatMessages = $('#messages');
    chatMessages.append($('<li>').text(getCurrentTime() + ': ' + data));     // berichten zullen onder elkaar geplaatst worden door <li>
    chatPanel.scrollTop(chatMessages.height());// automatisch naar beneden scrollen
});
socket.on('play', function (data) {                                         // we krijgen een chatbericht van de server --> {}
    console.log('Player ' + data.number + ' [' + data.name + '] wil spelen! HOERA!');
    if (data.self) {                                                         // self wilt zeggen dat men zijn eigen data terugkrijgt
        player = data; // de huidige speler is net toegevoegd
    }
    if (data.number == 2) {
        console.log('LET THE GAMES BEGIN !!!');
        $('#playButton').hide();                        //hide() wilt zeggen verbergen
    }
});

function mySteen() {
    document.getElementById("myImg").src = "steen.jpg";
    verstuurKeuzeNaarServer(1);
}
function myPapier() {
    document.getElementById("myImg").src = "http://2.bp.blogspot.com/_uKYhTYLmzIc/TAzeP4oohcI/AAAAAAAAAMg/OtMmLKGVcek/s1600/blad-papier-t10263.jpg";
    verstuurKeuzeNaarServer(2);
}
function mySchaar() {
    document.getElementById("myImg").src = "http://publicdomainvectors.org/photos/nicubunu_Scissors.png";
    verstuurKeuzeNaarServer(3);
}
function myHagedis() {
    document.getElementById("myImg").src = "hagedis.gif";
    verstuurKeuzeNaarServer(4);
}
function mySpock() {
    document.getElementById("myImg").src = "http://cdn.iphonehacks.com/wp-content/uploads/2015/07/Spock-emoji.jpg";
    verstuurKeuzeNaarServer(5);
}

function getCurrentTime() {
    var date = new Date();                                                           // met function kun je altijd gebruiken door naam te typen
    return date.getHours() + ':' + date.getMinutes();
}
                                                                                        // timestamps
function verstuurKeuzeNaarServer(keuze) {
    socket.emit('gamedata', {                             // hier wordt keuze opgeslagen die de player gekozen heeft. wordt naar server gestuurd
        player: player,
        keuze: keuze
    });
}
































