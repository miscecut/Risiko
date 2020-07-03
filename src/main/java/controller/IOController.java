package controller;

import model.gamemechanics.Player;

import java.util.Collection;
import java.util.Set;

public interface IOController {
    boolean askYesOrNo(Player player, String question);
    String askForATerritory(Player player, Collection<String> territories, String message);
    String askForAnAction(Player player, Collection<String> actions);
    Set<String> askForThreeCards(Player player, Collection<String> cards);
}
