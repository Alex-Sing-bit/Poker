package ru.vsu.cs.baklanova.Cards;

public class CardStatus {
    private CardStatusEnum status;
    private int max;

    public CardStatus(CardStatusEnum status, int max) {
        this.status = status;
        this.max = max;
    }

    public void setStatus(CardStatusEnum status) {
        this.status = status;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public CardStatusEnum getStatus() {
        return status;
    }

    public int getMax() {
        return max;
    }
}
