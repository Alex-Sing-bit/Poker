package ru.vsu.cs.baklanova.Cards;

public enum CardSuitEnum {
    CLUBS(0),
    DIAMONDS(1),
    HEARTS(2),
    SPADES(3);

    private int count;

    CardSuitEnum(int count){
        this.count = count;
    }

    public int getCount(){
        return count;
    }
}


