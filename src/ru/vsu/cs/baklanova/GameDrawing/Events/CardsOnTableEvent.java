package ru.vsu.cs.baklanova.GameDrawing.Events;

import ru.vsu.cs.baklanova.GameLogic.Bet.Combinations.CardsCombination;
import ru.vsu.cs.baklanova.GameLogic.Cards.Card;
import ru.vsu.cs.baklanova.GameLogic.Player.Player;

import java.util.List;
import java.util.Map;

import static ru.vsu.cs.baklanova.GameDrawing.EventsDrawingUtils.*;

public class CardsOnTableEvent implements GameEvent{
    List<Player> players;
    List<Card> table;
    Map<Player, CardsCombination> combinations;

    public CardsOnTableEvent(List<Player> players, List<Card> table, Map<Player, CardsCombination> combinations) {
        this.players = players;
        this.table = table;
        this.combinations = combinations;
    }

    @Override
    public void draw() {
        System.out.println("Карты на стол:");
        tableOutput(table);
        for (Player p : players) {
            if (p.getInGame()) {
                playerOutput(p, true);
                combinationOutput(combinations.get(p));
            }
        }
    }

}
