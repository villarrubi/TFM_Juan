package com.mycompany.tfm_juan;

import java.awt.*;

/**
 * Renderiza la información de los jugadores en las esquinas
 */
public class PlayerInfoRenderer {
    
    public void render(Graphics2D g2d, GameState gameState, int width, int height, int currentPlayerIndex) {
        int padding = 15;
        int boxWidth = 180;
        int boxHeight = 85;
        int topOffset = 95;
        
        Point[] positions = {
            new Point(padding, topOffset),
            new Point(width - boxWidth - padding, topOffset),
            new Point(padding, height - boxHeight - padding),
            new Point(width - boxWidth - padding, height - boxHeight - padding)
        };
        
        Player[] players = gameState.getPlayers();
        for (int i = 0; i < gameState.getNumPlayers(); i++) {
            drawPlayerBox(g2d, positions[i].x, positions[i].y, boxWidth, boxHeight, players[i], i == currentPlayerIndex);
        }
    }
    
    private void drawPlayerBox(Graphics2D g2d, int x, int y, int width, int height, Player player, boolean isCurrentPlayer) {
        drawPlayerBoxShadow(g2d, x, y, width, height);
        drawPlayerBoxBackground(g2d, x, y, width, height, player.getColor(), isCurrentPlayer);
        drawPlayerBoxContent(g2d, x, y, player, isCurrentPlayer);
    }
    
    private void drawPlayerBoxShadow(Graphics2D g2d, int x, int y, int width, int height) {
        g2d.setColor(new Color(0, 0, 0, 100));
        g2d.fillRoundRect(x + 3, y + 3, width, height, 15, 15);
    }
    
    private void drawPlayerBoxBackground(Graphics2D g2d, int x, int y, int width, int height, 
                                         Color playerColor, boolean isCurrentPlayer) {
        g2d.setColor(new Color(101, 67, 33, 240));
        g2d.fillRoundRect(x, y, width, height, 12, 12);
        
        g2d.setColor(playerColor);
        g2d.setStroke(new BasicStroke(isCurrentPlayer ? 4 : 3));
        g2d.drawRoundRect(x, y, width, height, 12, 12);
        
        g2d.setColor(new Color(218, 165, 32));
        g2d.setStroke(new BasicStroke(isCurrentPlayer ? 2 : 1.5f));
        g2d.drawRoundRect(x + 2, y + 2, width - 4, height - 4, 10, 10);
        
        if (isCurrentPlayer) {
            g2d.setColor(new Color(255, 215, 0, 150));
            g2d.setStroke(new BasicStroke(3));
            g2d.drawRoundRect(x - 4, y - 4, width + 8, height + 8, 15, 15);
        }
    }
    
    private void drawPlayerBoxContent(Graphics2D g2d, int x, int y, Player player, boolean isCurrentPlayer) {
        g2d.setFont(new Font("Trajan Pro", Font.BOLD, 15));
        g2d.setColor(new Color(255, 248, 220));
        String displayName = player.getName().toUpperCase() + (isCurrentPlayer ? " ⚔" : "");
        g2d.drawString(displayName, x + 45, y + 25);
        
        g2d.setColor(player.getColor());
        g2d.fillRoundRect(x + 10, y + 10, 25, 25, 5, 5);
        g2d.setColor(new Color(218, 165, 32));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(x + 10, y + 10, 25, 25, 5, 5);
        
        g2d.setFont(new Font("Serif", Font.PLAIN, 12));
        g2d.setColor(new Color(255, 248, 220, 180));
        g2d.drawString("Territorios:", x + 10, y + 50);
        g2d.setFont(new Font("Serif", Font.BOLD, 14));
        g2d.setColor(new Color(255, 248, 220));
        g2d.drawString(String.valueOf(player.getTerritoryCount()), x + 120, y + 50);
        
        g2d.setFont(new Font("Serif", Font.PLAIN, 12));
        g2d.setColor(new Color(255, 248, 220, 180));
        g2d.drawString("Tropas:", x + 10, y + 70);
        g2d.setFont(new Font("Serif", Font.BOLD, 14));
        g2d.setColor(new Color(255, 248, 220));
        g2d.drawString(String.valueOf(player.getTotalTroops()), x + 120, y + 70);
    }
}