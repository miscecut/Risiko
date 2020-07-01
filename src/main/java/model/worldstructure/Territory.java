package model.worldstructure;

import model.Observable;
import model.Observer;
import model.Player;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class Territory implements Observable {
    private final Collection<Observer> territoryObservers = new ArrayList<>();
    private final String name;
    private Player owner;
    private int armies;

    Territory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getArmies() {
        return armies;
    }

    public Optional<Player> getOwner() {
        return Optional.ofNullable(owner);
    }

    public void addArmies(Player playerWhoAdds, int quantity) {
        if(playerWhoAdds.equals(owner))
            armies += quantity;
        else {
            owner = playerWhoAdds;
            armies = quantity;
        }
        notifyObservers();
    }

    public void removeArmies(int quantity) {
        armies = Math.max(0 , armies - quantity);
        if(armies == 0)
            owner = null;
        notifyObservers();
    }

    @Override
    public void notifyObservers() {
        territoryObservers.forEach(Observer::update);
    }

    @Override
    public void registerObserver(Observer observer) {
        territoryObservers.add(observer);
    }
}
