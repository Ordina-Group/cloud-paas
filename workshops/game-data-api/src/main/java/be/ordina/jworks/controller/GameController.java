package be.ordina.jworks.controller;

import be.ordina.jworks.repository.Game;
import be.ordina.jworks.repository.GameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    @RequestMapping
    public Map<String, Object> getGames() {
        Map<String, Object> games = gameRepository.findAllGames();

        log.info("Found [" + games.size() + "] games");

        return games;
    }

    @RequestMapping(value = "/{name}")
    public ResponseEntity<Game> getGame(@PathVariable("name") final String name) {
        String gameName = name == null ? null : name.trim();
        if (gameName == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(gameRepository.findGame(gameName), HttpStatus.OK);
    }

    @RequestMapping(value = "/{name}/new", method = RequestMethod.POST)
    public ResponseEntity<Game> createGame(@PathVariable("name") final String name) {
        String gameName = name == null ? null : name.trim();
        if (gameName == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        log.info("Creating game [" + name + "]");

        Game game = gameRepository.createGame(gameName);

        if (game == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(game, HttpStatus.OK);
    }
}
