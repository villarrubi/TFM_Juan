package com.mycompany.tfm_juan;

import java.util.*;

/**
 * Gestiona el estado del juego
 */
public class GameState {
    private Map<String, Territory> territories;
    private Player[] players;
    private int currentPlayerIndex;
    private int numPlayers;
    private Territory attackFrom;
    private Territory attackTo;
    
    public GameState(int numPlayers, String[] playerNames) {
        this.numPlayers = numPlayers;
        this.currentPlayerIndex = 0;
        this.territories = new HashMap<>();
        
        initializePlayers(playerNames);
        initializeTerritories();
    }
    
    private void initializePlayers(String[] playerNames) {
        players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            players[i] = new Player(i, playerNames[i], GameConstants.AVAILABLE_COLORS[i]);
        }
    }
    
    private void initializeTerritories() {
        Random random = new Random();
        List<Integer> indices = new ArrayList<>();
        
        for (int i = 0; i < GameConstants.TERRITORY_NAMES.length; i++) {
            indices.add(i);
        }
        Collections.shuffle(indices, random);
        
        int territoriosBase = GameConstants.TERRITORY_NAMES.length / numPlayers;
        int territoriosExtra = GameConstants.TERRITORY_NAMES.length % numPlayers;
        
        int currentIndex = 0;
        for (int playerIdx = 0; playerIdx < numPlayers; playerIdx++) {
            int territoriosParaEsteJugador = territoriosBase + (playerIdx < territoriosExtra ? 1 : 0);
            Player player = players[playerIdx];
            
            for (int t = 0; t < territoriosParaEsteJugador; t++) {
                int territoryIndex = indices.get(currentIndex++);
                int randomTroops = random.nextInt(3) + 3;
                
                Territory territory = new Territory(
                    GameConstants.TERRITORY_NAMES[territoryIndex],
                    GameConstants.TERRITORY_X_COORDS[territoryIndex],
                    GameConstants.TERRITORY_Y_COORDS[territoryIndex],
                    player.getColor(),
                    randomTroops,
                    player
                );
                
                territories.put(GameConstants.TERRITORY_NAMES[territoryIndex], territory);
                player.addTerritory(territory);
            }
        }
    }
    
    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % numPlayers;
    }
    
    public Player getCurrentPlayer() {
        return players[currentPlayerIndex];
    }
    
    public Map<String, Territory> getTerritories() {
        return territories;
    }
    
    public Player[] getPlayers() {
        return players;
    }
    
    public int getNumPlayers() {
        return numPlayers;
    }
    
    public Territory getAttackFrom() {
        return attackFrom;
    }
    
    public void setAttackFrom(Territory attackFrom) {
        this.attackFrom = attackFrom;
    }
    
    public Territory getAttackTo() {
        return attackTo;
    }
    
    public void setAttackTo(Territory attackTo) {
        this.attackTo = attackTo;
    }
    
    public void clearAttack() {
        this.attackFrom = null;
        this.attackTo = null;
    }
    
    public List<Territory> getPlayerTerritories(Player player) {
        List<Territory> result = new ArrayList<>();
        for (Territory t : territories.values()) {
            if (t.getOwner() == player && t.getTroops() > 1) {
                result.add(t);
            }
        }
        return result;
    }
    
    public List<Territory> getEnemyTerritories(Player player) {
        List<Territory> result = new ArrayList<>();
        for (Territory t : territories.values()) {
            if (t.getOwner() != player) {
                result.add(t);
            }
        }
        return result;
    }
}