var socket = io();
$('#chatFormulier').submit(function () {                //als submit geactiveerd word dan wordt tussen {} uitgevoerd
    socket.emit('chat', $('#chatBericht').val());       //stuurt berichten naar alle sockets
    $('#chatBericht').val('');
    return false;
});
socket.on('chat', function (chatBericht) {                  //chatbericht wordt geactiveerd, --> {}
    $('#messages').append($('<li>').text(chatBericht));     //berichten zullen onder elkaar geplaatst worden
});