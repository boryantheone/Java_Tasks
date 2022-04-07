package com.company;

import java.util.ArrayList;

public class Laba1 {
    public static void main(String[] args) {
        int sum1 = 0;
        int sum2 = 0;
        ArrayList<Integer> al1 = new ArrayList<>();
        ArrayList<Integer> al2 = new ArrayList<>();
        try {
            for (String x : args) {
                if (Integer.parseInt(x) < 0 && Integer.parseInt(x) % 2 == 0) {
                    al1.add(Integer.parseInt(x));}
                if (Integer.parseInt(x) < 0 && Integer.parseInt(x) % 2 != 0) {
                    al2.add(Integer.parseInt(x)); }
            }
        }
        catch(Exception e) {
            System.out.println("ERROR");
            return ;
        }
        for (int i = 0; i < al1.size(); i++) {
            sum1 += al1.get(i);
        }
        for (int i = 0; i < al2.size(); i++) {
            sum2 += al2.get(i);
        }
        System.out.println("Сумма нечетных + отрицательных = " + sum1);
        System.out.println("Сумма четных + отрицательных = " + sum2);
    }
}
