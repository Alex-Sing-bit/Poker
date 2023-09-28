package ru.vsu.cs.baklanova;

public class Card implements Comparable <Card> {
    private final String cardSuit;
    private int cardValue;
    private boolean cardStatus;


    public Card(String cardSuit, int cardValue, boolean bol) throws Exception {
        this.cardSuit = cardSuit;
        setCardValue(cardValue);
        this.cardStatus = bol;
    }

    public void setCardValue(int cardValue) throws Exception{
        if (cardValue > 0) {
            this.cardValue = cardValue;
            return;
        }
        throw new Exception("Передано неправильное значение карты");
    }

    public int getCardValue() {
        return cardValue;
    }

    public String getCardSuit() {
        return cardSuit;
    }

    public boolean getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(boolean cardStatus) {
        this.cardStatus = cardStatus;
    }

    @Override
    public int compareTo(Card o) {
        if (this.cardValue > o.getCardValue()) {
            return 1;
        } else if (this.cardValue < o.getCardValue()) {
            return  -1;
        }
        return 0;
    }
}