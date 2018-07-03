package com.andyshon.slots.Global;

import android.content.Context;
import android.content.SharedPreferences;

import com.andyshon.slots.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by andyshon on 03.07.18.
 */

/*
* todo: Данные игрока, баланс, коэффициент, джэкпот.
* */

public class GlobalConstants {


    public static int _UserCoins = 950;
    public static int _BetAmount = 5;
    public static int _Rat = 1;
    public static int _Jackpot = 100000;
    public static boolean _IsFastMode;

    public static int drawable_1, drawable_2, drawable_3;
    public static String expectedCombination_1 = "", expectedCombination_2 = "", expectedCombination_3 = "";

    private static String[] combinations_arr = new String[3];

    public static List<String> combinations = Arrays.asList("combination_1", "combination_1", "combination_1", "combination_1",
            "combination_2", "combination_2", "combination_2", "combination_2",
            "combination_3", "combination_3", "combination_3", "combination_3",
            "combination_4", "combination_4", "combination_4", "combination_4",
            "combination_5", "combination_5", "combination_5", "combination_5",
            "combination_6", "combination_6", "combination_6", "combination_6",
            "combination_7", "combination_7", "combination_7", "combination_7"
    );


    /*
    * todo: В случае выигрыша возвращает кол-во монет, в случае проигрыша - 0
    */

    public static int getPrize () {

        combinations_arr[0] = expectedCombination_1;
        combinations_arr[1] = expectedCombination_2;
        combinations_arr[2] = expectedCombination_3;

        if (isPrizeCombination_1())
            return 10;
        else if (isPrizeCombination_2())
            return 15;
        else if (isPrizeCombination_3())
            return 25;
        else if (isPrizeCombination_4())
            return 35;
        else if (isPrizeCombination_5())
            return 50;
        else if (isPrizeCombination_6())
            return 75;
        else if (isPrizeCombination_7_one())
            return 25;
        else if (isPrizeCombination_7_two())
            return 50;
        else if (isPrizeCombination_7())
            return _Jackpot;
        return 0;
    }


    /*
    * todo: Проверка трёх слотов на одинаковые комбинации и на наличие комбинации для джэкпота
    * */

    private static boolean isPrizeCombination_1() {
        for (String comb : combinations_arr) {
            if (!comb.equals("combination_1"))
                return false;
        }
        return true;
    }
    private static boolean isPrizeCombination_2 () {
        for (String comb : combinations_arr) {
            if (!comb.equals("combination_2"))
                return false;
        }
        return true;
    }
    private static boolean isPrizeCombination_3 () {
        for (String comb : combinations_arr) {
            if (!comb.equals("combination_3"))
                return false;
        }
        return true;
    }
    private static boolean isPrizeCombination_4 () {
        for (String comb : combinations_arr) {
            if (!comb.equals("combination_4"))
                return false;
        }
        return true;
    }
    private static boolean isPrizeCombination_5 () {
        for (String comb : combinations_arr) {
            if (!comb.equals("combination_5"))
                return false;
        }
        return true;
    }
    private static boolean isPrizeCombination_6 () {
        for (String comb : combinations_arr) {
            if (!comb.equals("combination_6"))
                return false;
        }
        return true;
    }
    private static boolean isPrizeCombination_7 () {
        for (String comb : combinations_arr) {
            if (!comb.equals("combination_7"))
                return false;
        }
        return true;
    }
    private static boolean isPrizeCombination_7_one () {
        int counter=0;
        for (String comb : combinations_arr) {
            if (!comb.equals("combination_7")) {
                counter++;
            }
        }
        return counter == 2;
    }
    private static boolean isPrizeCombination_7_two () {
        int counter=0;
        for (String comb : combinations_arr) {
            if (!comb.equals("combination_7")) {
                counter++;
            }
        }
        return counter == 1;
    }


    /*
    * todo: Рандомная установка главной комбинации в текущем раунде и перетасовывание списка комбинаций
    * */

    public static void shuffleMainLine() {
        Collections.shuffle(combinations);

        Random random = new Random();
        int rNum = random.nextInt(7)+1;
        expectedCombination_1 = combinations.get(rNum);
        List<Integer> shuffledImages1 = getShuffledImages(combinations);
        drawable_1 = shuffledImages1.get(rNum);

        Collections.shuffle(combinations);
        rNum = random.nextInt(7)+1;
        expectedCombination_2 = combinations.get(rNum);
        List<Integer> shuffledImages2 = getShuffledImages(combinations);
        drawable_2 = shuffledImages2.get(rNum);

        Collections.shuffle(combinations);
        rNum = random.nextInt(7)+1;
        expectedCombination_3 = combinations.get(rNum);
        List<Integer> shuffledImages3 = getShuffledImages(combinations);
        drawable_3 = shuffledImages3.get(rNum);
    }


    /*
    * todo: Возвращает список изображений(drawables), соответствующий перетасованному списку с комбинациями
    * */

    public static List<Integer> getShuffledImages(List<String> combinations) {
        List<Integer> shuffledList = new ArrayList<>();
        for (int i=0; i<combinations.size(); i++) {
            if (combinations.get(i).equals("combination_1")) {
                shuffledList.add(R.drawable.combination_1);
            }
            else if (combinations.get(i).equals("combination_2")) {
                shuffledList.add(R.drawable.combination_2);
            }
            else if (combinations.get(i).equals("combination_3")) {
                shuffledList.add(R.drawable.combination_3);
            }
            else if (combinations.get(i).equals("combination_4")) {
                shuffledList.add(R.drawable.combination_4);
            }
            else if (combinations.get(i).equals("combination_5")) {
                shuffledList.add(R.drawable.combination_5);
            }
            else if (combinations.get(i).equals("combination_6")) {
                shuffledList.add(R.drawable.combination_6);
            }
            else if (combinations.get(i).equals("combination_7")) {
                shuffledList.add(R.drawable.combination_7);
            }
        }
        return shuffledList;
    }


    /*
    * todo: Сохраняем монеты игрока и значение джэкпота
    * */

    public static void saveUserPreferences (Context context) {
        SharedPreferences myPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPref.edit();
        editor.putInt("user_coins", GlobalConstants._UserCoins);
        editor.putInt("jackpot", GlobalConstants._Jackpot);
        editor.putBoolean("isFastMode", GlobalConstants._IsFastMode);
        editor.apply();
    }


    /*
    * todo: Получаем монеты игрока и значение джэкпота
    * */

    public static void loadUserPreferences (Context context) {
        SharedPreferences myPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        if(myPref.contains("user_coins")) {
            GlobalConstants._UserCoins = myPref.getInt("user_coins", 0);
        }
        if(myPref.contains("jackpot")) {
            GlobalConstants._Jackpot = myPref.getInt("jackpot", 0);
        }
        if (myPref.contains("isFastMode")) {
            GlobalConstants._IsFastMode = myPref.getBoolean("isFastMode", false);
        }
    }
}
