package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


/**
 * Diese Klasse wird fuer die Visuelle Gestaltung des Spielfedes benoetigt
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
			 * 0 = Wasser, 1 = Schiff, 2 = versenkt, 3 = Schiff, 4 = endpunkt beim setzen,
			 * 9 = grau fuer unbeschossene Felder, 7 = beschossenes Wasserfeld
			 */
            if (value instanceof String) {
                if (value.equals("X")) {
                    comp.setBackground(Color.BLUE);
                    comp.setForeground(Color.GRAY);
                }
            }
            if (value instanceof Integer) {
                if (value.equals(0)) {
                    comp.setBackground(Color.BLUE);
                    comp.setForeground(Color.BLUE);
                } else if (value.equals(3)) {
                    comp.setBackground(Color.DARK_GRAY);
                    comp.setForeground(Color.DARK_GRAY);
                } else if (value.equals(2)) {
                    comp.setBackground(Color.RED);
                    comp.setForeground(Color.RED);
                } else if (value.equals(1)) {
                    comp.setBackground(Color.YELLOW);
                    comp.setForeground(Color.YELLOW);
                } else if (value.equals(4)) {
                    comp.setBackground(Color.GREEN);
                    comp.setForeground(Color.GREEN);
                } else if (value.equals(9)) {
                    comp.setBackground(Color.LIGHT_GRAY);
                    comp.setForeground(Color.LIGHT_GRAY);
                } else if (value.equals(7)) {
                    comp.setBackground(Color.CYAN);
                    comp.setForeground(Color.CYAN);
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return comp;
    }
}
