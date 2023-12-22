package ru.vsu.cs.baklanova.GameLogic.Bet.Combinations.Types;

import ru.vsu.cs.baklanova.GameLogic.Bet.Combinations.CardsCombination;
import ru.vsu.cs.baklanova.GameLogic.Cards.Card;

import java.util.List;

public interface ICombination {
    CardsCombination check(List<Card> list);
}
