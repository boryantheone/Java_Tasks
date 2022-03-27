package com.company;

//класс источника события
public class HandleArray {
    private IEvent iEvent;

    public HandleArray(IEvent iEvent) {
        this.iEvent = iEvent;
    }

    public void generateEvent() {
        iEvent.Handler("Обращение к указанному массиву");
    }
}
