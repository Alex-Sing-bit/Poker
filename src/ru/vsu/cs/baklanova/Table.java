package ru.vsu.cs.baklanova;

import ru.vsu.cs.baklanova.Cards.Card;
import ru.vsu.cs.baklanova.Cards.CardBlock;
import ru.vsu.cs.baklanova.Player.Player;

import java.util.ArrayList;

public class Table {
    private ArrayList<Player> players;
    private ArrayList<Card> tableCards;

    private int bigBet;

    boolean haveRealPlayer;

    public Table(int playersNum, CardBlock main, boolean haveRealPlayer) throws Exception {
        int k = 0;
        this.haveRealPlayer = haveRealPlayer;
        if (haveRealPlayer) {
            k = 1;
        }
        setPlayers(main, playersNum, playersNum - k);
        setTableCards(main);
        bigBet = 0;
    }

    private void setTableCards(CardBlock main) throws Exception {
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

    public void setBigBet(int bigBet) {
        this.bigBet = bigBet;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Card> getTableCards() {
        return tableCards;
    }

    public int getBigBet() {
        return bigBet;
    }
}
