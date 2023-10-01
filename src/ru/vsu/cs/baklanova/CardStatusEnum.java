package ru.vsu.cs.baklanova;

public enum CardStatusEnum {
    HIGH_CARD("Старшая карта"),
    ONE_PAIR("Пара"),
    TWO_PAIR("Две пары"),
    THREE_OF_A_KIND("Тройка"),
    STRAIGHT("Стрит"),
    FLUSH("Флэш"),
    FULL_HOUSE("Фулл Хаус"),
    FOUR_OF_A_KIND("Каре"),
    STRAIGHT_FLUSH("Стрит"),
    ROYAL_FLUSH("Роял");

    private String rusStringName;

    CardStatusEnum(String rusStringName){
        this.rusStringName = rusStringName;
    }

    public String getRusStringName(){
        return rusStringName;
    }
}
