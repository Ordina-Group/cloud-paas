package be.ordina.jworks.gaming.web;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class GameMessage {

    private String action;

    private String actionValue;

    private String playerName;

    private Instant timestamp;

    public GameMessage(final GameMessage gameMessage) {
        this.action = gameMessage.getAction();
        this.actionValue = gameMessage.getActionValue();
        this.playerName = gameMessage.getPlayerName();
        this.timestamp = gameMessage.timestamp;
    }
}
