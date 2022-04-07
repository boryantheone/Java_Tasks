package com.company;

public class SizeMore extends Exception implements ISizeMore {
    public String getMessage(){
            return "Размер последовательности должен быть равен \"" + verConst + "\", но фактический размер больше. Урезаем до нужного размера.";
    }
}
