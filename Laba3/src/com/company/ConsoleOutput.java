package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ConsoleOutput implements IConsoleOutput {
    String journalPath;

    public ConsoleOutput(String journalPath) {
        this.journalPath = journalPath;
    }

    @Override
    public void Handler() {
        try {
            FileWriter writer = new FileWriter(journalPath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write("Обращение к потоку вывода на консоль!\n");
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
