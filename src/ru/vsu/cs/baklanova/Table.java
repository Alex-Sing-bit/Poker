package ru.vsu.cs.baklanova;

import java.util.ArrayList;

public class Table {
    private ArrayList<Player> players;
    private ArrayList<Card> tableCards;

    public Table(int playersNum, CardBlock main, boolean haveRealPlayer) throws Exception {
        int k = 0;
        if (haveRealPlayer) {
            k = 1;
        }
        setPlayers(main, playersNum, playersNum - k);
        setTableCards(main);
    }

    public void setTableCards(CardBlock main) throws Exception {
        setTableCards(main, 0);
    }

    public void setTableCards(CardBlock main, int num) throws Exception {
        if (tableCards == null || num <= 0) {
            tableCards = new ArrayList<>();
        }
        for (int i = 0; i < num; i++) {
            tableCards.add(CardBlock.takeCard(main));
        }
    }

    public void setPlayersToNewCircle() {
        for (Player p : players) {
            p.setInGame(true);
        }
    }


    public void setPlayers(CardBlock main, int playersNum, int npcNum) throws Exception {
        ArrayList<Player> players = new ArrayList<>();
        boolean isNPC = false;
        int k = 0;
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

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Card> getTableCards() {
        return tableCards;
    }
}
