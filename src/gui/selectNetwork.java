package gui;


import data.DataContainer;
import gameboard.Board;
import network.Network;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;


/**
 * Diese Klasse dient dazu eine Wahl zu treffen ob man
 * als Host oder als Client spielen moechte.
 * Des Weiteren wird hier einem die eigene IP Adresse
 * angezeigt, welche fuer den Client notwendig ist um
 * eine Verbindung zu dem Host herzustellen.
 */
class selectNetwork {

    private JDialog nw;
    private JTextField field;
    private JLabel displayIp;
    private JCheckBox isHost;
    private JCheckBox isClient;

    private static boolean isVmMac(byte[] mac) {
        byte invalidMacs[][] = {
                {0x00, 0x05, 0x69},             //VMWare
                {0x00, 0x1C, 0x14},             //VMWare
                {0x00, 0x0C, 0x29},             //VMWare
                {0x00, 0x50, 0x56},             //VMWare
                {0x0A, 0x00, 0x27}              //VirtualBox
        };

        for (byte[] b : invalidMacs) {
            if (b[0] == mac[0] && b[1] == mac[1] && b[2] == mac[2]) {
                return true;
            }
        }
        return false;
    }

    selectNetwork() throws SocketException {
        /**
         * eigene IP ermitteln
         */
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        final StringBuffer myIp = new StringBuffer();
        while (interfaces.hasMoreElements()) {
            /**
             * Check whether interface is up, isn't virtual and doesn't loop back
             */
            NetworkInterface iface = interfaces.nextElement();
            if (!(iface.isUp()) || (iface.isVirtual()) || (iface.isLoopback())) {
                continue;
            }

            Enumeration<InetAddress> addresses = iface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                /**
                 * Check physical interface address and filter out VM hosts
                 */
                byte[] mac = iface.getHardwareAddress();
                InetAddress addr = addresses.nextElement();

                if (mac == null) {
                    continue;
                }

                if (isVmMac(mac)) {
                    continue;
                }

                /**
                 * Check ip adress and accept IPv4 only at this moment to prevent issues
                 */
                try {
                    if (!addr.isReachable(1000)) {
                        continue;
                    }
                } catch (IOException e) {
                    continue;
                }

                if (!(addr.isLinkLocalAddress()) && !(addr.isLoopbackAddress()) && !(addr instanceof Inet6Address)) {
                    myIp.append(addr.getHostAddress());
                }
            }
        }

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
         * JTextField für die Eingabe einer IP
         */
        field = new JTextField("IP des Hosts..");
        field.setMaximumSize(new Dimension(200, 30));
        field.setMinimumSize(new Dimension(200, 30));
        field.setPreferredSize(new Dimension(200, 30));
        field.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                field.setText("");
            }
        });


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
                        field.setText(myIp.toString());
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
                        field.setText("IP des Hosts..");
                        isHost.setSelected(false);
                    }
                }
        );


        /**
         * DisplayIp zeigt die eigene Ip Adresse wenn man hosted. wird nur für die
         * Clientverindung benötigt
         */
        displayIp = new JLabel("My IP: " + myIp);
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
                    if (isHost.isSelected()) {
                        DataContainer.setIsHost(true);
                    } else {
                        DataContainer.setIsClient(true);
                    }

                    if (isClient.isSelected()) {
                        if (field.getText() != null)
                            DataContainer.setNetworkIP(field.getText());
                    }
                    DataContainer.setAllowed(DataContainer.getIsClient());
                    nw.setVisible(false);
                    if (DataContainer.getIsHost()) {
                        /**
                         * Aufbau HostVerindung
                         */
                        Network.createHostConnection();
                        new SelectFieldSize();
                    } else {

                        Board b = new Board();
                        DataContainer.setShipStack();
                        DataContainer.setFleets();


                        /**
                         * Aufbau einer ClientConnection
                         */
                        Network.createClientConnection(DataContainer.getNetworkIP());


                        /**
                         * Empfangen der übermittelten StartDaten
                         */
                        Network.recieveStartData();
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
                    nw.dispose();
                }
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
