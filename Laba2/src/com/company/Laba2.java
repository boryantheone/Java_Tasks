package com.company;

import java.util.Arrays;
import java.util.Objects;

public class Laba2 {
    public static void main(String[] args) {
        int sum1 = 0;
        int sum2 = 0;
        try {
            if (args.length > ISizeMore.verConst) {throw new SizeMore(); }
            if (args.length < ISizeLess.verConst) {throw new SizeLess(); }
            for (String x : args) {
                if (Objects.equals(x, CheckSymbol.verConst)) {throw new CheckSymbol(); }
            }
        }
        catch (SizeMore e) {
            System.out.println(e.getMessage());
            args = Arrays.copyOfRange(args, 0, e.verConst);
        }
        catch (SizeLess e) {
            System.out.println(e.getMessage());
            return ;
        }
        catch (CheckSymbol e) {
            System.out.println(e.getMessage());
            return ;
        }
        for (String x : args) {
            if (Integer.parseInt(x) < 0 && Integer.parseInt(x) % 2 == 0) {
                sum1 += Integer.parseInt(x); }
            if (Integer.parseInt(x) < 0 && Integer.parseInt(x) % 2 != 0) {
                sum2 += Integer.parseInt(x); }
        }
        System.out.println("Сумма нечетных + отрицательных = " + sum1);
        System.out.println("Сумма четных + отрицательных = " + sum2);
    }
}

