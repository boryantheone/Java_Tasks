package com.company;
import java.io.*;
import java.util.*;


public class Main {

    public static void main(String[] args) {
        String s1, journal;
        int i = 0;

        Scanner in = new Scanner(System.in);
        while (true){
            System.out.print("Введите путь к журналу -> ");
            journal = in.nextLine();
            File f = new File(journal);
            if (f.exists())
                break;
        }
	    Scanner sc = new Scanner(System.in);
        System.out.println("Введите число: 0 - ввод с консоли, 1 - чтение из файла");
        if (sc.hasNextInt()) {
            i = sc.nextInt();
        } else {
            System.out.println("Смотрите выше ^^^");
        }
        Scanner sc2 = new Scanner(System.in);
        if (i == 0) {
            System.out.println("Введите массив");
        }
        else {
            System.out.println("Введите путь до файла");
        }
        s1 = sc2.nextLine();
        if (i == 1) {
            File in_filename = new File(s1);
            StringBuilder sb = new StringBuilder();
            if (in_filename.exists()) {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(in_filename.getAbsoluteFile()));
                    try {
                        String s;
                        while ((s = br.readLine()) != null) {
                            sb.append(s);
                            sb.append("\n");
                        }
                    } finally { br.close(); }
                } catch (IOException e) { throw new RuntimeException(); }
            }
            System.out.print(sb);
            in_filename.delete();
        }
    }
}
