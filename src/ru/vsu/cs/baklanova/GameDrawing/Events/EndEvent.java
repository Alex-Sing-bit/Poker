package ru.vsu.cs.baklanova.GameDrawing.Events;

public class EndEvent implements GameEvent{
    int roundNum;

    public EndEvent(int roundNum) {
        this.roundNum = roundNum;
    }
    @Override
    public void draw() {
        System.out.println("Игра окончена. Прошло " + roundNum + " раундов");
    }
}
