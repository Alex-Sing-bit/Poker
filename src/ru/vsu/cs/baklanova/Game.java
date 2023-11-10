package ru.vsu.cs.baklanova;

import ru.vsu.cs.baklanova.Cards.Card;
import ru.vsu.cs.baklanova.Cards.CardBlock;
import ru.vsu.cs.baklanova.Cards.CardsCombinationSetStatus;
import ru.vsu.cs.baklanova.Cards.CardsCombinationStatus;
import ru.vsu.cs.baklanova.Player.Player;

import java.util.ArrayList;

public class Game {
    private final CardBlock block;
    private Table table;
    private ArrayList<Player> players;

    private int lastBet;

    int circle;

    public Game(boolean haveRealPlayer, int playersNum) throws Exception {
        this.block = new CardBlock(52);
        this.table = new Table(block);
        int k = (haveRealPlayer ? 1 : 0);
        setPlayers(block, playersNum, playersNum - k);

        this.lastBet = 0;
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
        int k = 0;
        while (!gameOver()) {
            oneRound();
            k++;
            if (k > 50) {
                break;
            }
        }
    }

    public void oneRound() throws Exception {
        preparationToNewRound();

        int roundOver = roundOver();
        ArrayList<Integer> winners = new ArrayList<>();

        while (winners.size() == 0) {
            if (circle < 0 || circle > 4) {
                circle = 0;
                lastBet = 100;
            } else
            if (circle > 0) {
                lastBet = 0;
                if (circle == 1) {
                    table.setTableCards(block, 3);
                } else if (circle == 2 || circle == 3) {
                    table.getTableCards().add(block.takeCard());
                }
            }

            output();

            final int BET_CIRCLES_NUM = 3;
            boolean stopBet;
            for (int i = 0; i < BET_CIRCLES_NUM; i++) {
                System.out.println("Это " + (i + 1) + " круг ставок.");
                stopBet = betCircle();
                if (stopBet) {
                    break;
                }
                roundOver = roundOver();

                if (roundOver != -1) {
                    break;
                }
            }

            //roundOver = roundOver();

            //output();

            if (roundOver > -1) {
                winners.add(roundOver);
            }else if (roundOver == -2) {
                System.out.println("Все проиграли"); //Найти решения, если победителя нет
                break;
            } else {
                System.out.println("Это не конец");
            }

            if (circle > 3) {
                winners.addAll(cardsOnTable());
            }
            circle++;
        }

        winnersTakeWinnings(winners);

        System.out.println("oneRound, the end. Я ПРОВЕРКА Я ПРОВЕРКА Я ПРОВЕРКА Я ПРОВЕРКА Я ПРОВЕРКА Я ПРОВЕРКА ");
        output();
        System.out.println("oneRound, the end. Я ПРОВЕРКА Я ПРОВЕРКА Я ПРОВЕРКА Я ПРОВЕРКА Я ПРОВЕРКА Я ПРОВЕРКА ");
    }

    private void tableOutput() {
        System.out.println("СТОЛ");
        System.out.println("Ставка: " + table.getBigBet());
        for (Card c : table.getTableCards()) {
            System.out.println(c.getCardValue() + " " + c.getCardSuit());
        }
        System.out.println("--------------------------------");
        System.out.println();
    }

    private void playerOutput() {
        System.out.println("ИГРОКИ");
        for (Player p : players) {
            System.out.println(p.getName() + "(" + p.getInGame() + ")");
            System.out.println("Деньги: " + p.getMoney());
            System.out.println("Ставка: " + p.getBet());
            for (Card c : p.getCards()) {
                System.out.println(c.getCardValue() + " " + c.getCardSuit());
            }

            if (p.getCardsStatus() != null) {
                System.out.println(p.getCardsStatus().getStatus() + ". Max card: " + p.getCardsStatus().getMax());
            }
            System.out.println();
        }
        System.out.println("--------------------------------");
        System.out.println();
    }

    private void output() {
        System.out.println(circle);
        System.out.println();
        tableOutput();
        playerOutput();
    }
    private void winnersTakeWinnings(ArrayList<Integer> winnersIndexes) {
        int winnersSize = winnersIndexes.size();
        int winning = table.getBigBet();
        if (winnersSize > 1) {
            winning = winning / winnersSize;
        }

        if (winnersSize >= 1) {
            for (Integer i : winnersIndexes) {
                takeWinning(i, winning);
                System.out.println(players.get(i).getName() + " победил!");
            }
        } else {
            for (int i = 0; i < players.size(); ) {
                takeWinning(i, winning);
            }
        }
    }
    private void preparationToNewRound() throws Exception {
        setBlockToNewCircle();
        setPlayersToNewCircle();

        table.setBigBet(0);
        table.setTableCards(block, 0);

        circle = 0;
        lastBet = 100;
    }

    private void takeWinning(int i, int winning) {
        Player p = players.get(i);
        p.setMoney(p.getMoney() + winning);
    }

