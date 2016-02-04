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
    return false;// <-------------------------------- ???????????????
});

socket.on('chat', function (data) {                  // we krijgen een chatbericht van de server --> {}
    voegTekstToeAanChatBox(data);
    var chatPanel = $('.gesprek');
    chatPanel.scrollTop(chatMessages.height());// automatisch naar beneden scrollen
});
socket.on('play', function (data) {                                         // we krijgen een chatbericht van de server --> {}
    voegTekstToeAanChatBox(data.name + ' is speler ' + data.number + '!');
    if (data.self) {                                                         // self wilt zeggen dat men zijn eigen data terugkrijgt
        player = data; // de huidige speler is net toegevoegd
    }
    if (data.number == 2) {
        voegTekstToeAanChatBox('LET THE GAMES BEGIN!');
        $('#playButton').hide();                        //hide() wilt zeggen verbergen
    }
});

keuzes = ["steen.jpg", "http://2.bp.blogspot.com/_uKYhTYLmzIc/TAzeP4oohcI/AAAAAAAAAMg/OtMmLKGVcek/s1600/blad-papier-t10263.jpg"];

function toonKeuze1(data) {
    voegTekstToeAanChatBox(data.players[0] + ' koos [' + data.keuzes[0] + ']');
    $('#img1').src = keuzes[data.keuzes[0]];
}

function toonKeuze2(data) {
    voegTekstToeAanChatBox(data.players[1] + ' koos [' + data.keuzes[1] + ']');
    $('#img2').src = keuzes[data.keuzes[1] - 1];
}

socket.on('gamedata', function (data) {
    var winnaarText = $('#bovensolid2');
    winnaarText.text(data.players[data.winner] + ' is gewonnen!');
    toonKeuze1(data);
    toonKeuze2(data);
    voegTekstToeAanChatBox(data.players[data.winner] + ' is gewonnen!');
});

function mySteen() {
    verstuurKeuzeNaarServer(1);
}
function myPapier() {
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

function voegTekstToeAanChatBox(text) {
    var chatMessages = $('#messages');
    chatMessages.append($('<li>').text(getCurrentTime() + ': ' + text));     // berichten zullen onder elkaar geplaatst worden door <li>
}
// timestamps
function verstuurKeuzeNaarServer(keuze) {
    socket.emit('gamedata', {                             // hier wordt keuze opgeslagen die de player gekozen heeft. wordt naar server gestuurd
        player: player,
        keuze: keuze
    });
}
































