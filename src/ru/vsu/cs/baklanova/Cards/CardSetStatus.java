package ru.vsu.cs.baklanova.Cards;

import java.util.ArrayList;

public class CardSetStatus {

    public static CardStatus setStatus(ArrayList<Card> table, ArrayList<Card> player) throws Exception {
        final int startK = 2;
        if (player == null) {
            throw new Exception("У игрока нет карт");
        }
        int size = player.size();
        int tableSize = 0;
        if (table != null) {
            tableSize += table.size();
            size += tableSize;
        }

        //ВЗЯТЬ КОНСТАНТУ ИЗ КОЛОДЫ
        int CARD_VALUES_NUM = 14;

        int[][] matrix = new int[CARD_VALUES_NUM + 1][CardSuitEnum.values().length];

        Card card;
        int max = -1;
        for (int i = 0; i < size; i++) {
            if (i > tableSize - 1) {
                card = player.get(i % (tableSize - 1) - 1);
            } else {
                card = table.get(i);
            }
            if (card.getCardValue() > max)
                max = card.getCardValue();
            matrix[card.getCardValue()][card.getCardSuit().getCount()] += 1;
        }
        CardStatus status = new CardStatus(CardStatusEnum.HIGH_CARD, max);

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
        for (int i = startK; i < matrix.length; i++) {   //По масти
            int k = 0;
            for (int j = 0; j < matrix[0].length; j++) { //По значению
                if (matrix[i][j] > 0) {
                    k++;
                }
            }
            if (k == 2) {
                doubles++;
            }
            maxValueRepeats = Math.max(k, maxValueRepeats);
        }

        int cEnum = -1;
        for (int i = 0; i < matrix[0].length; i++) {
            int maxSuitsRepeats = 0;
            for (int j = startK; j < matrix.length; j++) {
                if (matrix[j][i] > 0) {
                    maxSuitsRepeats++;
                }
            }
            if (maxSuitsRepeats == 5) {
                cEnum = i;
            }
        }

        int isLine = -1;
        if (cEnum >= 0) {
            isLine = isLine(matrix[cEnum]);
        }
        if (cEnum != -1) {
            if (isLine == -1) {
                status.setStatus(CardStatusEnum.FLUSH);
                return status;
            } else {
                if (isLine == 14) {
                    status.setStatus(CardStatusEnum.ROYAL_FLUSH);
                    return status;
                }
                status.setStatus(CardStatusEnum.STRAIGHT_FLUSH);
                return status;
            }
        } else {
            if (maxValueRepeats == 4) {
                status.setStatus(CardStatusEnum.FOUR_OF_A_KIND);
                return status;
            } else if (maxValueRepeats == 3 && doubles > 0) {
                status.setStatus(CardStatusEnum.FULL_HOUSE);
                return status;
            } else if (isStraight(matrix, startK)) {
                status.setStatus(CardStatusEnum.STRAIGHT);
                return status;
            } else if (maxValueRepeats == 3) {
                status.setStatus(CardStatusEnum.THREE_OF_A_KIND);
                return status;
            } else if (doubles > 0) {
                if (doubles == 2) {
                    status.setStatus(CardStatusEnum.TWO_PAIR);
                    return status;
                }
                status.setStatus(CardStatusEnum.ONE_PAIR);
                return status;
            }
        }

        return status;
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

    private static boolean isStraight(int[][] matrix, int startK) {
        int count = 0;
        for (int i = startK; i < matrix.length; i++) {
            boolean next = false;
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[j][i] > 0) {
                    next = true;
                    break;
                }
            }
            if (next) {
                count++;
                if (count >= 5) {
                    break;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }
}

//В карту сохраняется место в колоде, чтобы можно было вернуть по индексу