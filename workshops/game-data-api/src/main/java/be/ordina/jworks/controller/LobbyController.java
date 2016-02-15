package be.ordina.jworks.controller;

import be.ordina.jworks.repository.Game;
import be.ordina.jworks.repository.GameRepository;
import be.ordina.jworks.repository.Lobby;
import be.ordina.jworks.repository.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/lobbies")
public class LobbyController {

    @Autowired
    private GameRepository gameRepository;

    @RequestMapping(value = "/{gid}/new", method = RequestMethod.POST)
    public ResponseEntity<Lobby> createLobby(@PathVariable("gid") final String gid, @RequestParam("url") final String url) {
        Game game = gameRepository.findGameByGid(gid);

        if (game == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        log.info("Creating lobby for game [" + gid + "] with URL [" + url + "]");

        return new ResponseEntity<>(gameRepository.createLobby(gid, url), HttpStatus.OK);
    }

    @RequestMapping(value = "/{gid}/{lid}/player/{name}", method = RequestMethod.POST)
    public ResponseEntity<Player> addPlayerToLobby(@PathVariable("gid") String gid, @PathVariable("lid") final String lid, @PathVariable("name") final String name) {
        String playerName = name == null ? "" : name.trim();

        Player player = gameRepository.addPlayerToLobby(lid, playerName);

        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @RequestMapping(value = "/{gid}", method = RequestMethod.GET)
    public List<Lobby> getAllLobbies(@PathVariable("gid") final String gid) {
        Game game = gameRepository.findGameByGid(gid);

        if (game == null) {
            return null;
        }

        return gameRepository.findAllLobbies(gid);
    }

    @RequestMapping(value = "/{lid}/close", method = RequestMethod.POST)
    public void closeLobby(@PathVariable("lid") final String lid) {
        gameRepository.closeLobby(lid);
    }
}
