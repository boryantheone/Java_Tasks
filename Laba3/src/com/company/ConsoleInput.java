package com.company;

//класс источника события
public class ConsoleInput {
    private IEvent iEvent;

    public ConsoleInput(IEvent iEvent) {
        this.iEvent = iEvent;
    }

    public void generateEvent() {
        iEvent.Handler("Обращение к потоку ввода с консоли");
    }
}
