package ru.vsu.cs.baklanova;

import java.util.ArrayList;

public class Game {
    private CardBlock block;
    private Table table;

    int circle;

    public  Game() throws Exception {
        CardBlock block = new CardBlock(52, true, null);
        Table table = new Table(5, block);
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

    public static void betCircle(ArrayList<Player> players, CardBlock table) throws Exception {
        int lastBet = 0;
        if (table.getCARD_VALUES_NUM() != 0) {
            playersSetStatus(players, table);
        }
        for (Player p : players) {
            if (p.getMoney() <= 0 || !p.getInGame()) {
                continue;
            }
            if (p.getIsNPC()) {
                if (p.getCardsStatus() != null) {
                    int choice = (int) (Math.random() * 100);
                    int notGiveUp = p.getCardsStatus().getCount() * 5;
                    if (choice < notGiveUp + 45) {
                        if (choice < notGiveUp) {
                            lastBet = (int) (Math.random() * p.getMoney());
                            p.setBet(lastBet);
                        } else {
                            lastBet = p.getBet() + lastBet;
                            p.setBet(lastBet);
                        }

                        p.setInGame(true);
                    }
                    else {
                        p.setInGame(false);

                        p.setMoney(p.getMoney() - p.getBet());
                        p.setBet(100);
                    }
                } else {
                    lastBet = p.getBet() + lastBet;
                    p.setBet(lastBet);
                    p.setInGame(true);
                }
                //ЕСЛИ (рандомное число * 100) < вероятность не сбросить карты(внск)
                    //ЕСЛИ рандомное число < внск, ТО повысить ставку на ранд(100 : баланс, <= баланс);
                //Для обоих - статус в игре
                //ИНАЧЕ сбросить карты
            } /*else {
                //ЕСЛИ - повысить - повысить ставку на (задано в окне, <= баланс);
                //ЕСЛИ - поддержать ставку - ставка = ласт ставка
                //Для обоих - статус в игре
                //ИНАЧЕ сбросить карты
            }*/
        }
    }
    //После вызова прибавлять круг_счетчик

    public static void playersSetStatus(ArrayList<Player> players, CardBlock table) throws Exception {
        for (Player p : players) {
            p.setCardsStatus(CardSetStatus.setStatus(p.getCardBlock(), table));
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
}
