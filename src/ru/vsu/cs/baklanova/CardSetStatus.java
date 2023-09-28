package ru.vsu.cs.baklanova;

public class CardSetStatus {
    private final String[] STATUSES = new String[]{"Старшая карта", "Пара", "Две пары", "Тройка", "Стрит", "Флэш", "Фулл Хаус", "Каре", "Стрит", "Роял"};

    public static String setStatus(CardBlock table1, CardBlock player1) throws Exception {
        Card[] table = table1.getCardBlock();
        Card[] player = player1.getCardBlock();
        if (player == null) {
            throw new Exception("У игрока нет карт");
        }
        int size = player.length;
        int tableSize = 0;
        if (table != null) {
            tableSize += table.length;
            size += tableSize;
        }

        int[][] arr = new int[player1.getCARD_VALUES_NUM() + 1][player1.getCARD_SUIT().length];

        Card card = null;
        for (int i = 0; i < size; i++) {
            if (i > tableSize - 1) {
                card = player[i % (tableSize - 1) - 1];
            } else {
                card = table[i];
            }

            arr[card.getCardValue()][card.getCardSuit()] += 1;
        }



        /*
        : - из массива
        ; - по масти
        ? - последовательность
        :Пара, две пары - пара
        :Тройка - тройка
        ?;Стрит - от 10 до 6 разных мастей
        ;Флэш - 5 одной масти
        :Фулл Хаус - тройка и пара
        :Каре - четверка
        ?;Стрит - от 10 до 6 одной масти
        ?;Флэш - от туза(14) до 10 одной масти
         */
        for (int i = 0; i < size; i++) {

        }

        return "null";
    }
}

//В карту сохраняется место в колоде, чтобы можно было вернуть по индексу