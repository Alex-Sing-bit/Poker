package ru.vsu.cs.baklanova;

public class CardSetStatus {
    private final String[] STATUSES = new String[]{"Старшая карта", "Пара", "Две пары", "Тройка", "Стрит", "Флэш", "Фулл Хаус", "Каре", "Стрит", "Роял"};

    public static String setStatus(Card[] table, Card[] player) throws Exception {
        if (player == null) {
            throw new Exception("У игрока нет карт");
        }
        int size = player.length;
        int tableSize = 0;
        if (table != null) {
            tableSize += table.length;
            size += tableSize;
        }
        Card[] check = new Card[size];
        for (int i = 0; i < size; i++) {
            if (i > tableSize - 1) {
                check[i] = player[i % (tableSize - 1) - 1];
            } else {
                check[i] = table[i];
            }
        }

       CardBlock.cardSort(check);

        return "null";
    }
}

//В карту сохраняется место в колоде, чтобы можно было вернуть по индексу