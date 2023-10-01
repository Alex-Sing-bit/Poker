package ru.vsu.cs.baklanova;

import java.util.ArrayList;

public class Table {
    private ArrayList<Player> players;
    private CardBlock tableCards;

    public Table(int playersNum, int npcNum, CardBlock main) throws Exception {
        if (npcNum > playersNum) {
            npcNum = playersNum;
        }
        setPlayers(main, playersNum, npcNum);
        tableCards = new CardBlock(0, false, main);
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
