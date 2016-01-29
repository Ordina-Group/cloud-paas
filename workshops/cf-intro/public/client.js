var socket = io();
$('#chatFormulier').submit(function () {                // als submit geactiveerd word dan wordt tussen {} uitgevoerd
    socket.emit('chat', $('#chatBericht').val());       // stuurt berichten naar de server
    $('#chatBericht').val('');
    return false;
});
$('#playFormulier').submit(function () {                // als submit geactiveerd word dan wordt tussen {} uitgevoerd
    socket.emit('play', $('#naam').val());              // stuurt berichten naar de server
    $('#naam').hide();
    return false;
});
socket.on('chat', function (data) {                  // we krijgen een chatbericht van de server --> {}
    $('#messages').append($('<li>').text(data));     // berichten zullen onder elkaar geplaatst worden
});
socket.on('play', function (data) {                  // we krijgen een chatbericht van de server --> {}
    console.log('Player ' + data.player + ' [' + data.name + '] wil spelen! HOERA!')
    if (data.player == 2) {
        console.log('LET THE GAMES BEGIN !!!');
        $('#playButton').hide();
    }
});
