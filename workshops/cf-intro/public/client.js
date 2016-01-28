var socket = io();
$('#chatFormulier').submit(function () {
    socket.emit('chat', $('#chatBericht').val());
    $('#chatBericht').val('');
    return false;
});
socket.on('chat', function (chatBericht) {
    $('#messages').append($('<li>').text(chatBericht));
});