package ru.vsu.cs.baklanova.GameDrawing;

import ru.vsu.cs.baklanova.GameLogic.Bet.Combinations.CardsCombination;
import ru.vsu.cs.baklanova.GameLogic.Cards.Card;
import ru.vsu.cs.baklanova.GameLogic.Player.Player;

import java.util.List;

import static ru.vsu.cs.baklanova.GameDrawing.ConsoleTextEnum.*;

public class EventsDrawingUtils {
    public static void playersOutput(List<Player> players) {
        playersOutput(players, false);
    }
    public static void playersOutput(List<Player> players, boolean withCards) {
        int k = 0;
        for (Player p : players) {
            System.out.print(k++ + ".\t");
            playerOutput(p, withCards);
        }
    }

    public static void playerOutput(Player p) {
        playerOutput(p, false);
    }
    public static void playerOutput(Player p, boolean withCards) {
        if (p == null) {
            return;
        }

        if (p.getInGame()) {
            System.out.print(UNDERLINE.code);
            System.out.println(WHITE.code);
        } else {
            System.out.println(GREY.code);
        }
        System.out.print(p.getName() + RESET.code);
        System.out.println(GREY.code + "(" + p.getRiskRatio() + ")" + RESET.code); //+ " " + p.getInGame() +

        if (withCards && p.getInGame()) {
            cardsOutput(p.getCards());
        }
    }
    public static void cardsOutput(List<Card> cards) {
        for (Card c : cards) {
            cardOutput(c);
        }
        System.out.println();
    }

    private static void cardOutput(Card c) {
        if (c == null) {
            return;
        }
        System.out.print(WHITE_BG.code + BLACK.code + "["+ c.getCardSuit().getSuitChar() + " "
                + c.getCardValue().getCount() + "]" + RESET.code + "\t");
    }

    public static void combinationOutput(CardsCombination cardsCombination) {
        if (cardsCombination == null) {
            return;
        }
        System.out.println(CYAN.code + "Статус: " + cardsCombination.getStatus().getRusStringName() + RESET.code);
        cardsOutput(cardsCombination.getMax());
        System.out.println();
    }

    public static void betOutput(int bet) {
        betOutput(bet, "");
    }
    public static void betOutput(int bet, String betMessage) {
        System.out.println(GREEN.code + "Игрок " + betMessage.toLowerCase() + ", ставка: " + bet + RESET.code);
    }


    public static void moneyOutput(int money) {
        System.out.println(GREEN.code + "Деньги игрока: " + money + RESET.code);
    }

    public static void tableOutput(List<Card> table, int commonBet) {
        tableOutput(table);
        System.out.println(GREEN.code + "Общая ставка: " + commonBet  + RESET.code);
    }
    public static void tableOutput(List<Card> table) {
        if (table == null) {
            return;
        } if (table.size() == 0) {
            return;
        }
        System.out.println(BOLD.code + "Стол: " + RESET.code);
        cardsOutput(table);
    }

    public static void winnerOutput(Player p, int winning) {
        System.out.println(YELLOW.code + "Победитель: ");
        playerOutput(p);
        if (winning > 0) {
            System.out.println(YELLOW.code + "Выигрыш: " + winning + RESET.code);
        }
    }
}
