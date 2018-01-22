package GUI;

import data.DataContainer;
import gameboard.*;

import javax.swing.*;
import java.awt.*;

/**
 * Diese Klasse dient lediglich dazu die Spielfeldgroesse zu erfragen
 * und die entsprechenden Werte in den DataContainer zu schreiben.
 */
public class SelectFieldSize {

    static JDialog eingaben;

    public SelectFieldSize(){

        // Fenster für die eingabe der Spielfeldgröße
        eingaben = new JDialog();
        eingaben.setModal(true);
        eingaben.setContentPane(Box.createVerticalBox());
        eingaben.setMinimumSize(new Dimension(400,400));
        eingaben.setBackground(Color.BLACK);
        eingaben.setUndecorated(true);


        Box hbox = Box.createHorizontalBox();
        Box hbox2 = Box.createHorizontalBox();
        Box hbox3 = Box.createHorizontalBox();
        Box vbox = Box.createVerticalBox();


        vbox.add(Box.createVerticalStrut(20));
        JLabel groesseFeld = new JLabel("Spielfedgröße (5-30)");
        groesseFeld.setAlignmentX(Component.CENTER_ALIGNMENT);
        groesseFeld.setFont(new Font("Tahoma", Font.PLAIN, 25));
        groesseFeld.setForeground(Color.WHITE);

        vbox.add(Box.createVerticalStrut(20));
        hbox.add(Box.createHorizontalStrut(5));


        /**
         * JLabel: Bitte Spielfeldbreite waehlen
         */
        JLabel lblBreite = new JLabel("Spielfeldbreite:");
        lblBreite.setVisible(true);
        lblBreite.setForeground(Color.WHITE);
        lblBreite.setFont(new Font("Tahoma", Font.PLAIN, 18));

        /**
         * SpinnerModel fuer die Feldgroesse
         */
        SpinnerNumberModel spinNumModel1 = new SpinnerNumberModel(10,5,30,1);
        SpinnerNumberModel spinNumModel2 = new SpinnerNumberModel(10,5,30,1);
        /**
         * JSpinner zur Wahl der Feldbreite
         */
        JSpinner spinnerBreite = new JSpinner();
        spinnerBreite.setModel(spinNumModel1);
        ((JSpinner.DefaultEditor)spinnerBreite.getEditor()).getTextField().setEditable(false);
        spinnerBreite.setMinimumSize(new Dimension(50,30));
        spinnerBreite.setMaximumSize(new Dimension(50,30));
        spinnerBreite.setPreferredSize(new Dimension(50,30));


        /**
         * JSpinner zur Wahl der Feldhoehe
         */
        JSpinner spinnerHoehe = new JSpinner();
        spinnerHoehe.setModel(spinNumModel2);
        ((JSpinner.DefaultEditor)spinnerHoehe.getEditor()).getTextField().setEditable(false);
        spinnerHoehe.setMinimumSize(new Dimension(50,30));
        spinnerHoehe.setMaximumSize(new Dimension(50,30));
        spinnerHoehe.setPreferredSize(new Dimension(50,30));

        /**
         * JLabel: Bitte Spielfeldhoehe waehlen
         */
        JLabel lblHoehe = new JLabel("Spielfeldhoehe:");
        lblHoehe.setForeground(Color.WHITE);
        lblHoehe.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblHoehe.setVisible(true);

        /**
         Bestaetigen Button ( liest die eingegebenen Werte ein und und speichert diese
        */
        JButton ok = new JButton("OK");
        ok.setBackground(Color.BLACK);
        ok.setForeground(Color.WHITE);
        ok.setFont(new Font("Tahoma", Font.PLAIN, 20));
        ok.addActionListener(
                (e) -> {

                    DataContainer.setGameboardWidth((int) spinnerBreite.getValue());
                    DataContainer.setGameboardHeight((int) spinnerHoehe.getValue());

                    eingaben.setVisible(false);

                    //setzt die Maximale Schiffslaenge
                    DataContainer.setOccupancy();
                    DataContainer.setMaxShipLength();

                    new SelectShips();
                    //erzeugt spielbrett objekt mit datenstruktur
                    Board b = new Board();
                }
        );


        // abbrechen Button (schließt den JDialog)
        hbox2.add(Box.createHorizontalStrut(10));

        JButton abort = new JButton("abbrechen");
        abort.setBackground(Color.BLACK);
        abort.setForeground(Color.WHITE);
        abort.setFont(new Font("Tahoma", Font.PLAIN, 20));
        abort.addActionListener(
                (e) -> {
                    eingaben.dispose(); }
        );

        hbox.add(lblBreite);
        hbox.add(spinnerBreite);
        hbox3.add(lblHoehe);
        hbox3.add(spinnerHoehe);


        hbox2.add(ok);
        hbox2.add(abort);

        vbox.add(groesseFeld);
        vbox.add(hbox);

        eingaben.add(vbox);  // Box mit Feldbreite
        eingaben.add(hbox3); // Box mit Feldhoehe
        eingaben.add(hbox2); // Box mit Buttons

        eingaben.pack();
        eingaben.setLocationRelativeTo(null);
        eingaben.setVisible(true);

    }

    /**
     * message dialog zur information das auf die Netzwerkverbindung
     * gewartet wird.
     */
    public static void buildWaitingFrame() {

        JOptionPane.showMessageDialog(eingaben,
                "Waiting for Client Connection...\nSie muessen mit OK bestaetigen",
                "Information", JOptionPane.PLAIN_MESSAGE);
    }
}
