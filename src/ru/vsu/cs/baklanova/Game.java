package ru.vsu.cs.baklanova;

import ru.vsu.cs.baklanova.Cards.Card;
import ru.vsu.cs.baklanova.Cards.CardBlock;
import ru.vsu.cs.baklanova.Cards.CardSetStatus;
import ru.vsu.cs.baklanova.Cards.CardStatus;
import ru.vsu.cs.baklanova.Player.Player;

import java.util.ArrayList;

public class Game {
    private CardBlock block;
    private Table table;

    int circle;

    public Game(boolean haveRealPlayer, int playerNum) throws Exception {
        this.block = new CardBlock(52);
        this.table = new Table(playerNum, block, haveRealPlayer);
        this.circle = 0;
    }

    public Table getTable() {
        return table;
    }

    public CardBlock getBlock() {
        return block;
    }

    private void setCircle(int circle) {
        this.circle = circle;
    }

    public void gameRound() throws Exception {
        while (!gameOver()) {
            oneRound();
        }
    }

    public void oneRound() throws Exception {
        while (roundOver() == -1) {
            if (circle < 0 || circle > 4) {
                circle = 0;
            }
            if (circle == 0) {
                setTable(new Table(table.getPlayers().size(), block, table.haveRealPlayer));
            }
            if (circle == 1) {
                table.setTableCards(block, 3);
            } else if (circle == 2 || circle == 3) {
                table.getTableCards().add(CardBlock.takeCard(block));
            }
            betCircle();
            if (circle > 3) {
                System.out.println("Победитель = " + table.getPlayers().get(cardsOnTable(table)));
            }
        }

        if (roundOver() >= 0) { //Один победитель
            int money = table.getPlayers().get(roundOver()).getMoney();
            int bet = table.getBigBet();
            table.getPlayers().get(roundOver()).setMoney(money + bet);
            table.setBigBet(0);
        }

        //Ничья if (roundOver() <= -2)
    }

    private void betCircle() throws Exception {
        ArrayList<Player> players = table.getPlayers();
        int lastBet = 100;
        if (circle == 0) {
            playersSetStatus(players, null);
        } else {
            playersSetStatus(players, table);
        }
        for (Player p : players) {
            if (p.getMoney() <= 0 || !p.getInGame()) {
                continue;
            }
            if (p.getIsNPC()) {
                lastBet = npcChoice(p, lastBet);
            } /*else {
                //ЕСЛИ - повысить - повысить ставку на (задано в окне, <= баланс);
                //ЕСЛИ - поддержать ставку - ставка = ласт ставка
                //Для обоих - статус в игре
                //ИНАЧЕ сбросить карты
            } */
        }
        circle++;
    }
    //После вызова прибавлять круг_счетчик

    private int npcChoice(Player p, int lastBet) {
        if (p.getCardsStatus() == null) {
            return lastBet;
        }
        int money = p.getMoney();
        int bet = p.getBet();

        int choice = (int) (Math.random() * 100);
        int notGiveUp = p.getCardsStatus().getStatus().getCount() * 5 + p.getCardsStatus().getMax();
        if (choice < notGiveUp + 75 && money > lastBet) {
            if (choice < notGiveUp) {
                lastBet = (int) (Math.random() * money);
            }

            p.setBet(bet + lastBet);
            p.setMoney(p.getMoney() - lastBet);
            table.setBigBet(table.getBigBet() + lastBet);
            p.setInGame(true);
        }
        else {
            p.setInGame(false);
        }

        return lastBet;
        //ЕСЛИ (рандомное число * 100) < вероятность не сбросить карты(внск)
        //ЕСЛИ рандомное число < внск, ТО повысить ставку на ранд(100 : баланс, <= баланс);
        //Для обоих - статус в игре
        //ИНАЧЕ сбросить карты
    }

    private static void playersSetStatus(ArrayList<Player> players, Table table) throws Exception {
        for (Player p : players) {
            if (table != null) {
                p.setCardsStatus(CardSetStatus.setStatus(table.getTableCards(), p.getCards()));
            } else {
                p.setCardsStatus(CardSetStatus.setStatus(null, p.getCards()));
            }
        }
    }

    private static int cardsOnTable(Table table) throws Exception {
        ArrayList<Player> players = table.getPlayers();
        int winner = -1;
        int maxStatus = -1;
        int winnerMaxCard = -1;
        for (int i = 0; i < players.size(); i++) {
            CardStatus c = players.get(i).getCardsStatus();
            int statusC = c.getStatus().getCount();
            if (maxStatus < statusC) {
                maxStatus = statusC;
                winner = i;
                winnerMaxCard = c.getMax();
            } else if (maxStatus == statusC) {
                if (winnerMaxCard < c.getMax()) {
                    winner = i;
                    winnerMaxCard = c.getMax();
                }
            }
        }

        return winner;
    }
    //Победитель получает общую ставку со стола, на столе она становится 0, обновление статусов

    //НАУЧИТЬ ВОЗВРАЩАТЬ КАРТЫ ПРИ СБРОСЕ
    //Функция вероятности не сбросить карты
    //45% + Статус * 5%

    private boolean gameOver() {
        for (Player p : table.getPlayers()) {
            if (p.getMoney() > 0) {
                return false;
            }
        }
        return true;
    }

    private int roundOver() {
        int k = 0;
        int iWinner = -1;
        for (int i = 0; i < table.getPlayers().size(); i++) {
            Player p = table.getPlayers().get(i);
            if (p.getInGame()) {
                k++;
                iWinner = i;
            }
            if (k > 1) {
                return -1;
            }
        }

        if (k == 1) {
            return iWinner;
        }
        return -2;
    }

    private void setTable(Table table) {
        this.table = table;
    }
}
