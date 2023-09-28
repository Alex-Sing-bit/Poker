package ru.vsu.cs.baklanova;

public class CardBlock {
    private int cardNumber;
    private final int CARD_WEIGHTS_NUM = 14;
    private final String[] CARD_SUIT = new String[]{"крести", "бубна", "черва", "пика"};
    //private final String[] CARD_SUIT = new String[]{"club", "diamond", "heart", "spade"};
    private Card[] cardBlock;

    private boolean isMainBlock;

    public CardBlock(int cardNumber, boolean isMainBlock, CardBlock main) throws Exception {
        this.isMainBlock = isMainBlock;
        this.cardNumber = cardNumber;
        if (isMainBlock) {
            this.cardBlock = createMainCardBlock();
        } else {
            this.cardBlock = createCardBlock(main);
        }
    }

    public Card[] createMainCardBlock() throws Exception {
        Card[] cardBlock = new Card[cardNumber];
        int k = 0;
        for (int i = 0; i < cardNumber; i += CARD_SUIT.length) {
            for (String j : CARD_SUIT) {
                cardBlock[i + k] = new Card(j, CARD_WEIGHTS_NUM - i/4, true);
                k++;
            }
            if (k > 3) k = 0;
        }

        return cardBlock;
    }

    private Card[] createCardBlock(CardBlock c) throws Exception {
        Card[] cards1 = new Card[cardNumber];
        cards1[0] = randomCart(c);
        cards1[1] = randomCart(c);
        return cards1;
    }

    private Card randomCart(CardBlock cb) throws Exception{
        int num = cb.getCardBlock().length;
        int k = (int) (num * Math.random());
        for (int i = 0; i < num; i++) {
            if (cb.getCardBlock()[k].getCardStatus()) {
                cb.getCardBlock()[k].setCardStatus(false);
                return cb.getCardBlock()[k];
            } else {
                k++;
                k = k % num;
            }
        }

        throw new Exception("В колоде закончились карты");
    }

    public Card[] getCardBlock() {
        return cardBlock;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public String[] getCARD_SUIT() {
        return CARD_SUIT;
    }


}
//Карта как отдельный элемент хранит масть, значение, изображение
//Колода хранит 36 карт и их состояние
