package ru.vsu.cs.baklanova.GameLogic.Player;

public enum PlayerEnum {
    SASHA("Саша"),
    ALEX("Алекс"),
    SAM("Сэм"),
    BETTY("Бэтти"),
    PAUL("Пол"),
    KATTY("Кэтти"),
    MARIA_ANDREEVA("Мария Андреева"),
    FIONA("Фиона"),
    CARL("Карл"),
    MASHA("Маша"),
    OLEG("Олег"),
    TIHON("Тихон"),
    STEVE("Стив");

    String rusName;
    PlayerEnum(String s) {
        rusName = s;
    }
}


