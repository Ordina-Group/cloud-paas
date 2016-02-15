package be.ordina.jworks.controller;

import be.ordina.jworks.repository.GameRepository;
import be.ordina.jworks.repository.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private GameRepository gameRepository;

    @RequestMapping
    public Map<String, Object> findAllPlayers() {
        return gameRepository.findAllPlayers();
    }

    @RequestMapping(value = "/{gid}/{pid}/{score}", method = RequestMethod.POST)
    public void addPlayerScore(@PathVariable("gid") String gid, @PathVariable("pid") String pid, @PathVariable("score") Long score) {
        gameRepository.addScoreForPlayer(gid, pid, score);
    }

    @RequestMapping(value = "/{gid}/ranking")
    public Map<Long, Player> getRanking(@PathVariable("gid") String gid) {
        return gameRepository.getScores(gid).entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new));
    }
}
