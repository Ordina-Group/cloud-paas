package be.ordina.jworks.gaming.controller;

import be.ordina.jworks.gaming.web.ChatMessage;
import be.ordina.jworks.gaming.web.GameMessage;
import be.ordina.jworks.gaming.web.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.NotConnectedException;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.time.Instant;

@RestController
@RequestMapping(value = "/play", method = RequestMethod.GET)
@SessionAttributes("player")
public class PlayController {

    @Autowired
    private ConnectionRepository connectionRepository;

    @RequestMapping
    public ResponseEntity<String> play(HttpSession session) {
        try {
            Connection<Twitter> connection = connectionRepository.getPrimaryConnection(Twitter.class);

            if (connection != null) {
                Player player = new Player(connection.getDisplayName(), connection.getImageUrl(), connection.getProfileUrl());
                session.setAttribute("player", player);
            }

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotConnectedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public ChatMessage chat(final ChatMessage chatMessage) throws Exception {
        ChatMessage echoMsg = new ChatMessage(chatMessage);
        echoMsg.setTimestamp(Instant.now());
        return echoMsg;
    }

    @MessageMapping("/game")
    @SendTo("/topic/game")
    public GameMessage game(final GameMessage gameMessage) throws Exception {
        GameMessage echoMsg = new GameMessage(gameMessage);
        echoMsg.setTimestamp(Instant.now());
        return echoMsg;
    }
}
