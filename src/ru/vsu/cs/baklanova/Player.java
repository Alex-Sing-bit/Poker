package ru.vsu.cs.baklanova;

import java.util.ArrayList;

public class Player {
    //private String[] names = new String[]{"Sasha", "Alex", "Sam", "Betty", "Paul", "Katty", "Mari"};
    private boolean isNPC;
    private String name;
    private int money;
    private int bet;
    private final int cardsNumber = 2;
    private ArrayList<Card> cards;

    private boolean isInGame;

    private CardStatusEnum cardsStatus;

    public Player(CardBlock main, boolean isNPC) throws Exception {
        setName();
        this.isNPC = isNPC;
        setMoney(10000);
        setBet(100);
        this.isInGame = true;
        this.cardsStatus = null;
        setCards(main);
    }

    public void setCards(CardBlock main) throws Exception {
        if (main == null) {
            //ERROR
        }
        ArrayList <Card> cards = new ArrayList<>();
        for (int i = 0; i < cardsNumber; i++) {
            cards.add(CardBlock.takeCard(main));
        }

        this.cards = cards;
    }

    public void setBet(int bet) {
        if (bet > 0) {
            this.bet = bet;
        }
        //ERROR
    }

    public void setMoney(int money) {
        if (money >= 0) {
            this.money = money;
        }
        //ERROR
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

    public ArrayList<Card> getCards() {
        return cards;
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
