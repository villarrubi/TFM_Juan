package com.mycompany.tfm_juan;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Se encarga de renderizar el mapa y el fondo del océano - OPTIMIZADO
 */
public class MapRenderer {
    private BufferedImage oceanBackgroundCache;
    private int lastWidth = -1;
    private int lastHeight = -1;
    
    public void render(Graphics2D g2d, GameState gameState, int width, int height, Territory selectedTerritory) {
        setupRenderingHints(g2d);
        
        // Solo regenerar el océano si el tamaño cambió
        if (oceanBackgroundCache == null || lastWidth != width || lastHeight != height) {
            createOceanBackgroundCache(width, height);
        }
        
        // Dibujar océano desde cache (muy rápido)
        g2d.drawImage(oceanBackgroundCache, 0, 90, null);
        
        double scaleX = width / GameConstants.BASE_WIDTH;
        double scaleY = (height - 90) / GameConstants.BASE_HEIGHT;
        
        g2d.translate(0, 90);
        
        // PRIMERO: Dibujar territorios decorativos (fondo)
        for (DecorativeTerritory dt : gameState.getDecorativeTerritories()) {
            dt.drawScaled(g2d, scaleX, scaleY);
        }
        
        // SEGUNDO: Dibujar rutas marítimas (encima de territorios decorativos)
        for (MaritimeRoute route : gameState.getMaritimeRoutes()) {
            route.drawScaled(g2d, scaleX, scaleY);
        }
        
        // TERCERO: Dibujar territorios conquistables (encima de todo)
        for (Territory t : gameState.getTerritories().values()) {
            t.drawScaled(g2d, t == selectedTerritory, scaleX, scaleY);
        }
        
        g2d.translate(0, -90);
    }
    
    private void setupRenderingHints(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        // IMPORTANTE: Desactivar interpolación para imágenes en cache
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
    }
    
