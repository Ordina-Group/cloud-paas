package be.ordina.jworks.gaming.controller;

import be.ordina.jworks.gaming.web.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.NotConnectedException;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/session", method = RequestMethod.GET)
public class SessionController {

    @Autowired
    private ConnectionRepository connectionRepository;

    @RequestMapping
    public ResponseEntity<Player> player(HttpSession session) {
        try {
            Connection<Twitter> connection = connectionRepository.getPrimaryConnection(Twitter.class);

            if (connection != null) {
                Player player = new Player(connection.getDisplayName(), connection.getImageUrl(), connection.getProfileUrl());
                session.setAttribute("player", player);
            }

            Player player = (Player) session.getAttribute("player");

            return new ResponseEntity<>(player, HttpStatus.OK);
        } catch (NotConnectedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
