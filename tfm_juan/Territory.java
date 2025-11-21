package com.mycompany.tfm_juan;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Representa un territorio en el mapa del juego con texturas mejoradas y optimizadas
 */
public class Territory {
    private String name;
    private Polygon polygon;
    private Color color;
    private int troops;
    private boolean hovered = false;
    private Player owner;
    private Random textureRandom;
    
    // OPTIMIZACIÓN: Cache de las imágenes del territorio
    private BufferedImage territoryBaseCache;
    private BufferedImage territoryTextureCache;
    private double lastScaleX = -1;
    private double lastScaleY = -1;
    private Color lastColor = null;
    
    public Territory(String name, int[] xPoints, int[] yPoints, Color color, int troops, Player owner) {
        this.name = name;
        this.polygon = new Polygon(xPoints, yPoints, xPoints.length);
        this.color = color;
        this.troops = troops;
        this.owner = owner;
        this.textureRandom = new Random(name.hashCode());
    }
    
    // Getters y Setters
    public String getName() {
        return name;
    }
    
    public Color getColor() {
        return color;
    }
    
    public void setColor(Color color) {
        if (!color.equals(this.color)) {
            this.color = color;
            invalidateCache(); // Solo invalida si el color cambió
        }
    }
    
    public int getTroops() {
        return troops;
    }
    
    public void setTroops(int troops) {
        this.troops = troops;
    }
    
