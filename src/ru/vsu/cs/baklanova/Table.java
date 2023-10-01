package ru.vsu.cs.baklanova;

import java.util.ArrayList;

public class Table {
    private ArrayList<Player> players;
    private CardBlock tableCards;

    private int bigBet;

    public Table(int playersNum, CardBlock main) throws Exception {
        setPlayers(main, playersNum, playersNum - 0); // -1
        tableCards = new CardBlock(0, false, main);
        this.bigBet = 0;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public CardBlock getTableCards() {
        return tableCards;
    }

    public void setPlayersToNewCircle() {
        for (Player p : players) {
            p.setInGame(true);
        }
    }
    public boolean gameOver() {
        for (Player p : players) {
            if (p.getMoney() > 0) {
                return false;
            }
        }
        return true;
    }

    public void setPlayers(CardBlock main, int playersNum, int npcNum) throws Exception {
        ArrayList<Player> players = new ArrayList<>();
        boolean isNPC = false;
        int k = 1;
        for (int i = 0; i < playersNum; i++) {
            if (k < playersNum - npcNum) {
                k++;
            } else {
                isNPC = true;
            }
            players.add(new Player(main, isNPC));
        }

        this.players = players;
    }
}
