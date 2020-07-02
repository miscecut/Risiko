package controller;

import model.Player;

import java.util.Collection;

public interface IOController {
    boolean askYesOrNo(Player player, String question);
    String askForATerritory(Player player, Collection<String> territories, String message);
}
