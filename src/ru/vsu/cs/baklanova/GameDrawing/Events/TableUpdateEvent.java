package ru.vsu.cs.baklanova.GameDrawing.Events;

import ru.vsu.cs.baklanova.GameLogic.Cards.Card;

import java.util.List;

import static ru.vsu.cs.baklanova.GameDrawing.EventsDrawingUtils.tableOutput;

public class TableUpdateEvent implements GameEvent{
    List<Card> table;
    int commonBet;

    public TableUpdateEvent(List<Card> table) {
        this.table = table;
    }
    public TableUpdateEvent(List<Card> table, int commonBet) {
        this.table = table;
        this.commonBet = commonBet;
    }
    @Override
    public void draw() {
        tableOutput(table, commonBet);
    }

}
