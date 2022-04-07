package com.company;

public class CheckSymbol extends Exception implements ISymbol {
    public String getMessage(){
        return "В последовательности чисел встретился указанный символ \"" + verConst + "\"\nЗавершение работы...";
    }
}