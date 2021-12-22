package model;

import Interfaces.Observable;
import Interfaces.Observer;
import java.util.ArrayList;

public class Model implements Observable {
    private final ArrayList<Observer> observerArrayList = new ArrayList<>();
    private final ArrayList<Game> saves = new ArrayList<>();

    public void addSave(Game save){
        saves.add(save);
    }

    public void removeSave(Game save){
        saves.remove(save);
    }


    @Override
    public void addObserver(Observer observer) {
        observerArrayList.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observerArrayList.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for(Observer o: observerArrayList){
            o.update();
        }
    }
}
