package ru.vsu.cs.baklanova;

import java.util.ArrayList;

public class Player {
    //private String[] names = new String[]{"Sasha", "Alex", "Sam", "Betty", "Paul", "Katty", "Mari"};
    private String name;
    private int money;
    private int bet;
    private final int cardsNumber = 2;
    private CardBlock cards;

    private int сardsStatus;

    public Player(CardBlock c) throws Exception {
        setName();
        this.money = 10000;
        this.bet = 100;
        cards = new CardBlock(cardsNumber, false, c);
    }

    public void setName() {
        PlayerEnum[] arr = PlayerEnum.values();
        this.name = arr[(int) (Math.random()* arr.length)].name();
    }

    public CardBlock getCardBlock() {
        return cards;
    }
    public ArrayList<Card> getCards() {
        return cards.getCardBlock();
    }

    public String getName() {
        return name;
    }

    public int getBet() {
        return bet;
    }

    public int getMoney() {
        return money;
    }
}
