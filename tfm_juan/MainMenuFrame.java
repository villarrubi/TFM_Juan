package com.mycompany.tfm_juan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Ventana del menú principal del juego - Estilo Imperio Romano
 */
public class MainMenuFrame extends JFrame {
    public MainMenuFrame() {
        setTitle("🏛 AQUILAE - Risk Imperio Romano");
        setSize(700, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel mainPanel = createMainPanel();
        add(mainPanel);
        setVisible(true);
    }
    
    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Fondo estilo templo romano
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(139, 69, 19),
                    0, getHeight(), new Color(101, 67, 33)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Columnas decorativas
                g2d.setColor(new Color(184, 134, 11, 40));
                for (int i = 50; i < getWidth(); i += 120) {
                    g2d.fillRect(i, 0, 25, getHeight());
                }
                
                // Borde dorado
                g2d.setColor(new Color(218, 165, 32));
                g2d.setStroke(new BasicStroke(4));
                g2d.drawRect(10, 10, getWidth() - 20, getHeight() - 20);
                
                // Detalle de águila romana (simulado)
                g2d.setColor(new Color(218, 165, 32, 100));
                g2d.setFont(new Font("Serif", Font.BOLD, 80));
                g2d.drawString("🦅", getWidth() / 2 - 40, getHeight() - 50);
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        
        addTitleLabels(mainPanel, gbc);
        JToggleButton[] playerButtons = addPlayerSelection(mainPanel, gbc);
        JPanel[] colorBoxes = addColorIndicators(mainPanel, gbc, playerButtons);
        addPlayButton(mainPanel, gbc, playerButtons);
        
        return mainPanel;
    }
    
