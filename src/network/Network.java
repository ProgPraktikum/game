package network;

import gui.SelectFieldSize;
import data.DataContainer;
import gui.PlaceShips;
import data.Game;
import data.Ship;
import gui.VictoryScreen;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.Stack;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;


/**
 * Diese Klasse ist für die Kommunikation im Netzwerk zustaendig. Sie dient
 * dem Aufbau einer Host- bzw. Client Connection.
 * Des Weiteren beinhaltet diese Klasse saemtliche Methoden zum uebermitteln und
 * empfangen der relevanten Daten wie z.B. Feldgroesse und Anzahl der Schiffe.
 */
public class Network {
    // MEMBER VARIABLES

    /**
     * Speichert den Netzwerkport. (besteht aus 50000 + GruppenNr).
     */
    private static String addr;

    /**
     * Serversocket des Hosts.
     */
    private static ServerSocket ss;

    /**
     * Socket Client.
     */
    private static Socket s;

    /**
     * Reader, der ueber das Netzwerk uebermittelte Daten liest.
     */
    private static BufferedReader reader;

    /**
     * Writer, der Daten über das Netzwerk übermittelt.
     */
    private static Writer writer;

    /**
     * Intervalle für Socket-Recovering nach Verbindungsabbruch. --> In Implementation letztendlich doch nicht genutzt.
     */
    private static int[] recoverIntervals = {1000, 3000, 6000, 10000, 150000}; // varying from the RFC the intervals won't be randomly generated here since we've got just one client and thus no DOS like issues


    // PUBLIC METHODS

    /**
     * Erstellt einen lokalen Websocket Host.
     */
    public static void createHostConnection() {
        try {
            /* Es wird ein neuen Serversocket mit uebergebenem Port erstellt. */
            ss = new ServerSocket(DataContainer.getNetworkPort());

            /*
             * Dem Benutzer wird ein Informationsfenster angezeigt, dass er auf
             * eine Clientverbindung warten muss.
             */
            SelectFieldSize.buildWaitingFrame();
            s = ss.accept();

            reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            writer = new OutputStreamWriter(s.getOutputStream());
        } catch (IOException e) {
            // Both players are loosing if network fails
            DataContainer.setAllowed(false);
            new VictoryScreen(false);

            for (int i = 0; i < 5; i++) { // Not sure if this type of recovering is actually useful here, but..
                try {
                    TimeUnit.MILLISECONDS.sleep(recoverIntervals[i]);
                    recoverHostSocket();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                } catch (Exception ex) {
                    ;
                }

                if (s.isBound()) {
                    break;
                }
            }
            e.printStackTrace();
        }
    }

