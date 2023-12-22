package ru.vsu.cs.baklanova.GameLogic.Player;

import ru.vsu.cs.baklanova.GameLogic.Cards.Card;
import ru.vsu.cs.baklanova.GameLogic.Cards.CardBlock;

import java.util.List;

public class Player {
    private final boolean isNPC;
    private PlayerEnum name;
    private final int cardsNumber = 2;
    private List<Card> cards;

    private boolean isInGame;

    private int riskRatio;

    public Player(CardBlock main, boolean isNPC) throws Exception {
        setName();
        this.isNPC = isNPC;
        this.isInGame = true;
        setCards(main);
        setRiskRatio();
    }

    public void setRiskRatio() {
        this.riskRatio = 30 + (int) (Math.random() * 50);
    }

    public static void addRiskRatioForAll(List<Player> players, int n) {
        for (Player p : players) {
            p.setRiskRatio(p.getRiskRatio() + n);
        }
    }
    public void setRiskRatio(int n) {
        if (n > 100) {
            n = 100;
        }
        this.riskRatio = n;
    }

    public void setCards(CardBlock main) throws Exception {
        if (main == null) {
            throw new Exception("Колода пустая. Игрок не может взять карту.");
        }

        this.cards = CardBlock.takeCardsList(main, cardsNumber);
    }

    public void setName() {
        PlayerEnum[] arr = PlayerEnum.values();
        this.name = arr[(int) (Math.random()* arr.length)];
    }

    public void setInGame(boolean inGame) {
        isInGame = inGame;
    }

    public List<Card> getCards() {
        return cards;
    }

    public Card getCard(int i) throws Exception {
        if (i > 0 && i < cards.size() - 1) {
            return cards.get(i);
        }

        throw new Exception("Неправильный номер карты");
    }

    public String getName() {
        return name.rusName;
    }

    public boolean getIsNPC() {
        return isNPC;
    }

    public boolean getInGame() {
        return isInGame;
    }

    public int getRiskRatio() {
        return riskRatio;
    }

    public static Player getFirstActive(List<Player> pl) {
        for (Player p : pl) {
            if (p.getInGame()) {
                return p;
            }
        }

        return null;
    }
}