    private void addTitleLabels(JPanel mainPanel, GridBagConstraints gbc) {
        JLabel titleLabel = new JLabel("AQUILAE", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Trajan Pro", Font.BOLD, 82));
        titleLabel.setForeground(new Color(218, 165, 32));
        
        // Efecto de sombra
        titleLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 0, 5, 0),
            BorderFactory.createMatteBorder(0, 0, 3, 0, new Color(184, 134, 11))
        ));
        
        gbc.gridy = 0;
        mainPanel.add(titleLabel, gbc);
        
        JLabel subtitleLabel = new JLabel("RISK IMPERIO ROMANO", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Trajan Pro", Font.ITALIC, 22));
        subtitleLabel.setForeground(new Color(255, 248, 220));
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 20, 35, 20);
        mainPanel.add(subtitleLabel, gbc);
    }
    
    private JToggleButton[] addPlayerSelection(JPanel mainPanel, GridBagConstraints gbc) {
        JPanel playerPanel = new JPanel();
        playerPanel.setOpaque(false);
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        
        JLabel playerLabel = new JLabel("NÚMERO DE JUGADORES");
        playerLabel.setFont(new Font("Trajan Pro", Font.BOLD, 18));
        playerLabel.setForeground(new Color(218, 165, 32));
        playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerPanel.add(playerLabel);
        
        playerPanel.add(Box.createVerticalStrut(18));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 0));
        buttonPanel.setOpaque(false);
        
        ButtonGroup playerGroup = new ButtonGroup();
        JToggleButton[] playerButtons = new JToggleButton[3];
        
        for (int i = 0; i < 3; i++) {
            playerButtons[i] = createRomanPlayerButton(String.valueOf(i + 2), playerGroup);
            buttonPanel.add(playerButtons[i]);
        }
        
        playerButtons[0].setSelected(true);
        playerPanel.add(buttonPanel);
        
        gbc.gridy = 2;
        gbc.insets = new Insets(15, 20, 35, 20);
        mainPanel.add(playerPanel, gbc);
        
        return playerButtons;
    }
    
    private JPanel[] addColorIndicators(JPanel mainPanel, GridBagConstraints gbc, JToggleButton[] playerButtons) {
        JLabel colorLabel = new JLabel("COLORES EN EL JUEGO:", SwingConstants.CENTER);
        colorLabel.setFont(new Font("Trajan Pro", Font.PLAIN, 14));
        colorLabel.setForeground(new Color(255, 248, 220, 200));
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 20, 12, 20);
        mainPanel.add(colorLabel, gbc);
        
        JPanel colorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        colorPanel.setOpaque(false);
        
        JPanel[] colorBoxes = new JPanel[4];
        for (int i = 0; i < 4; i++) {
            colorBoxes[i] = createRomanColorBox(GameConstants.AVAILABLE_COLORS[i]);
            colorPanel.add(colorBoxes[i]);
        }
        
        setupColorVisibility(playerButtons, colorBoxes);
        
        colorBoxes[2].setVisible(false);
        colorBoxes[3].setVisible(false);
        
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 20, 35, 20);
        mainPanel.add(colorPanel, gbc);
        
        return colorBoxes;
    }
    
    private void setupColorVisibility(JToggleButton[] playerButtons, JPanel[] colorBoxes) {
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
    }
    
    private void addPlayButton(JPanel mainPanel, GridBagConstraints gbc, JToggleButton[] playerButtons) {
        JButton playButton = new JButton("⚔ INICIAR CONQUISTA ⚔") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                Color bgColor;
                if (getModel().isPressed()) {
                    bgColor = new Color(139, 0, 0);
                } else if (getModel().isRollover()) {
                    bgColor = new Color(178, 34, 34);
                } else {
                    bgColor = new Color(165, 42, 42);
                }
                
                // Fondo con gradiente
                GradientPaint gradient = new GradientPaint(
                    0, 0, bgColor.brighter(),
                    0, getHeight(), bgColor.darker()
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                
                // Borde dorado triple
                g2d.setColor(new Color(218, 165, 32));
                g2d.setStroke(new BasicStroke(3));
                g2d.drawRoundRect(2, 2, getWidth() - 5, getHeight() - 5, 12, 12);
                
                g2d.setColor(new Color(255, 215, 0));
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.drawRoundRect(5, 5, getWidth() - 11, getHeight() - 11, 10, 10);
                
                // Efecto relieve superior
                g2d.setColor(new Color(255, 215, 0, 120));
                g2d.drawLine(8, 5, getWidth() - 8, 5);
                
                super.paintComponent(g);
            }
        };
        
        playButton.setFont(new Font("Trajan Pro", Font.BOLD, 24));
        playButton.setForeground(new Color(255, 248, 220));
        playButton.setFocusPainted(false);
        playButton.setBorderPainted(false);
        playButton.setContentAreaFilled(false);
        playButton.setPreferredSize(new Dimension(350, 70));
        playButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        playButton.addActionListener(e -> showPlayerNameDialog(playerButtons));
        
        gbc.gridy = 5;
        gbc.insets = new Insets(25, 20, 20, 20);
        mainPanel.add(playButton, gbc);
    }
    
    private JToggleButton createRomanPlayerButton(String text, ButtonGroup group) {
        JToggleButton button = new JToggleButton(text + " Jugadores") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                Color bgColor;
                if (isSelected()) {
                    bgColor = new Color(165, 42, 42);
                } else if (getModel().isRollover()) {
                    bgColor = new Color(139, 90, 43);
                } else {
                    bgColor = new Color(101, 67, 33);
                }
                
                g2d.setColor(bgColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                // Borde dorado
                if (isSelected()) {
                    g2d.setColor(new Color(218, 165, 32));
                    g2d.setStroke(new BasicStroke(3));
                } else {
                    g2d.setColor(new Color(184, 134, 11));
                    g2d.setStroke(new BasicStroke(2));
                }
                g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 10, 10);
                
                super.paintComponent(g);
            }
        };
        
        button.setFont(new Font("Trajan Pro", Font.BOLD, 14));
        button.setForeground(new Color(255, 248, 220));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(160, 50));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        group.add(button);
        return button;
    }
    
    private JPanel createRomanColorBox(Color color) {
        JPanel box = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Sombra
                g2d.setColor(new Color(0, 0, 0, 80));
                g2d.fillRoundRect(4, 4, getWidth() - 8, getHeight() - 8, 12, 12);
                
                // Color principal
                g2d.setColor(color);
                g2d.fillRoundRect(0, 0, getWidth() - 8, getHeight() - 8, 12, 12);
                
                // Borde dorado
                g2d.setColor(new Color(218, 165, 32));
                g2d.setStroke(new BasicStroke(3));
                g2d.drawRoundRect(0, 0, getWidth() - 8, getHeight() - 8, 12, 12);
                
                // Brillo interno
                g2d.setColor(new Color(255, 255, 255, 100));
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.drawRoundRect(3, 3, getWidth() - 14, getHeight() - 14, 10, 10);
            }
        };
        box.setPreferredSize(new Dimension(60, 60));
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
        
        JPanel namePanel = createNamePanel(numPlayers, nameDialog);
        
        nameDialog.add(namePanel, BorderLayout.CENTER);
        nameDialog.pack();
        nameDialog.setLocationRelativeTo(this);
        nameDialog.setVisible(true);
    }
    
    private JPanel createNamePanel(int numPlayers, JDialog nameDialog) {
        JPanel namePanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                
                // Fondo tipo pergamino
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(222, 184, 135),
                    0, getHeight(), new Color(205, 170, 125)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        namePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(139, 69, 19), 5),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel titleLabel = new JLabel("INTRODUCE LOS NOMBRES");
        titleLabel.setFont(new Font("Trajan Pro", Font.BOLD, 22));
        titleLabel.setForeground(new Color(139, 69, 19));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        namePanel.add(titleLabel, gbc);
        
        gbc.gridwidth = 1;
        
        JTextField[] nameFields = new JTextField[numPlayers];
        
        for (int i = 0; i < numPlayers; i++) {
            JPanel colorBox = createPlayerColorBox(i);
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            namePanel.add(colorBox, gbc);
            
            nameFields[i] = new JTextField("Jugador " + (i + 1));
            nameFields[i].setFont(new Font("Serif", Font.PLAIN, 15));
            nameFields[i].setPreferredSize(new Dimension(220, 40));
            nameFields[i].setBackground(new Color(245, 222, 179));
            nameFields[i].setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(139, 69, 19), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            gbc.gridx = 1;
            namePanel.add(nameFields[i], gbc);
        }
        
        addStartButton(namePanel, gbc, numPlayers, nameFields, nameDialog);
        
        return namePanel;
    }
    
    private JPanel createPlayerColorBox(final int colorIndex) {
        JPanel colorBox = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2d.setColor(GameConstants.AVAILABLE_COLORS[colorIndex]);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                g2d.setColor(new Color(218, 165, 32));
                g2d.setStroke(new BasicStroke(3));
                g2d.drawRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            }
        };
        colorBox.setPreferredSize(new Dimension(45, 45));
        colorBox.setOpaque(false);
        return colorBox;
    }
    
    private void addStartButton(JPanel namePanel, GridBagConstraints gbc, int numPlayers, 
                                JTextField[] nameFields, JDialog nameDialog) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);
        
        JButton okButton = new JButton("⚔ COMENZAR ⚔") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                Color bgColor = getModel().isRollover() 
                    ? new Color(46, 125, 50) 
                    : new Color(34, 139, 34);
                
                g2d.setColor(bgColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                g2d.setColor(new Color(218, 165, 32));
                g2d.setStroke(new BasicStroke(2.5f));
                g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 10, 10);
                
                super.paintComponent(g);
            }
        };
        
        okButton.setFont(new Font("Trajan Pro", Font.BOLD, 15));
        okButton.setForeground(new Color(255, 248, 220));
        okButton.setFocusPainted(false);
        okButton.setBorderPainted(false);
        okButton.setContentAreaFilled(false);
        okButton.setPreferredSize(new Dimension(200, 50));
        okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        okButton.addActionListener(e -> {
            String[] names = new String[numPlayers];
            for (int i = 0; i < numPlayers; i++) {
                names[i] = nameFields[i].getText().trim();
                if (names[i].isEmpty()) {
                    names[i] = "Jugador " + (i + 1);
                }
            }
            nameDialog.dispose();
            startGame(numPlayers, names);
        });
        
        buttonPanel.add(okButton);
        
        gbc.gridx = 0;
        gbc.gridy = numPlayers + 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(25, 12, 12, 12);
        namePanel.add(buttonPanel, gbc);
    }
    
    private void startGame(int numPlayers, String[] playerNames) {
        dispose();
        
        SwingUtilities.invokeLater(() -> {
            JFrame gameFrame = new JFrame("🏛 AQUILAE - Imperio Romano (" + numPlayers + " Jugadores)");
            TFM_Juan map = new TFM_Juan(numPlayers, playerNames);
            
            gameFrame.setLayout(new BorderLayout());
            gameFrame.add(map, BorderLayout.CENTER);
            gameFrame.setSize(950, 700);
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setLocationRelativeTo(null);
            gameFrame.getContentPane().setBackground(new Color(41, 128, 185));
            gameFrame.setVisible(true);
        });
    }
}