    private boolean betCircle() throws Exception {
        if (circle == 0) {
            playersSetCombinationStatus(players, null);
        } else {
            playersSetCombinationStatus(players, table);
        }
        int thisFirstBet = -1;
        int thisLastBet = -1;

        int k = 0;
        int roundOver = roundOver();
        for (int i = 0; i < players.size() && roundOver == -1; i++) {
            Player p = players.get(i);
            if (p.getMoney() <= 0 || !p.getInGame()) {
                continue;
            }
            if (p.getIsNPC()) {
                int nc = npcChoice(p, i);
                if (nc > -1 && k == 0) {
                    thisFirstBet = nc;
                    k = 1;
                } else if (k == 1 && nc != -1) {
                    thisLastBet = nc;
                }
            }
            roundOver = roundOver();
        }

        return thisFirstBet == thisLastBet && thisFirstBet != -1;
    }
    //После вызова прибавлять круг_счетчик

    private int npcChoice(Player p, int i) throws Exception {
        if (p.getCardsStatus() == null) {
            return -1;
        }
        int money = p.getMoney();
        int bet = p.getBet();

        int choice = (int) (Math.random() * 99) + 1;
        int notGiveUp = p.getCardsStatus().getStatus().getCount() * 5 + p.getCardsStatus().getMax() + 30;
        if (choice < notGiveUp * 2 || (i < 2 && circle == 0)) {
            if (money < lastBet) {
                if (choice >= notGiveUp) {
                    p.setInGame(false);
                    System.out.println(p.getName() + " сдался.\n");
                    return -1;
                }
                p.setBet(bet + money);
                p.setMoney(0);
                table.setBigBet(table.getBigBet() + money);
                System.out.println(p.getName() + " ставит всё: +" + money + "(Всего: " + p.getBet() + ")\n");
                return lastBet;
            }

            if (choice < notGiveUp + circle * 10) {
                int k = (int) (Math.random() * (money - lastBet) * (choice/100.0));;
                lastBet += k;
            }

            p.setBet(bet + lastBet);
            p.setMoney(p.getMoney() - lastBet);
            table.setBigBet(table.getBigBet() + lastBet);
            System.out.println(p.getName() + " ставит +"  + lastBet + " (Всего: " + p.getBet() + ")\n");

            return lastBet;
        }

        System.out.println(p.getName() + " сдался.\n");
        p.setInGame(false);

        return -1;
        //ЕСЛИ (рандомное число * 100) < вероятность не сбросить карты(внск)
        //ЕСЛИ рандомное число < внск, ТО повысить ставку на ранд(100 : баланс, <= баланс);
        //Для обоих - статус в игре
        //ИНАЧЕ сбросить карты
    }

    private static void playersSetCombinationStatus(ArrayList<Player> players, Table table) throws Exception {
        for (Player p : players) {
            if (table != null) {
                p.setCombinationStatus(CardsCombinationSetStatus.setStatus(table.getTableCards(), p.getCards()));
            } else {
                p.setCombinationStatus(CardsCombinationSetStatus.setStatus(null, p.getCards()));
            }
        }
    }

    private ArrayList<Integer> cardsOnTable() throws Exception {
        int winner = -1;
        int maxStatus = -1;
        int winnerMaxCard = -1;

        ArrayList<Integer> winners = new ArrayList<>();

        for (int i = 0; i < players.size(); i++) {
            CardsCombinationStatus c = players.get(i).getCardsStatus();
            int statusC = c.getStatus().getCount();
            if (maxStatus < statusC) {
                maxStatus = statusC;
                winner = i;
                winnerMaxCard = c.getMax();
            } else if (maxStatus == statusC) {
                if (winnerMaxCard < c.getMax()) {
                    winner = i;
                    winnerMaxCard = c.getMax();
                } else if (winnerMaxCard == c.getMax()) {
                    winners.add(i);
                }
            }
        }

        winners.add(winner);
        return winners;
    }
    //Победитель получает общую ставку со стола, на столе она становится 0, обновление статусов

    //НАУЧИТЬ ВОЗВРАЩАТЬ КАРТЫ ПРИ СБРОСЕ
    //Функция вероятности не сбросить карты
    //45% + Статус * 5%

    private boolean gameOver() {
        int k = 0;
        for (Player p : players) {
            if (p.getMoney() > 0) {
                k++;
            }
            if (k > 1) {
                return false;
            }
        }
        return true;
    }

    private int roundOver() {
        int k = 0;
        int iWinner = -1;
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
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

    public void setPlayersToNewCircle() throws Exception {
        for (Player p : players) {
            if (p.getMoney() > 0) {
                p.setInGame(true);
            }
            p.setCards(block);
            p.setBet(0);
            p.setCardsStatus(null);
        }
    }

    public void setBlockToNewCircle() throws Exception {
        for (Card c : block.getCardBlock()) {
            c.setCardStatus(true);
        }
    }

    public void setPlayers(CardBlock main, int playersNum, int npcNum) throws Exception {
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
}


//++ Оба сдаются в одном круге ставок --> Оба проиграли !! Исправить

// 2/3 игроков true, у второго из них закончились деньги --> первый доигрывает круг ставок один !!
// Исправить: Если у всех true нет денег - остановиться.
// Добавить проверку на количество людей с деньгами среди true игроков