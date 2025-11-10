package com.mycompany.tfm_juan;


import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.util.ArrayList;
import java.util.Collections;

public class TFM_Juan extends JPanel {
    private Map<String, Territory> territories;
    private Territory selectedTerritory;
    private Random random;
    private int numPlayers;
    private Color[] gameColors;
    private String[] playerNames;
    
    // Dimensiones base para el escalado
    private static final double BASE_WIDTH = 600.0;
    private static final double BASE_HEIGHT = 550.0;
    
    // Paleta de colores moderna y vibrante
    private static final Color[] AVAILABLE_COLORS = {
        new Color(46, 204, 113),   // Verde esmeralda
        new Color(241, 196, 15),   // Amarillo dorado
        new Color(231, 76, 60),    // Rojo coral
        new Color(155, 89, 182)    // Púrpura
    };
    private static final String[] COLOR_NAMES = {
        "Verde", "Amarillo", "Rojo", "Púrpura"
    };
    
    // Colores del tema
    private static final Color OCEAN_COLOR = new Color(41, 128, 185);
    private static final Color PANEL_BG = new Color(44, 62, 80);
    
    public TFM_Juan(int numPlayers, String[] playerNames) {
        this.numPlayers = numPlayers;
        this.playerNames = playerNames;
        this.gameColors = Arrays.copyOf(AVAILABLE_COLORS, numPlayers);
        
        random = new Random();
        territories = new HashMap<>();
        initializeTerritories();
        
        setPreferredSize(new Dimension(950, 700));
        setBackground(OCEAN_COLOR);
        
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
        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                repaint();
            }
        });
    }
    
    private void initializeTerritories() {
    // Crear todos los territorios primero sin asignar color
    String[] territoryNames = {
        "Lusitania", "Tarraconensis", "Baetica", "Mauritania", "África", 
        "Cirenaica", "Egipto", "Arabia", "Judea", "Siria", "Asia", "Ponto", 
        "Armenia", "Grecia", "Macedonia", "Tracia", "Dacia", "Moesia", 
        "Dalmacia", "Panonia", "Magna grecia", "Roma", "Galia Cisalpina", "Raetia",
        "Germania", "Narbonense", "Galia", "Britania"
    };
    
    int[][] xCoords = {
        {20, 50, 50, 40, 20}, // Lusitania
        {20, 140, 80, 50, 50, 20}, // Tarraconensis
        {50, 80, 40, 30}, // Baetica
        {20, 40, 80, 160, 160, 20}, // Mauritania
        {160, 210, 210, 290, 290, 160}, // África
        {290, 300, 320, 370, 370, 290}, // Cirenaica
        {370, 460, 450, 370}, // Egipto
        {450, 470, 480, 470, 500, 450}, // Arabia
        {450, 460, 470, 460}, // Judea
        {460, 480, 490, 480, 460}, // Siria
        {460, 510, 490, 410, 380, 370, 370, 400}, // Asia
        {380, 410, 430, 450, 470, 490, 480, 410, 380}, // Ponto
        {480, 510, 530, 570, 570, 550}, // Armenia
        {310, 320, 310, 320, 320, 330, 330, 340, 340, 330}, // Grecia
        {340, 290, 310, 330, 325, 335}, // Macedonia
        {370, 330, 360, 390, 380, 380}, // Tracia
        {390, 410, 400, 340, 320, 320, 310, 310, 340, 360, 370, 370}, // Dacia
        {310, 330, 360, 390, 370, 370, 360, 340, 310, 290, 290}, // Moesia
        {290, 310, 290, 290, 250, 240, 280}, // Dalmacia
        {290, 310, 310, 320, 300, 250, 250}, // Panonia
        {250, 280, 285, 270, 265, 270, 270, 267, 270, 260, 250, 250, 255, 250, 225, 225, 250, 260, 260, 250}, // Magna grecia
        {200, 215, 240, 255, 245, 280, 250, 240, 240, 230}, // Roma
        {250, 230, 230, 160, 160, 170, 190, 200, 215, 220, 230, 235, 240}, // Galia Cisalpina
        {250, 250, 235, 235, 180, 180, 230, 230}, // Raetia
        {235, 235, 200, 200, 180, 160, 160, 170, 180}, // Germania
        {120, 140, 160, 190, 190, 180, 160, 120}, // Narbonense
        {90,  120, 120, 160, 185, 170, 160, 160, 180, 180, 200, 200, 180, 160, 160, 140, 125, 110, 70, 70, 90}, // Galia
        {60, 60, 90, 120, 120}, // Britania
    };
    
    int[][] yCoords = {
        {360, 360, 410, 440, 440}, // Lusitania
        {310, 310, 440, 410, 380, 360}, // Tarraconensis
        {410, 440, 460, 440}, // Baetica
        {490, 460, 470, 430, 550, 550}, // Mauritania
        {430, 430, 480, 530, 550, 550}, // África
        {530, 510, 500, 530, 550, 550}, // Cirenaica
        {530, 530, 550, 550}, // Egipto
        {540, 490, 490, 540, 550, 550}, // Arabia
        {530, 530, 490, 490}, // Judea
        {490, 490, 460, 420, 450}, // Siria
        {450, 360, 360, 380, 370, 370, 440, 460}, // Asia
        {360, 360, 340, 340, 360, 360, 370, 380, 370, 370}, // Ponto
        {410, 360, 360, 370, 390, 410, 410}, // Armenia
        {400, 410, 420, 430, 440, 440, 420, 420, 410, 400}, // Grecia
        {360, 360, 405, 405, 370, 370}, // Macedonia
        {360, 360, 320, 290, 350, 370}, // Tracia
        {290, 260, 220, 220, 230, 240, 245, 280, 300, 300, 290, 280}, // Dacia
        {360, 360, 320, 290, 280, 290, 300, 300, 280, 280, 310}, // Moesia
        {360, 360, 310, 290, 270, 280, 320}, // Dalmacia
        {310, 280, 245, 240, 230, 190, 270}, // Panonia
        {370, 360, 375, 370, 385, 390, 400, 405, 410, 420, 420, 435, 440, 445, 420, 420, 410, 400, 380, 380}, // Magna grecia
        {310, 295, 340, 340, 345, 360, 370, 375, 365, 365}, // Roma
        {270, 270, 250, 250, 260, 260, 300, 310, 295, 280, 275, 280, 280}, // Galia Cisalpina
        {270, 195, 195, 210, 240, 250, 250, 270, 270}, // Raetia
        {195, 210, 230, 180, 175, 130, 120, 110, 110}, // Germania
        {310, 310, 320, 300, 280, 290, 300, 300}, // Narbonense
        {310, 310, 300, 300, 290, 260, 260, 250, 250, 240, 230, 185, 175, 130, 120, 160, 180, 210, 220, 250, 310}, // Galia
        {180, 60, 60, 120, 160} // Britania
    };
    
    // Crear lista de índices y mezclarla para asignación aleatoria
    java.util.List<Integer> indices = new ArrayList<>();
    for (int i = 0; i < territoryNames.length; i++) {
        indices.add(i);
    }
    Collections.shuffle(indices, random);
    
    // Calcular territorios por jugador
    int territoriosBase = territoryNames.length / numPlayers;
    int territoriosExtra = territoryNames.length % numPlayers;
    
    // Asignar territorios a jugadores
    int currentIndex = 0;
    for (int player = 0; player < numPlayers; player++) {
        int territoriosParaEsteJugador = territoriosBase + (player < territoriosExtra ? 1 : 0);
        Color playerColor = gameColors[player];
        
        for (int t = 0; t < territoriosParaEsteJugador; t++) {
            int territoryIndex = indices.get(currentIndex++);
            int randomTroops = random.nextInt(10) + 1; // Entre 1 y 10 tropas
            
            territories.put(
                territoryNames[territoryIndex],
                new Territory(
                    territoryNames[territoryIndex],
                    xCoords[territoryIndex],
                    yCoords[territoryIndex],
                    playerColor,
                    randomTroops
                )
            );
        }
    }
}

    private void addTerritory(String name, int[] xPoints, int[] yPoints) {
        Color randomColor = gameColors[random.nextInt(gameColors.length)];
        int randomTroops = random.nextInt(31);
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
            "Configurar Territorio", true);
        dialog.setLayout(new BorderLayout());
        dialog.setBackground(PANEL_BG);
        
        // Panel principal con padding
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(PANEL_BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Título del territorio
        JLabel titleLabel = new JLabel(territory.name);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);
        
        gbc.gridwidth = 1;
        
        // Etiqueta de color
        JLabel colorLabel = new JLabel("Color del Territorio:");
        colorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        colorLabel.setForeground(new Color(189, 195, 199));
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(colorLabel, gbc);
        
        // ComboBox de colores con renderer personalizado (solo colores del juego)
        String[] availableColorNames = Arrays.copyOf(COLOR_NAMES, numPlayers);
        JComboBox<String> colorCombo = new JComboBox<>(availableColorNames);
        colorCombo.setRenderer(new ColorComboRenderer(numPlayers));
        colorCombo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        int currentColorIndex = getColorIndex(territory.color);
        colorCombo.setSelectedIndex(currentColorIndex);
        gbc.gridx = 1;
        mainPanel.add(colorCombo, gbc);
        
        // Etiqueta de tropas
        JLabel troopsLabel = new JLabel("Número de Tropas:");
        troopsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        troopsLabel.setForeground(new Color(189, 195, 199));
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(troopsLabel, gbc);
        
        // Spinner para tropas con estilo
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(territory.troops, 0, 30, 1);
        JSpinner troopsSpinner = new JSpinner(spinnerModel);
        troopsSpinner.setFont(new Font("Segoe UI", Font.BOLD, 14));
        ((JSpinner.DefaultEditor) troopsSpinner.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER);
        gbc.gridx = 1;
        mainPanel.add(troopsSpinner, gbc);
        
        // Panel de botones con estilo
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(PANEL_BG);
        
        JButton okButton = createStyledButton("✓ Aceptar", new Color(46, 204, 113));
        JButton cancelButton = createStyledButton("✕ Cancelar", new Color(231, 76, 60));
        
        okButton.addActionListener(e -> {
            int selectedColorIndex = colorCombo.getSelectedIndex();
            territory.color = gameColors[selectedColorIndex];
            territory.troops = (Integer) troopsSpinner.getValue();
            dialog.dispose();
            repaint();
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        mainPanel.add(buttonPanel, gbc);
        
        dialog.add(mainPanel, BorderLayout.CENTER);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(130, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.brighter());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    private int getColorIndex(Color color) {
        for (int i = 0; i < gameColors.length; i++) {
            if (gameColors[i].equals(color)) {
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
        
        // Activar antialiasing para bordes suaves
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        // Gradiente para el océano
        GradientPaint oceanGradient = new GradientPaint(
            0, 0, new Color(41, 128, 185),
            0, getHeight(), new Color(52, 152, 219)
        );
        g2d.setPaint(oceanGradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        // Calcular escala
        double scaleX = getWidth() / BASE_WIDTH;
        double scaleY = getHeight() / BASE_HEIGHT;
        
        // Dibujar territorios con sombras
        for (Territory t : territories.values()) {
            t.drawScaled(g2d, t == selectedTerritory, scaleX, scaleY);
        }
        
        // Dibujar información de jugadores en las esquinas
        drawPlayerInfo(g2d);
    }
    
    private void drawPlayerInfo(Graphics2D g2d) {
        int padding = 15;
        int boxWidth = 180;
        int boxHeight = 85;
        
        // Posiciones de las esquinas
        Point[] positions = {
            new Point(padding, padding), // Superior izquierda
            new Point(getWidth() - boxWidth - padding, padding), // Superior derecha
            new Point(padding, getHeight() - boxHeight - padding), // Inferior izquierda
            new Point(getWidth() - boxWidth - padding, getHeight() - boxHeight - padding) // Inferior derecha
        };
        
        for (int i = 0; i < numPlayers; i++) {
            drawPlayerBox(g2d, positions[i].x, positions[i].y, boxWidth, boxHeight, i);
        }
    }
    
    private void drawPlayerBox(Graphics2D g2d, int x, int y, int width, int height, int playerIndex) {
        // Contar territorios y tropas del jugador
        int territoryCount = 0;
        int troopCount = 0;
        Color playerColor = gameColors[playerIndex];
        
        for (Territory t : territories.values()) {
            if (t.color.equals(playerColor)) {
                territoryCount++;
                troopCount += t.troops;
            }
        }
        
        // Sombra
        g2d.setColor(new Color(0, 0, 0, 100));
        g2d.fillRoundRect(x + 3, y + 3, width, height, 15, 15);
        
        // Fondo semi-transparente
        g2d.setColor(new Color(44, 62, 80, 230));
        g2d.fillRoundRect(x, y, width, height, 15, 15);
        
        // Borde del color del jugador
        g2d.setColor(playerColor);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRoundRect(x, y, width, height, 15, 15);
        
        // Nombre del jugador
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 16));
        g2d.setColor(Color.WHITE);
        g2d.drawString(playerNames[playerIndex], x + 45, y + 23);
        
        // Cuadrado de color
        g2d.setColor(playerColor);
        g2d.fillRoundRect(x + 10, y + 10, 25, 25, 5, 5);
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(x + 10, y + 10, 25, 25, 5, 5);
        
        // Información de territorios
        g2d.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        g2d.setColor(new Color(189, 195, 199));
        g2d.drawString("Territorios:", x + 10, y + 50);
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 14));
        g2d.setColor(Color.WHITE);
        g2d.drawString(String.valueOf(territoryCount), x + 120, y + 50);
        
        // Información de tropas
        g2d.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        g2d.setColor(new Color(189, 195, 199));
        g2d.drawString("Tropas:", x + 10, y + 70);
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 14));
        g2d.setColor(Color.WHITE);
        g2d.drawString(String.valueOf(troopCount), x + 120, y + 70);
    }
    
    public static void main(String[] args) {
        // Configurar look and feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            // Mostrar menú de inicio
            new MainMenuFrame();
        });
    }
}

