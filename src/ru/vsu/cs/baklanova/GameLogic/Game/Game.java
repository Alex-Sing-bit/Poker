package ru.vsu.cs.baklanova.GameLogic.Game;

import ru.vsu.cs.baklanova.GameDrawing.Events.*;
import ru.vsu.cs.baklanova.GameLogic.Bet.Croupier;
import ru.vsu.cs.baklanova.GameLogic.Cards.*;
import ru.vsu.cs.baklanova.GameLogic.Player.Player;
import ru.vsu.cs.baklanova.GameLogic.Table;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final CardBlock block;
    private final Table table;
    private ArrayList<Player> players;

    private final Croupier croupier;

    private int roundCircle;

    private final int ROUND_CIRCLES_NUM = 4;


    public Game(boolean haveRealPlayer, int playersNum, int startMoney) throws Exception {
        this.block = new CardBlock();
        this.table = new Table(block);
        int k = (haveRealPlayer ? 1 : 0);
        setPlayers(block, playersNum, playersNum - k);

        this.croupier = new Croupier(this, startMoney);

        this.roundCircle = 0;
    }

    public Table getTable() {
        return table;
    }

    public CardBlock getBlock() {
        return block;
    }

    private void setRoundCircle(int roundCircle) {
        this.roundCircle = roundCircle;
    }

    public void setPlayers(CardBlock main, int playersNum, int npcNum) throws Exception {
        if (playersNum > 8) {
            throw new Exception("Превышен лимит игроков");
        }
        ArrayList<Player> players = new ArrayList<>();
        boolean isNPC = false;
        int k = 0;
        for (int i = 0; i < playersNum; i++) {
            if (k < playersNum - npcNum) {
                k++;
            } else {
                isNPC = true;
            }
            players.add(new Player(main, isNPC));
        }

        this.players = players;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void gameStart() throws Exception {
        int k = 0;
        int n = 0;
        final int upRiskEveryNRounds = 20;
        while (!(gameOver())) {
            startRound();
            n++;
            k++;
            if (n > upRiskEveryNRounds) {
                final int RISK_UP = 5;
                Player.addRiskRatioForAll(players, RISK_UP);
                n = 0;
            }
        }

        checkWinner();
        new EndEvent(k).saveEventAndDrawIt();
    }

    public void startRound() throws Exception {
        preparationToNewRound();
        new AllUpdateEvent(players, table.getTableCards()).saveEventAndDrawIt();
        //boolean stopRound = false;
        while (roundCircle < ROUND_CIRCLES_NUM) {
            if (checkPlayersForNewRound()) {
                break;
            }
            setTable();
            new TableUpdateEvent(table.getTableCards(), croupier.getCommonBet()).saveEventAndDrawIt();
            if (croupier.startBet() || roundCircle >= 4) {
                break;
            }
            roundCircle++;
        }
        winnersTakeWinnings();
    }

    public void winnersTakeWinnings() throws Exception {
        List<Player> winners = cardsOnTable();

        int winnersSize = winners.size();
        int winning = croupier.getCommonBet();
        if (winnersSize > 1) {
            winning = winning / winnersSize;
        }

        for (Player p : winners) {
            new WinnerEvent(p, winning).saveEventAndDrawIt();
            takeWinning(p, winning);
        }
    }

    private void takeWinning(Player p, int winning) {
        croupier.getPlayerMoney().put(p, croupier.getPlayerMoney().get(p) + winning);
    }

    private List<Player> cardsOnTable() throws Exception {
        new CardsOnTableEvent(players, table.getTableCards(), croupier.getPlayerCombination()).saveEventAndDrawIt();
        List<Player> winners = new ArrayList<>();
        Player winner = null;
        for (Player p : players) {
            if (!p.getInGame()) {
                continue;
            } else if (winner == null) {
                winner = p;
                winners.add(p);
                continue;
            }

            winner = checkBestCombo(p, winner, winners);
        }
        return winners;
    }

    private Player checkBestCombo(Player p, Player winner, List<Player> winners) throws Exception {
        int pCombo = croupier.getPlayerStatus(p).getStatus().getCount();
        int winnerCombo = croupier.getPlayerStatus(winner).getStatus().getCount();

        if (winnerCombo < pCombo) {
            winners.clear();
            winner = p;
            winners.add(p);
        } else if (pCombo == winnerCombo) {
            int compared = compareCardsInCombination(p, winner);
            if (compared >= 0) {
                if (compared != 0) {
                    winners.clear();
                }
                winners.add(p);
                winner = p;
            }
        }

        return winner;
    }

    private int compareCardsInCombination(Player p1, Player p2) throws Exception {
        List<Card> p1Cards = croupier.getPlayerStatus(p1).getMax();
        List<Card> p2Cards = croupier.getPlayerStatus(p2).getMax();
        int p1Size = p1Cards.size();
        int p2Size = p2Cards.size();
        if (p1Size != p2Size) {
            throw new Exception("Players have different number of cards");
        }

        for (int i = p1Size - 1; i >= 0; i--) {
            int winner = p1Cards.get(i).compareTo(p2Cards.get(i));
            if (winner == 0) {
                continue;
            }
            return winner;
        }

        return 0;
    }

    private void preparationToNewRound() throws Exception {
        roundCircle = 0;
        croupier.resetCommonBet();
        setTable();
        setBlock();
        setPlayers();
        changeButton();
    }

    private void setTable() throws Exception {
        switch (roundCircle) {
            case 0 -> {
                block.returnCardsToBlock(table.getTableCards());
                table.addTableCards(block, -1);
            }
            case 1 -> table.addTableCards(block, 3);
            case 2, 3  -> table.addTableCards(block, 1);
        }
    }

    private void setBlock() throws Exception {
        for (Player p : players) {
            block.returnCardsToBlock(p.getCards());
        }
        block.returnCardsToBlock(table.getTableCards());
        block.shuffleCards();
    }

    private void setPlayers() throws Exception {
        for (Player p : players) {
            p.setInGame(croupier.getPlayerMoney(p) > 0);

            if (!p.getInGame()) {
                block.returnCardsToBlock(p.getCards());
            } else {
                p.setCards(block);
            }
        }
    }

    private boolean checkPlayersForNewRound() {
        int notVabank = 0;
        for (Player p : players) {
            if (p.getInGame()) {
                notVabank++;
                if (croupier.getPlayerMoney(p) <= 0) {
                    notVabank--;
                }
            }
        }

        return notVabank == 1;
    }

    private void changeButton() {
        Player first = players.get(0);
        players.add(first);
        players.remove(0);
    }

    private boolean gameOver() {
        return !croupier.checkMoney();
    }
    private void checkWinner() {
        for (Player p : players) {
            if (p.getInGame() && croupier.getPlayerMoney(p) > 0) {
                new WinnerEvent(p).saveEventAndDrawIt();
            }
        }
    }


 /*private int gameOver() {
        int k = 0;
        int lastI = -1;
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            if (p.getMoney() > 0) {
                k++;
                lastI = i;
            }
            if (k > 1) {
                return -1;
            }
        }
        return lastI;
    }

    private boolean roundOver() {
        int k = 0;
        for (Player p : players) {
            if (p.getInGame() && (p.getMoney() > 0)) {
                k++;
            }
            if (k > 1) {
                return false;
            }
        }

        return true;
    }*/

}

//++ Оба сдаются в одном круге ставок --> Оба проиграли !! Исправить

// ++ 2/3 игроков true, у второго из них закончились деньги --> первый доигрывает круг ставок один !!
// Исправить: Если у всех true нет денег - остановиться.
// Добавить проверку на количество людей с деньгами среди true игроков

//++ Повышать, а не добавлять, ставку

//++ Каждый круг не обновлять ставки стола, а объединять с прошлым кругом

//++ Проверка значения второй карты

//++На третьем круге ставок последний игрок может повысить (это не имеет смысла и только мешает)

//**Две пары на столе, у игрока нет, проверка

//Все проиграли <-- у всех активных игроков нет денег

//Сдавшийся запоминает в себе сумму на столе до того, как он сдался

//Stack из крупье не вливается в обычный Stack