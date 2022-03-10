package com.company;

//класс источника события
public class ConsoleOutput {
    private IEvent iEvent;

    public ConsoleOutput(IEvent iEvent) {
        this.iEvent = iEvent;
    }

    public void generateEvent() {
        iEvent.Handler("Обращение к потоку вывода на консоль");
    }
}
