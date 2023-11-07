package ru.vsu.cs.baklanova;

import ru.vsu.cs.baklanova.Cards.CardBlock;
import ru.vsu.cs.baklanova.Player.Player;

import java.io.PrintStream;
import java.util.Locale;


public class Program {

    public static class CmdParams {
        //public String inputFile;
        //public String outputFile;
        public boolean window;
        public boolean first;
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
            if (args[0].equals("-first")) {
                params.first = true;
            }
            if (args.length > 1) {
                params.help = true;
                params.error = true;
                return params;
            }
            /*
            params.inputFile = args[1];
            if (args.length > 2) {
                params.outputFile = args[2];
            }*/
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
            out.println("  <cmd> args <input-file> (<output-file>)");
            out.println("    -first  // give answer version 1");
            out.println("  <cmd> --help");
            out.println("  <cmd> --window  // show window");
            System.exit(params.error ? 1 : 0);
        }
        if (params.window) {
            winMain();
        }
        else {
            if (params.first) {
                Game mainGame = new Game(false, 2);
                for (Player player : mainGame.getTable().getPlayers()) {
                    System.out.println("NAME: " + player.getName());
                    System.out.println("MONEY: " + player.getMoney());
                    System.out.println(player.getCards().get(0).getCardSuit() + " " + player.getCards().get(0).getCardValue());
                    System.out.println(player.getCards().get(1).getCardSuit() + " " + player.getCards().get(1).getCardValue());
                    System.out.println();
                }

                CardBlock cardBlock = new CardBlock(12);
                cardBlock.takeCard();
                System.out.println();
                //mainGame.gameRound();
            }

        }
    }
}