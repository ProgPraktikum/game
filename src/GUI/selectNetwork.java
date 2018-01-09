package GUI;


import data.DataContainer;
import gameboard.Board;
import network.Network;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;

 class selectNetwork {

    private JDialog nw;
    private JTextField field;
    private JLabel displayIp;
    private JCheckBox isHost;
    private JCheckBox isClient;


     selectNetwork() throws SocketException {

        /**
         * Neuer JDialog wird erstellt und auf Modal gesetzt. Des Weiteren wird undecorated gesetzt.
         */
        nw = new JDialog();
        nw.setModal(true);
        nw.setUndecorated(true);
        nw.setBackground(Color.BLACK);
        nw.setPreferredSize(new Dimension(400, 400));
        nw.setContentPane(Box.createVerticalBox());

        /**
         * Boxn um die JCheckBox'n und JButtons und JTextField aufzunehmen.
         */
        Box horizontalBox = Box.createHorizontalBox();
        Box horizontalBox1 = Box.createHorizontalBox();
        Box tf = Box.createHorizontalBox();

        /**
         * JCheckBox für isHost
         */
        isHost = new JCheckBox("Host");
        isHost.setAlignmentX(Component.LEFT_ALIGNMENT);
        isHost.setBackground(Color.BLACK);
        isHost.setForeground(Color.WHITE);
        isHost.addActionListener(
                (e) -> {

                    if (isHost.isSelected()) {
                        isClient.setSelected(false);
                    }
                }
        );


        /**
         * JCheckBox für isClient
         */
        isClient = new JCheckBox("Client");
        isClient.setAlignmentX(Component.LEFT_ALIGNMENT);
        isClient.setBackground(Color.BLACK);
        isClient.setForeground(Color.WHITE);
        isClient.addActionListener(
                (e) -> {

                    if (isClient.isSelected()) {
                        isHost.setSelected(false);
                    }
                }
        );


        /**
         * JTextField für die Eingabe einer IP
         */
        field = new JTextField("IP des Host eingeben");
        field.setMaximumSize(new Dimension(200, 30));
        field.setMinimumSize(new Dimension(200, 30));
        field.setPreferredSize(new Dimension(200, 30));

        /**
         * eigene IP ermitteln
         */
        Enumeration<NetworkInterface> netInter = NetworkInterface.getNetworkInterfaces();
        String myIp = null;
        while ( netInter.hasMoreElements() )
        {
            NetworkInterface ni = netInter.nextElement();

            for ( InetAddress iaddress : Collections.list(ni.getInetAddresses()) )
            {
                if(iaddress.isSiteLocalAddress() && !(iaddress.isLoopbackAddress())){
                    System.out.println( "IP: " + iaddress.getHostAddress() );
                    myIp = iaddress.getHostAddress();
                }

            }
        }


        /**
         * DisplayIp zeigt die eigene Ip Adresse wenn man hosted. wird nur für die
         * Clientverindung benötigt
         */
        displayIp = new JLabel("My IP: "+ myIp);
        displayIp.setForeground(Color.GREEN);
        displayIp.setAlignmentX(Component.LEFT_ALIGNMENT);


        /**
         * OK Button, der bei Benutzung das Fenster schließt und falls man Host ist
         * select FieldSize öffnet.
         * Ist man Client wird das PlaceShips View geöffnet.
         */
        JButton ok = new JButton("OK");
        ok.setBackground(Color.BLACK);
        ok.setForeground(Color.WHITE);
        ok.setFont(new Font("Tahoma", Font.PLAIN, 20));
        ok.addActionListener(
                (e) -> {
                    if(isHost.isSelected()){
                        DataContainer.setIsHost(true);
                    }else{
                        DataContainer.setIsClient(true);
                    }

                    if(isClient.isSelected()){
                        if(field.getText() != null)
                        DataContainer.setNetworkIP(field.getText());
                        // ausgaben nur für debug
                        System.out.println("dc " +DataContainer.getNetworkIP());
                        System.out.println("tf " +field.getText());

                    }

                    nw.setVisible(false);
                    if(DataContainer.getIsHost()){
                        /**
                         * Aufbau HostVerindung
                         */
                        Network.createHostConnection();
                        new SelectFieldSize();
                    }else{

                        Board b = new Board();
                        DataContainer.setShipStack();
                        DataContainer.setFleet();


                        /**
                         * Aufbau einer ClientConnection
                         */
                        Network.createClientConnection(DataContainer.getNetworkIP());


                        /**
                         * Empfangen der übermittelten StartDaten
                         */
                        Network.recieveStartData();
                      //  new PlaceShips();
                    }
                }
        );

        /**
         * abbrechen Button (schließt den JDialog)
         */
        JButton abbrechen = new JButton("abbrechen");
        abbrechen.setBackground(Color.BLACK);
        abbrechen.setForeground(Color.WHITE);
        abbrechen.setFont(new Font("Tahoma", Font.PLAIN, 20));
        abbrechen.addActionListener(
                (e) -> {
                    nw.dispose(); }
        );

        /**
         * Box welche die JCheckBox isHost und isClient aufnimmt
         */
        horizontalBox1.add(Box.createHorizontalStrut(10));
        horizontalBox1.add(isHost);
        horizontalBox1.add(Box.createHorizontalStrut(10));
        horizontalBox1.add(isClient);

        /**
         * Box welche die JButtons OK und abbrechen aufnimmt
         */
        horizontalBox.add(Box.createHorizontalStrut(20));
        horizontalBox.add(ok);
        horizontalBox.add(abbrechen);

        /**
         * Box welche das JTextField aufnimmt
         */
        tf.add(field);
        tf.add(Box.createHorizontalStrut(5));   //abstand zwischen TextField und Label
        tf.add(displayIp);
        nw.add(horizontalBox1);
        nw.add(tf);

        nw.add(horizontalBox);
        nw.pack();
        nw.setLocationRelativeTo(null);
        nw.setVisible(true);

    }


}
