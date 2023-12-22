package ru.vsu.cs.baklanova.GameDrawing.Events;

import ru.vsu.cs.baklanova.GameLogic.Player.Player;

import static ru.vsu.cs.baklanova.GameDrawing.EventsDrawingUtils.playerOutput;
import static ru.vsu.cs.baklanova.GameDrawing.EventsDrawingUtils.winnerOutput;

public class WinnerEvent implements GameEvent {
    Player p;
    Integer winning;

    public WinnerEvent(Player p, int winning) {
        this.p = p;
        this.winning = winning;
    }

    public WinnerEvent(Player p) {
        this.p = p;
        this.winning = 0;
    }

    @Override
    public void draw() {
        if (p == null) {
            System.out.println("Победитель не выявлен");
            return;
        }
        winnerOutput(p, winning);
    }
}
