package ru.vsu.cs.baklanova;

import java.util.ArrayList;

public class CardSetStatus {

    public static CardStatusEnum setStatus(CardBlock table1, CardBlock player1) throws Exception {
        ArrayList<Card> table = table1.getCardBlock();
        ArrayList<Card>  player = player1.getCardBlock();
        if (player == null) {
            throw new Exception("У игрока нет карт");
        }
        int size = player.size();
        int tableSize = 0;
        if (table != null) {
            tableSize += table.size();
            size += tableSize;
        }

        int[][] arr = new int[player1.getCARD_VALUES_NUM() + 1][CardSuitEnum.values().length];

        Card card;
        for (int i = 0; i < size; i++) {
            if (i > tableSize - 1) {
                card = player.get(i % (tableSize - 1) - 1);
            } else {
                card = table.get(i);
            }

            arr[card.getCardValue()][card.getCardSuit().getCount()] += 1;
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

        int doubles = 0;
        int maxValueRepeats = 0;
        for (int i = 0; i < arr.length; i++) {   //По масти
            int k = 0;
            for (int j = 0; j < arr[0].length; j++) { //По значению
                if (arr[i][j] > 0) {
                    k++;
                }
            }
            if (k == 2) {
                doubles++;
            }
            maxValueRepeats = Math.max(k, maxValueRepeats);
        }

        int cEnum = -1;
        for (int i = 0; i < arr[0].length; i++) {
            int maxSuitsRepeats = 0;
            for (int j = 0; j < arr.length; j++) {
                if(arr[j][i] > 0) {
                    maxSuitsRepeats++;
                }
            }
            if (maxSuitsRepeats == 5) {
                cEnum = i;
            }
        }

        int isLine = -1;
        if (cEnum >= 0) {
            isLine = isLine(arr[cEnum]);
        }
        if (cEnum != -1) {
            if (isLine == -1) {
                return CardStatusEnum.FLUSH;
            } else {
                if (isLine == 14) {
                    return CardStatusEnum.ROYAL_FLUSH;
                }
                return CardStatusEnum.STRAIGHT_FLUSH;
            }
        } else {
            if (maxValueRepeats == 4) {
                return CardStatusEnum.FOUR_OF_A_KIND;
            } else if (maxValueRepeats == 3 && doubles > 0) {
                return CardStatusEnum.FULL_HOUSE;
            } else if (isLine >= 0) {
                return CardStatusEnum.STRAIGHT;
            } else if (maxValueRepeats == 3) {
                return CardStatusEnum.THREE_OF_A_KIND;
            } else if (doubles > 0) {
                if (doubles == 2) {
                    return CardStatusEnum.TWO_PAIR;
                }
                return CardStatusEnum.ONE_PAIR;
            }
        }

        return CardStatusEnum.HIGH_CARD;
    }

    private static int isLine(int[] arr) {
        int firstIn = 0;
        int n = 0;
        for (int i = arr.length - 1; i > 0; i--) {
            if (n >= 5) {
                return firstIn;
            }
            if (arr[i] == arr[i - 1] && arr[i] > 0) {
                n++;
            } else {
                firstIn = i;
                n = 0;
            }
        }
        return -1;
    }
}

//В карту сохраняется место в колоде, чтобы можно было вернуть по индексу