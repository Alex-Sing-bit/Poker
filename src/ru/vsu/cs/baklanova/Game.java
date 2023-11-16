package ru.vsu.cs.baklanova;

import ru.vsu.cs.baklanova.Cards.*;
import ru.vsu.cs.baklanova.Player.Player;

import java.util.ArrayList;
import java.util.Stack;

public class Game {
    private int riskRatio;
    public Stack<GameEvent> stack = new Stack<>();
    private final CardBlock block;
    private Table table;
    private ArrayList<Player> players;

    private int lastBet;

    private int round;

    private final int BET_CIRCLES_NUM = 3;

    public Game(boolean haveRealPlayer, int playersNum, int coef) throws Exception {
        this.block = new CardBlock(52);
        this.table = new Table(block);
        int k = (haveRealPlayer ? 1 : 0);
        setPlayers(block, playersNum, playersNum - k);

        this.lastBet = 0;
        this.round = 0;
        setRiskRatio(coef);
    }

    public void setRiskRatio(int riskRatio) {
        if (riskRatio < 0 || riskRatio > 100) {
            riskRatio = 50;
        }
        this.riskRatio = riskRatio;
    }

    public Table getTable() {
        return table;
    }

    public CardBlock getBlock() {
        return block;
    }

    private void setRound(int round) {
        this.round = round;
    }

    public void gameStart() throws Exception {
        int k = 0;
        boolean stop = false;
        while (gameOver() < 0 && !stop) {
            stop = oneRound();
            k++;
        }
        stack.push(new GameEvent(GameEventEnum.ALL_UPDATE, players, -1, table));

        System.out.println(k + " Победитель: " + players.get(gameOver()).getName());

        stack.push(new GameEvent(GameEventEnum.WINNER, players, gameOver(), table));
        stack.push(new GameEvent(GameEventEnum.END, players, -1, table));
    }

    public boolean oneRound() throws Exception {
        if (!preparationToNewRound()) {
            return true;
        }
        stack.push(new GameEvent(GameEventEnum.ALL_UPDATE, players, -1, table));

        boolean roundOver = false;
        ArrayList<Integer> winners = new ArrayList<>();

        while (winners.size() == 0) {
            if (round != 0) {
                if (round < 0 || round > 3) {
                    round = 0;
                    lastBet = 100;
                } else {
                    lastBet = 0;
                    if (round == 1) {
                        table.setTableCards(block, 3);
                        stack.push(new GameEvent(GameEventEnum.TABLE_CARDS_UPDATE, players, -1, table));
                    } else {
                        table.getTableCards().add(block.takeCard());
                        stack.push(new GameEvent(GameEventEnum.TABLE_CARDS_UPDATE, players, -1, table));
                    }
                }
            } else {
                table.setTableCards(block, 0);
                stack.push(new GameEvent(GameEventEnum.TABLE_CARDS_UPDATE, players, -1, table));
            }

            output();
            boolean stopBet = false;

            for (int i = 0; i < BET_CIRCLES_NUM; i++) {
                System.out.println("Это " + (i + 1) + " круг ставок.");
                stopBet = betCircle(i);
                if (stopBet) {// || roundOver) {
                    break;
                }
            }

            stack.push(new GameEvent(GameEventEnum.PERSON_BET, players, -1, table));
            output();

            roundOver = roundOver();
            //int wp = hereOnlyOneWorkablePlayers();
            if (roundOver || stopBet || round > 2) {
                if (roundOver) {
                    System.out.println("Это был RoundOver()");
                } else {
                    System.out.println("Это был последний круг.");
                }
                winners.addAll(cardsOnTable());
                if (winners.size() == 0) {
                    break;
                }
            }


            setPlayersBetsToZero();
            round++;
        }

        if (winners.size() > 0) {
            winnersTakeWinnings(winners);
        }
        return false;
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
        System.out.println(round);
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

        for (Integer i : winnersIndexes) {
            takeWinning(i, winning);

            stack.push(new GameEvent(GameEventEnum.ROUND_WINNER, players, i, table));
            System.out.println(players.get(i).getName() + " победил!");
        }
    }
    private boolean preparationToNewRound() throws Exception {
        setBlockToNewCircle();
        int k = setPlayersToNewCircle();

        table.setBigBet(0);
        table.setTableCards(block, 0);

        round = 0;
        lastBet = 100;

        return !(players.size() - k <= 1);
    }

    private void takeWinning(int i, int winning) {
        Player p = players.get(i);
        p.setMoney(p.getMoney() + winning);
    }

    private boolean betCircle(int circle) throws Exception {
        if (round == 0) {
            playersSetCombinationStatus(players, null);
        } else {
            playersSetCombinationStatus(players, table);
        }

        //Здесь сохранение статуса игрока
        int thisFirstBet = -1;
        int thisLastBet = -1;

        int k = 0;
        boolean roundOver = roundOver();
        for (int i = 0; i < players.size() && !roundOver; i++) {
            Player p = players.get(i);
            if (p.getMoney() <= 0 || !p.getInGame()) {
                continue;
            }
            if (p.getIsNPC()) {
                int nc = npcChoice(circle, i);
                if (nc > -1 && k == 0) {
                    thisFirstBet = nc;
                    k = 1;
                } else if (k == 1 && nc > -1) {
                    thisLastBet = nc;
                }
            }
            roundOver = roundOver();
        }

        return (thisFirstBet == thisLastBet && thisFirstBet != -1) || roundOver;
    }

