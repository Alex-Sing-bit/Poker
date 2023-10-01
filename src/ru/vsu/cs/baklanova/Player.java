package ru.vsu.cs.baklanova;

import java.util.ArrayList;

public class Player {
    //private String[] names = new String[]{"Sasha", "Alex", "Sam", "Betty", "Paul", "Katty", "Mari"};
    private boolean isNPC;
    private String name;
    private int money;
    private int bet;
    private final int cardsNumber = 2;
    private CardBlock cards;

    private boolean isInGame;

    private CardStatusEnum cardsStatus;

    public Player(CardBlock c, boolean isNPC) throws Exception {
        setName();
        this.isNPC = isNPC;
        this.money = 10000;
        this.bet = 100;
        this.isInGame = true;
        this.cardsStatus = null;
        cards = new CardBlock(cardsNumber, false, c);
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setName() {
        PlayerEnum[] arr = PlayerEnum.values();
        this.name = arr[(int) (Math.random()* arr.length)].name();
    }

    public void setCardsStatus(CardStatusEnum cardsStatus) {
        this.cardsStatus = cardsStatus;
    }

    public void setInGame(boolean inGame) {
        isInGame = inGame;
    }


    public CardBlock getCardBlock() {
        return cards;
    }
    public ArrayList<Card> getCards() {
        return cards.getCardBlock();
    }

    public CardStatusEnum getCardsStatus() {
        return cardsStatus;
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

    public boolean getIsNPC() {
        return isNPC;
    }

    public boolean getInGame() {
        return isInGame;
    }
}
