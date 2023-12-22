package ru.vsu.cs.baklanova.GameLogic.Cards;

import java.util.Comparator;

public class Card implements Comparable <Card>, Comparator<Card> {
    private final CardSuitEnum cardSuit;
    private final CardValueEnum cardValue;

    public Card(CardSuitEnum cardSuit, CardValueEnum cardValue) {
        this.cardSuit = cardSuit;
        this.cardValue = cardValue;
    }


    public int getCardValueNum() {
        return cardValue.getCount();
    }

    public CardValueEnum getCardValue() {
        return cardValue;
    }

    public CardSuitEnum getCardSuit() {
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

    @Override
    public int compareTo(Card o) {
        if (this.cardValue.getCount() > o.getCardValueNum()) {
            return 1;
        } else if (this.cardValue.getCount() < o.getCardValueNum()) {
            return  -1;
        }
        return 0;
    }


    @Override
    public int compare(Card o1, Card o2) {
        return o1.compareTo(o2);
    }
}