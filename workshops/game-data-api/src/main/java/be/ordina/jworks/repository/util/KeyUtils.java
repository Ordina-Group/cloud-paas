package be.ordina.jworks.repository.util;

public class KeyUtils {

    public static final String GLOBAL_LID = "global:lid";
    public static final String GLOBAL_PID = "global:pid";
    public static final String GLOBAL_GID = "global:gid";

    public static final String GAMES = "games";
    public static final String LOBBIES = "lobbies";
    public static final String PLAYERS = "players";

    public static String GAME(final String gameName) {
        return "game:" + gameName + ":gid";
    }

    public static String PLAYER(final String playerName) {
        return "player:" + playerName + ":pid";
    }

    public static String GAME_LOBBIES(String gid) {
        return "gid:" + gid + ":lobbies";
    }

    public static String LOBBY_PLAYERS(String lid) {
        return "lid:" + lid + ":players";
    }

    public static String PLAYER_SCORES(String gid) {
        return "gid:" + gid + ":scores";
    }
}
