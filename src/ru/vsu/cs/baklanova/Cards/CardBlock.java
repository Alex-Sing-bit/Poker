package ru.vsu.cs.baklanova.Cards;

import java.util.ArrayList;

public class CardBlock {
    private final int cardNumber;
    private final int CARD_VALUES_NUM = 14;

    private ArrayList<Card> cardBlock;

    //Делать блок рандомным, добавлять карты в конец, забираем из начала

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

        size = cardBlock.size();

        return cardBlock;
    }

    public static ArrayList<Card> createArrayFromCardBlock(CardBlock c) throws Exception {
        ArrayList<Card> cards1 = new ArrayList<>();
        for (int i = 0; i < c.getCardNumber(); i++) {
            cards1.add(c.randomCart());
        }
        return cards1;
    }

    private Card randomCart() throws Exception{
        int num = cardNumber;
        int k = (int) (num * Math.random());
        for (int i = 0; i < num; i++) {
            Card b = cardBlock.get(k);
            if (b.getCardStatus()) {
                b.setCardStatus(false);
                return b;
            } else {
                k++;
                k = k % num;
            }
        }

        throw new Exception("В колоде закончились карты");
    }

    public void returnCardToBlock(Card card) throws Exception {
        int i = card.getCardValue();
        int j = card.getCardSuit().getCount();
        int k = 4 * (CARD_VALUES_NUM - i) + j;
        if (k > cardNumber) {
            throw new Exception("В блоке не было такой карты");
        }
        cardBlock.get(k).setCardStatus(true);
    };

    public Card takeCard() throws Exception {
        return randomCart();
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
