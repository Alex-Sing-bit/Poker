package ru.vsu.cs.baklanova;

public class CardSetStatus {
    private final String[] STATUSES = new String[]{"Старшая карта", "Пара", "Две пары", "Тройка", "Стрит", "Флэш", "Фулл Хаус", "Каре", "Стрит", "Роял"};

    public static String setStatus(Card[] table, Card[] player) {
        int tableSize = table.length;
        int size = tableSize + player.length;
        Card[] check = new Card[size];
        for (int i = 0; i < size; i++) {
            if (i > tableSize - 1) {
                check[i] = player[i % (tableSize- 1)];
            } else {
                check[i] = table[i];
            }
        }

        //check. НАПИСАТЬ СОРТИРОВКУ от больших к меньшим

        return "null";
    }
}

//В карту сохраняется место в колоде, чтобы можно было вернуть по индексу