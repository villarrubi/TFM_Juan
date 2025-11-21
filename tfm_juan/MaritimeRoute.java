package com.mycompany.tfm_juan;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa una ruta marítima entre dos territorios
 */
public class MaritimeRoute {
    private String fromTerritory;
    private String toTerritory;
    private List<Point> waypoints; // Puntos de control para la ruta
    
    public MaritimeRoute(String from, String to, Point... points) {
        this.fromTerritory = from;
        this.toTerritory = to;
        this.waypoints = new ArrayList<>();
        for (Point p : points) {
            waypoints.add(p);
        }
    }
    
    /**
     * Dibuja la ruta marítima con escala
     */
    public void drawScaled(Graphics2D g2d, double scaleX, double scaleY) {
        if (waypoints.size() < 2) return;
        
        // Configurar estilo de línea
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        float lineWidth = 2.5f * (float)Math.min(scaleX, scaleY);
        
        // Sombra de la ruta
        g2d.setColor(new Color(0, 0, 0, 60));
        g2d.setStroke(new BasicStroke(
            lineWidth + 2f,
            BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_ROUND
        ));
        drawRoutePath(g2d, scaleX, scaleY, 2, 2);
        
        // Línea base (color océano oscuro)
        g2d.setColor(new Color(25, 55, 85, 180));
        g2d.setStroke(new BasicStroke(
            lineWidth,
            BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_ROUND
        ));
        drawRoutePath(g2d, scaleX, scaleY, 0, 0);
        
        // Línea decorativa (punteada blanca)
        float[] dashPattern = {10f * (float)Math.min(scaleX, scaleY), 
                              5f * (float)Math.min(scaleX, scaleY)};
        g2d.setColor(new Color(174, 214, 241, 150));
        g2d.setStroke(new BasicStroke(
            lineWidth * 0.6f,
            BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_ROUND,
            10f,
            dashPattern,
            0f
        ));
        drawRoutePath(g2d, scaleX, scaleY, 0, 0);
        
        // Puntos de inicio y fin
        drawEndpoints(g2d, scaleX, scaleY);
    }
    
    /**
     * Dibuja el camino de la ruta
     */
    private void drawRoutePath(Graphics2D g2d, double scaleX, double scaleY, int offsetX, int offsetY) {
        if (waypoints.size() == 2) {
            // Ruta simple (línea recta)
            Point p1 = waypoints.get(0);
            Point p2 = waypoints.get(1);
            g2d.drawLine(
                (int)(p1.x * scaleX) + offsetX,
                (int)(p1.y * scaleY) + offsetY,
                (int)(p2.x * scaleX) + offsetX,
                (int)(p2.y * scaleY) + offsetY
            );
        } else {
            // Ruta con curvas (usando Path2D para suavidad)
            Path2D path = new Path2D.Double();
            Point first = waypoints.get(0);
            path.moveTo((int)(first.x * scaleX) + offsetX, (int)(first.y * scaleY) + offsetY);
            
            // Dibujar líneas suaves entre waypoints
            for (int i = 1; i < waypoints.size(); i++) {
                Point current = waypoints.get(i);
                
                if (i < waypoints.size() - 1) {
                    // Curva suave entre puntos
                    Point next = waypoints.get(i + 1);
                    int ctrlX = (int)((current.x + next.x) / 2 * scaleX) + offsetX;
                    int ctrlY = (int)((current.y + next.y) / 2 * scaleY) + offsetY;
                    
                    path.quadTo(
                        (int)(current.x * scaleX) + offsetX,
                        (int)(current.y * scaleY) + offsetY,
                        ctrlX,
                        ctrlY
                    );
                } else {
                    // Último punto
                    path.lineTo(
                        (int)(current.x * scaleX) + offsetX,
                        (int)(current.y * scaleY) + offsetY
                    );
                }
            }
            
            g2d.draw(path);
        }
    }
    
    /**
     * Dibuja puntos decorativos en inicio y fin de la ruta
     */
    private void drawEndpoints(Graphics2D g2d, double scaleX, double scaleY) {
        int dotSize = (int)(6 * Math.min(scaleX, scaleY));
        
        // Punto de inicio
        Point start = waypoints.get(0);
        int x1 = (int)(start.x * scaleX);
        int y1 = (int)(start.y * scaleY);
        
        g2d.setColor(new Color(255, 215, 0, 200));
        g2d.fillOval(x1 - dotSize/2, y1 - dotSize/2, dotSize, dotSize);
        g2d.setColor(new Color(218, 165, 32));
        g2d.setStroke(new BasicStroke(1.5f * (float)Math.min(scaleX, scaleY)));
        g2d.drawOval(x1 - dotSize/2, y1 - dotSize/2, dotSize, dotSize);
        
        // Punto de fin
        Point end = waypoints.get(waypoints.size() - 1);
        int x2 = (int)(end.x * scaleX);
        int y2 = (int)(end.y * scaleY);
        
        g2d.setColor(new Color(255, 215, 0, 200));
        g2d.fillOval(x2 - dotSize/2, y2 - dotSize/2, dotSize, dotSize);
        g2d.setColor(new Color(218, 165, 32));
        g2d.drawOval(x2 - dotSize/2, y2 - dotSize/2, dotSize, dotSize);
    }
    
    public String getFromTerritory() {
        return fromTerritory;
    }
    
    public String getToTerritory() {
        return toTerritory;
    }
}