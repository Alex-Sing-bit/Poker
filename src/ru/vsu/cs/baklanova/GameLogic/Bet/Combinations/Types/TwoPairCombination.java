package ru.vsu.cs.baklanova.GameLogic.Bet.Combinations.Types;

import ru.vsu.cs.baklanova.GameLogic.Bet.Combinations.CardsCombination;
import ru.vsu.cs.baklanova.GameLogic.Bet.Combinations.CardsCombinationEnum;
import ru.vsu.cs.baklanova.GameLogic.Cards.Card;

import java.util.List;

import static ru.vsu.cs.baklanova.GameLogic.Bet.Combinations.CombinationUtils.combinationWithCardsInSameValueTwice;

public class TwoPairCombination implements ICombination {
    @Override
    public CardsCombination check(List<Card> list) {
        if (list.size() < 5) {
            return null;
        }
        list.sort(Card::compareTo);

        List<Card> arr = combinationWithCardsInSameValueTwice(list, 2, 2, 1);
        if (arr != null) {
            return new CardsCombination(CardsCombinationEnum.TWO_PAIR, arr);
        }

        return null;
    }
}
