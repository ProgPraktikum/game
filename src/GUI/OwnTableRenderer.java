package GUI;

import javax.swing.*;
import java.awt.*;


/**
 * Diese Klasse wird für die Visuelle Gestaltung des Spielfedes benötigt
 */
public class OwnTableRenderer extends javax.swing.table.DefaultTableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        Component comp = super.getTableCellRendererComponent(table, value,
                isSelected, hasFocus, row, column);
        comp.setVisible(false);
        try {
			/*
			 * ist der Wert ein Int dann wird mittels des Wertes geprueft welche Farbe genutz wird
			 */
            if (value instanceof Integer) {
                if (value.equals(0)) {
                    comp.setBackground(Color.BLUE);
                    comp.setForeground(Color.BLUE);
                } else if (value.equals(1)) {
                    comp.setBackground(Color.DARK_GRAY);
                    comp.setForeground(Color.DARK_GRAY);
                } else if (value.equals(2)) {
                    comp.setBackground(Color.RED);
                    comp.setForeground(Color.RED);

                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return comp;
    }
}
