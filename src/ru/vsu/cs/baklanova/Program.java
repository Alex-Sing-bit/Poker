package ru.vsu.cs.baklanova;

import ru.vsu.cs.baklanova.GameLogic.Bet.Combinations.CardsCombination;
import ru.vsu.cs.baklanova.GameLogic.Bet.Combinations.CombinationUtils;
import ru.vsu.cs.baklanova.GameLogic.Cards.Card;
import ru.vsu.cs.baklanova.GameLogic.Cards.CardBlock;
import ru.vsu.cs.baklanova.GameLogic.Cards.CardSuitEnum;
import ru.vsu.cs.baklanova.GameLogic.Cards.CardValueEnum;
import ru.vsu.cs.baklanova.GameDrawing.EventsDrawingUtils;
import ru.vsu.cs.baklanova.GameLogic.Game.Game;
import ru.vsu.cs.baklanova.GameLogic.Table;

import java.io.PrintStream;
import java.util.*;


public class Program {

    public static class CmdParams {
        //public String inputFile;
        //public String outputFile;
        public boolean window;
        public boolean start;
        public boolean error;
        public boolean help;

    }

    public static CmdParams parseArgs(String[] args) {
        CmdParams params = new CmdParams();
        if (args.length > 0) {
            if (args[0].equals("--help")) {
                params.help = true;
                return params;
            }
            if (args[0].equals("--window")) {
                params.window = true;
                return params;
            }
            if (args[0].equals("-start")) {
                params.start = true;
            }
            if (args.length > 3) {
                params.help = true;
                params.error = true;
                return params;
            }

        } else {
            params.help = true;
            params.error = true;
        }
        return params;
    }

    public static void winMain() throws Exception {
        Locale.setDefault(Locale.ROOT);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new FrameMain().setVisible(true);
            }
        });
    }

    public static void main(String[] args) throws Exception {
        CmdParams params = parseArgs(args);
        if (params.help) {
            PrintStream out = params.error ? System.err : System.out;
            out.println("Usage:");
            out.println("  <cmd> args <players number> (<start money>)");
            out.println("  <cmd> --start  // start game");
            out.println("  <cmd> --help");
            out.println("  <cmd> --window  // show window");
            System.exit(params.error ? 1 : 0);
        }
        if (params.window) {
            winMain();
        }
        else {
            if (params.start) {
                int pNum = 2;
                int startMoney = 1000;
                if (args.length >= 2) {
                    pNum = Integer.parseInt(args[1]);
                }
                if (args.length >= 3) {
                    startMoney = Integer.parseInt(args[2]);
                }
                game(pNum, startMoney);
            }

        }
    }

    private static void game(int playersNum, int startMoney) throws Exception {
         new Game(false, playersNum, startMoney).gameStart();

        System.out.println();
    }

    private static void test(int k, int n) throws Exception {
        CardBlock c = new CardBlock();

        int i = 0;
        while (i < k) {
            Table t = new Table(c);
            t.addTableCards(c, -1);
            t.addTableCards(c, n);

            CardsCombination cc = CombinationUtils.check(t.getTableCards());
            c.returnCardsToBlock(t.getTableCards());
            c.shuffleCards();

            try {
                EventsDrawingUtils.cardsOutput(t.getTableCards());
                System.out.println(cc.getStatus());
                EventsDrawingUtils.cardsOutput(cc.getMax());
                System.out.println();

                i++;
            } catch (NullPointerException e) {
                throw e;
            }
        }
    }

    private static void test1() throws Exception {
        CardBlock c = new CardBlock();

        List<Card> t = new ArrayList<>();

        t.add(new Card(CardSuitEnum.HEARTS, CardValueEnum.ACE));
        t.add(new Card(CardSuitEnum.HEARTS, CardValueEnum.QUEEN));
        t.add(new Card(CardSuitEnum.HEARTS, CardValueEnum.TEN));
        t.add(new Card(CardSuitEnum.HEARTS, CardValueEnum.JACK));
        t.add(new Card(CardSuitEnum.HEARTS, CardValueEnum.KING));

        CardsCombination cc = CombinationUtils.check(t);
        c.returnCardsToBlock(t);

        EventsDrawingUtils.cardsOutput(t);
        System.out.println(cc.getStatus());
        EventsDrawingUtils.cardsOutput(cc.getMax());
        System.out.println();
    }
}