    public boolean isHovered() {
        return hovered;
    }
    
    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }
    
    public Player getOwner() {
        return owner;
    }
    
    public void setOwner(Player owner) {
        this.owner = owner;
    }
    
    /**
     * Invalida el cache cuando hay cambios importantes
     */
    public void invalidateCache() {
        territoryBaseCache = null;
        territoryTextureCache = null;
        lastScaleX = -1;
        lastScaleY = -1;
        lastColor = null;
    }
    
    /**
     * Verifica si un punto está dentro del territorio escalado
     */
    public boolean containsScaled(Point p, double scaleX, double scaleY) {
        Polygon scaledPoly = new Polygon();
        for (int i = 0; i < polygon.npoints; i++) {
            scaledPoly.addPoint(
                (int)(polygon.xpoints[i] * scaleX),
                (int)(polygon.ypoints[i] * scaleY)
            );
        }
        return scaledPoly.contains(p);
    }
    
    /**
     * Dibuja el territorio con texturas realistas tipo mapa antiguo - OPTIMIZADO
     */
    public void drawScaled(Graphics2D g2d, boolean isSelected, double scaleX, double scaleY) {
        Polygon scaledPoly = createScaledPolygon(scaleX, scaleY);
        Rectangle bounds = scaledPoly.getBounds();
        
        // Verificar si necesitamos regenerar el cache
        boolean needsRegeneration = territoryBaseCache == null || 
                                   lastScaleX != scaleX || 
                                   lastScaleY != scaleY ||
                                   !color.equals(lastColor);
        
        if (needsRegeneration) {
            regenerateCache(scaledPoly, bounds, scaleX, scaleY);
        }
        
        // Dibujar desde el cache (sin sombra)
        g2d.drawImage(territoryBaseCache, bounds.x, bounds.y, null);
        g2d.drawImage(territoryTextureCache, bounds.x, bounds.y, null);
        
        // Solo estos elementos se dibujan dinámicamente (porque cambian con hover/selección)
        drawBorders(g2d, scaledPoly, isSelected, scaleX, scaleY);
        drawTroopsBadge(g2d, scaledPoly, scaleX, scaleY);
    }
    
    /**
     * Regenera el cache de imágenes del territorio
     */
    private void regenerateCache(Polygon scaledPoly, Rectangle bounds, double scaleX, double scaleY) {
        // Crear imágenes de cache
        territoryBaseCache = new BufferedImage(
            Math.max(1, bounds.width), 
            Math.max(1, bounds.height), 
            BufferedImage.TYPE_INT_ARGB
        );
        territoryTextureCache = new BufferedImage(
            Math.max(1, bounds.width), 
            Math.max(1, bounds.height), 
            BufferedImage.TYPE_INT_ARGB
        );
        
        Graphics2D g2dBase = territoryBaseCache.createGraphics();
        Graphics2D g2dTexture = territoryTextureCache.createGraphics();
        
        g2dBase.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2dTexture.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Trasladar el polígono para que empiece en (0,0) en el cache
        Polygon localPoly = new Polygon();
        for (int i = 0; i < scaledPoly.npoints; i++) {
            localPoly.addPoint(
                scaledPoly.xpoints[i] - bounds.x,
                scaledPoly.ypoints[i] - bounds.y
            );
        }
        
        // Renderizar a las imágenes de cache
        drawTerritoryBase(g2dBase, localPoly, scaleX, scaleY);
        drawTerritoryTexture(g2dTexture, localPoly);
        drawMountainsAndRelief(g2dTexture, localPoly);
        
        g2dBase.dispose();
        g2dTexture.dispose();
        
        // Actualizar valores de cache
        lastScaleX = scaleX;
        lastScaleY = scaleY;
        lastColor = color;
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
    
    private void drawShadow(Graphics2D g2d, Polygon scaledPoly, double scaleX, double scaleY) {
        g2d.setColor(new Color(0, 0, 0, 80));
        Polygon shadowPoly = new Polygon();
        for (int i = 0; i < scaledPoly.npoints; i++) {
            shadowPoly.addPoint(
                scaledPoly.xpoints[i] + (int)(5 * scaleX),
                scaledPoly.ypoints[i] + (int)(5 * scaleY)
            );
        }
        g2d.fill(shadowPoly);
    }
    
    private void drawTerritoryBase(Graphics2D g2d, Polygon localPoly, double scaleX, double scaleY) {
        Color drawColor = color; // No usar hovered aquí, se maneja en borders
        Rectangle bounds = localPoly.getBounds();

        Color baseColor = new Color(
            Math.min(255, drawColor.getRed() + 30),
            Math.min(255, drawColor.getGreen() + 30),
            Math.min(255, drawColor.getBlue() + 30)
        );

        Color shadowColor = new Color(
            Math.max(0, drawColor.getRed() - 60),
            Math.max(0, drawColor.getGreen() - 60),
            Math.max(0, drawColor.getBlue() - 60)
        );

        int centerX = bounds.x + bounds.width / 2;
        int centerY = bounds.y + bounds.height / 2;

        RadialGradientPaint radialGradient = new RadialGradientPaint(
            centerX, centerY,
            Math.max(bounds.width, bounds.height) * 0.6f,
            new float[]{0.0f, 0.4f, 0.8f, 1.0f},
            new Color[]{
                new Color(
                    Math.min(255, baseColor.getRed() + 40),
                    Math.min(255, baseColor.getGreen() + 40),
                    Math.min(255, baseColor.getBlue() + 40)
                ),
                baseColor,
                drawColor,
                new Color(
                    Math.max(0, shadowColor.getRed() - 30),
                    Math.max(0, shadowColor.getGreen() - 30),
                    Math.max(0, shadowColor.getBlue() - 30)
                )
            }
        );

        g2d.setPaint(radialGradient);
        g2d.fill(localPoly);

        GradientPaint lightOverlay = new GradientPaint(
            bounds.x, bounds.y, new Color(255, 255, 255, 90),
            bounds.x + bounds.width / 2, bounds.y + bounds.height / 2, new Color(255, 255, 255, 0)
        );
        g2d.setPaint(lightOverlay);
        g2d.fill(localPoly);

        GradientPaint bottomShadow = new GradientPaint(
            bounds.x, bounds.y + bounds.height / 2, new Color(0, 0, 0, 0),
            bounds.x, bounds.y + bounds.height, new Color(0, 0, 0, 70)
        );
        g2d.setPaint(bottomShadow);
        g2d.fill(localPoly);

        GradientPaint rightShadow = new GradientPaint(
            bounds.x + bounds.width / 2, bounds.y, new Color(0, 0, 0, 0),
            bounds.x + bounds.width, bounds.y, new Color(0, 0, 0, 40)
        );
        g2d.setPaint(rightShadow);
        g2d.fill(localPoly);
    }
    
    private void drawTerritoryTexture(Graphics2D g2d, Polygon localPoly) {
        Rectangle bounds = localPoly.getBounds();
        Shape originalClip = g2d.getClip();
        g2d.setClip(localPoly);
        
        textureRandom.setSeed(name.hashCode());
        
        // Manchas orgánicas grandes (reducido de 30 a 20)
        g2d.setColor(new Color(0, 0, 0, 8));
        for (int i = 0; i < 20; i++) {
            int x = bounds.x + textureRandom.nextInt(Math.max(1, bounds.width));
            int y = bounds.y + textureRandom.nextInt(Math.max(1, bounds.height));
            int size = textureRandom.nextInt(20) + 10;
            GradientPaint blobGradient = new GradientPaint(
                x, y, new Color(0, 0, 0, 15),
                x + size, y + size, new Color(0, 0, 0, 0)
            );
            g2d.setPaint(blobGradient);
            g2d.fillOval(x, y, size, size);
        }
        
        // Puntos pequeños (reducido de 200 a 100)
        g2d.setColor(new Color(0, 0, 0, 12));
        for (int i = 0; i < 100; i++) {
            int x = bounds.x + textureRandom.nextInt(Math.max(1, bounds.width));
            int y = bounds.y + textureRandom.nextInt(Math.max(1, bounds.height));
            int size = textureRandom.nextInt(2) + 1;
            g2d.fillOval(x, y, size, size);
        }
        
        // Líneas de rugosidad (reducido de 40 a 25)
        g2d.setColor(new Color(0, 0, 0, 15));
        textureRandom.setSeed(name.hashCode());
        for (int i = 0; i < 25; i++) {
            int x1 = bounds.x + textureRandom.nextInt(Math.max(1, bounds.width));
            int y1 = bounds.y + textureRandom.nextInt(Math.max(1, bounds.height));
            int length = textureRandom.nextInt(25) + 10;
            double angle = textureRandom.nextDouble() * Math.PI * 2;
            int x2 = x1 + (int)(length * Math.cos(angle));
            int y2 = y1 + (int)(length * Math.sin(angle));
            
            g2d.setStroke(new BasicStroke(0.8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.drawLine(x1, y1, x2, y2);
        }
        
        // Vetas de color (reducido de 15 a 10)
        Color lightVein = new Color(255, 255, 255, 20);
        g2d.setColor(lightVein);
        textureRandom.setSeed(name.hashCode() + 1000);
        for (int i = 0; i < 10; i++) {
            int x = bounds.x + textureRandom.nextInt(Math.max(1, bounds.width));
            int y = bounds.y + textureRandom.nextInt(Math.max(1, bounds.height));
            int width = textureRandom.nextInt(30) + 20;
            int height = textureRandom.nextInt(15) + 10;
            g2d.fillOval(x, y, width, height);
        }
        
        // Efecto de envejecimiento (espaciado de 4 a 6)
        g2d.setColor(new Color(139, 69, 19, 6));
        for (int y = bounds.y; y < bounds.y + bounds.height; y += 6) {
            int offset = textureRandom.nextInt(3);
            g2d.drawLine(bounds.x + offset, y, bounds.x + bounds.width, y);
        }
        
        g2d.setClip(originalClip);
    }
    
    private void drawMountainsAndRelief(Graphics2D g2d, Polygon localPoly) {
        Rectangle bounds = localPoly.getBounds();
        Shape originalClip = g2d.getClip();
        g2d.setClip(localPoly);

        Random reliefRandom = new Random(name.hashCode() + 5000);

        // Montañas (reducido de 8 a 5)
        g2d.setColor(new Color(0, 0, 0, 25));
        for (int i = 0; i < 5; i++) {
            int x = bounds.x + reliefRandom.nextInt(Math.max(1, bounds.width));
            int y = bounds.y + reliefRandom.nextInt(Math.max(1, bounds.height));
            int width = reliefRandom.nextInt(40) + 30;
            int height = reliefRandom.nextInt(50) + 40;

            GradientPaint mountainGradient = new GradientPaint(
                x, y, new Color(0, 0, 0, 35),
                x + width/2, y + height, new Color(0, 0, 0, 0)
            );
            g2d.setPaint(mountainGradient);

            int[] xPoints = {x, x + width/2, x + width};
            int[] yPoints = {y + height, y, y + height};
            g2d.fillPolygon(xPoints, yPoints, 3);

            g2d.setColor(new Color(255, 255, 255, 30));
            g2d.drawLine(x + width/2 - 5, y + 10, x + width/2, y);
        }

        // Colinas (reducido de 15 a 10)
        for (int i = 0; i < 10; i++) {
            int x = bounds.x + reliefRandom.nextInt(Math.max(1, bounds.width));
            int y = bounds.y + reliefRandom.nextInt(Math.max(1, bounds.height));
            int width = reliefRandom.nextInt(25) + 15;
            int height = reliefRandom.nextInt(20) + 15;

            GradientPaint hillGradient = new GradientPaint(
                x, y, new Color(255, 255, 255, 20),
                x, y + height, new Color(0, 0, 0, 15)
            );
            g2d.setPaint(hillGradient);
            g2d.fillOval(x, y, width, height);
        }

        // Valles (reducido de 10 a 6)
        g2d.setColor(new Color(0, 0, 0, 20));
        for (int i = 0; i < 6; i++) {
            int x = bounds.x + reliefRandom.nextInt(Math.max(1, bounds.width));
            int y = bounds.y + reliefRandom.nextInt(Math.max(1, bounds.height));
            int width = reliefRandom.nextInt(35) + 20;
            int height = reliefRandom.nextInt(25) + 15;

            g2d.fillOval(x, y, width, height);
        }

        // Líneas de contorno (reducido de 20 a 12)
        g2d.setColor(new Color(0, 0, 0, 12));
        g2d.setStroke(new BasicStroke(0.7f));
        reliefRandom.setSeed(name.hashCode() + 6000);
        for (int i = 0; i < 12; i++) {
            int x = bounds.x + reliefRandom.nextInt(Math.max(1, bounds.width - 30));
            int y = bounds.y + reliefRandom.nextInt(Math.max(1, bounds.height));
            int length = reliefRandom.nextInt(30) + 20;

            for (int offset = 0; offset < 4; offset += 1) {
                g2d.drawArc(x, y + offset * 3, length, 15, 0, 180);
            }
        }

        // Sombras de elevación (reducido de 12 a 8)
        reliefRandom.setSeed(name.hashCode() + 7000);
        for (int i = 0; i < 8; i++) {
            int x = bounds.x + reliefRandom.nextInt(Math.max(1, bounds.width));
            int y = bounds.y + reliefRandom.nextInt(Math.max(1, bounds.height));
            int size = reliefRandom.nextInt(30) + 20;

            RadialGradientPaint elevationShadow = new RadialGradientPaint(
                x + size/2, y + size/2,
                size / 2f,
                new float[]{0.0f, 0.7f, 1.0f},
                new Color[]{new Color(255, 255, 255, 25), new Color(0, 0, 0, 15), new Color(0, 0, 0, 0)}
            );
            g2d.setPaint(elevationShadow);
            g2d.fillOval(x, y, size, size);
        }

        g2d.setClip(originalClip);
    }
    
    private void drawBorders(Graphics2D g2d, Polygon scaledPoly, boolean isSelected, double scaleX, double scaleY) {
        float borderScale = (float)Math.min(scaleX, scaleY);
        
        // Brillo en hover - ahora se aplica correctamente
        Color drawColor = hovered ? color.brighter() : color;
        
        g2d.setColor(new Color(40, 30, 20, 80));
        g2d.setStroke(new BasicStroke(2.0f * borderScale, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.draw(scaledPoly);
        
        g2d.setColor(new Color(70, 50, 30));
        g2d.setStroke(new BasicStroke(1.5f * borderScale, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.draw(scaledPoly);
        
        g2d.setColor(new Color(139, 90, 43));
        g2d.setStroke(new BasicStroke(1.0f * borderScale, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.draw(scaledPoly);
        
        if (isSelected) {
            g2d.setColor(new Color(255, 215, 0, 200));
            g2d.setStroke(new BasicStroke(2.5f * borderScale, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.draw(scaledPoly);
            
            g2d.setColor(new Color(255, 255, 0, 80));
            g2d.setStroke(new BasicStroke(4.0f * borderScale, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.draw(scaledPoly);
        }
        
        if (hovered) {
            g2d.setColor(new Color(255, 248, 220, 150));
            g2d.setStroke(new BasicStroke(1.5f * borderScale, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.draw(scaledPoly);
        }
    }
    
    private void drawTroopsBadge(Graphics2D g2d, Polygon scaledPoly, double scaleX, double scaleY) {
        Rectangle bounds = scaledPoly.getBounds();
        int centerX = bounds.x + bounds.width / 2;
        int centerY = bounds.y + bounds.height / 2;
        
        String troopsText = String.valueOf(troops);
        int fontSize = (int)(14 * Math.min(scaleX, scaleY));
        g2d.setFont(new Font("Trajan Pro", Font.BOLD, fontSize));
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(troopsText);
        int textHeight = fm.getHeight();
        
        int padding = (int)(8 * Math.min(scaleX, scaleY));
        int badgeWidth = textWidth + padding * 2;
        int badgeHeight = textHeight + padding;
        
        g2d.setColor(new Color(0, 0, 0, 120));
        g2d.fillRoundRect(
            centerX - badgeWidth / 2 + 4,
            centerY - badgeHeight / 2 + 4,
            badgeWidth, badgeHeight, 12, 12
        );
        
        GradientPaint badgeGradient = new GradientPaint(
            centerX, centerY - badgeHeight / 2, new Color(240, 220, 180),
            centerX, centerY + badgeHeight / 2, new Color(205, 170, 125)
        );
        g2d.setPaint(badgeGradient);
        g2d.fillRoundRect(
            centerX - badgeWidth / 2,
            centerY - badgeHeight / 2,
            badgeWidth, badgeHeight, 12, 12
        );
        
        g2d.setColor(new Color(139, 69, 19, 25));
        for (int i = centerY - badgeHeight/2; i < centerY + badgeHeight/2; i += 2) {
            g2d.drawLine(centerX - badgeWidth/2 + 2, i, centerX + badgeWidth/2 - 2, i);
        }
        
        g2d.setColor(new Color(139, 90, 43));
        g2d.setStroke(new BasicStroke(3.0f * (float)Math.min(scaleX, scaleY)));
        g2d.drawRoundRect(
            centerX - badgeWidth / 2,
            centerY - badgeHeight / 2,
            badgeWidth, badgeHeight, 12, 12
        );
        
        g2d.setColor(new Color(218, 165, 32));
        g2d.setStroke(new BasicStroke(1.5f * (float)Math.min(scaleX, scaleY)));
        g2d.drawRoundRect(
            centerX - badgeWidth / 2 + 2,
            centerY - badgeHeight / 2 + 2,
            badgeWidth - 4, badgeHeight - 4, 10, 10
        );
        
        g2d.setColor(new Color(101, 67, 33));
        g2d.drawString(troopsText, 
            centerX - textWidth / 2, 
            centerY + fm.getAscent() / 2);
    }
}