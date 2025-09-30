package com.mycompany.tfm_juan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TFM_Juan extends JPanel {
    private Map<String, Territory> territories;
    private Territory selectedTerritory;
    private Random random;
    
    // Dimensiones base para el escalado
    private static final double BASE_WIDTH = 700.0;
    private static final double BASE_HEIGHT = 650.0;
    
    // Colores disponibles
    private static final Color[] AVAILABLE_COLORS = {
        Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED, Color.BLACK
    };
    private static final String[] COLOR_NAMES = {
        "Azul", "Verde", "Amarillo", "Rojo", "Negro"
    };
    
    public TFM_Juan() {
        random = new Random();
        territories = new HashMap<>();
        initializeTerritories();
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e.getPoint());
            }
        });
        
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                handleHover(e.getPoint());
            }
        });
    }
    
    private void initializeTerritories() {
        // España
        addTerritory("España",
            new int[]{120, 140, 160, 180, 190, 180, 160, 140, 110, 100},
            new int[]{450, 430, 440, 460, 490, 510, 520, 510, 490, 470});
        
        // Portugal
        addTerritory("Portugal",
            new int[]{80, 100, 110, 100, 85, 75},
            new int[]{450, 470, 490, 505, 500, 475});
        
        // Francia
        addTerritory("Francia",
            new int[]{180, 200, 230, 250, 260, 240, 210, 190, 160, 140},
            new int[]{380, 360, 350, 370, 410, 440, 450, 460, 440, 410});
        
        // Reino Unido
        addTerritory("Reino Unido",
            new int[]{150, 170, 190, 200, 190, 170, 155, 145},
            new int[]{260, 250, 260, 290, 320, 330, 320, 280});
        
        // Irlanda
        addTerritory("Irlanda",
            new int[]{100, 120, 130, 125, 110, 95},
            new int[]{270, 265, 285, 310, 315, 295});
        
        // Alemania
        addTerritory("Alemania",
            new int[]{280, 310, 340, 350, 340, 320, 300, 270, 260},
            new int[]{320, 310, 330, 360, 390, 400, 390, 370, 350});
        
        // Italia
        addTerritory("Italia",
            new int[]{300, 320, 330, 340, 330, 315, 300, 290, 285},
            new int[]{420, 410, 430, 470, 510, 530, 520, 480, 440});
        
        // Polonia
        addTerritory("Polonia",
            new int[]{360, 390, 410, 420, 410, 390, 370, 350},
            new int[]{300, 290, 310, 340, 370, 380, 360, 330});
        
        // Noruega
        addTerritory("Noruega",
            new int[]{280, 300, 320, 330, 325, 310, 295, 275, 270},
            new int[]{100, 80, 90, 130, 180, 200, 190, 150, 120});
        
        // Suecia
        addTerritory("Suecia",
            new int[]{330, 360, 380, 390, 385, 370, 350, 325},
            new int[]{130, 110, 130, 170, 210, 230, 220, 180});
        
        // Finlandia
        addTerritory("Finlandia",
            new int[]{390, 420, 440, 450, 445, 430, 410, 385},
            new int[]{90, 70, 85, 120, 160, 180, 170, 130});
        
        // Grecia
        addTerritory("Grecia",
            new int[]{380, 400, 420, 425, 415, 395, 375},
            new int[]{500, 490, 500, 525, 545, 540, 520});
        
        // Rumania
        addTerritory("Rumania",
            new int[]{420, 450, 470, 475, 465, 445, 425, 410},
            new int[]{420, 410, 425, 450, 470, 475, 460, 440});
        
        // Ucrania
        addTerritory("Ucrania",
            new int[]{420, 460, 500, 520, 530, 520, 490, 460, 440, 410},
            new int[]{340, 330, 340, 360, 390, 410, 405, 390, 370, 350});
        
        // Turquía
        addTerritory("Turquía",
            new int[]{430, 460, 480, 490, 475, 450, 435},
            new int[]{520, 510, 520, 540, 555, 550, 535});
        
        // Países Bajos
        addTerritory("Países Bajos",
            new int[]{250, 270, 280, 275, 260, 245},
            new int[]{310, 305, 320, 335, 340, 325});
        
        // Bélgica
        addTerritory("Bélgica",
            new int[]{240, 260, 270, 265, 250, 235},
            new int[]{340, 335, 350, 365, 370, 355});
        
        // Suiza
        addTerritory("Suiza",
            new int[]{270, 290, 300, 295, 280, 265},
            new int[]{390, 385, 400, 415, 420, 405});
        
        // Austria
        addTerritory("Austria",
            new int[]{310, 340, 360, 365, 355, 335, 315},
            new int[]{380, 375, 385, 400, 410, 405, 395});
        
        // República Checa
        addTerritory("República Checa",
            new int[]{320, 345, 360, 355, 340, 325},
            new int[]{340, 335, 350, 365, 370, 355});
    }
    
    private void addTerritory(String name, int[] xPoints, int[] yPoints) {
        Color randomColor = AVAILABLE_COLORS[random.nextInt(AVAILABLE_COLORS.length)];
        int randomTroops = random.nextInt(31); // 0-30
        territories.put(name, new Territory(name, xPoints, yPoints, randomColor, randomTroops));
    }
    
    private void handleClick(Point p) {
        double scaleX = getWidth() / BASE_WIDTH;
        double scaleY = getHeight() / BASE_HEIGHT;
        
        for (Territory t : territories.values()) {
            if (t.containsScaled(p, scaleX, scaleY)) {
                showTerritoryEditor(t);
                return;
            }
        }
    }
    
    private void showTerritoryEditor(Territory territory) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), 
            "Editar: " + territory.name, true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Etiqueta de color
        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(new JLabel("Color:"), gbc);
        
        // ComboBox de colores
        JComboBox<String> colorCombo = new JComboBox<>(COLOR_NAMES);
        int currentColorIndex = getColorIndex(territory.color);
        colorCombo.setSelectedIndex(currentColorIndex);
        gbc.gridx = 1;
        dialog.add(colorCombo, gbc);
        
        // Etiqueta de tropas
        gbc.gridx = 0;
        gbc.gridy = 1;
        dialog.add(new JLabel("Número de tropas (0-30):"), gbc);
        
        // Spinner para tropas
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(
            territory.troops, 0, 30, 1);
        JSpinner troopsSpinner = new JSpinner(spinnerModel);
        gbc.gridx = 1;
        dialog.add(troopsSpinner, gbc);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton okButton = new JButton("Aceptar");
        JButton cancelButton = new JButton("Cancelar");
        
        okButton.addActionListener(e -> {
            int selectedColorIndex = colorCombo.getSelectedIndex();
            territory.color = AVAILABLE_COLORS[selectedColorIndex];
            territory.troops = (Integer) troopsSpinner.getValue();
            dialog.dispose();
            repaint();
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        dialog.add(buttonPanel, gbc);
        
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private int getColorIndex(Color color) {
        for (int i = 0; i < AVAILABLE_COLORS.length; i++) {
            if (AVAILABLE_COLORS[i].equals(color)) {
                return i;
            }
        }
        return 0;
    }
    
    private void handleHover(Point p) {
        double scaleX = getWidth() / BASE_WIDTH;
        double scaleY = getHeight() / BASE_HEIGHT;
        
        boolean needsRepaint = false;
        for (Territory t : territories.values()) {
            boolean wasHovered = t.isHovered;
            t.isHovered = t.containsScaled(p, scaleX, scaleY);
            if (wasHovered != t.isHovered) {
                needsRepaint = true;
            }
        }
        if (needsRepaint) {
            repaint();
            setCursor(territories.values().stream().anyMatch(t -> t.isHovered) 
                ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
                : Cursor.getDefaultCursor());
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Fondo (mar)
        g2d.setColor(new Color(173, 216, 230));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        // Calcular escala
        double scaleX = getWidth() / BASE_WIDTH;
        double scaleY = getHeight() / BASE_HEIGHT;
        
        // Dibujar territorios escalados
        for (Territory t : territories.values()) {
            t.drawScaled(g2d, t == selectedTerritory, scaleX, scaleY);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Mapa de Europa - Risk");
            TFM_Juan map = new TFM_Juan();
            
            frame.setLayout(new BorderLayout());
            frame.add(map, BorderLayout.CENTER);
            frame.setSize(700, 650);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

class Territory {
    String name;
    Polygon polygon;
    Color color;
    int troops;
    boolean isHovered = false;
    
    public Territory(String name, int[] xPoints, int[] yPoints, Color color, int troops) {
        this.name = name;
        this.polygon = new Polygon(xPoints, yPoints, xPoints.length);
        this.color = color;
        this.troops = troops;
    }
    
    public boolean contains(Point p) {
        return polygon.contains(p);
    }
    
    public boolean containsScaled(Point p, double scaleX, double scaleY) {
        // Crear polígono escalado temporal para verificar contención
        Polygon scaledPoly = new Polygon();
        for (int i = 0; i < polygon.npoints; i++) {
            scaledPoly.addPoint(
                (int)(polygon.xpoints[i] * scaleX),
                (int)(polygon.ypoints[i] * scaleY)
            );
        }
        return scaledPoly.contains(p);
    }
    
    public void drawScaled(Graphics2D g2d, boolean isSelected, double scaleX, double scaleY) {
        // Crear polígono escalado
        Polygon scaledPoly = new Polygon();
        for (int i = 0; i < polygon.npoints; i++) {
            scaledPoly.addPoint(
                (int)(polygon.xpoints[i] * scaleX),
                (int)(polygon.ypoints[i] * scaleY)
            );
        }
        
        // Color de fondo del territorio
        Color drawColor = isHovered ? color.brighter() : color;
        g2d.setColor(drawColor);
        g2d.fill(scaledPoly);
        
        // Borde negro
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke((float)(2.0 * Math.min(scaleX, scaleY))));
        g2d.draw(scaledPoly);
        
        // Borde adicional si está seleccionado
        if (isSelected) {
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke((float)(3.0 * Math.min(scaleX, scaleY))));
            g2d.draw(scaledPoly);
        }
        
        // Calcular centro del territorio escalado
        Rectangle bounds = scaledPoly.getBounds();
        int centerX = bounds.x + bounds.width / 2;
        int centerY = bounds.y + bounds.height / 2;
        
        // Dibujar número de tropas con fondo
        String troopsText = String.valueOf(troops);
        int fontSize = (int)(16 * Math.min(scaleX, scaleY));
        g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(troopsText);
        int textHeight = fm.getHeight();
        
        // Fondo blanco para el número (más pequeño)
        int padding = (int)(2 * Math.min(scaleX, scaleY));
        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(
            centerX - textWidth / 2 - padding,
            centerY - textHeight / 2 - padding,
            textWidth + padding * 2,
            textHeight + padding * 2,
            (int)(3 * Math.min(scaleX, scaleY)), 
            (int)(3 * Math.min(scaleX, scaleY))
        );
        
        // Borde del fondo
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke((float)(1.0 * Math.min(scaleX, scaleY))));
        g2d.drawRoundRect(
            centerX - textWidth / 2 - padding,
            centerY - textHeight / 2 - padding,
            textWidth + padding * 2,
            textHeight + padding * 2,
            (int)(3 * Math.min(scaleX, scaleY)), 
            (int)(3 * Math.min(scaleX, scaleY))
        );
        
        // Dibujar número
        g2d.setColor(Color.BLACK);
        g2d.drawString(troopsText, 
            centerX - textWidth / 2, 
            centerY + fm.getAscent() / 2);
        
        // Nombre del país (más pequeño, arriba)
        int nameFontSize = (int)(9 * Math.min(scaleX, scaleY));
        g2d.setFont(new Font("Arial", Font.PLAIN, nameFontSize));
        fm = g2d.getFontMetrics();
        textWidth = fm.stringWidth(name);
        
        // Sombra del texto
        g2d.setColor(Color.WHITE);
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx != 0 || dy != 0) {
                    g2d.drawString(name, 
                        centerX - textWidth / 2 + dx, 
                        bounds.y + (int)(12 * scaleY) + dy);
                }
            }
        }
        
        // Texto del nombre
        g2d.setColor(Color.BLACK);
        g2d.drawString(name, 
            centerX - textWidth / 2, 
            bounds.y + (int)(12 * scaleY));
    }
}