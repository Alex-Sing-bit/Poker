package ru.vsu.cs.baklanova.GameLogic.Bet.Combinations;

import ru.vsu.cs.baklanova.GameLogic.Bet.Combinations.Types.*;
import ru.vsu.cs.baklanova.GameLogic.Cards.Card;
import ru.vsu.cs.baklanova.GameLogic.Cards.CardSuitEnum;
import ru.vsu.cs.baklanova.GameLogic.Cards.CardValueEnum;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class CombinationUtils {

    private static final int CARDS_IN_COMB_NUM = 5;



    public static CardsCombination check(List<Card> cards) {
        ICombination[] combinations = new ICombination[] {
                new FlushRoyalCombination(),
                new StraightFlushCombination(),
                new FourOfAKindCombination(),
                new FullHouseCombination(),
                new FlushCombination(),
                new StraightCombination(),
                new ThreeOfAKindCombination(),
                new TwoPairCombination(),
                new OnePairCombination(),
                new HighCardCombination()
        };
        CardsCombination result = null;

        for (ICombination combination : combinations) {
            result = combination.check(cards);
            if (result != null) {
                break;
            }
        }

        return result;
    }

    public static Map<CardSuitEnum, List<Card>> sortForCheckSuits(List<Card> cards) {
       Map<CardSuitEnum, List<Card>> suits = new TreeMap<>();

        for (CardSuitEnum c : CardSuitEnum.values()) {
            suits.put(c, new ArrayList<>());
        }

        for (Card c : cards) {
            suits.get(c.getCardSuit()).add(c);
        }

        return suits;
    }

    public static Map<CardValueEnum, List<Card>> sortForCheckValues(List<Card> cards) {
        Map<CardValueEnum, List<Card>> suits = new TreeMap<>();

        for (CardValueEnum c : CardValueEnum.values()) {
            suits.put(c, new ArrayList<>());
        }

        for (Card c : cards) {
            suits.get(c.getCardValue()).add(c);
        }

        return suits;
    }

    public static List<Card> combinationWithCardsInSameValueTwice(List<Card> list, int first, int second) {
        return combinationWithCardsInSameValueTwice(list, 1, first, second);
    }
    public static List<Card> combinationWithCardsInSameValueTwice( List<Card> list, int firstCount, int first, int second) {
        Map<CardValueEnum, List<Card>> map = CombinationUtils.sortForCheckValues(list);
        List<Card> arr = new ArrayList<>();

        List<CardValueEnum> cv = Arrays.asList(CardValueEnum.values());
        AtomicInteger k = new AtomicInteger();
        int cvsize = cv.size();

        IntStream.range(1, cvsize + 1).map(i -> (cvsize - i)).
                filter(i -> arr.size() < CARDS_IN_COMB_NUM).
                filter(i -> k.get() < firstCount).
                forEach(i -> {
                    CardValueEnum c = cv.get(i);
                    int size = map.get(c).size();
                    if (size >= first) {
                        List<Card> arr1 = map.get(c);
                        arr.addAll(0, arr1.subList(size - first, size));
                        k.getAndIncrement();
                    }
                });


        if (k.get() < firstCount) {
            return null;
        } else if (arr.size() >= CARDS_IN_COMB_NUM) {
            List<Card> rArr = arr.subList(0, CARDS_IN_COMB_NUM);
            return rArr;
        }

        k.set(0);
        IntStream.range(1, cvsize + 1).map(i -> (cvsize - i)).
                filter(i -> arr.size() < CARDS_IN_COMB_NUM).
                forEach(i -> {
                    CardValueEnum c = cv.get(i);
                    int size = map.get(c).size();
                    if (size >= second) {
                        List<Card> arr1 = map.get(c);
                        if (size == first && k.get() < firstCount) {
                            k.getAndIncrement();
                        } else if (size < first || k.get() >= firstCount) {
                            arr.addAll(0, arr1);
                        } else {
                            arr.addAll(0, arr1.subList(0, size - first));
                            k.getAndIncrement();
                        }
                    }
                });
        /*for (int i = cvsize - 1; i >= 0; i--) {
            CardValueEnum c = cv.get(i);
            int size = map.get(c).size();
            if (size >= second) {
                List<Card> arr1 = map.get(c);
                if (size == first) {
                    if (k.get() < firstCount) {
                        k.getAndIncrement();
                        continue;
                    }
                }
                if (size < first || k.get() >= firstCount) {
                    arr.addAll(arr1);
                    continue;
                }
                arr.addAll(arr1.subList(0, first - 1));
                k.getAndIncrement();

            }
            if (arr.size() >= CARDS_IN_COMB_NUM) {
                arr.subList(0, CARDS_IN_COMB_NUM);
                arr.sort(Card::compareTo);
                return arr;
            }
        }*/

        if (arr.size() >= CARDS_IN_COMB_NUM) {
            //rArr.sort(Card::compareTo);
            return arr.subList(0, CARDS_IN_COMB_NUM);
        }

        int comboSize = first * firstCount + (second == 1 ? 0 : second);
        comboSize = Math.max(comboSize, arr.size());
        if (arr.size() >= comboSize)  {
            //rArr.sort(Card::compareTo);
            return arr.subList(0, comboSize);
        }

        return null;

        /*int k = 0;

        int kStop = (CARDS_IN_COMB_NUM - first * firstCount / second);
        for (int i = cv.size() - 1; i >= 0; i--) {
            CardValueEnum c = cv.get(i);
            int size = map.get(c).size();
            if (CARDS_IN_COMB_NUM - arr.size() >= first && size >= first) {
                List<Card> arr1 = map.get(c);
                arr.addAll(arr1.subList(size - first, size));
            }
            else if (k < kStop && size >= second) {
                List<Card> arr1 = map.get(c);
                arr.addAll(arr1.subList(size - second, size));
                k++;
            }

            if (arr.size() >= CARDS_IN_COMB_NUM) {
                return arr;
            }
        }*/
    }

    public static List<Card> combinationFiveValuesInRow(Map<CardSuitEnum, List<Card>> map, CardValueEnum maxValue) {

        for (CardSuitEnum c : CardSuitEnum.values()) {
            if (map.get(c).size() >= CARDS_IN_COMB_NUM) {
                List<Card> arr = CombinationUtils.fiveMaxCardsValuesInRow(map.get(c));
                if (arr == null) {
                    continue;
                }
                if (arr.get(arr.size() - 1).getCardValue() == maxValue || maxValue == null) {
                    return arr;
                }
            }
        }

        return null;
    }

    public static List<Card> fiveMaxCardsValuesInRow(List<Card> list) {
        if (list == null) {
            throw new NullPointerException("List is null");
        }
        int size = list.size();
        if (size < CARDS_IN_COMB_NUM) {
            return null;
        }

        list.sort(Card::compareTo);
        List<Card> retCards = new ArrayList<>();
        retCards.add(list.get(size - 1));

       IntStream.range(2, size + 1).map(i -> (size - i)).
               filter(i -> retCards.size() != CARDS_IN_COMB_NUM).
                forEach(i -> {
                    int lastValue = list.get(i + 1).getCardValueNum();
                    Card thisCard = list.get(i);
                    int thisValue = thisCard.getCardValueNum();
                    if (lastValue - 1 == thisValue) {
                        retCards.add(thisCard);

                    } else if (lastValue != thisValue) {
                        retCards.clear();
                        retCards.add(thisCard);
                    }
                });

        if (retCards.size() == CARDS_IN_COMB_NUM) {
            retCards.sort(Card::compareTo);
            return retCards;
        }

        return null;
        /*for (int i = size - 2; i >= 0; i--) {
            if (retCards.size() == CARDS_IN_COMB_NUM) {
                break;
            }

            int lastValue = list.get(i + 1).getCardValueNum();
                    Card thisCard = list.get(i);
                    int thisValue = thisCard.getCardValueNum();
                    if (lastValue - 1 == thisValue) {
                        retCards.add(thisCard);

                    } else if (lastValue != thisValue) {
                        retCards.clear();
                        retCards.add(thisCard);
                    }

        }

        if (retCards.size() == CARDS_IN_COMB_NUM) {
            retCards.sort(Card::compareTo);
            return retCards;
        }

        return null;*/
    }
}
