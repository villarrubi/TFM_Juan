package com.mycompany.tfm_juan;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Maneja la entrada del usuario (clicks y hover) - OPTIMIZADO
 */
public class InputHandler {
    private TFM_Juan panel;
    private GameState gameState;
    private DialogManager dialogManager;
    private Territory lastHoveredTerritory = null;
    
    public InputHandler(TFM_Juan panel, GameState gameState, DialogManager dialogManager) {
        this.panel = panel;
        this.gameState = gameState;
        this.dialogManager = dialogManager;
    }
    
    public void handleClick(Point p) {
        Point adjustedPoint = new Point(p.x, p.y - 90);
        
        double scaleX = panel.getWidth() / GameConstants.BASE_WIDTH;
        double scaleY = (panel.getHeight() - 90) / GameConstants.BASE_HEIGHT;
        
        for (Territory t : gameState.getTerritories().values()) {
            if (t.containsScaled(adjustedPoint, scaleX, scaleY)) {
                dialogManager.showTerritoryEditor(t);
                return;
            }
        }
    }
    
    /**
     * Maneja el hover de manera optimizada - solo repinta si hay cambios
     * @return true si necesita repintado, false si no
     */
    public boolean handleHover(Point p) {
        Point adjustedPoint = new Point(p.x, p.y - 90);
        
        double scaleX = panel.getWidth() / GameConstants.BASE_WIDTH;
        double scaleY = (panel.getHeight() - 90) / GameConstants.BASE_HEIGHT;
        
        Territory currentlyHovered = null;
        
        // Encontrar el territorio bajo el cursor
        for (Territory t : gameState.getTerritories().values()) {
            if (t.containsScaled(adjustedPoint, scaleX, scaleY)) {
                currentlyHovered = t;
                break;
            }
        }
        
        // Solo actualizar si el hover cambió
        if (currentlyHovered != lastHoveredTerritory) {
            // Quitar hover del territorio anterior
            if (lastHoveredTerritory != null) {
                lastHoveredTerritory.setHovered(false);
            }
            
            // Aplicar hover al nuevo territorio
            if (currentlyHovered != null) {
                currentlyHovered.setHovered(true);
            }
            
            // Actualizar cursor
            panel.setCursor(currentlyHovered != null 
                ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
                : Cursor.getDefaultCursor());
            
            lastHoveredTerritory = currentlyHovered;
            return true; // Necesita repintado
        }
        
        return false; // No necesita repintado
    }
    
    /**
     * Limpia el estado de hover (útil cuando el mouse sale del panel)
     */
    public void clearHover() {
        if (lastHoveredTerritory != null) {
            lastHoveredTerritory.setHovered(false);
            lastHoveredTerritory = null;
            panel.setCursor(Cursor.getDefaultCursor());
            panel.repaint();
        }
    }
}