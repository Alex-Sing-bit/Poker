package ru.vsu.cs.baklanova.GameLogic.Cards;

public enum CardSuitEnum {
    CLUBS(0, '♣'),
    DIAMONDS(1, '♦'),
    HEARTS(2, '♥'),
    SPADES(3, '♠');

    private int count;
    private char suitChar;

    CardSuitEnum(int count, char suitChar){
        this.suitChar = suitChar;
        this.count = count;
    }

    public int getCount(){
        return count;
    }

    public char getSuitChar() {
        return suitChar;
    }
}


