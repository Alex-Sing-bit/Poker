package ru.vsu.cs.baklanova;

import java.util.ArrayList;

public class Table {
    private Player[] players;
    private CardBlock tableCards;

    public Table(int playersNum, CardBlock main) throws Exception {
        players = new Player[playersNum];
        tableCards = new CardBlock(0, false, main);
    }
}
