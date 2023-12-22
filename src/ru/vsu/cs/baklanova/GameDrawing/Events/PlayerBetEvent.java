package ru.vsu.cs.baklanova.GameDrawing.Events;

import ru.vsu.cs.baklanova.GameDrawing.ConsoleTextEnum;
import ru.vsu.cs.baklanova.GameLogic.Bet.Combinations.CardsCombination;
import ru.vsu.cs.baklanova.GameLogic.Player.Player;

import static ru.vsu.cs.baklanova.GameDrawing.EventsDrawingUtils.*;

public class PlayerBetEvent implements GameEvent {
    Player p;
    Integer bet;
    Integer money;
    CardsCombination combination;

    String betMessage;

    public PlayerBetEvent(Player p, int bet, int money, CardsCombination combination, String betMessage) {
        this.p = p;
        this.bet = bet;
        this.money = money;
        this.combination = combination;

        this.betMessage = betMessage;
    }

    public PlayerBetEvent(Player p, int bet, int money, CardsCombination combination) {
        this.p = p;
        this.bet = bet;
        this.money = money;
        this.combination = combination;
    }

    @Override
    public void draw() {

        playerOutput(p);
        if (bet >= 0) {
            betOutput(bet, betMessage);
            moneyOutput(money);
            combinationOutput(combination);
        } else {
            System.out.println(ConsoleTextEnum.RED.getCode() + "Игрок сбросил карты" + ConsoleTextEnum.RESET.getCode());
        }
    }

}
