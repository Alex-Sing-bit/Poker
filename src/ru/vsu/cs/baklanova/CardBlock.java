package ru.vsu.cs.baklanova;

public class CardBlock {
    private final int CARD_NUMBER = 52;
    private final int CARD_WEIGHTS_NUM = 14;
    private final String[] CARD_SUIT = new String[]{"club", "diamond", "heart", "spade"};
    private Card[] cardBlock;

    public class Card {
        private String cardSuit;
        private int cardWeight;

        private boolean cardStatus;

        public Card(String cardSuit, int cardWeight, boolean bol) {
            this.cardSuit = cardSuit;
            setCardWeight(cardWeight);
            this.cardStatus = bol;
        }

        public void setCardWeight(int cardWeight) {
            if (cardWeight > 0) {
                this.cardWeight = cardWeight;
            }
            //Иначе ошибка
        }

        public int getCardWeight() {
            return cardWeight;
        }

        public String getCardSuit() {
            return cardSuit;
        }

        public boolean getCardStatus() {
            return cardStatus;
        }

        public void setCardStatus(boolean cardStatus) {
            this.cardStatus = cardStatus;
        }
    }

    public CardBlock() {
        this.cardBlock = createCardBlock();
    }

    public Card[] createCardBlock() {
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
