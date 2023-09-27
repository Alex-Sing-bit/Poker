package ru.vsu.cs.baklanova;

public class Card {
    private String cardSuit;
    private int cardWeight;

    private boolean cardStatus;

    public Card(String cardSuit, int cardWeight, boolean bol) {
        this.cardSuit = cardSuit;
        setCardWeight(cardWeight);
        this.cardStatus = bol;
    }

    public void setCardWeight(int cardWeight) {
        if (cardWeight > 0) {
            this.cardWeight = cardWeight;
        }
        //Иначе ошибка
    }

    public int getCardWeight() {
        return cardWeight;
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
}