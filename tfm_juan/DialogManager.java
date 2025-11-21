package com.mycompany.tfm_juan;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona todos los diálogos del juego
 */
public class DialogManager {
    private Component parentComponent;
    private GameState gameState;
    private Runnable repaintCallback;
    
    public DialogManager(Component parentComponent, GameState gameState, Runnable repaintCallback) {
        this.parentComponent = parentComponent;
        this.gameState = gameState;
        this.repaintCallback = repaintCallback;
    }
    
    public void showTerritoryEditor(Territory territory) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parentComponent), 
            "Configurar Territorio", true);
        dialog.setLayout(new BorderLayout());
        
        JPanel mainPanel = createEditorPanel(territory, dialog);
        
        dialog.add(mainPanel, BorderLayout.CENTER);
        dialog.pack();
        dialog.setLocationRelativeTo(parentComponent);
        dialog.setVisible(true);
    }
    
    private JPanel createEditorPanel(Territory territory, JDialog dialog) {
        JPanel mainPanel = UIComponentFactory.createParchmentPanel();
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(139, 69, 19), 4),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        addEditorTitle(mainPanel, gbc, territory);
        JComboBox<String> playerCombo = addPlayerSelector(mainPanel, gbc, territory);
        JSpinner troopsSpinner = addTroopsSelector(mainPanel, gbc, territory);
        addEditorButtons(mainPanel, gbc, territory, playerCombo, troopsSpinner, dialog);
        
        return mainPanel;
    }
    
    private void addEditorTitle(JPanel panel, GridBagConstraints gbc, Territory territory) {
        JLabel titleLabel = new JLabel(territory.getName().toUpperCase());
        titleLabel.setFont(new Font("Trajan Pro", Font.BOLD, 26));
        titleLabel.setForeground(new Color(139, 69, 19));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(218, 165, 32)),
            BorderFactory.createEmptyBorder(5, 0, 10, 0)
        ));
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        gbc.gridwidth = 1;
    }
    
    private JComboBox<String> addPlayerSelector(JPanel panel, GridBagConstraints gbc, Territory territory) {
        JLabel playerLabel = new JLabel("Propietario:");
        playerLabel.setFont(new Font("Trajan Pro", Font.BOLD, 14));
        playerLabel.setForeground(new Color(101, 67, 33));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(playerLabel, gbc);
        
        Player[] players = gameState.getPlayers();
        String[] playerNames = new String[gameState.getNumPlayers()];
        for (int i = 0; i < gameState.getNumPlayers(); i++) {
            playerNames[i] = players[i].getName();
        }
        
        JComboBox<String> playerCombo = new JComboBox<>(playerNames);
        playerCombo.setRenderer(new PlayerComboRenderer(players));
        playerCombo.setFont(new Font("Serif", Font.PLAIN, 14));
        playerCombo.setBackground(new Color(245, 222, 179));
        
        int currentPlayerIdx = territory.getOwner() != null ? territory.getOwner().getId() : 0;
        playerCombo.setSelectedIndex(currentPlayerIdx);
        
        gbc.gridx = 1;
        panel.add(playerCombo, gbc);
        
        return playerCombo;
    }
    
    private JSpinner addTroopsSelector(JPanel panel, GridBagConstraints gbc, Territory territory) {
        JLabel troopsLabel = new JLabel("Número de Tropas:");
        troopsLabel.setFont(new Font("Trajan Pro", Font.BOLD, 14));
        troopsLabel.setForeground(new Color(101, 67, 33));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(troopsLabel, gbc);
        
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(territory.getTroops(), 1, 30, 1);
        JSpinner troopsSpinner = new JSpinner(spinnerModel);
        troopsSpinner.setFont(new Font("Serif", Font.BOLD, 16));
        troopsSpinner.setBackground(new Color(245, 222, 179));
        ((JSpinner.DefaultEditor) troopsSpinner.getEditor()).getTextField()
            .setHorizontalAlignment(JTextField.CENTER);
        ((JSpinner.DefaultEditor) troopsSpinner.getEditor()).getTextField()
            .setBackground(new Color(245, 222, 179));
        gbc.gridx = 1;
        panel.add(troopsSpinner, gbc);
        
        return troopsSpinner;
    }
    
    private void addEditorButtons(JPanel panel, GridBagConstraints gbc, Territory territory,
                                  JComboBox<String> playerCombo, JSpinner troopsSpinner, JDialog dialog) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);
        
        JButton okButton = UIComponentFactory.createRomanDialogButton("⚔ ACEPTAR", new Color(34, 139, 34));
        JButton cancelButton = UIComponentFactory.createRomanDialogButton("✖ CANCELAR", new Color(178, 34, 34));
        
        okButton.addActionListener(e -> {
            int selectedPlayerIndex = playerCombo.getSelectedIndex();
            Player newOwner = gameState.getPlayers()[selectedPlayerIndex];
            
            if (territory.getOwner() != null) {
                territory.getOwner().removeTerritory(territory);
            }
            territory.setOwner(newOwner);
            territory.setColor(newOwner.getColor());
            newOwner.addTerritory(territory);
            
            territory.setTroops((Integer) troopsSpinner.getValue());
            dialog.dispose();
            repaintCallback.run();
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        panel.add(buttonPanel, gbc);
    }
    
    public void showAttackSelectionDialog() {
        JDialog selectionDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parentComponent), 
            "Seleccionar Territorios", true);
        selectionDialog.setLayout(new BorderLayout());
        
        JPanel mainPanel = createAttackSelectionPanel(selectionDialog);
        
        selectionDialog.add(mainPanel, BorderLayout.CENTER);
        selectionDialog.pack();
        selectionDialog.setLocationRelativeTo(parentComponent);
        selectionDialog.setVisible(true);
    }
    
    private JPanel createAttackSelectionPanel(JDialog dialog) {
        JPanel mainPanel = UIComponentFactory.createParchmentPanel();
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(139, 69, 19), 5),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel titleLabel = new JLabel("SELECCIONAR TERRITORIOS PARA ATACAR");
        titleLabel.setFont(new Font("Trajan Pro", Font.BOLD, 20));
        titleLabel.setForeground(new Color(139, 69, 19));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);
        
        gbc.gridwidth = 1;
        
        List<Territory> myTerritories = gameState.getPlayerTerritories(gameState.getCurrentPlayer());
        List<Territory> enemyTerritories = gameState.getEnemyTerritories(gameState.getCurrentPlayer());
        
        JLabel fromLabel = new JLabel("Atacar desde:");
        fromLabel.setFont(new Font("Trajan Pro", Font.BOLD, 14));
        fromLabel.setForeground(new Color(101, 67, 33));
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(fromLabel, gbc);
        
        JComboBox<String> fromCombo = new JComboBox<>();
        for (Territory t : myTerritories) {
            fromCombo.addItem(t.getName() + " (" + t.getTroops() + " tropas)");
        }
        fromCombo.setFont(new Font("Serif", Font.PLAIN, 14));
        fromCombo.setBackground(new Color(245, 222, 179));
        gbc.gridx = 1;
        mainPanel.add(fromCombo, gbc);
        
        JLabel toLabel = new JLabel("Atacar a:");
        toLabel.setFont(new Font("Trajan Pro", Font.BOLD, 14));
        toLabel.setForeground(new Color(101, 67, 33));
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(toLabel, gbc);
        
        JComboBox<String> toCombo = new JComboBox<>();
        for (Territory t : enemyTerritories) {
            toCombo.addItem(t.getName() + " (" + t.getTroops() + " tropas)");
        }
        toCombo.setFont(new Font("Serif", Font.PLAIN, 14));
        toCombo.setBackground(new Color(245, 222, 179));
        gbc.gridx = 1;
        mainPanel.add(toCombo, gbc);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);
        
        JButton cancelButton = UIComponentFactory.createRomanDialogButton("✖ CANCELAR", new Color(178, 34, 34));
        cancelButton.addActionListener(e -> dialog.dispose());
        
        JButton attackButtonDialog = UIComponentFactory.createRomanDialogButton("⚔ ATACAR", new Color(165, 42, 42));
        attackButtonDialog.addActionListener(e -> {
            if (fromCombo.getSelectedIndex() >= 0 && toCombo.getSelectedIndex() >= 0) {
                gameState.setAttackFrom(myTerritories.get(fromCombo.getSelectedIndex()));
                gameState.setAttackTo(enemyTerritories.get(toCombo.getSelectedIndex()));
                dialog.dispose();
                showAttackQuestionDialog();
            }
        });
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(attackButtonDialog);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(25, 15, 15, 15);
        mainPanel.add(buttonPanel, gbc);
        
        return mainPanel;
    }
    
    public void showAttackQuestionDialog() {
        QuestionManager.Question question = QuestionManager.getRandomQuestion();

        if (question == null) {
            showFinalResultDialog();
            return;
        }

        JDialog questionDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parentComponent), 
            "¡Combate!", true);
        questionDialog.setLayout(new BorderLayout());

        JPanel mainPanel = createQuestionPanel(questionDialog, question);

        questionDialog.add(mainPanel, BorderLayout.CENTER);
        questionDialog.pack();
        questionDialog.setLocationRelativeTo(parentComponent);
        questionDialog.setVisible(true);
    }
    
    // ESTA ES LA CONTINUACIÓN - Agrégala después del comentario "// Continúa en la siguiente parte..."
