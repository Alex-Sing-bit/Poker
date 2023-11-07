package ru.vsu.cs.baklanova;

import ru.vsu.cs.baklanova.Cards.Card;
import ru.vsu.cs.baklanova.Cards.CardBlock;
import ru.vsu.cs.baklanova.Player.Player;

import java.util.ArrayList;

public class Table {
    private ArrayList<Card> tableCards;

    private int bigBet;

    public Table(CardBlock main) throws Exception {
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
            tableCards.add(main.takeCard());
        }
    }

    public void setBigBet(int bigBet) {
        this.bigBet = bigBet;
    }



    public ArrayList<Card> getTableCards() {
        return tableCards;
    }

    public int getBigBet() {
        return bigBet;
    }
}
