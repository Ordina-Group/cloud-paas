package be.ordina.jworks.repository;

import be.ordina.jworks.repository.util.KeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GameRepository {

    private RedisTemplate template;

    private final ValueOperations<String, Object> valueOperations;
    private final HashOperations<String, String, Object> hashOperations;
    private final ListOperations<String, String> listOperations;

    private final RedisAtomicLong gameCounter;
    private final RedisAtomicLong lobbyCounter;
    private final RedisAtomicLong playerCounter;

    @Autowired
    public GameRepository(RedisTemplate redisTemplate) {
        this.template = redisTemplate;

        valueOperations = this.template.opsForValue();
        hashOperations = this.template.opsForHash();
        listOperations = this.template.opsForList();

        gameCounter = new RedisAtomicLong(KeyUtils.GLOBAL_GID, this.template.getConnectionFactory());
        lobbyCounter = new RedisAtomicLong(KeyUtils.GLOBAL_LID, this.template.getConnectionFactory());
        playerCounter = new RedisAtomicLong(KeyUtils.GLOBAL_PID, this.template.getConnectionFactory());
    }

    public Game createGame(final String name) {
        String gid = String.valueOf(gameCounter.incrementAndGet());
        Long created = System.nanoTime();
        Game game = new Game(gid, name, created);

        hashOperations.put(KeyUtils.GAMES, gid, game);

        valueOperations.set(KeyUtils.GAME(name), gid);

        return game;
    }

    public Game findGame(final String name) {
        String gid = (String) valueOperations.get(KeyUtils.GAME(name));

        if (gid == null) {
            throw new RuntimeException("Could not find game with name " + name);
        }

        return findGameByGid(gid);
    }

    public Game findGameByGid(String gid) {
        return (Game) hashOperations.get(KeyUtils.GAMES, gid);
    }

    public Map<String, Object> findAllGames() {
        return hashOperations.entries(KeyUtils.GAMES);
    }

    public Lobby createLobby(final String gid, final String url) {
        String lid = String.valueOf(lobbyCounter.incrementAndGet());

        Lobby lobby = new Lobby(lid, gid, url, new ArrayList<>());

        hashOperations.put(KeyUtils.LOBBIES, lid, lobby);
        listOperations.leftPush(KeyUtils.GAME_LOBBIES(gid), lid);

        return lobby;
    }

    public Player addPlayerToLobby(final String lid, final String name) {
        Lobby lobby = findLobbyByLid(lid);
        Player player = findPlayerByName(name);

        if (player == null) {
            player = createPlayer(name);
        }

        lobby.getPlayers().add(player);
        hashOperations.put(KeyUtils.LOBBIES, lid, lobby);

        listOperations.leftPush(KeyUtils.LOBBY_PLAYERS(lid), player.getPid());

        return player;
    }

    public void closeLobby(final String lid) {
        Lobby lobby = findLobbyByLid(lid);

        lobby.setActive(false);

        hashOperations.put(KeyUtils.LOBBIES, lid, lobby);
    }

    private Player findPlayerByName(String name) {
        String pid = (String) valueOperations.get(KeyUtils.PLAYER(name));

        if (pid == null) {
            return null;
        }

        return findPlayerByPid(pid);
    }

    public Player findPlayerByPid(String pid) {
        return (Player) hashOperations.get(KeyUtils.PLAYERS, pid);
    }

    public Player createPlayer(final String name) {
        String pid = String.valueOf(playerCounter.incrementAndGet());

        Player player = new Player(pid, name);

        hashOperations.put(KeyUtils.PLAYERS, pid, player);
        valueOperations.set(KeyUtils.PLAYER(name), pid);

        return player;
    }

    public Lobby findLobbyByLid(final String lid) {
        return (Lobby) hashOperations.get(KeyUtils.LOBBIES, lid);
    }

    public List<Player> findPlayersInLobby(String lid) {
        List<Player> players = new ArrayList<>();
        List<String> pids = listOperations.range(KeyUtils.LOBBY_PLAYERS(lid), 0, -1);

        players.addAll(pids.stream().map(this::findPlayerByPid).collect(Collectors.toList()));

        return players;
    }

    public void addScoreForPlayer(final String gid, final String pid, final Long score) {
        Long oldScore = (Long) hashOperations.get(KeyUtils.PLAYER_SCORES(gid), pid);

        if (oldScore == null) {
            oldScore = 0L;
        }

        Long newScore = oldScore + score;

        hashOperations.put(KeyUtils.PLAYER_SCORES(gid), pid, newScore);
    }

    public List<Lobby> findAllLobbies(String gid) {
        List<Lobby> lobbyList = new ArrayList<>();
        List<String> lobbies = listOperations.range(KeyUtils.GAME_LOBBIES(gid), 0, -1);

        lobbyList.addAll(lobbies.stream().map(this::findLobbyByLid).collect(Collectors.toList()));

        return lobbyList;
    }

    public Map<String, Object> findAllPlayers() {
        return hashOperations.entries(KeyUtils.PLAYERS);
    }

    public Map<Long, Player> getScores(String gid) {
        Map<Long, Player> scores = new HashMap<>();
        Map<String, Object> playerScores = hashOperations.entries(KeyUtils.PLAYER_SCORES(gid));

        playerScores.forEach((pid, score) -> scores.put((Long) score, findPlayerByPid(pid)));

        return scores;
    }
}
