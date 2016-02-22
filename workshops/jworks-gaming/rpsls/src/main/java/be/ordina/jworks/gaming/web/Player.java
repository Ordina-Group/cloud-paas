package be.ordina.jworks.gaming.web;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Player implements Serializable {

    private static final long serialVersionUID = 4655749641181961586L;

    private String displayName;

    private String imageUrl;

    private String profileUrl;

    public Player(final String displayName, final String imageUrl, final String profileUrl) {
        this.displayName = displayName;
        this.imageUrl = imageUrl;
        this.profileUrl = profileUrl;
    }
}
