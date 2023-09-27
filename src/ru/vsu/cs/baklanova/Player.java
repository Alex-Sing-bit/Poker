package ru.vsu.cs.baklanova;

public class Player {
    private String[] names = new String[]{"Sasha", "Alex", "Sam", "Betty", "Paul", "Katty", "Mari"};
    private String name;
    private int money;
    private int bet;
    private final int cardsNumber = 2;
    private Card[] cards;

    public Player(CardBlock c) {
        setName();
        this.money = 10000;
        this.bet = 100;
        setCards(c);
    }

    public void setName() {
        this.name = names[(int) (Math.random()* names.length)];
    }

    public void setCards(CardBlock c) {
        Card[] cards1 = new Card[2];
        cards1[0] = randomCart(c);
        cards1[1] = randomCart(c);
        this.cards = cards1;
    }

    private Card randomCart(CardBlock cb) {
        int num = cb.getCardBlock().length;
        int k = (int) (num * Math.random());
        for (int i = 0; i < num; i++) {
            if (cb.getCardBlock()[k].getCardStatus()) {
                cb.getCardBlock()[k].setCardStatus(false);
                return cb.getCardBlock()[k];
            } else {
                k++;
                k = k % num;
            }
        }

        return null; //Ошибка
    }

    public Card[] getCards() {
        return cards;
    }
}