// ============== MENÚ DE INICIO ==============
class MainMenuFrame extends JFrame {
    private static final Color OCEAN_COLOR = new Color(41, 128, 185);
    private static final Color PANEL_BG = new Color(44, 62, 80);
    private static final Color ACCENT_COLOR = new Color(46, 204, 113);
    
    public MainMenuFrame() {
        setTitle("🎲 AQUILAE - Risk Imperio Romano");
        setSize(600, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Panel principal con gradiente
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                GradientPaint gradient = new GradientPaint(
                    0, 0, PANEL_BG,
                    0, getHeight(), new Color(52, 73, 94)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        
        // Título del juego
        JLabel titleLabel = new JLabel("AQUILAE", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 72));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridy = 0;
        mainPanel.add(titleLabel, gbc);
        
        JLabel subtitleLabel = new JLabel("Risk Imperio Romano", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.ITALIC, 24));
        subtitleLabel.setForeground(new Color(189, 195, 199));
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 20, 30, 20);
        mainPanel.add(subtitleLabel, gbc);
        
        // Panel de selección de jugadores
        JPanel playerPanel = new JPanel();
        playerPanel.setOpaque(false);
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        
        JLabel playerLabel = new JLabel("Número de Jugadores");
        playerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        playerLabel.setForeground(Color.WHITE);
        playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerPanel.add(playerLabel);
        
