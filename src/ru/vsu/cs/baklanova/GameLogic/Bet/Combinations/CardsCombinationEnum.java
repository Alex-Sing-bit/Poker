package ru.vsu.cs.baklanova.GameLogic.Bet.Combinations;

public enum CardsCombinationEnum {
    HIGH_CARD(0, "Старшая карта"),
    ONE_PAIR(1,"Пара"),
    TWO_PAIR(2,"Две пары"),
    THREE_OF_A_KIND(3,"Тройка"),
    STRAIGHT(4,"Стрит"),
    FLUSH(5,"Флеш"),
    FULL_HOUSE(6,"Фулл хаус"),
    FOUR_OF_A_KIND(7,"Каре"),
    STRAIGHT_FLUSH(8,"Стрит-флеш"),
    ROYAL_FLUSH(9,"Флеш-рояль");

    private int count;
    private String rusStringName;

    CardsCombinationEnum(int count, String rusStringName){
        this.count = count;
        this.rusStringName = rusStringName;
    }

    public String getRusStringName(){
        return rusStringName;
    }

    public int getCount() {
        return count;
    }
}
