package ru.vsu.cs.baklanova.Player;

import ru.vsu.cs.baklanova.Cards.Card;
import ru.vsu.cs.baklanova.Cards.CardBlock;
import ru.vsu.cs.baklanova.Cards.CardsCombinationStatus;

import java.util.ArrayList;

public class Player {
    private final boolean isNPC;
    private String name;
    private int money;
    private int bet;
    private final int cardsNumber = 2;
    private ArrayList<Card> cards;

    private boolean isInGame;

    private CardsCombinationStatus cardsStatus;

    public Player(CardBlock main, boolean isNPC) throws Exception {
        setName();
        this.isNPC = isNPC;
        setMoney(1000);
        setBet(0);
        this.isInGame = true;
        this.cardsStatus = null;
        setCards(main);
    }

    public Player(CardBlock main, boolean isNPC, int money, int bet) throws Exception {
        setName();
        this.isNPC = isNPC;
        setMoney(money);
        setBet(bet);
        this.isInGame = true;
        this.cardsStatus = null;
        setCards(main);
    }

    public void setCardsStatus(CardsCombinationStatus cardsStatus) {
        this.cardsStatus = cardsStatus;
    }

    public void setCards(CardBlock main) throws Exception {
        if (main == null) {
            throw new Exception("Колода пустая. Игрок не может взять карту.");
        }
        ArrayList <Card> cards = new ArrayList<>();
        for (int i = 0; i < cardsNumber; i++) {
            cards.add(main.takeCard());
        }

        this.cards = cards;
    }

    public void setBet(int bet) throws Exception {
        if (bet < 0) {
            throw new Exception("Попытка назначить отрицательную ставку.");
        }
        this.bet = bet;
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

    public void setCombinationStatus(CardsCombinationStatus cardsStatus) {
        this.cardsStatus = cardsStatus;
    }

    public void setInGame(boolean inGame) {
        isInGame = inGame;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
    public Card getCard(int i) throws Exception {
        if (i > 0 && i < cardsNumber - 1) {
            return cards.get(i);
        }

        throw new Exception("Неправильный номер карты");
    }

    public CardsCombinationStatus getCardsStatus() {
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