        playerPanel.add(Box.createVerticalStrut(15));
        
        // Panel de botones de selección de jugadores
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);
        
        ButtonGroup playerGroup = new ButtonGroup();
        JToggleButton[] playerButtons = new JToggleButton[3];
        
        for (int i = 0; i < 3; i++) {
            final int players = i + 2;
            playerButtons[i] = createPlayerButton(String.valueOf(players), playerGroup);
            buttonPanel.add(playerButtons[i]);
        }
        
        // Seleccionar 2 jugadores por defecto
        playerButtons[0].setSelected(true);
        
        playerPanel.add(buttonPanel);
        
        gbc.gridy = 2;
        gbc.insets = new Insets(15, 20, 30, 20);
        mainPanel.add(playerPanel, gbc);
        
        // Indicador de colores
        JLabel colorLabel = new JLabel("Colores en el juego:", SwingConstants.CENTER);
        colorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        colorLabel.setForeground(new Color(189, 195, 199));
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 20, 10, 20);
        mainPanel.add(colorLabel, gbc);
        
        // Panel de colores
        JPanel colorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        colorPanel.setOpaque(false);
        
        Color[] colors = {
            new Color(46, 204, 113),
            new Color(241, 196, 15),
            new Color(231, 76, 60),
            new Color(155, 89, 182)
        };
        
        JPanel[] colorBoxes = new JPanel[4];
        for (int i = 0; i < 4; i++) {
            colorBoxes[i] = createColorBox(colors[i]);
            colorPanel.add(colorBoxes[i]);
        }
        
        // Actualizar visibilidad de colores según selección
        ActionListener updateColors = e -> {
            int numPlayers = 2;
            for (int i = 0; i < playerButtons.length; i++) {
                if (playerButtons[i].isSelected()) {
                    numPlayers = i + 2;
                    break;
                }
            }
            for (int i = 0; i < colorBoxes.length; i++) {
                colorBoxes[i].setVisible(i < numPlayers);
            }
        };
        
        for (JToggleButton btn : playerButtons) {
            btn.addActionListener(updateColors);
        }
        
        // Inicializar visibilidad
        colorBoxes[2].setVisible(false);
        colorBoxes[3].setVisible(false);
        
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 20, 30, 20);
        mainPanel.add(colorPanel, gbc);
        
        // Botón de jugar
        JButton playButton = new JButton("⚔ JUGAR ⚔");
        playButton.setFont(new Font("Segoe UI", Font.BOLD, 24));
        playButton.setForeground(Color.WHITE);
        playButton.setBackground(ACCENT_COLOR);
        playButton.setFocusPainted(false);
        playButton.setBorderPainted(false);
        playButton.setPreferredSize(new Dimension(250, 60));
        playButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                playButton.setBackground(ACCENT_COLOR.brighter());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                playButton.setBackground(ACCENT_COLOR);
            }
        });
        
        playButton.addActionListener(e -> showPlayerNameDialog(playerButtons));
        
        gbc.gridy = 5;
        gbc.insets = new Insets(20, 20, 20, 20);
        mainPanel.add(playButton, gbc);
        
        add(mainPanel);
        setVisible(true);
        
    }
    public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(() -> {
        new MainMenuFrame().setVisible(true);
    });
}

    private JToggleButton createPlayerButton(String text, ButtonGroup group) {
        JToggleButton button = new JToggleButton(text + " Jugadores");
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(52, 73, 94));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(140, 45));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addItemListener(e -> {
            if (button.isSelected()) {
                button.setBackground(OCEAN_COLOR);
            } else {
                button.setBackground(new Color(52, 73, 94));
            }
        });
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!button.isSelected()) {
                    button.setBackground(new Color(52, 73, 94).brighter());
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (!button.isSelected()) {
                    button.setBackground(new Color(52, 73, 94));
                }
            }
        });
        
        group.add(button);
        return button;
    }
    
    private JPanel createColorBox(Color color) {
        JPanel box = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Sombra
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 10, 10);
                
                // Color
                g2d.setColor(color);
                g2d.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, 10, 10);
                
                // Borde
                g2d.setColor(Color.WHITE);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(0, 0, getWidth() - 4, getHeight() - 4, 10, 10);
            }
        };
        box.setPreferredSize(new Dimension(50, 50));
        box.setOpaque(false);
        return box;
    }
    
    private void showPlayerNameDialog(JToggleButton[] playerButtons) {
        int numPlayers = 2;
        for (int i = 0; i < playerButtons.length; i++) {
            if (playerButtons[i].isSelected()) {
                numPlayers = i + 2;
                break;
            }
        }
        
        JDialog nameDialog = new JDialog(this, "Nombres de Jugadores", true);
        nameDialog.setLayout(new BorderLayout());
        
        JPanel namePanel = new JPanel(new GridBagLayout());
        namePanel.setBackground(PANEL_BG);
        namePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel titleLabel = new JLabel("Introduce los nombres");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        namePanel.add(titleLabel, gbc);
        
        gbc.gridwidth = 1;
        
        Color[] colors = {
            new Color(46, 204, 113),
            new Color(241, 196, 15),
            new Color(231, 76, 60),
            new Color(155, 89, 182)
        };
        
        String[] colorNames = {"Verde", "Amarillo", "Rojo", "Púrpura"};
        
        JTextField[] nameFields = new JTextField[numPlayers];
        
        for (int i = 0; i < numPlayers; i++) {
            // Cuadrado de color
            JPanel colorBox = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(colors[i]);
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                    g2d.setColor(Color.WHITE);
                    g2d.setStroke(new BasicStroke(2));
                    g2d.drawRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                }
                private int i;
                public JPanel setColorIndex(int index) {
                    this.i = index;
                    return this;
                }
            }.setColorIndex(i);
            colorBox.setPreferredSize(new Dimension(40, 40));
            colorBox.setOpaque(false);
            
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            namePanel.add(colorBox, gbc);
            
            nameFields[i] = new JTextField("Jugador " + (i + 1));
            nameFields[i].setFont(new Font("Segoe UI", Font.PLAIN, 14));
            nameFields[i].setPreferredSize(new Dimension(200, 35));
            gbc.gridx = 1;
            namePanel.add(nameFields[i], gbc);
        }
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(PANEL_BG);
        
        JButton okButton = new JButton("✓ Comenzar");
        okButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        okButton.setForeground(Color.WHITE);
        okButton.setBackground(ACCENT_COLOR);
        okButton.setFocusPainted(false);
        okButton.setBorderPainted(false);
        okButton.setPreferredSize(new Dimension(130, 40));
        okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        final int finalNumPlayers = numPlayers;
        okButton.addActionListener(e -> {
            String[] names = new String[finalNumPlayers];
            for (int i = 0; i < finalNumPlayers; i++) {
                names[i] = nameFields[i].getText().trim();
                if (names[i].isEmpty()) {
                    names[i] = "Jugador " + (i + 1);
                }
            }
            nameDialog.dispose();
            startGame(finalNumPlayers, names);
        });
        
        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                okButton.setBackground(ACCENT_COLOR.brighter());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                okButton.setBackground(ACCENT_COLOR);
            }
        });
        
        buttonPanel.add(okButton);
        
        gbc.gridx = 0;
        gbc.gridy = numPlayers + 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        namePanel.add(buttonPanel, gbc);
        
        nameDialog.add(namePanel, BorderLayout.CENTER);
        nameDialog.pack();
        nameDialog.setLocationRelativeTo(this);
        nameDialog.setVisible(true);
    }
    
    private void startGame(int numPlayers, String[] playerNames) {
        // Cerrar menú
        dispose();
        
        // Crear y mostrar el juego
        SwingUtilities.invokeLater(() -> {
            JFrame gameFrame = new JFrame("🎲 RISK - Imperio Romano (" + numPlayers + " Jugadores)");
            TFM_Juan map = new TFM_Juan(numPlayers, playerNames);
            
            gameFrame.setLayout(new BorderLayout());
            gameFrame.add(map, BorderLayout.CENTER);
            gameFrame.setSize(950, 700);
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setLocationRelativeTo(null);
            gameFrame.getContentPane().setBackground(new Color(44, 62, 80));
            gameFrame.setVisible(true);
        });
    }
}

