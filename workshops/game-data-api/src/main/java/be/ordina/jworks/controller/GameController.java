package be.ordina.jworks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping
    public Set<String> getGames() {
        return redisTemplate.opsForSet().members("games");
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.POST)
    public ResponseEntity<String> addGame(@PathVariable("name") final String name) {
        String gameName = name == null ? null : name.trim();
        if (gameName == null || redisTemplate.opsForSet().isMember("games", gameName)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        redisTemplate.opsForSet().add("games", gameName);
        return new ResponseEntity<>(gameName, HttpStatus.OK);
    }
}
