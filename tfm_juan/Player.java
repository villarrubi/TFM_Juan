package com.mycompany.tfm_juan;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un jugador en el juego
 */
public class Player {
    private String name;
    private Color color;
    private int id;
    private List<Territory> territories;
    
    public Player(int id, String name, Color color) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.territories = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Color getColor() {
        return color;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }
    
    public int getId() {
        return id;
    }
    
    public List<Territory> getTerritories() {
        return territories;
    }
    
    public void addTerritory(Territory territory) {
        if (!territories.contains(territory)) {
            territories.add(territory);
        }
    }
    
    public void removeTerritory(Territory territory) {
        territories.remove(territory);
    }
    
    public int getTerritoryCount() {
        return territories.size();
    }
    
    public int getTotalTroops() {
        int total = 0;
        for (Territory t : territories) {
            total += t.getTroops();
        }
        return total;
    }
    
    public void clearTerritories() {
        territories.clear();
    }
}