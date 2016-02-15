package be.ordina.jworks.repository;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Player implements Serializable {

    private String pid;
    private String name;

    public Player(String pid, String name) {
        this.pid = pid;
        this.name = name;
    }
}
