var socket = io();
var player = {};


$('#chatFormulier').submit(function () {                // als submit geactiveerd word dan wordt tussen {} uitgevoerd
    var chatVeld = $('#chatBericht');
    if(chatVeld.val().length < 1){
        console.log('wordt niet gestuurd!');
        return false;                                   //moet blijven staande paginas's
    }else {
        socket.emit('chat', {
            chatBericht: chatVeld.val(),             // stuurt berichten naar de server
            naam: $('#naam').val()
        });
        chatVeld.val('');                                // maakt het chatveld leeg, gaat het vanzelf niet weg
        return false;                               // dit moet je bijtypen als je submit gebruikt, om aan te duiden dat het formulier niet opnieuw moet worden opgestuurd.
    }

});

$('#naamFormulier').submit(function () {                // als submit geactiveerd word dan wordt tussen {} uitgevoerd
    var naamVeld = $('#naam');                        // je moet dit doen want het is gemakkelijker
    socket.emit('naam', naamVeld.val());              // stuurt berichten naar de server
    naamVeld.hide();                                  // naamVeld gaat weg --> je kan niet opnieuw je naam intypen
    var label = $('#naamVanSpeler');
    label.text(naamVeld.val());
    label.show();
    $('#goKnop').hide();
    return false;     // dit moet je bijtypen als je submit gebruikt, om aan te duiden dat het formulier niet opnieuw moet worden opgestuurd.
});

$('#playFormulier').submit(function () {                // als submit geactiveerd word dan wordt tussen {} uitgevoerd
    var naamVeld = $('#naam');                        // je moet dit doen want het is gemakkelijker
    socket.emit('play', naamVeld.val());              // stuurt berichten naar de server
    return false;     // dit moet je bijtypen als je submit gebruikt, om aan te duiden dat het formulier niet opnieuw moet worden opgestuurd.
});



socket.on('netVerbonden', function(data){
    voegTekstToeAanChatBox(data.message);
    if(data.self) {
        $('#chatBericht').removeAttr("disabled");
        $('#send').removeAttr("disabled");

        if(data.players.length < 2){
            $('#playButton').show();
        }
    }
});


socket.on('chat', function (data) {                  // we krijgen een chatbericht van de server --> {}
    voegTekstToeAanChatBox(data);

});
socket.on('play', function (data) {                                         // we krijgen een chatbericht van de server --> {}
    voegTekstToeAanChatBox(data.name + ' is speler ' + data.number + '!');      // je gaat tussen de () in de chatbox zien
    if (data.self) {                                                         // self wilt zeggen dat men zijn eigen data terugkrijgt
        player = data;                                                  // de huidige speler is net toegevoegd,    ????????????????hij kan zijn eigen naam en nummer zien?????????
        $('#playButton').hide();

        $('#mySteen').removeAttr("disabled");
        $('#myPapier').removeAttr("disabled");
        $('#mySchaar').removeAttr("disabled");
        $('#myHagedis').removeAttr("disabled");
        $('#mySpock').removeAttr("disabled");
    }
    if (data.number == 2) {
        voegTekstToeAanChatBox('LET THE GAMES BEGIN!');
        $('#playButton').hide();                                         //hide() wilt zeggen verbergen
    }

    if (data.number == 1) {
        $('#img1').attr('src', '/images/witte_vierkant.png');
        $('#img2').attr('src', '/images/witte_vierkant.png');

        $('#speler2').text('');
    }

    $('#speler'+ data.number).text(data.name);

});

keuzes = ["/images/steen.jpg", "/images/papier.jpg",
    "/images/schaar.png", "/images/hagedis.gif", "/images/spock.jpg"];

function toonKeuze1(data) {
    voegTekstToeAanChatBox(data.players[0] + ' koos [' + data.keuzes[0] + ']');
    $('#img1').attr('src', keuzes[data.keuzes[0] - 1]);
}

function toonKeuze2(data) {
    voegTekstToeAanChatBox(data.players[1] + ' koos [' + data.keuzes[1] + ']');
    $('#img2').attr('src',keuzes[data.keuzes[1] - 1]);
}

socket.on('gamedata', function (data) {
    if (data.keuzes[0] != undefined && data.keuzes[1] != undefined) {
        var text;
        if (data.winner != undefined){
            text = data.players[data.winner] + ' is gewonnen!';
        } else {
            text = 'gelijkspel!';
        }

        var winnaarText = $('#bovensolid2');
        winnaarText.text( text );

        toonKeuze1(data);
        toonKeuze2(data);
        voegTekstToeAanChatBox(text);

        if ($('#naam').val().length > 0) {
            $('#playButton').show();
        }
    } else {
        for (var i = 0; i < 2; i++) {
            if (data.keuzes[i] != undefined) {                          //als de keuzes ingevuld is en vraagteken tonen en anders blijft het wit
                $('#img' + (i+1)).attr('src', '/images/vraagteken.jpg');
            } else {
                $('#img' + (i+1)).attr('src', '/images/witte_vierkant.png');
            }
        }

        for (var i = 0; i < 2; i++) {
            if (data.players[i] != undefined) {
                $('#speler'+ (i+1)).text(data.players[i]);
            } else { // een speler heeft mogelijk het spel verlaten dus moeten we de naamlabels mogenlijk ook terug leeg maken
                $('#speler'+ (i+1)).text('');
            }
        }
    }
});

function mySteen() {
    verstuurKeuzeNaarServer(1);
}
function myPapier() {
    verstuurKeuzeNaarServer(2);
}
function mySchaar() {
    verstuurKeuzeNaarServer(3);
}
function myHagedis() {
    verstuurKeuzeNaarServer(4);
}
function mySpock() {
    verstuurKeuzeNaarServer(5);
}

function getCurrentTime() {
    var date = new Date();                                                           // met function kun je altijd gebruiken door naam te typen
    return date.getHours() + ':' + date.getMinutes();
}

function voegTekstToeAanChatBox(text) {                                      // tussen () mag je zelf naam kiezen
    var chatMessages = $('#messages');
    chatMessages.append($('<li>').text(getCurrentTime() + ': ' + text));     // berichten zullen onder elkaar geplaatst worden door <li>
    var chatPanel = $('.gesprek');
    chatPanel.scrollTop($('#messages').height());       // automatisch naar beneden scrollen
}
                                                                                // timestamps
function verstuurKeuzeNaarServer(keuze) {
    socket.emit('gamedata', {                             // hier wordt keuze opgeslagen die de player gekozen heeft. wordt naar server gestuurd
        player: player,
        keuze: keuze
    });

    $('#mySteen').attr("disabled", "disabled");
    $('#myPapier').attr("disabled", "disabled");
    $('#mySchaar').attr("disabled", "disabled");
    $('#myHagedis').attr("disabled", "disabled");
    $('#mySpock').attr("disabled", "disabled");
}


