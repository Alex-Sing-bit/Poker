package ru.vsu.cs.baklanova;

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

    public void setCircle(int circle) {
        this.circle = circle;
    }

    public void betCircle() throws Exception {
        ArrayList<Player> players = table.getPlayers();
        int lastBet = 100;
        if (table.getTableCards().size() != 0) {
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
        int notGiveUp = p.getCardsStatus().getCount() * 5;
        if (choice < notGiveUp + 45 || money > lastBet) {
            if (choice < notGiveUp) {
                lastBet = (int) (Math.random() * money);
            } /*else {
                lastBet = bet + lastBet;
            }*/

            p.setBet(lastBet);
            table.setBigBet(table.getBigBet() + lastBet);
            p.setInGame(true);
        }
        else {
            p.setMoney(money - bet);
            p.setBet(100);
            p.setInGame(false);
        }

        return lastBet;
        //ЕСЛИ (рандомное число * 100) < вероятность не сбросить карты(внск)
        //ЕСЛИ рандомное число < внск, ТО повысить ставку на ранд(100 : баланс, <= баланс);
        //Для обоих - статус в игре
        //ИНАЧЕ сбросить карты
    }

    public static void playersSetStatus(ArrayList<Player> players, Table table) throws Exception {
        for (Player p : players) {
            p.setCardsStatus(CardSetStatus.setStatus(p.getCards(), table.getTableCards()));
        }
    }

    public static Player cardsOnTable(ArrayList<Player> players, CardBlock table) throws Exception {
        Player winner = null;
        int maxStatus = -1;
        for (Player p : players) {
            int c = p.getCardsStatus().getCount();
            if (maxStatus < c) {
                maxStatus = c;
                winner = p;
            } else if (maxStatus == c) {
                //Сравнить макс карту
                ;
            }
        }

        return winner;
    }
    //Победитель получает общую ставку со стола, на столе она становится 0, обновление статусов

    //НАУЧИТЬ ВОЗВРАЩАТЬ КАРТЫ ПРИ СБРОСЕ
    //Функция вероятности не сбросить карты
    //45% + Статус * 5%

    public boolean gameOver() {
        for (Player p : table.getPlayers()) {
            if (p.getMoney() > 0) {
                return false;
            }
        }
        return true;
    }
}