// Renderer personalizado para el ComboBox de colores
class ColorComboRenderer extends DefaultListCellRenderer {
    private int numColors;
    
    public ColorComboRenderer(int numColors) {
        this.numColors = numColors;
    }
    
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, 
            int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(
            list, value, index, isSelected, cellHasFocus);
        
        if (index >= 0 && value != null && index < numColors) {
            Color[] colors = {
                new Color(46, 204, 113), new Color(241, 196, 15), 
                new Color(231, 76, 60), new Color(155, 89, 182)
            };
            
            if (index < colors.length) {
                // Crear un icono cuadrado con el color
                Icon colorIcon = new Icon() {
                    public void paintIcon(Component c, Graphics g, int x, int y) {
                        g.setColor(colors[index]);
                        g.fillRect(x, y, 20, 20);
                        g.setColor(Color.BLACK);
                        g.drawRect(x, y, 20, 20);
                    }
                    public int getIconWidth() { return 20; }
                    public int getIconHeight() { return 20; }
                };
                label.setIcon(colorIcon);
            }
        }
        
        return label;
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
    
    public void drawScaled(Graphics2D g2d, boolean isSelected, double scaleX, double scaleY) {
        Polygon scaledPoly = new Polygon();
        for (int i = 0; i < polygon.npoints; i++) {
            scaledPoly.addPoint(
                (int)(polygon.xpoints[i] * scaleX),
                (int)(polygon.ypoints[i] * scaleY)
            );
        }
        
        // Sombra del territorio
        g2d.setColor(new Color(0, 0, 0, 30));
        Polygon shadowPoly = new Polygon();
        for (int i = 0; i < scaledPoly.npoints; i++) {
            shadowPoly.addPoint(
                scaledPoly.xpoints[i] + (int)(3 * scaleX),
                scaledPoly.ypoints[i] + (int)(3 * scaleY)
            );
        }
        g2d.fill(shadowPoly);
        
        // Color del territorio con efecto hover
        Color drawColor = isHovered ? color.brighter() : color;
        
        // Gradiente sutil en el territorio
        Rectangle bounds = scaledPoly.getBounds();
        GradientPaint territoryGradient = new GradientPaint(
            bounds.x, bounds.y, drawColor,
            bounds.x, bounds.y + bounds.height, new Color(
                Math.max(0, drawColor.getRed() - 30),
                Math.max(0, drawColor.getGreen() - 30),
                Math.max(0, drawColor.getBlue() - 30)
            )
        );
        g2d.setPaint(territoryGradient);
        g2d.fill(scaledPoly);
        
        // Borde del territorio
        g2d.setColor(new Color(44, 62, 80));
        g2d.setStroke(new BasicStroke((float)(3.0 * Math.min(scaleX, scaleY))));
        g2d.draw(scaledPoly);
        
        // Borde brillante si está seleccionado
        if (isSelected) {
            g2d.setColor(new Color(255, 255, 255, 200));
            g2d.setStroke(new BasicStroke((float)(4.0 * Math.min(scaleX, scaleY))));
            g2d.draw(scaledPoly);
        }
        
        // Borde brillante en hover
        if (isHovered) {
            g2d.setColor(new Color(255, 255, 255, 150));
            g2d.setStroke(new BasicStroke((float)(2.5 * Math.min(scaleX, scaleY))));
            g2d.draw(scaledPoly);
        }
        
        // Centro del territorio
        int centerX = bounds.x + bounds.width / 2;
        int centerY = bounds.y + bounds.height / 2;
        
        // Insignia de tropas con estilo moderno
        String troopsText = String.valueOf(troops);
        int fontSize = (int)(11 * Math.min(scaleX, scaleY));
        g2d.setFont(new Font("Segoe UI", Font.BOLD, fontSize));
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(troopsText);
        int textHeight = fm.getHeight();
        
        int padding = (int)(4 * Math.min(scaleX, scaleY));
        int badgeWidth = textWidth + padding * 2;
        int badgeHeight = textHeight + padding;
        
        // Sombra de la insignia
        g2d.setColor(new Color(0, 0, 0, 50));
        g2d.fillRoundRect(
            centerX - badgeWidth / 2 + 2,
            centerY - badgeHeight / 2 + 2,
            badgeWidth, badgeHeight,
            (int)(8 * Math.min(scaleX, scaleY)),
            (int)(8 * Math.min(scaleX, scaleY))
        );
        
        // Fondo de la insignia con gradiente
        GradientPaint badgeGradient = new GradientPaint(
            centerX, centerY - badgeHeight / 2, new Color(255, 255, 255, 240),
            centerX, centerY + badgeHeight / 2, new Color(236, 240, 241)
        );
        g2d.setPaint(badgeGradient);
        g2d.fillRoundRect(
            centerX - badgeWidth / 2,
            centerY - badgeHeight / 2,
            badgeWidth, badgeHeight,
            (int)(8 * Math.min(scaleX, scaleY)),
            (int)(8 * Math.min(scaleX, scaleY))
        );
        
        // Borde de la insignia
        g2d.setColor(new Color(52, 73, 94));
        g2d.setStroke(new BasicStroke((float)(2.0 * Math.min(scaleX, scaleY))));
        g2d.drawRoundRect(
            centerX - badgeWidth / 2,
            centerY - badgeHeight / 2,
            badgeWidth, badgeHeight,
            (int)(8 * Math.min(scaleX, scaleY)),
            (int)(8 * Math.min(scaleX, scaleY))
        );
        
        // Número de tropas
        g2d.setColor(new Color(44, 62, 80));
        g2d.drawString(troopsText, 
            centerX - textWidth / 2, 
            centerY + fm.getAscent() / 2);
    }
}