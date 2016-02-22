package be.ordina.jworks.gaming.controller;

import be.ordina.jworks.gaming.web.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.NotConnectedException;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/play", method = RequestMethod.GET)
@SessionAttributes("player")
public class PlayController {

    @Autowired
    private ConnectionRepository connectionRepository;

    @RequestMapping
    public String play(HttpSession session) {
        try {
            Connection<Twitter> connection = connectionRepository.getPrimaryConnection(Twitter.class);

            if (connection != null) {
                Player player = new Player(connection.getDisplayName(), connection.getImageUrl(), connection.getProfileUrl());
                session.setAttribute("player", player);
            }

            return "play";
        } catch (NotConnectedException e) {
            return "redirect:/auth/twitter";
        }
    }
}
