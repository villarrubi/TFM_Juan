package com.mycompany.tfm_juan;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Representa territorio decorativo (no conquistable) - solo visual
 */
public class DecorativeTerritory {
    private String name;
    private Polygon polygon;
    private Color baseColor;
    private Random textureRandom;
    
    // Cache para optimización
    private BufferedImage territoryCache;
    private double lastScaleX = -1;
    private double lastScaleY = -1;
    
    public DecorativeTerritory(String name, int[] xPoints, int[] yPoints) {
        this.name = name;
        this.polygon = new Polygon(xPoints, yPoints, xPoints.length);
        // Color tierra/desierto neutral
        this.baseColor = new Color(194, 178, 128);
        this.textureRandom = new Random(name.hashCode());
    }
    
    public String getName() {
        return name;
    }
    
    public void invalidateCache() {
        territoryCache = null;
        lastScaleX = -1;
        lastScaleY = -1;
    }
    
    /**
     * Dibuja el territorio decorativo con escala
     */
    public void drawScaled(Graphics2D g2d, double scaleX, double scaleY) {
        Polygon scaledPoly = createScaledPolygon(scaleX, scaleY);
        Rectangle bounds = scaledPoly.getBounds();
        
        // Verificar si necesitamos regenerar el cache
        boolean needsRegeneration = territoryCache == null || 
                                   lastScaleX != scaleX || 
                                   lastScaleY != scaleY;
        
        if (needsRegeneration) {
            regenerateCache(scaledPoly, bounds);
        }
        
        // Dibujar desde el cache
        g2d.drawImage(territoryCache, bounds.x, bounds.y, null);
        
        // Borde decorativo
        drawBorder(g2d, scaledPoly, scaleX, scaleY);
    }
    
    private Polygon createScaledPolygon(double scaleX, double scaleY) {
        Polygon scaledPoly = new Polygon();
        for (int i = 0; i < polygon.npoints; i++) {
            scaledPoly.addPoint(
                (int)(polygon.xpoints[i] * scaleX),
                (int)(polygon.ypoints[i] * scaleY)
            );
        }
        return scaledPoly;
    }
    
    private void regenerateCache(Polygon scaledPoly, Rectangle bounds) {
        territoryCache = new BufferedImage(
            Math.max(1, bounds.width), 
            Math.max(1, bounds.height), 
            BufferedImage.TYPE_INT_ARGB
        );
        
        Graphics2D g2d = territoryCache.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Trasladar el polígono para que empiece en (0,0)
        Polygon localPoly = new Polygon();
        for (int i = 0; i < scaledPoly.npoints; i++) {
            localPoly.addPoint(
                scaledPoly.xpoints[i] - bounds.x,
                scaledPoly.ypoints[i] - bounds.y
            );
        }
        
        // Renderizar base y textura
        drawTerritoryBase(g2d, localPoly);
        drawTerritoryTexture(g2d, localPoly);
        
        g2d.dispose();
        
        lastScaleX = bounds.width > 0 ? 1.0 : -1;
        lastScaleY = bounds.height > 0 ? 1.0 : -1;
    }
    
    private void drawTerritoryBase(Graphics2D g2d, Polygon localPoly) {
        Rectangle bounds = localPoly.getBounds();
        int centerX = bounds.x + bounds.width / 2;
        int centerY = bounds.y + bounds.height / 2;
        
        // Colores tierra/desierto
        Color lightSand = new Color(210, 195, 150);
        Color mediumSand = new Color(194, 178, 128);
        Color darkSand = new Color(170, 150, 100);
        
        // Gradiente radial para dar profundidad
        RadialGradientPaint radialGradient = new RadialGradientPaint(
            centerX, centerY,
            Math.max(bounds.width, bounds.height) * 0.6f,
            new float[]{0.0f, 0.5f, 1.0f},
            new Color[]{lightSand, mediumSand, darkSand}
        );
        
        g2d.setPaint(radialGradient);
        g2d.fill(localPoly);
        
        // Luz superior
        GradientPaint topLight = new GradientPaint(
            bounds.x, bounds.y, new Color(255, 255, 255, 50),
            bounds.x, bounds.y + bounds.height / 3, new Color(255, 255, 255, 0)
        );
        g2d.setPaint(topLight);
        g2d.fill(localPoly);
        
        // Sombra inferior
        GradientPaint bottomShadow = new GradientPaint(
            bounds.x, bounds.y + 2 * bounds.height / 3, new Color(0, 0, 0, 0),
            bounds.x, bounds.y + bounds.height, new Color(0, 0, 0, 50)
        );
        g2d.setPaint(bottomShadow);
        g2d.fill(localPoly);
    }
    
