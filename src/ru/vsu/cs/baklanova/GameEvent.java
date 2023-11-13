package ru.vsu.cs.baklanova;

import ru.vsu.cs.baklanova.Player.Player;

import java.util.ArrayList;

public class GameEvent {
    private GameEventEnum status;
    private ArrayList<Player> players;
    private int activePlayer;
    private Table table;

    public GameEvent(GameEventEnum status, ArrayList<Player> players, int activePlayer, Table table) {
        this.status = status;
        this.players = players;
        this.activePlayer = activePlayer;
        this.table = table;
    }
}
