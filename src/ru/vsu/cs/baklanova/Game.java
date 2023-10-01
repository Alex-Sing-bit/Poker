package ru.vsu.cs.baklanova;

import java.util.ArrayList;

public class Game {
    private CardBlock block;
    private Table table;

    public  Game() throws Exception {
        CardBlock block = new CardBlock(52, true, null);
        Table table = new Table(5, block);
    }

    public Table getTable() {
        return table;
    }

    public CardBlock getBlock() {
        return block;
    }

    public static void betCircle(ArrayList<Player> players) {
        //Ласт ставка = 0;
        for (Player p : players) {
            if (p.getIsNPC()) {
                //ЕСЛИ (рандомное число * 100) < вероятность не сбросить карты(внск)
                    //ЕСЛИ рандомное число < внск, ТО повысить ставку на ранд(100 : баланс, <= баланс);
                //Для обоих - статус в игре
                //ИНАЧЕ сбросить карты
            } else {
                //ЕСЛИ - повысить - повысить ставку на (задано в окне, <= баланс);
                //ЕСЛИ - поддержать ставку - ставка = ласт ставка
                //Для обоих - статус в игре
                //ИНАЧЕ сбросить карты
            }
        }
    }

    //Функция вероятности не сбросить карты
    //45% + Статус * 5%
}
