package ru.vsu.cs.baklanova.GameLogic.Bet;

import ru.vsu.cs.baklanova.GameLogic.Bet.Combinations.CardsCombination;
import ru.vsu.cs.baklanova.GameLogic.Bet.Combinations.CombinationUtils;
import ru.vsu.cs.baklanova.GameLogic.Cards.Card;
import ru.vsu.cs.baklanova.GameDrawing.Events.PlayerBetEvent;
import ru.vsu.cs.baklanova.GameLogic.Game.Game;
import ru.vsu.cs.baklanova.GameLogic.Player.Player;

import java.util.*;

public class Croupier {

    private final int BET_CIRCLE_NUM = 3;

    private final int startMoney;
    private final int startBet;

    private final Map<Player, Integer> playerMoney = new HashMap<>();
    private final Map<Player, Integer> playerBet = new HashMap<>();
    private final Map<Player, CardsCombination> playerCombination = new HashMap<>();

    Game game;

    private int betCircle;
    private int commonBet;
    private int lastBet;

    public Croupier(Game game, int startMoney) {
        this.startMoney = startMoney;
        this.startBet = startMoney / 10;
        this.game = game;
        setAllPlayerMoney(startMoney);
        setForNextCircle();
    }

    public boolean startBet() {
        boolean retBool = false;
        setForNextCircle();
        while (betCircle < BET_CIRCLE_NUM) {
            setAllPlayerCombination();
            retBool = betCircle();

            if (retBool) {
                break;
            }
            betCircle++;
        }


        return retBool;
    }

    private boolean betCircle() {
        int i = 0;
        boolean stopBet = false;
        int firstBet = 0;

        for (Player p : players()) {
            if (!p.getInGame()) {
                continue;
            }
            i++;
            npcChoice(p, i);
            if (i == 1) {
                firstBet = lastBet;
            }
            stopBet = stopBet || stopBetCircles();
            if (stopBet) {
                break;
            } else if (nHaveMoney(1)) {
                stopBet = true;
            }
        }

        return stopBet || firstBet == lastBet;
    }

    private void npcChoice(Player p, int personIndex) {
        int choice = (int) (Math.random() * 100);
        int confidence = npcConfident(p);

        int money = getPlayerMoney(p);
        if (personIndex < 2 && betCircle == 0) {
            placeBet(lastBet, p);
            return;
        }
        if (lastBet != 0 && choice / 2 > confidence) {
            discardCards(p);
            return;
        }
        if (choice < confidence / 2 && !nHaveMoney(1)) {
            int newBet = (int) (((1 + confidence)/100.0) * Math.min(lastBet, money));
            int bet = lastBet + newBet;
            placeBet(bet, p);
            return;
        }

        placeBet(lastBet, p);
    }

    private int npcConfident(Player p) {
        CardsCombination c = getPlayerStatus(p);
        if (c == null) {
            return -1;
        }

        int statusValue = c.getStatus().getCount() * 5;

        List<Card> cards = c.getMax();
        int maxCard = cards.get(cards.size() - 1).getCardValueNum();

        return p.getRiskRatio() + statusValue + maxCard;
    }

    private boolean stopBetCircles() {
        return countPlayersInGame() <= 1 || nHaveMoney(0);
    }
    private int countPlayersInGame() {
        int k = 0;
        for (Player p : players()) {
            k += (p.getInGame() ? 1 : 0);
        }

        return k;
    }

    private boolean nHaveMoney(int n) {
        int k = 0;
        for (Player p : players()) {
            k += (p.getInGame() && getPlayerMoney(p) != 0 ? 1 : 0);
        }

        return k <= n;
    }



    private void placeBet(int bet, Player p) {
        int lastMoney = playerMoney.get(p);
        int lastBet = playerBet.get(p);

        String m = (bet > this.lastBet ? "Повышает" : "Поддерживает");

        int betDelta = bet - lastBet;
        if (lastMoney < betDelta) {
            final int I_GIVE_UP = 50;
            if (p.getRiskRatio() < I_GIVE_UP && !(lastMoney < startMoney)) {
                discardCards(p);
                return;
            }
            bet = lastBet + lastMoney;
            betDelta = lastMoney;
            m = "идёт ва-банк";
        }
        playerMoney.put(p, lastMoney - betDelta);
        playerBet.put(p, bet);
        commonBet += betDelta;


        new PlayerBetEvent(p, bet, lastMoney  + lastBet - bet, getPlayerStatus(p), m)
                .saveEventAndDrawIt();
        this.lastBet = Math.max(bet, this.lastBet);
    }

    private void discardCards(Player p) {
        p.setInGame(false);
        new PlayerBetEvent(p, -1, getPlayerMoney(p), getPlayerStatus(p)).saveEventAndDrawIt();
    }

    private void setForNextCircle() {
        setAllBetsForStart();
        betCircle = 0;
    }

    private void setAllPlayerCombination() {
        for (Player p : players()) {
            setPlayerCombination(p);
        }
    }

    private void setPlayerCombination(Player p) {
        this.playerCombination.remove(p);
        this.playerCombination.put(p, getCardCombination(p.getCards()));
    }

    private CardsCombination getCardCombination(List<Card> list) {
        List<Card> cards = new ArrayList<>();
        cards.addAll(list);
        cards.addAll(tableCards());

        return CombinationUtils.check(cards);
    }

    public boolean checkMoney() {
        int k = 0;
        for (Player p : players()) {
            if (getPlayerMoney(p) > 0) {
                k++;
            }
        }

        return k > 1;
    }

    private void setAllPlayerMoney(int money) {
        for (Player p : players()) {
            setPlayerMoney(p, money);
        }
    }

    private void setAllBetsForStart() {
        for (Player p : players()) {
            setPlayerBet(p, 0);
        }
        lastBet = startBet;
    }

    private void setPlayerBet(Player p, int bet) {
        if (bet < 0) {
            bet = 0;
        }
        this.playerBet.put(p, bet);
    }

    private void setPlayerMoney(Player p, int money) {
        if (money < 0) {
            money = 0;
        }
        this.playerMoney.put(p, money);
    }

    private void changePlayerMoney(Player p, int money) {
        int last = this.playerMoney.get(p);
        this.playerMoney.put(p, last + money);
    }

    private void changePlayerBet(Player p, int bet) {
        int last = this.playerBet.get(p);
        this.playerBet.put(p, last + bet);
    }

    public CardsCombination getPlayerStatus(Player p) {
        return playerCombination.get(p);
    }

    public int getPlayerMoney(Player p) {
        return (playerMoney.get(p) == null ? -1 : playerMoney.get(p));
    }
    public int getPlayerBet(Player p) {
        return (playerBet.get(p) == null ? -1 : playerBet.get(p));
    }

    private List<Card> getTableCards() {
        return tableCards();
    }

    public List<Card> tableCards() {
        return game.getTable().getTableCards();
    }

    public List<Player> players() {
        return game.getPlayers();
    }

    public void resetCommonBet() {
        this.commonBet = 0;
    }

    public int getCommonBet() {
        return commonBet;
    }

    public Map<Player, CardsCombination> getPlayerCombination() {
        return playerCombination;
    }

    public Map<Player, Integer> getPlayerBet() {
        return playerBet;
    }

    public Map<Player, Integer> getPlayerMoney() {
        return playerMoney;
    }
}
