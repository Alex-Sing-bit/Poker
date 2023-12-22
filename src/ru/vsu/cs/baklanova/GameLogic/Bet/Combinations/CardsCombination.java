package ru.vsu.cs.baklanova.GameLogic.Bet.Combinations;

import ru.vsu.cs.baklanova.GameLogic.Cards.Card;
;
import java.util.List;

public class CardsCombination {
    private CardsCombinationEnum status;
    private List<Card> maxCombination;

    public CardsCombination(CardsCombinationEnum status, List<Card> maxCombination) {
        this.status = status;
        this.maxCombination = maxCombination;
    }

    public void setStatus(CardsCombinationEnum status) {
        this.status = status;
    }

    public void setMax(List<Card> max) {
        this.maxCombination = maxCombination;
    }

    public CardsCombinationEnum getStatus() {
        return status;
    }


    public List<Card> getMax() {
        return maxCombination;
    }
}
