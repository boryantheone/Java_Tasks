package com.company;
import java.io.IOException;

//класс приемника события
public class Receiver implements IEvent{
	public void Handler(String message){
		System.out.println("Произошло событие - " + message);
		try{
			Main.writeToJournal("Произошло событие - " + message);
		}catch (IOException e){
			System.out.println(e);
		}
	}
}