    private int npcChoice(int circle, int playerIndex) throws Exception {
        Player p = players.get(playerIndex);
        if (p.getCardsStatus() == null) {
            return -1;
        }
        int money = p.getMoney();
        int bet = p.getBet();

        if (money == 0 && bet == 0) {
            p.setInGame(false);
            stack.push(new GameEvent(GameEventEnum.PERSON_BET, players, playerIndex, table));
            return -1;
        }

        int choice = (int) (Math.random() * 100);

        int notGiveUp = p.getCardsStatus().getStatus().getCount() * 5 + p.getCardsStatus().getMax() + riskRatio;
        if (choice < notGiveUp  || (playerIndex < 2 && round == 0)) {
            if (money + bet < lastBet + (4 - round) * 5) {
                if (choice < notGiveUp - riskRatio) {
                    p.setInGame(false);

                    stack.push(new GameEvent(GameEventEnum.PERSON_BET, players, playerIndex, table));
                    System.out.println(p.getName() + " сдался.\n");

                    return -1;
                }
                p.setBet(bet + money);
                p.setMoney(0);
                table.setBigBet(table.getBigBet() + money);

                stack.push(new GameEvent(GameEventEnum.PERSON_BET, players, playerIndex, table));
                System.out.println(p.getName() + " ставит всё: " + p.getBet() + "\n");

                return lastBet;
            }

            if (choice < notGiveUp + round * 5 && (circle < BET_CIRCLES_NUM - 1 || playerIndex == 0)) { //Если 3 круг ставок и не 0 игрок, то не входим
                int k = (int) (Math.random() * (money + bet - lastBet) * (choice/100.0)) / 2;
                lastBet += k;
            }

            p.setBet(lastBet);
            p.setMoney(p.getMoney() + bet - lastBet);
            table.setBigBet(table.getBigBet() + lastBet - bet);

            stack.push(new GameEvent(GameEventEnum.PERSON_BET, players, playerIndex, table));
            System.out.println(p.getName() + " ставит "  + lastBet + "\n");

            return lastBet;
        }

        stack.push(new GameEvent(GameEventEnum.PERSON_BET, players, playerIndex, table));
        System.out.println(p.getName() + " сдался.\n");

        p.setInGame(false);

        return -1;
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
        Player p = new Player(block, true, 0, 0);
        p.setCardsStatus(new CardsCombinationStatus(CardsCombinationStatusEnum.HIGH_CARD, 1));
        int winner = -1;

        stack.push(new GameEvent(GameEventEnum.CARDS_ON_TABLE, players, -1, table));

        ArrayList<Integer> winners = new ArrayList<>();

        for (int i = 0; i < players.size(); i++) {
            Player thisP = players.get(i);
            if (!thisP.getInGame()) {
                continue;
            }
            CardsCombinationStatus c = p.getCardsStatus();
            int statusC = c.getStatus().getCount();

            CardsCombinationStatus last = thisP.getCardsStatus();
            int statusLast = last.getStatus().getCount();

            if (statusLast < statusC) {
                continue;
            } else if (statusLast > statusC) {
                winners.clear();
                p = thisP;
                winner = i;
                continue;
            }

            if (last.getMax() < c.getMax()) {
                continue;
            } else if (last.getMax() > c.getMax()) {
                winners.clear();
                p = thisP;
                winner = i;
                continue;
            }

            int pSum = p.getCards().get(0).getCardValue() + p.getCards().get(1).getCardValue();

            int thisSum = thisP.getCards().get(0).getCardValue() + thisP.getCards().get(1).getCardValue();

            if (thisSum >= pSum) {
                if (pSum == thisSum && winner >= 0) {
                    winners.add(winner);
                } else if (pSum < thisSum) {
                    winners.clear();
                }
                p = thisP;
                winner = i;
            }
        }

        if (winner == -1) {
            return winners;
        }
        winners.add(winner);
        for (int i = 0; i < winners.size(); i++) {
            stack.push(new GameEvent(GameEventEnum.ROUND_WINNER, players, i, table));
        }
        return winners;
    }
    //Победитель получает общую ставку со стола, на столе она становится 0, обновление статусов

    //НАУЧИТЬ ВОЗВРАЩАТЬ КАРТЫ ПРИ СБРОСЕ
    //Функция вероятности не сбросить карты
    //45% + Статус * 5%

    private int gameOver() {
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
    }

    private void setTable(Table table) {
        this.table = table;
    }

    private int setPlayersToNewCircle() throws Exception {
        int k = 0;
        for (Player p : players) {
            if (p.getMoney() > 0) {
                p.setInGame(true);
            } else {
                k++;
                p.setInGame(false);
            }
            p.setCards(block);
            p.setBet(0);
            p.setCardsStatus(null);
        }

        return k;
    }

    private void setPlayersBetsToZero() throws Exception {
        for (Player p : players) {
            p.setBet(0);
        }
    }

    private int hereOnlyOneWorkablePlayers() {
        int n = 0;
        int lastI  = -1;
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            if (p.getInGame() && p.getMoney() > 0) {
                lastI = i;
                n++;
            }
            if (n > 1) {
                return -1;
            }
        }

        return lastI;
    }

    public void setBlockToNewCircle() throws Exception {
        for (Card c : block.getCardBlock()) {
            c.setCardStatus(true);
        }
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