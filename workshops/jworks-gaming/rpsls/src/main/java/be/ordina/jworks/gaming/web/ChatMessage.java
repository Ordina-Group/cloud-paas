package be.ordina.jworks.gaming.web;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ChatMessage {

    private String message;

    private String playerName;

    private Instant timestamp;

    public ChatMessage(final ChatMessage chatMessage) {
        this.message = chatMessage.message;
        this.playerName = chatMessage.playerName;
        this.timestamp = chatMessage.timestamp;
    }
}
