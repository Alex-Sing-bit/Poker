package ru.vsu.cs.baklanova.Cards;

public class CardsCombinationStatus {
    private CardsCombinationStatusEnum status;
    private int max;

    public CardsCombinationStatus(CardsCombinationStatusEnum status, int max) {
        this.status = status;
        this.max = max;
    }

    public void setStatus(CardsCombinationStatusEnum status) {
        this.status = status;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public CardsCombinationStatusEnum getStatus() {
        return status;
    }

    public int getMax() {
        return max;
    }
}