    /**
     * Schliesst den Websocket Host.
     */
    public static void closeHostConnection() {
        try {
            s.shutdownOutput();
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Verbindung zum Host-Websocket wird hergestellt.
     * @param ip IP-Adresse des Hosts
     */
    public static void createClientConnection(String ip) {
        addr = ip;
        try {
            /* Es wird ein neuer Socket mit uebergeber Ip und Port erstellt */
            s = new Socket(ip, DataContainer.getNetworkPort());

            reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            writer = new OutputStreamWriter(s.getOutputStream());
        } catch (IOException e) {
            // Both players are loosing if network fails
            DataContainer.setAllowed(false);
            new VictoryScreen(false);
            
            for (int i = 0; i < 5; i++) { // Not sure if this type of recovering is actually useful here, but..
                try {
                    TimeUnit.MILLISECONDS.sleep(recoverIntervals[i]);
                    recoverClientSocket(ip);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                } catch (Exception ex) {
                    ;
                }

                if (s.isBound()) {
                    break;
                }
            }
            e.printStackTrace();
        }
    }

    /**
     * Verbindung des Clients wird geschlossen.
     */
    public static void closeClientConnection() {
        try {
            s.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Uebermittlung der Feldbreite, Feldhöhe, sowie den zu setzenden Schiffslaengen.
     */
    public static void sendStartData(int width, int height, Stack<Integer> lengths) {
        StringBuffer line = new StringBuffer();
        Iterator<Integer> iterator = lengths.iterator();

        /* Size beinhaltet die Feldbreite und Höhe
         * Ships beinhaltet die Anzahl Schiffe und die Schiffslänge
         */
        line.append("size ").append(width).append(" ").append(height).append(", ");
        line.append("ships ").append(lengths.size());
        while (iterator.hasNext()) {
            line.append(", ship ").append(iterator.next());
        }

        try {
            writer.write(String.format("%s%n", line));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clientseitiges Lesen der StartUpDaten vom Host (Feldgroesse und Anzahl der Schiffe mit den entsprechenden
     * Laengen)
     */
    public static void recieveStartData() {
        String line = "";

        try {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
         * Der String wird an den Leerzeilen getrennt und die Werte im
         * DataContainer gespeichert.
         */
        String[] startData = line.split(", ");
        String[] size = startData[0].split(" ");

        /*
        Breite und Hoehe werden in den DataContainer geschrieben.
         */
        DataContainer.setGameboardWidth(Integer.parseInt(size[1]));
        DataContainer.setGameboardHeight(Integer.parseInt(size[2]));
        /*
        Initialisiere map
         */
        Game.setMap();
        for (int i = 2; i < startData.length; i++) {
            String[] ship = startData[i].split(" ");

            DataContainer.getShipLenghts().push(Integer.parseInt(ship[1]));
            DataContainer.getShipLengthsAI().push(Integer.parseInt(ship[1]));
            Ship s = new Ship(Integer.parseInt(ship[1]));
            DataContainer.getfleet().push(s);
        }
        new PlaceShips();
    }

    /**
     * Schiessen auf Netzwerkgegner
     * @param x X-Koordinate des zu beschiessenden Feldes.
     * @param y Y-Koordinate des zu beschiessenden Feldes.
     * @return Den Vorgaben entsprechender Trefferwert.
     */
    public static int networkShoot(int x, int y) {
        StringBuffer line = new StringBuffer();
        line.append("shot ").append(y).append(" ").append(x);
        try {
            writer.write(String.format("%s%n", line));
            writer.flush();
        } catch (IOException e) {
            System.err.println("Connection lost - trying to recover!");
            try {
                recover();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return shootanswer();
    }

    /**
     * Liest den Rueckgabewert nach Schuss auf Gegnerisches Feld aus.
     * @return Den Vorgaben entsprechender Trefferwert.
     */
    public static int shootanswer() {
        String inputLine = ""; //1 zeichen return wert von shoot des gegners
        try {
            inputLine = reader.readLine();
        } catch (IOException e) {
            System.err.println("Connection lost - trying to recover!");
            try {
                recover();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        int ret = -1;
        try {
            ret = Integer.parseInt(inputLine);
        } catch (Exception e) {
            try {
                System.err.println("Connection seems lost - trying to recover!");
                recover();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * Empfaengt via Netzwerk einkommenden Treffer.
     */
    public static void networkHit() {
        String inputLine = "";
        try {
            inputLine = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] input = inputLine.split(" ");
        int y = Integer.parseInt(input[1]);
        int x = Integer.parseInt(input[2]);

        StringBuffer outputLine = new StringBuffer();
        outputLine.append(Integer.toString(Game.getHit(x, y)));
        try {
            writer.write(String.format("%s%n", outputLine));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // PRIVATE METHODS

    /**
     * Versucht verlorene Verbindung wiederherzustellen.
     * @throws IOException Bei nicht Gelingen der Wiederherstellung.
     */

    private static void recover() throws IOException {
        boolean isHost = DataContainer.getIsHost();
        try {
            if (isHost) {
                recoverHostSocket();
            } else {
                recoverClientSocket(addr);
            }
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * Versucht, Websocket Host wiederherzustellen.
     * @throws IOException Bei nicht Gelingen der Wiederherstellung.
     */
    private static void recoverHostSocket() throws IOException {
        try {
            s = ss.accept();
            System.out.println("Recovered connection");

            reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            writer = new OutputStreamWriter(s.getOutputStream());
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * Versucht, Verbindung zu Websocket Host wiederherzustellen.
     * @param ip Internetadresse des Websocket Hosts.
     * @throws IOException Bei nicht Gelingen der Wiederherstellung.
     */
    private static void recoverClientSocket(String ip) throws IOException {
        try {
            s.shutdownOutput();
        } catch (IOException e) {
            ;
        }

        try {
            s = new Socket(ip, DataContainer.getNetworkPort());
            System.out.println("Recovered connection");

            reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            writer = new OutputStreamWriter(s.getOutputStream());
        } catch (IOException e) {
            throw e;
        }
    }

}