package com.company;
import java.io.*;

public class Main {
    private static BufferedWriter journal;

    public static void main(String[] args) throws IOException {

        int sum1 = 0;
        int sum2 = 0;

        Receiver receiver = new Receiver();
        ConsoleOutput consoleOutput = new ConsoleOutput(receiver);
        ConsoleInput consoleInput = new ConsoleInput(receiver);
        HandleArray handleArray = new HandleArray(receiver);

        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));


        System.out.println("Введите путь к файлу");
        consoleOutput.generateEvent();

        String inFilePath = consoleReader.readLine();
        consoleInput.generateEvent();

        File inFile = new File(inFilePath);
        if (inFile.exists() && inFile.length() != 0) {
            try {
                BufferedReader fileBufferedReader = new BufferedReader(new FileReader(inFile));
                String argumentsString = fileBufferedReader.readLine();
                String journalPath = fileBufferedReader.readLine();
                journal = new BufferedWriter(new FileWriter(journalPath));
                String[] arguments = argumentsString.split(" ");
                handleArray.generateEvent();
                try {
                    handleArray.generateEvent();
                    for (String s : arguments) {
                        if (Integer.parseInt(s) < 0 && Integer.parseInt(s) % 2 == 0) {
                            sum1 += Integer.parseInt(s);
                        }
                        if (Integer.parseInt(s) < 0 && Integer.parseInt(s) % 2 != 0) {
                            sum2 += Integer.parseInt(s);
                        }
                    }
                    System.out.println("Сумма нечетных + отрицательных = " + sum1);
                    writeToJournal("Сумма нечетных + отрицательных = " + sum1);
                    consoleOutput.generateEvent();
                    System.out.println("Сумма четных + отрицательных = " + sum2);
                    writeToJournal("Сумма четных + отрицательных = " + sum2);
                    consoleOutput.generateEvent();
                } catch (IllegalArgumentException e) {
                    System.out.println("Неверный формат данных");
                    consoleOutput.generateEvent();
                    writeToJournal("Неверный формат данных");
                } finally {
                    journal.close();
                }
            }
            catch(IOException e){throw new RuntimeException();}
        }
    }


    public static void writeToJournal(String message) throws IOException{
        if(journal != null) {
            journal.write(message + "\n");
        }
    }
}
