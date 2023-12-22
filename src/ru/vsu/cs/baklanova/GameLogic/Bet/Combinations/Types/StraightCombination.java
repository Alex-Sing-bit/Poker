package ru.vsu.cs.baklanova.GameLogic.Bet.Combinations.Types;

import ru.vsu.cs.baklanova.GameLogic.Bet.Combinations.CardsCombination;
import ru.vsu.cs.baklanova.GameLogic.Bet.Combinations.CardsCombinationEnum;
import ru.vsu.cs.baklanova.GameLogic.Bet.Combinations.CombinationUtils;
import ru.vsu.cs.baklanova.GameLogic.Cards.Card;

import java.util.List;

import static ru.vsu.cs.baklanova.GameLogic.Bet.Combinations.CombinationUtils.combinationWithCardsInSameValueTwice;

public class StraightCombination implements ICombination {
    @Override
    public CardsCombination check(List<Card> list) {
        if (list.size() < 5) {
            return null;
        }

        List<Card> arr = CombinationUtils.fiveMaxCardsValuesInRow(list);
        if (arr == null) {
            return null;
        }

        return new CardsCombination(CardsCombinationEnum.STRAIGHT, arr);
    }
}