package ru.vsu.cs.baklanova.Cards;

public enum CardStatusEnum {
    HIGH_CARD(0, "Старшая карта"),
    ONE_PAIR(1,"Пара"),
    TWO_PAIR(2,"Две пары"),
    THREE_OF_A_KIND(3,"Тройка"),
    STRAIGHT(4,"Стрит"),
    FLUSH(5,"Флэш"),
    FULL_HOUSE(6,"Фулл Хаус"),
    FOUR_OF_A_KIND(7,"Каре"),
    STRAIGHT_FLUSH(8,"Стрит"),
    ROYAL_FLUSH(9,"Роял");

    private int count;
    private String rusStringName;

    CardStatusEnum(int count, String rusStringName){
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
