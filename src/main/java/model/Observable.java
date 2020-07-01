package model;

public interface Observable {
    void notifyObservers();
    void registerObserver(Observer observer);
}
