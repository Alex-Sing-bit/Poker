package ru.vsu.cs.baklanova.GameLogic.Bet.Combinations.Types;

import ru.vsu.cs.baklanova.GameLogic.Bet.Combinations.CardsCombination;
import ru.vsu.cs.baklanova.GameLogic.Bet.Combinations.CardsCombinationEnum;
import ru.vsu.cs.baklanova.GameLogic.Cards.Card;

import java.util.List;

import static ru.vsu.cs.baklanova.GameLogic.Bet.Combinations.CombinationUtils.combinationWithCardsInSameValueTwice;

public class HighCardCombination implements ICombination {
    @Override
    public CardsCombination check(List<Card> list) {
        list.sort(Card::compareTo);

        int size = Math.max(list.size(), 5);
        return new CardsCombination(CardsCombinationEnum.HIGH_CARD,  list.subList(size - 5, list.size()));
    }
}