    private void drawTerritoryTexture(Graphics2D g2d, Polygon localPoly) {
        Rectangle bounds = localPoly.getBounds();
        Shape originalClip = g2d.getClip();
        g2d.setClip(localPoly);
        
        textureRandom.setSeed(name.hashCode());
        
        // Manchas de arena/tierra
        for (int i = 0; i < 25; i++) {
            int x = bounds.x + textureRandom.nextInt(Math.max(1, bounds.width));
            int y = bounds.y + textureRandom.nextInt(Math.max(1, bounds.height));
            int size = textureRandom.nextInt(25) + 15;
            
            int alpha = textureRandom.nextInt(30) + 10;
            Color color = textureRandom.nextBoolean() ? 
                new Color(139, 119, 80, alpha) : 
                new Color(210, 180, 140, alpha);
            
            GradientPaint blobGradient = new GradientPaint(
                x, y, color,
                x + size, y + size, new Color(color.getRed(), color.getGreen(), color.getBlue(), 0)
            );
            g2d.setPaint(blobGradient);
            g2d.fillOval(x, y, size, size);
        }
        
        // Puntos pequeños (textura arenosa)
        g2d.setColor(new Color(139, 119, 80, 40));
        for (int i = 0; i < 150; i++) {
            int x = bounds.x + textureRandom.nextInt(Math.max(1, bounds.width));
            int y = bounds.y + textureRandom.nextInt(Math.max(1, bounds.height));
            g2d.fillRect(x, y, 1, 1);
        }
        
        // Grietas y líneas del terreno
        g2d.setColor(new Color(139, 119, 80, 60));
        g2d.setStroke(new BasicStroke(0.8f));
        textureRandom.setSeed(name.hashCode() + 1000);
        for (int i = 0; i < 20; i++) {
            int x1 = bounds.x + textureRandom.nextInt(Math.max(1, bounds.width));
            int y1 = bounds.y + textureRandom.nextInt(Math.max(1, bounds.height));
            int length = textureRandom.nextInt(30) + 10;
            double angle = textureRandom.nextDouble() * Math.PI * 2;
            int x2 = x1 + (int)(length * Math.cos(angle));
            int y2 = y1 + (int)(length * Math.sin(angle));
            
            g2d.drawLine(x1, y1, x2, y2);
        }
        
        // Dunas (ondulaciones)
        textureRandom.setSeed(name.hashCode() + 2000);
        for (int i = 0; i < 12; i++) {
            int x = bounds.x + textureRandom.nextInt(Math.max(1, bounds.width));
            int y = bounds.y + textureRandom.nextInt(Math.max(1, bounds.height));
            int width = textureRandom.nextInt(40) + 20;
            int height = textureRandom.nextInt(20) + 10;
            
            GradientPaint duneGradient = new GradientPaint(
                x, y, new Color(255, 255, 255, 25),
                x, y + height, new Color(0, 0, 0, 20)
            );
            g2d.setPaint(duneGradient);
            g2d.fillOval(x, y, width, height);
        }
        
        g2d.setClip(originalClip);
    }
    
    private void drawBorder(Graphics2D g2d, Polygon scaledPoly, double scaleX, double scaleY) {
        float borderScale = (float)Math.min(scaleX, scaleY);
        
        // Borde sutil de tierra
        g2d.setColor(new Color(120, 100, 70, 100));
        g2d.setStroke(new BasicStroke(1.5f * borderScale, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.draw(scaledPoly);
        
        g2d.setColor(new Color(160, 140, 100, 80));
        g2d.setStroke(new BasicStroke(0.8f * borderScale, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.draw(scaledPoly);
    }
}