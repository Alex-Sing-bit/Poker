package ru.vsu.cs.baklanova;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;


public class Program {

    public static class CmdParams {
        public String inputFile;
        public String outputFile;
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
            if (args.length < 2) {
                params.help = true;
                params.error = true;
                return params;
            }
            params.inputFile = args[1];
            if (args.length > 2) {
                params.outputFile = args[2];
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

            PrintStream out = (params.outputFile != null) ? new PrintStream(params.outputFile) : System.out;
            out.print('8');
            out.close();
        }
    }
}