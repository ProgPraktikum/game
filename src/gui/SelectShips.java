package gui;

import data.DataContainer;
import network.Network;
import data.Game;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse SelectShips ist notwendig um eine Auswahl zu treffen
 * mit welchen Schiffen ( sprich Anzahl und Laenge) man spielen moechte.
 * Dafuer werden abhaengig von der gewaehlten Spielfeldgroesse fuer
 * die jeweilge Laenge ein JSpinner erstellt, ueber diesen man dann die
 * Anzahl auswaehlen kann.
 */
class SelectShips {

    private JDialog selectships;
    private List<JSpinner> spinners;
    int values[] = Game.recomendation();

    SelectShips() {

        // Fenster für die Auswahl der Schiffe
        selectships = new JDialog();
        selectships.setModal(true);
        selectships.setUndecorated(true);
        selectships.setContentPane(Box.createVerticalBox());
        selectships.setMinimumSize(new Dimension(400, 400));
        selectships.setBackground(Color.BLACK);


        /**
         ArrayList nimmt die ganzen JSpinner auf welche für die Anzahl der Schiffe genutzt werden.
         */
        spinners = new ArrayList<JSpinner>();


        /**
         *JLabel Waehle Anzahl Schiffe
         */

        JLabel label1 = new JLabel("Waehle die Anzahl der Schiffe");
        label1.setForeground(Color.WHITE);
        label1.setBackground(Color.BLACK);
        label1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        label1.setAlignmentX(Component.CENTER_ALIGNMENT);

        Box vbox = Box.createVerticalBox();


        Box vbox_label = Box.createVerticalBox();
        Box vbox_spinner = Box.createVerticalBox();
        Box horizont = Box.createHorizontalBox();

        /**
         * JLabel mit dem Text "Laenge ..." wird initialisiert
         * und zusätzlich jeweils ein spinner
         */

        for (int i = DataContainer.getMaxShipLength(); i >= 2; i--) {

            JLabel labelSize = new JLabel("Laenge " + i + " : ");
            labelSize.setForeground(Color.WHITE);
            labelSize.setBackground(Color.BLACK);
            labelSize.setFont(new Font("Tahoma", Font.PLAIN, 20));

            final SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 10, 1);
            final JSpinner Size = new JSpinner(model);
            Size.setMinimumSize(new Dimension(50, 25));
            Size.setMaximumSize(new Dimension(50, 25));
            Size.setPreferredSize(new Dimension(50, 25));
            Size.setValue(values[i - 2]);
            Size.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    JSpinner s = (JSpinner) e.getSource();

                }
            });
            vbox_label.add(labelSize);
            vbox_spinner.add(Size);

			/*
             * Der JSpinner wird zur Liste spinners hinzugefuegt
			 */
            spinners.add(Size);
        }


        /**
         *   Bestätigung Button.
         *   schliesst SelectShips und oeffnet das PlaceShip Fenster
         */
        JButton ok = new JButton("OK");
        ok.setBackground(Color.BLACK);
        ok.setForeground(Color.WHITE);
        ok.setFont(new Font("Tahoma", Font.PLAIN, 20));
        ok.setEnabled(true);

        ok.addActionListener(
                (e) -> {
                    DataContainer.setShipStack();
                    DataContainer.setFleets();
                    Game.setMap();
                    if (DataContainer.setShipLengthPush(spinners, DataContainer.getOccupancy())) {

                        selectships.dispose();
                        /**
                         * Daten übermitteln falls Netzwerkspiel
                         */
                        if (DataContainer.getGameType().equals("mp") || DataContainer.getGameType()
                                .equals("mps")) {
                            Network.sendStartData(DataContainer.getGameboardWidth(), DataContainer.getGameboardHeight(),
                                    DataContainer.getShipLenghts());
                        }
                        new PlaceShips();
                    } else {
                        /*
                        Falls die zulaessige Anzahl der Schiffe überschritten wurde
                        werden die Stacks neu erstellt
                         */
                        DataContainer.setShipStack();
                        DataContainer.setFleets();
                    }


                }
        );
        JButton recommended = new JButton("empfohlen");
        recommended.setBackground(Color.BLACK);
        recommended.setForeground(Color.WHITE);
        recommended.setFont(new Font("Tahoma", Font.PLAIN, 20));
        recommended.addActionListener(
                (e) -> resetSpinners());

        /**
         * abbrechen Button (schließt den JDialog)
         */
        JButton abort = new JButton("abbrechen");
        abort.setBackground(Color.BLACK);
        abort.setForeground(Color.WHITE);
        abort.setFont(new Font("Tahoma", Font.PLAIN, 20));
        abort.addActionListener(
                (e) -> selectships.dispose());

        Box btn_box = Box.createHorizontalBox();

        btn_box.add(ok);
        btn_box.add(abort);
        btn_box.add(recommended);

        vbox.add(Box.createVerticalStrut(10));
        vbox.add(label1);
        vbox.add(Box.createVerticalStrut(20));

        horizont.add(vbox_label);
        horizont.add(vbox_spinner);
        horizont.add(Box.createHorizontalStrut(40));

        vbox.add(horizont);

        selectships.add(vbox);
        selectships.add(Box.createVerticalStrut(20));
        selectships.add(btn_box);
        selectships.pack();
        selectships.setLocationRelativeTo(null);
        selectships.setVisible(true);

    }

    public void resetSpinners() {
        for (int i = 0; i < spinners.size(); i++) {
            spinners.get(i).setValue(values[spinners.size() - 1 - i]);
        }
    }

}
