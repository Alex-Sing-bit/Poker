package ru.vsu.cs.baklanova.GameLogic;

import ru.vsu.cs.baklanova.GameLogic.Cards.Card;
import ru.vsu.cs.baklanova.GameLogic.Cards.CardBlock;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private List<Card> tableCards = new ArrayList<>();

    public Table(CardBlock main) throws Exception {
        addTableCards(main);
    }

    private void addTableCards(CardBlock main) throws Exception {
        addTableCards(main, 0);
    }

    public void addTableCards(CardBlock main, int num) throws Exception {
        if (main == null) {
            throw new Exception("Колода пустая. Стол не может получить карту.");
        }

        if (tableCards == null || num < 0) {
            tableCards = new ArrayList<>();
        }

        tableCards.addAll(CardBlock.takeCardsList(main, num));
    }
    public List<Card> getTableCards() {
        return tableCards;
    }

}