// en la parte 1 del DialogManager, reemplazando el último "}"

    private JPanel createQuestionPanel(JDialog dialog, QuestionManager.Question question) {
        JPanel mainPanel = UIComponentFactory.createParchmentPanel();
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(139, 69, 19), 5),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("¡COMBATE!");
        titleLabel.setFont(new Font("Trajan Pro", Font.BOLD, 26));
        titleLabel.setForeground(new Color(165, 42, 42));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        JLabel combatInfo = new JLabel("<html><b>Atacante:</b> " + gameState.getAttackFrom().getName() + 
            " (" + gameState.getAttackFrom().getTroops() + " tropas)<br>" +
            "<b>Defensor:</b> " + gameState.getAttackTo().getName() + 
            " (" + gameState.getAttackTo().getTroops() + " tropas)</html>");
        combatInfo.setFont(new Font("Serif", Font.PLAIN, 14));
        combatInfo.setForeground(new Color(101, 67, 33));
        gbc.gridy = 1;
        mainPanel.add(combatInfo, gbc);

        JPanel questionPanel = new JPanel(new BorderLayout());
        questionPanel.setBackground(new Color(245, 222, 179));
        questionPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(139, 69, 19), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel questionLabel = new JLabel("<html><b>Pregunta:</b><br><br>" + 
            question.getQuestion() + "</html>");
        questionLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        questionLabel.setForeground(new Color(101, 67, 33));
        questionPanel.add(questionLabel, BorderLayout.CENTER);

        gbc.gridy = 2;
        gbc.gridwidth = 2;
        mainPanel.add(questionPanel, gbc);

        JPanel answerPanel = new JPanel(new BorderLayout());
        answerPanel.setBackground(new Color(212, 237, 218));
        answerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(46, 125, 50), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        answerPanel.setVisible(false);

        JLabel answerLabel = new JLabel("<html><b>Respuesta correcta:</b> " + 
            question.getAnswer() + "</html>");
        answerLabel.setFont(new Font("Serif", Font.PLAIN, 15));
        answerLabel.setForeground(new Color(27, 94, 32));
        answerPanel.add(answerLabel, BorderLayout.CENTER);

        gbc.gridy = 3;
        mainPanel.add(answerPanel, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);

        JButton showAnswerButton = UIComponentFactory.createRomanDialogButton("👁 VER RESPUESTA", new Color(41, 128, 185));
        showAnswerButton.addActionListener(e -> {
            answerPanel.setVisible(true);
            showAnswerButton.setVisible(false);
            dialog.pack();
        });

        JButton attackWinsButton = UIComponentFactory.createRomanDialogButton("✓ GANA ATAQUE", new Color(46, 125, 50));
        attackWinsButton.setVisible(false);
        attackWinsButton.addActionListener(e -> {
            CombatManager.executeAttackVictory(gameState.getAttackFrom(), gameState.getAttackTo(), 
                (TFM_Juan)parentComponent);
            dialog.dispose();
            gameState.clearAttack();
        });

        JButton defenseWinsButton = UIComponentFactory.createRomanDialogButton("✓ GANA DEFENSA", new Color(178, 34, 34));
        defenseWinsButton.setVisible(false);
        defenseWinsButton.addActionListener(e -> {
            CombatManager.executeDefenseVictory(gameState.getAttackFrom(), (TFM_Juan)parentComponent);
            dialog.dispose();
            gameState.clearAttack();
        });

        showAnswerButton.addActionListener(e2 -> {
            attackWinsButton.setVisible(true);
            defenseWinsButton.setVisible(true);
            dialog.pack();
        });

        buttonPanel.add(showAnswerButton);
        buttonPanel.add(attackWinsButton);
        buttonPanel.add(defenseWinsButton);

        gbc.gridy = 4;
        gbc.insets = new Insets(25, 12, 12, 12);
        mainPanel.add(buttonPanel, gbc);

        return mainPanel;
    }
    
    public void showFinalResultDialog() {
        List<Player> ranking = new ArrayList<>();
        for (Player player : gameState.getPlayers()) {
            ranking.add(player);
        }

        ranking.sort((p1, p2) -> {
            int territoriesCompare = Integer.compare(p2.getTerritoryCount(), p1.getTerritoryCount());
            if (territoriesCompare != 0) {
                return territoriesCompare;
            }
            return Integer.compare(p2.getTotalTroops(), p1.getTotalTroops());
        });

        JDialog resultDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parentComponent), 
            "Fin del Juego - Ranking Final", true);
        resultDialog.setLayout(new BorderLayout());

        JPanel mainPanel = createRankingPanel(ranking);

        resultDialog.add(mainPanel, BorderLayout.CENTER);
        resultDialog.pack();
        resultDialog.setLocationRelativeTo(parentComponent);
        resultDialog.setVisible(true);
    }
    
    private JPanel createRankingPanel(List<Player> ranking) {
        JPanel mainPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(255, 215, 0),
                    0, getHeight(), new Color(218, 165, 32)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(139, 69, 19), 5),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("¡SE ACABARON LAS PREGUNTAS!");
        titleLabel.setFont(new Font("Trajan Pro", Font.BOLD, 26));
        titleLabel.setForeground(new Color(139, 69, 19));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        mainPanel.add(titleLabel, gbc);

        JLabel subtitleLabel = new JLabel("RANKING FINAL");
        subtitleLabel.setFont(new Font("Trajan Pro", Font.BOLD, 20));
        subtitleLabel.setForeground(new Color(165, 42, 42));
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 15, 20, 15);
        mainPanel.add(subtitleLabel, gbc);

        gbc.insets = new Insets(8, 15, 8, 15);
        for (int i = 0; i < ranking.size(); i++) {
            JPanel playerPanel = createPlayerRankPanel(ranking.get(i), i + 1);
            gbc.gridy = i + 2;
            mainPanel.add(playerPanel, gbc);
        }

        JButton closeButton = UIComponentFactory.createRomanDialogButton("⚔ ACEPTAR ⚔", new Color(165, 42, 42));
        closeButton.addActionListener(e -> 
            SwingUtilities.getWindowAncestor(mainPanel).dispose());
        gbc.gridy = ranking.size() + 2;
        gbc.insets = new Insets(20, 15, 10, 15);
        mainPanel.add(closeButton, gbc);

        return mainPanel;
    }
    
    private JPanel createPlayerRankPanel(final Player player, final int position) {
        JPanel playerPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color bgColor;
                if (position == 1) {
                    bgColor = new Color(255, 215, 0, 100);
                } else if (position == 2) {
                    bgColor = new Color(192, 192, 192, 100);
                } else if (position == 3) {
                    bgColor = new Color(205, 127, 50, 100);
                } else {
                    bgColor = new Color(222, 184, 135, 80);
                }

                g2d.setColor(bgColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);

                g2d.setColor(new Color(139, 69, 19));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
            }
        };
        playerPanel.setOpaque(false);
        playerPanel.setPreferredSize(new Dimension(400, 60));

        GridBagConstraints playerGbc = new GridBagConstraints();
        playerGbc.insets = new Insets(5, 10, 5, 10);
        playerGbc.anchor = GridBagConstraints.WEST;

        JLabel positionLabel = new JLabel(position + "º");
        positionLabel.setFont(new Font("Trajan Pro", Font.BOLD, 24));
        positionLabel.setForeground(new Color(139, 69, 19));
        positionLabel.setPreferredSize(new Dimension(50, 40));
        playerGbc.gridx = 0;
        playerGbc.gridy = 0;
        playerGbc.gridheight = 2;
        playerPanel.add(positionLabel, playerGbc);

        final Color playerColor = player.getColor();
        JPanel colorBox = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(playerColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);

                g2d.setColor(new Color(218, 165, 32));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
            }
        };
        colorBox.setPreferredSize(new Dimension(40, 40));
        colorBox.setOpaque(false);
        playerGbc.gridx = 1;
        playerPanel.add(colorBox, playerGbc);

        JLabel nameLabel = new JLabel(player.getName().toUpperCase());
        nameLabel.setFont(new Font("Trajan Pro", Font.BOLD, 16));
        nameLabel.setForeground(new Color(101, 67, 33));
        playerGbc.gridx = 2;
        playerGbc.gridy = 0;
        playerGbc.gridheight = 1;
        playerGbc.insets = new Insets(8, 10, 2, 10);
        playerPanel.add(nameLabel, playerGbc);

        JLabel statsLabel = new JLabel("Territorios: " + player.getTerritoryCount() + 
                                       "  •  Tropas: " + player.getTotalTroops());
        statsLabel.setFont(new Font("Serif", Font.PLAIN, 13));
        statsLabel.setForeground(new Color(101, 67, 33));
        playerGbc.gridy = 1;
        playerGbc.insets = new Insets(2, 10, 8, 10);
        playerPanel.add(statsLabel, playerGbc);

        return playerPanel;
    }
}