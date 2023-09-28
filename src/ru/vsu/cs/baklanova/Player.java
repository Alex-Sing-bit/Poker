package ru.vsu.cs.baklanova;

public class Player {
    private String[] names = new String[]{"Sasha", "Alex", "Sam", "Betty", "Paul", "Katty", "Mari"};
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
        this.name = names[(int) (Math.random()* names.length)];
    }

    public Card[] getCardBlock() {
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
