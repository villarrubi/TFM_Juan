package com.mycompany.tfm_juan;

import javax.swing.*;
import java.awt.*;

public class UIComponentFactory {
    
    public static JButton createRomanButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2d.setColor(new Color(139, 0, 0));
                } else if (getModel().isRollover()) {
                    g2d.setColor(new Color(178, 34, 34));
                } else {
                    g2d.setColor(new Color(165, 42, 42));
                }
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);

                g2d.setColor(new Color(218, 165, 32));
                g2d.setStroke(new BasicStroke(2.5f));
                g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 8, 8);

                g2d.setColor(new Color(255, 215, 0, 100));
                g2d.drawLine(5, 3, getWidth() - 5, 3);

                super.paintComponent(g);
            }
        };

        button.setFont(new Font("Trajan Pro", Font.BOLD, 14));
        button.setForeground(new Color(255, 248, 220));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(220, 50));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }
    
    public static JButton createRomanDialogButton(String text, Color baseColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color currentColor = baseColor;
                if (getModel().isPressed()) {
                    currentColor = baseColor.darker();
                } else if (getModel().isRollover()) {
                    currentColor = baseColor.brighter();
                }

                g2d.setColor(currentColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);

                g2d.setColor(new Color(218, 165, 32));
                g2d.setStroke(new BasicStroke(2f));
                g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 8, 8);

                super.paintComponent(g);
            }
        };

        button.setFont(new Font("Trajan Pro", Font.BOLD, 12));
        button.setForeground(new Color(255, 248, 220));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(190, 45));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }
    
    public static JPanel createParchmentPanel() {
        return new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(222, 184, 135),
                    0, getHeight(), new Color(205, 170, 125)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
    }
}