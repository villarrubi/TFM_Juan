package com.mycompany.tfm_juan;

import javax.swing.*;
import java.awt.*;

/**
 * Renderer personalizado para el ComboBox de colores
 */
public class ColorComboRenderer extends DefaultListCellRenderer {
    private Color[] colors;
    
    // Constructor que acepta el array de colores
    public ColorComboRenderer(Color[] colors) {
        this.colors = colors;
    }
    
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, 
            int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(
            list, value, index, isSelected, cellHasFocus);
        
        if (index >= 0 && value != null && index < colors.length) {
            Icon colorIcon = createColorIcon(colors[index]);
            label.setIcon(colorIcon);
        }
        
        return label;
    }
    
    private Icon createColorIcon(final Color color) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                g.setColor(color);
                g.fillRect(x, y, 20, 20);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, 20, 20);
            }
            
            @Override
            public int getIconWidth() { 
                return 20; 
            }
            
            @Override
            public int getIconHeight() { 
                return 20; 
            }
        };
    }
}