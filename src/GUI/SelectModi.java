package GUI;

import data.DataContainer;
import data.Ship;

import javax.swing.*;
import java.awt.*;
import java.net.SocketException;


/**
 * Hier wird ein neuer JDialog erstellt um ein Auswahlfenster für diverse SpielModi
 * (Einzelspieler, Netzwerk, Com vs Com).
 * Für jeden Modi wird ein JButton erzeugt.
 *
 * @author Christopher Kisch, Jan Riedel, Felix Graeber
 */
class SelectModi {

    private JDialog new_Game;

     SelectModi(){

        new_Game = new JDialog();

        new_Game.setModal(true);
        new_Game.setUndecorated(true);
        new_Game.setBackground(Color.BLACK);
        new_Game.setContentPane(Box.createVerticalBox());

        Box vbox = Box.createVerticalBox();
        Box hbox = Box.createHorizontalBox();

        hbox.add(Box.createHorizontalStrut(20));
        vbox.add(Box.createVerticalStrut(20));


        /**
         *  Schnelles Spiel
         */
        vbox.add(Box.createVerticalStrut(7));   //Abstand zwischen Buttons
        JButton sSpiel = new JButton("Schnellestart");
        sSpiel.setToolTipText("Spiel gegen den Computer mit einer Standartfeldgroesse von 10x10 und der dazugehoerigen" +
                "Standartflotte");
        sSpiel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sSpiel.setPreferredSize(new Dimension(250, 50));
        sSpiel.setMinimumSize(new Dimension(250, 50));
        sSpiel.setMaximumSize(new Dimension(250, 50));
        sSpiel.setBackground(Color.BLACK);
        sSpiel.setForeground(Color.WHITE);
        sSpiel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        sSpiel.addActionListener(
                (e) -> {
                    DataContainer.setGameType("ss");

                    DataContainer.setShipStack();
                    DataContainer.setFleet();
                    int anz = 4;
                    //for(int i = 5; i>=2; i--){
                    for(int i= 2; i<=5;i++){

                        for(int j = 1; j <= anz; j++){
                            DataContainer.getShipLenghts().push(i);
                            DataContainer.getShipLengthsAI().push(i);
                            //DataContainer.getShipLengthsInverted().push(i);
                            Ship s = new Ship(i);
                            DataContainer.getfleet().push(s);
                        }
                        anz --;
                    }
                   new PlaceShips();
                    //new GameView();
                }
        );
        vbox.add(sSpiel);


        /**
         * Benutzerdefiniert Button
         */
        vbox.add(Box.createVerticalStrut(7));   //Abstand zwischen Buttons
        JButton vsCom = new JButton("Benutzerdefiniert");
        vsCom.setToolTipText("Spiel gegen den Computer mit selbst gewaehlter Feldgroesse und selbst" +
                "gewaehlter Flotte");
        vsCom.setAlignmentX(Component.CENTER_ALIGNMENT);
        vsCom.setPreferredSize(new Dimension(250, 50));
        vsCom.setMinimumSize(new Dimension(250, 50));
        vsCom.setMaximumSize(new Dimension(250, 50));
        vsCom.setBackground(Color.BLACK);
        vsCom.setForeground(Color.WHITE);
        vsCom.setFont(new Font("Tahoma", Font.PLAIN, 20));
        vsCom.addActionListener(
                (e) -> {
                    DataContainer.setGameType("bdf");
                    new_Game.setVisible(false);
                    new SelectFieldSize();
                }
        );
        vbox.add(vsCom);

        /**
         *  Spieler gegen Spieler Button
         */
        vbox.add(Box.createVerticalStrut(7));   //Abstand zwischen Buttons
        JButton vsHuman = new JButton("Multiplayer");
        vsHuman.setToolTipText("Spiel gegen einen anderen Spieler (benötigt Netzwerk)");
        vsHuman.setAlignmentX(Component.CENTER_ALIGNMENT);
        vsHuman.setPreferredSize(new Dimension(250, 50));
        vsHuman.setMinimumSize(new Dimension(250, 50));
        vsHuman.setMaximumSize(new Dimension(250, 50));
        vsHuman.setBackground(Color.BLACK);
        vsHuman.setForeground(Color.WHITE);
        vsHuman.setFont(new Font("Tahoma", Font.PLAIN, 20));
        vsHuman.addActionListener((e) -> {

                    new_Game.setVisible(false);
                    DataContainer.setGameType("mp");
                    try {
                        new selectNetwork();
                    } catch (SocketException e1) {
                        e1.printStackTrace();
                    }
                }
        );
        vbox.add(vsHuman);


        vbox.add(Box.createVerticalStrut(7));   //Abstand zwischen Buttons
         /**
          *   Computer gegen Computer Button
          */
        JButton comvsCom = new JButton("Zuschauer");
        comvsCom.setToolTipText("Lasse den Computer gegen einen anderen Computer antreten (benötigt Netzwerk)");
        comvsCom.setAlignmentX(Component.CENTER_ALIGNMENT);
        comvsCom.setPreferredSize(new Dimension(250, 50));
        comvsCom.setMinimumSize(new Dimension(250, 50));
        comvsCom.setMaximumSize(new Dimension(250, 50));
        comvsCom.setBackground(Color.BLACK);
        comvsCom.setForeground(Color.WHITE);
        comvsCom.setFont(new Font("Tahoma", Font.PLAIN, 20));
        comvsCom.addActionListener((e) -> {

                    new_Game.setVisible(false);
                    DataContainer.setGameType("mps");
                    try {
                        new selectNetwork();
                    } catch (SocketException e1) {
                        e1.printStackTrace();
                    }
                }
        );

        vbox.add(comvsCom);

         /**
          * zurueck Button
          */
        vbox.add(Box.createVerticalStrut(7));   //Abstand zwischen Buttons
        JButton back = new JButton("zurück");
        back.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.setPreferredSize(new Dimension(250, 50));
        back.setMinimumSize(new Dimension(250, 50));
        back.setMaximumSize(new Dimension(250, 50));
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setFont(new Font("Tahoma", Font.PLAIN, 20));

        back.addActionListener(
                (e) -> {

                    new_Game.dispose();               // schließt den JDialog
                }
        );
        vbox.add(back);

        vbox.add(Box.createVerticalStrut(20));
        hbox.add(vbox);
        hbox.add(Box.createHorizontalStrut(20));


        new_Game.add(hbox);
        new_Game.pack();
        new_Game.setLocationRelativeTo(null);

        new_Game.setVisible(true);
    }
}
