package ru.vsu.cs.baklanova.GameDrawing.Events;

import ru.vsu.cs.baklanova.GameLogic.Cards.Card;
import ru.vsu.cs.baklanova.GameLogic.Player.Player;

import java.util.List;

import static ru.vsu.cs.baklanova.GameDrawing.EventsDrawingUtils.*;

public class AllUpdateEvent implements GameEvent{
    List<Player> players;
    List<Card> table;

    public AllUpdateEvent(List<Player> players, List<Card> table) {
        this.players = players;
        this.table = table;
    }

    @Override
    public void draw() {
        playersOutput(players, true);
        tableOutput(table);
    }

}
