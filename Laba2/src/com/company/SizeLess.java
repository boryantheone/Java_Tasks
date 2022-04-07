package com.company;

public class SizeLess extends Exception implements ISizeLess {
    public String getMessage(){
        return "Размер последовательности должен быть равен \"" + verConst + ". \"Завершение работы...\n";
    }
}
