package be.ordina.jworks.repository;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Game implements Serializable {

    private String gid;
    private String name;
    private Long created;

    public Game(final String gid, final String name, final Long created) {
        this.gid = gid;
        this.name = name;
        this.created = created;
    }
}
