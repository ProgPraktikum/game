package gui;

import data.DataContainer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 * Diese Klasse erzeugt ein Objekt das ein Spielfeld enthaelt
 * Verwendet wird hierfuehr eine JTable
 *
 */
public class TableView extends JTable {

    public TableView(){

        // Das array names wird ueber die Breite des Spielfeldes initialisiert
        Object[] name = new Integer[DataContainer.getGameboardWidth()];

        // Das 2D Array wird ueber Spielfeldbreite und Hoehe initialisiert
        Object[][] data = new Integer[DataContainer.getGameboardHeight()][DataContainer.getGameboardWidth()];

        // Array name wird mit 1 - breite numeriert
        for(int i = 0; i < DataContainer.getGameboardWidth(); i++){
            name[i] = i;
        }
        // Array data wird komplett mit 0 befuellt. 0 steht hierbei fuer den Wert fuer WASSER
        for(int i = 0; i < DataContainer.getGameboardHeight(); i++){
            for(int j = 0; j < DataContainer.getGameboardWidth(); j++){
                data[i][j] = 0;
            }
        }

        /**
         * Hier wird die Editierbarkeit im TableModel ueberschrieben
         */
        this.setModel(new DefaultTableModel(data, name){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        });

        TableCellRenderer renderer = new OwnTableRenderer();

        /**
         * zeigt Gitternetzlinien an
         */
        setShowGrid(true);
        setDefaultRenderer(Object.class, renderer);

        /**
         *  Felder der Tabelle werden mit einer festen Groesse erstellt.
         *
         */
        setRowHeight(25);
        TableColumnModel columnModel = getColumnModel();
        for (int i = 0; i < getColumnCount(); i++) {
            columnModel.getColumn(i).setPreferredWidth(25);


        }
    }
}