    private void createOceanBackgroundCache(int width, int height) {
        lastWidth = width;
        lastHeight = height - 90;
        
        oceanBackgroundCache = new BufferedImage(width, height - 90, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = oceanBackgroundCache.createGraphics();
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        drawOceanBackground(g2d, width, height - 90);
        
        g2d.dispose();
    }
    
    private void drawOceanBackground(Graphics2D g2d, int width, int height) {
        // Gradientes base
        GradientPaint deepOcean = new GradientPaint(
            0, 0, new Color(25, 55, 85),
            0, height / 3, new Color(32, 84, 138)
        );
        g2d.setPaint(deepOcean);
        g2d.fillRect(0, 0, width, height / 3);

        GradientPaint midOcean = new GradientPaint(
            0, height / 3, new Color(32, 84, 138),
            0, 2 * height / 3, new Color(41, 128, 185)
        );
        g2d.setPaint(midOcean);
        g2d.fillRect(0, height / 3, width, height / 3);

        GradientPaint surfaceOcean = new GradientPaint(
            0, 2 * height / 3, new Color(41, 128, 185),
            0, height, new Color(21, 67, 96)
        );
        g2d.setPaint(surfaceOcean);
        g2d.fillRect(0, 2 * height / 3, width, height / 3);

        // Olas (reducido de 15 a 20 píxeles de espaciado)
        g2d.setColor(new Color(52, 152, 219, 35));
        for (int y = 0; y < height; y += 20) {
            double waveOffset1 = Math.sin(y * 0.05) * 8;
            double waveOffset2 = Math.cos(y * 0.08 + 1) * 5;
            int totalOffset = (int)(waveOffset1 + waveOffset2);

            g2d.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            for (int x = 0; x < width; x += 35) { // Aumentado de 30 a 35
                double localWave = Math.sin((x + y) * 0.02) * 4;
                g2d.drawLine(
                    x + totalOffset, y + (int)localWave,
                    x + 25 + totalOffset, y + (int)localWave
                );
            }
        }

        // Líneas de brillo (reducido de 3 a 5 píxeles)
        for (int y = 0; y < height; y += 5) {
            int alpha = 15 + (int)(Math.sin(y * 0.1) * 10);
            g2d.setColor(new Color(41, 128, 185, alpha));
            double offset = Math.sin(y * 0.08) * 4;
            g2d.drawLine((int)offset, y, width, y);
        }

        // Líneas diagonales (reducido de 35 a 45)
        g2d.setColor(new Color(52, 152, 219, 25));
        g2d.setStroke(new BasicStroke(1.2f));
        for (int i = 0; i < width + height; i += 45) {
            double curve = Math.sin(i * 0.02) * 15;
            int x1 = i;
            int y1 = 0;
            int x2 = (int)(i - height + curve);
            int y2 = height;
            g2d.drawLine(x1, y1, x2, y2);
        }

        // Círculos de profundidad (reducido de 12 a 8)
        g2d.setColor(new Color(25, 55, 85, 30));
        Random oceanRandom = new Random(42);
        for (int i = 0; i < 8; i++) {
            int centerX = oceanRandom.nextInt(width);
            int centerY = oceanRandom.nextInt(height);

            for (int r = 15; r < 80; r += 20) { // Aumentado de 15 a 20
                int alpha = Math.max(5, 35 - r / 3);
                g2d.setColor(new Color(25, 55, 85, alpha));
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.drawOval(centerX - r, centerY - r, r * 2, r * 2);
            }
        }

        // Partículas de luz (reducido de 60 a 40)
        oceanRandom = new Random(123);
        for (int i = 0; i < 40; i++) {
            int x = oceanRandom.nextInt(width);
            int y = oceanRandom.nextInt(height);
            int size = oceanRandom.nextInt(4) + 2;
            int alpha = oceanRandom.nextInt(40) + 30;

            float depth = (float)y / height;
            alpha = (int)(alpha * (1.2 - depth));

            g2d.setColor(new Color(174, 214, 241, alpha));
            g2d.fillOval(x, y, size, size);

            if (oceanRandom.nextFloat() > 0.7) {
                g2d.setColor(new Color(174, 214, 241, alpha / 3));
                g2d.fillOval(x - 2, y - 2, size + 4, size + 4);
            }
        }

        // Sombras de profundidad (reducido de 20 a 12)
        g2d.setColor(new Color(15, 35, 55, 40));
        oceanRandom = new Random(456);
        for (int i = 0; i < 12; i++) {
            int x = oceanRandom.nextInt(width);
            int y = oceanRandom.nextInt(height);
            int w = oceanRandom.nextInt(80) + 40;
            int h = oceanRandom.nextInt(60) + 30;

            GradientPaint depthGradient = new GradientPaint(
                x, y, new Color(15, 35, 55, 50),
                x + w, y + h, new Color(15, 35, 55, 0)
            );
            g2d.setPaint(depthGradient);
            g2d.fillOval(x, y, w, h);
        }

        // Ondas superficiales (reducido espaciado de 25 a 30)
        g2d.setColor(new Color(100, 180, 220, 45));
        for (int y = 20; y < height; y += 30) {
            g2d.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            for (int x = 0; x < width; x += 60) { // Aumentado de 50 a 60
                double wave1 = Math.sin(x * 0.03 + y * 0.02) * 6;
                double wave2 = Math.cos(x * 0.05) * 3;
                int yOffset = (int)(wave1 + wave2);
                g2d.drawArc(x - 10, y + yOffset - 5, 30, 10, 0, 180);
            }
        }

        // Burbujas (reducido de 30 a 20)
        g2d.setColor(new Color(220, 240, 250, 25));
        oceanRandom = new Random(789);
        for (int i = 0; i < 20; i++) {
            int x = oceanRandom.nextInt(width);
            int y = oceanRandom.nextInt(height);
            int radius = oceanRandom.nextInt(15) + 5;

            for (int j = 0; j < 3; j++) {
                int offset = j * 3;
                g2d.fillOval(x - radius + offset, y, radius * 2 - offset * 2, radius);
            }
        }

        // Viñetas de borde
        GradientPaint edgeVignette = new GradientPaint(
            0, 0, new Color(0, 0, 0, 0),
            0, 50, new Color(0, 0, 0, 30)
        );
        g2d.setPaint(edgeVignette);
        g2d.fillRect(0, 0, width, 50);

        GradientPaint bottomVignette = new GradientPaint(
            0, height - 50, new Color(0, 0, 0, 30),
            0, height, new Color(0, 0, 0, 0)
        );
        g2d.setPaint(bottomVignette);
        g2d.fillRect(0, height - 50, width, 50);
    }
    
    public void invalidateCache() {
        oceanBackgroundCache = null;
    }
}