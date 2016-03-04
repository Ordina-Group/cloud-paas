package be.ordina.jworks.gaming.controller;

import be.ordina.jworks.gaming.web.ChatMessage;
import be.ordina.jworks.gaming.web.GameMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.time.Instant;

@RestController
@RequestMapping(value = "/play", method = RequestMethod.GET)
@SessionAttributes("player")
public class PlayController {

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
