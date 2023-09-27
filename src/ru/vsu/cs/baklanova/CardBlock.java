package ru.vsu.cs.baklanova;

import java.util.function.Function;

public class CardBlock {
    private final int CARD_NUMBER = 52;
    private final int CARD_WEIGHTS_NUM = 14;
    private final String[] CARD_SUIT = new String[]{"club", "diamond", "heart", "spade"};
    private Card[] cardBlock;

    public CardBlock() {
        this.cardBlock = createMainCardBlock();
    }

    public Card[] createMainCardBlock() {
        Card[] cardBlock = new Card[CARD_NUMBER];
        int k = 0;
        for (int i = 0; i < CARD_NUMBER; i += CARD_SUIT.length) {
            for (String j : CARD_SUIT) {
                cardBlock[i + k] = new Card(j, CARD_WEIGHTS_NUM - i/4, true);
                k++;
            }
            if (k > 3) k = 0;
        }

        return cardBlock;
    }

    public Card[] getCardBlock() {
        return cardBlock;
    }

    public int getCARD_NUMBER() {
        return CARD_NUMBER;
    }

    public String[] getCARD_SUIT() {
        return CARD_SUIT;
    }
}
//Карта как отдельный элемент хранит масть, значение, изображение
//Колода хранит 36 карт и их состояние
