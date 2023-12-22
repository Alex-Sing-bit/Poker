package ru.vsu.cs.baklanova.GameLogic.Bet.Combinations.Types;

import ru.vsu.cs.baklanova.GameLogic.Bet.Combinations.CardsCombination;
import ru.vsu.cs.baklanova.GameLogic.Bet.Combinations.CardsCombinationEnum;
import ru.vsu.cs.baklanova.GameLogic.Bet.Combinations.CombinationUtils;
import ru.vsu.cs.baklanova.GameLogic.Cards.Card;
import ru.vsu.cs.baklanova.GameLogic.Cards.CardSuitEnum;

import java.util.List;
import java.util.Map;

public class FlushCombination implements ICombination {
    @Override
    public CardsCombination check(List<Card> list) {
        if (list.size() < 5) {
            return null;
        }
        Map<CardSuitEnum, List<Card>> checked1 = CombinationUtils.sortForCheckSuits(list);

        for (CardSuitEnum c : CardSuitEnum.values()) {
            if (checked1.get(c).size() >= 5) {
                List<Card> arr = checked1.get(c);
                int size = arr.size();
                return new CardsCombination(CardsCombinationEnum.FLUSH, arr.subList(size - 5, size));
            }
        }

        return null;
    }
}
