package com.mycompany.tfm_juan;

import javax.swing.*;

/**
 * Clase principal - Punto de entrada del programa
 */
public class Main {
    public static void main(String[] args) {
        // Configurar look and feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Iniciar la aplicación en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new MainMenuFrame();
        });
    }
}