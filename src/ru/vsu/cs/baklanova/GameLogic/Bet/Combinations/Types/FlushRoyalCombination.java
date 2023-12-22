package ru.vsu.cs.baklanova.GameLogic.Bet.Combinations.Types;

import ru.vsu.cs.baklanova.GameLogic.Bet.Combinations.CardsCombination;
import ru.vsu.cs.baklanova.GameLogic.Bet.Combinations.CardsCombinationEnum;
import ru.vsu.cs.baklanova.GameLogic.Bet.Combinations.CombinationUtils;
import ru.vsu.cs.baklanova.GameLogic.Cards.Card;
import ru.vsu.cs.baklanova.GameLogic.Cards.CardSuitEnum;
import ru.vsu.cs.baklanova.GameLogic.Cards.CardValueEnum;

import java.util.List;
import java.util.Map;

public class FlushRoyalCombination implements ICombination {
    @Override
    public CardsCombination check(List<Card> list) {
        if (list.size() < 5) {
            return null;
        }
        Map<CardSuitEnum, List<Card>> map = CombinationUtils.sortForCheckSuits(list);

        for (CardSuitEnum c : CardSuitEnum.values()) {
            if (map.get(c).size() >= 5) {
                List<Card> arr = CombinationUtils.combinationFiveValuesInRow(map, CardValueEnum.ACE);
                if (arr == null) {
                    continue;
                }
                return new CardsCombination(CardsCombinationEnum.ROYAL_FLUSH, arr);
            }
        }

        return null;
    }
}
