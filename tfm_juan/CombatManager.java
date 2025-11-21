package com.mycompany.tfm_juan;

public class CombatManager {
    
    public static void executeAttackVictory(Territory attackFrom, Territory attackTo, TFM_Juan gamePanel) {
        Player previousOwner = attackTo.getOwner();
        Player attacker = attackFrom.getOwner();
        
        // Remover territorio del defensor
        if (previousOwner != null) {
            previousOwner.removeTerritory(attackTo);
        }
        
        // Asignar territorio al atacante
        attackTo.setOwner(attacker);
        attackTo.setColor(attacker.getColor());
        attacker.addTerritory(attackTo);
        
        // Mover todas las tropas excepto 1 que se queda en retaguardia
        int troopsToMove = attackFrom.getTroops() - 1;
        attackTo.setTroops(troopsToMove);
        attackFrom.setTroops(1);
        
        gamePanel.repaint();
    }
    
    public static void executeDefenseVictory(Territory attackFrom, TFM_Juan gamePanel) {
        // La defensa gana - el atacante pierde todas sus tropas excepto 1
        attackFrom.setTroops(1);
        gamePanel.repaint();
    }
}