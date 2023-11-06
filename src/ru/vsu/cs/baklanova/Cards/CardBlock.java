package ru.vsu.cs.baklanova.Cards;

import java.util.ArrayList;

public class CardBlock {
    private int cardNumber;
    private final int CARD_VALUES_NUM = 14;

    private ArrayList<Card> cardBlock;

    public CardBlock(int cardNumber) throws Exception {
        this.cardNumber = cardNumber;
        this.cardBlock = createCardBlock();
    }

    private ArrayList<Card> createCardBlock() throws Exception {
        ArrayList<Card> cardBlock = new ArrayList<>();
        int k = 0;
        int size = CardSuitEnum.values().length;
        for (int i = 0; i < cardNumber; i += size) {
            for (CardSuitEnum j : CardSuitEnum.values()) {
                cardBlock.add(k + i, new Card(j, CARD_VALUES_NUM - i/4, true));
                k++;
            }
            if (k > 3) k = 0;
        }

        return cardBlock;
    }

    public static ArrayList<Card> createArrayFromCardBlock(CardBlock c) throws Exception {
        ArrayList<Card> cards1 = new ArrayList<>();
        for (int i = 0; i < c.getCardNumber(); i++) {
            cards1.add(randomCart(c));
        }
        return cards1;
    }

    private static Card randomCart(CardBlock cb) throws Exception{
        int num = cb.getCardBlock().size();
        int k = (int) (num * Math.random());
        for (int i = 0; i < num; i++) {
            if (cb.getCardBlock().get(k).getCardStatus()) {
                cb.getCardBlock().get(k).setCardStatus(false);
                return cb.getCardBlock().get(k);
            } else {
                k++;
                k = k % num;
            }
        }

        throw new Exception("В колоде закончились карты");
    }

    public void addCardToBlock(Card card) {
        this.cardNumber++;
        cardBlock.add(card);
    };

    public static Card takeCard(CardBlock main) throws Exception {
        return randomCart(main);
    };


    public ArrayList<Card> getCardBlock() {
        return cardBlock;
    }

    public int getCardNumber() {
        return cardNumber;
    }


    public int getCARD_VALUES_NUM() {
        return CARD_VALUES_NUM;
    }
}
//Карта как отдельный элемент хранит масть, значение, изображение
//Колода хранит 36 карт и их состояние
