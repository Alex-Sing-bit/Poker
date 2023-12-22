package ru.vsu.cs.baklanova.GameLogic.Cards;

import java.util.*;

public class CardBlock {
    private final int CARD_VALUES_NUM = CardValueEnum.values().length;

    private List<Card> cardBlock;

    public CardBlock() throws Exception {
        this.cardBlock = createCardBlock();
        shuffleCards();
    }

    public void shuffleCards() {
        Collections.shuffle(this.cardBlock);
    }
    private List<Card> createCardBlock() throws Exception {
        List<Card> cardBlock = new ArrayList<>();

        for (CardValueEnum i : CardValueEnum.values()) {
            for (CardSuitEnum j : CardSuitEnum.values()) {
                cardBlock.add(new Card(j, i));
            };
        }

        return cardBlock;
    }

    public static List<Card> takeCardsList(CardBlock c, int num) throws Exception {
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            cards.add(c.takeCard());
        }
        return cards;
    }


    public void returnCardsToBlock(List<Card> cards) throws Exception {
        for (Card c : cards) {
            returnCardToBlock(c);
        }
    }

    public void returnCardToBlock(Card card) {
        cardBlock.add(card);
    }

    public Card takeCard() throws Exception {
        Card c = cardBlock.get(0);
        cardBlock.remove(0);
        return c;
    }


    public List<Card> getCardBlock() {
        return cardBlock;
    }

    public int getCardNumber() {
        return CARD_VALUES_NUM * CardSuitEnum.values().length;
    }


    public int getCARD_VALUES_NUM() {
        return CARD_VALUES_NUM;
    }
}

