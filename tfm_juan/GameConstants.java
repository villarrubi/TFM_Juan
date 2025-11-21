package com.mycompany.tfm_juan;

import java.awt.Color;

/**
 * Clase que contiene todas las constantes del juego. Colores, coordenadas de los territorios
 */
public class GameConstants {
    
    // Dimensiones base para el escalado
    public static final double BASE_WIDTH = 600.0;
    public static final double BASE_HEIGHT = 550.0;
    
    // Paleta de colores moderna y vibrante
    public static final Color[] AVAILABLE_COLORS = {
        new Color(46, 204, 113),   // Verde esmeralda
        new Color(241, 196, 15),   // Amarillo dorado
        new Color(231, 76, 60),    // Rojo coral
        new Color(155, 89, 182)    // PÃºrpura
    };
    
    public static final String[] COLOR_NAMES = {
        "Verde", "Amarillo", "Rojo", "PÃºrpura"
    };
    
    // Colores del tema
    public static final Color OCEAN_COLOR = new Color(41, 128, 185);
    public static final Color OCEAN_COLOR_LIGHT = new Color(52, 152, 219);
    public static final Color PANEL_BG = new Color(44, 62, 80);
    public static final Color PANEL_BG_LIGHT = new Color(52, 73, 94);
    public static final Color ACCENT_COLOR = new Color(46, 204, 113);
    public static final Color TEXT_SECONDARY = new Color(189, 195, 199);
    
    // Nombres de territorios
    public static final String[] TERRITORY_NAMES = {
        "Lusitania", "Tarraconensis", "Baetica", "Mauritania", "Ãfrica", 
        "Cirenaica", "Egipto", "Arabia", "Judea", "Siria", "Asia", "Ponto", 
        "Armenia", "Grecia", "Macedonia", "Tracia", "Dacia", "Moesia", 
        "Dalmacia", "Panonia", "Magna grecia", "Roma", "Galia Cisalpina", "Raetia",
        "Germania", "Narbonense", "Galia", "Britania"
    };
    
    // Coordenadas X de los territorios
    public static final int[][] TERRITORY_X_COORDS = {
        {20, 50, 50, 40, 20}, // Lusitania
        {20, 140, 80, 50, 50, 20}, // Tarraconensis
        {50, 80, 40, 30}, // Baetica
        {20, 40, 80, 160, 160, 20}, // Mauritania
        {160, 210, 210, 290, 290, 160}, // Ãfrica
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
    
    // Coordenadas Y de los territorios
    public static final int[][] TERRITORY_Y_COORDS = {
        {360, 360, 410, 440, 440}, // Lusitania
        {310, 310, 440, 410, 380, 360}, // Tarraconensis
        {410, 440, 460, 440}, // Baetica
        {490, 460, 470, 430, 550, 550}, // Mauritania
        {430, 430, 480, 530, 550, 550}, // Ãfrica
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
    
    private GameConstants() {
        // Constructor privado para evitar instanciaciÃ³n
    }
}