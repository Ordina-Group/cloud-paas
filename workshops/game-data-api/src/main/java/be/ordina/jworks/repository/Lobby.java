package be.ordina.jworks.repository;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class Lobby implements Serializable {

    private String lid;
    private String gid;
    private String url;

    private List<Player> players;
    private boolean active;

    public Lobby(String lid, String gid, String url, List<Player> players) {
        this.lid = lid;
        this.gid = gid;
        this.url = url;
        this.players = players;
        this.active = true;
    }
}
