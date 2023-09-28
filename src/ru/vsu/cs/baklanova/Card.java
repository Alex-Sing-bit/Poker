package ru.vsu.cs.baklanova;

public class Card implements Comparable <Card> {
    private final int cardSuit;
    private int cardValue;
    private boolean cardStatus;


    public Card(int cardSuit, int cardValue, boolean bol) throws Exception {
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

    public int getCardSuit() {
        return cardSuit;
    }

    public static String getCardSuitString(int suit) {
        if (suit == 0) {
            return "Крести";
        } else if (suit == 1) {
            return "Бубны";
        } else if (suit == 2) {
            return "Червы";
        } else if (suit == 3) {
            return "Пики";
        } else {
            return "Масть не обозначена";
        }
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