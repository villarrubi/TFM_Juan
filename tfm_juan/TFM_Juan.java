package com.mycompany.tfm_juan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Clase principal del panel del mapa de juego - OPTIMIZADO
 */
public class TFM_Juan extends JPanel {
    private GameState gameState;
    private MapRenderer mapRenderer;
    private PlayerInfoRenderer playerInfoRenderer;
    private DialogManager dialogManager;
    private InputHandler inputHandler;
    
    private Territory selectedTerritory;
    private JLabel turnLabel;
    private JButton nextTurnButton;
    private JButton attackButton;
    
    public TFM_Juan(int numPlayers, String[] playerNames) {
        // Inicializar componentes
        this.gameState = new GameState(numPlayers, playerNames);
        this.mapRenderer = new MapRenderer();
        this.playerInfoRenderer = new PlayerInfoRenderer();
        this.dialogManager = new DialogManager(this, gameState, this::repaint);
        this.inputHandler = new InputHandler(this, gameState, dialogManager);
        
        setupUI();
        setupListeners();
    }
    
    private void setupUI() {
        setPreferredSize(new Dimension(950, 700));
        setBackground(GameConstants.OCEAN_COLOR);
        setLayout(new BorderLayout());
        
        add(createTurnPanel(), BorderLayout.NORTH);
    }
    
    private JPanel createTurnPanel() {
        JPanel turnPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(139, 69, 19),
                    0, getHeight(), new Color(101, 67, 33)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.setColor(new Color(218, 165, 32));
                g2d.fillRect(0, 0, getWidth(), 3);
                g2d.fillRect(0, getHeight() - 3, getWidth(), 3);

                g2d.setColor(new Color(184, 134, 11, 60));
                for (int i = 0; i < getWidth(); i += 40) {
                    g2d.fillRect(i, 5, 2, getHeight() - 10);
                }
            }
        };
        turnPanel.setPreferredSize(new Dimension(950, 90));
        turnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 20));

        JPanel currentTurnPanel = createCurrentTurnDisplay();
        
        attackButton = UIComponentFactory.createRomanButton("⚔ ATACAR ⚔");
        attackButton.addActionListener(e -> dialogManager.showAttackSelectionDialog());

        nextTurnButton = UIComponentFactory.createRomanButton("➤ SIGUIENTE TURNO ➤");
        nextTurnButton.addActionListener(e -> nextTurn());

        turnPanel.add(currentTurnPanel);
        turnPanel.add(attackButton);
        turnPanel.add(nextTurnButton);

        return turnPanel;
    }
    
    private JPanel createCurrentTurnDisplay() {
        JPanel currentTurnPanel = new JPanel();
        currentTurnPanel.setOpaque(false);
        currentTurnPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 0));

        JLabel turnTextLabel = new JLabel("TURNO DE:");
        turnTextLabel.setFont(new Font("Trajan Pro", Font.BOLD, 16));
        turnTextLabel.setForeground(new Color(218, 165, 32));
        currentTurnPanel.add(turnTextLabel);

        turnLabel = new JLabel(gameState.getCurrentPlayer().getName().toUpperCase());
        turnLabel.setFont(new Font("Trajan Pro", Font.BOLD, 18));
        turnLabel.setForeground(new Color(255, 248, 220));
        turnLabel.setOpaque(true);
        turnLabel.setBackground(gameState.getCurrentPlayer().getColor());
        turnLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(218, 165, 32), 3),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        currentTurnPanel.add(turnLabel);
        
        return currentTurnPanel;
    }
    
    private void setupListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                inputHandler.handleClick(e.getPoint());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                // Limpiar hover cuando el mouse sale del panel
                inputHandler.clearHover();
            }
        });
        
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (inputHandler.handleHover(e.getPoint())) {
                    repaint();
                }
            }
        });
        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                mapRenderer.invalidateCache();
                // Invalidar cache de todos los territorios al redimensionar
                for (Territory t : gameState.getTerritories().values()) {
                    t.invalidateCache();
                }
                // Invalidar cache de territorios decorativos
                for (DecorativeTerritory dt : gameState.getDecorativeTerritories()) {
                    dt.invalidateCache();
                }
                repaint();
            }
        });
    }
    
    private void nextTurn() {
        gameState.nextTurn();
        updateTurnDisplay();
        repaint();
    }
    
    private void updateTurnDisplay() {
        Player currentPlayer = gameState.getCurrentPlayer();
        turnLabel.setText(currentPlayer.getName().toUpperCase());
        turnLabel.setBackground(currentPlayer.getColor());
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Renderizar mapa (ahora con cache eficiente)
        mapRenderer.render(g2d, gameState, getWidth(), getHeight(), selectedTerritory);
        
        // Renderizar información de jugadores
        playerInfoRenderer.render(g2d, gameState, getWidth(), getHeight(), 
            gameState.getPlayers().length > 0 ? 
            java.util.Arrays.asList(gameState.getPlayers()).indexOf(gameState.getCurrentPlayer()) : 0);
    }
    
    // Getters para InputHandler
    public Territory getSelectedTerritory() {
        return selectedTerritory;
    }
    
    public void setSelectedTerritory(Territory territory) {
        this.selectedTerritory = territory;
    }
}