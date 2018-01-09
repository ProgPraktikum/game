package GUI;

import data.DataContainer;
import network.Network;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


 class SelectShips {

    private JDialog selectships;
    private List<JSpinner> spinners;


     SelectShips() {

        // Fenster für die Auswahl der Schiffe
        selectships = new JDialog();
        selectships.setModal(true);
        selectships.setUndecorated(true);
        selectships.setContentPane(Box.createVerticalBox());
        selectships.setMinimumSize(new Dimension(400,400));
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
        label1.setFont(new Font("Tahoma", Font.PLAIN,20));
        label1.setAlignmentX(Component.CENTER_ALIGNMENT);

        Box vbox = Box.createVerticalBox();


        Box vbox_label = Box.createVerticalBox();
        Box vbox_spinner = Box.createVerticalBox();
        Box horizont = Box.createHorizontalBox();

			/**
			 * JLabel mit dem Text "Laenge ..." wird initialisiert
			 * und zusätzlich jeweils ein spinner
			 */

        for ( int i = DataContainer.getMaxShipLength(); i >= 2; i--) {

            JLabel labelSize = new JLabel("Laenge " + i + " : ");
            labelSize.setForeground(Color.WHITE);
            labelSize.setBackground(Color.BLACK);
            labelSize.setFont(new Font("Tahoma", Font.PLAIN,20));


            final SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 10, 1);
            final JSpinner Size = new JSpinner(model);
            Size.setMinimumSize(new Dimension(50,25));
            Size.setMaximumSize(new Dimension(50,25));
            Size.setPreferredSize(new Dimension(50,25));
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




        // Bestätigung Button
        JButton ok = new JButton("OK");
        ok.setBackground(Color.BLACK);
        ok.setForeground(Color.WHITE);
        ok.setFont(new Font("Tahoma", Font.PLAIN, 20));
        ok.setEnabled(true);

        ok.addActionListener(
                (e) -> {
                    DataContainer.setShipStack();
                    DataContainer.setFleet();
                    if (DataContainer.setShipLengthPush(spinners,
                            ((DataContainer.getGameboardWidth()*DataContainer.getGameboardHeight())*30/100))){

                        selectships.dispose();
                        /**
                         * Daten übermitteln falls Netzwerkspiel
                         */
                        if(DataContainer.getGameType().equals("mp")|| DataContainer.getGameType()
                                .equals("mps")){
                            Network.sendStartData(DataContainer.getGameboardWidth(),DataContainer.getGameboardHeight(),
                                    DataContainer.getShipLenghts());
                        }
                            new PlaceShips();
                    }else{
                        /*
                        Falls die zulaessige Anzahl der Schiffe überschritten wurde
                        werden die Stacks neu erstellt
                         */
                        DataContainer.setShipStack();
                        DataContainer.setFleet();
                    }


                }
        );
        // abbrechen Button (schließt den JDialog)
        JButton abbrechen = new JButton("abbrechen");
        abbrechen.setBackground(Color.BLACK);
        abbrechen.setForeground(Color.WHITE);
        abbrechen.setFont(new Font("Tahoma", Font.PLAIN, 20));
        abbrechen.addActionListener(
                (e) -> {

                    selectships.dispose(); }
        );






        Box btn_box = Box.createHorizontalBox();




        btn_box.add(ok);
       // btn_box.add(Box.createHorizontalStrut(2));
        btn_box.add(abbrechen);

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

}
