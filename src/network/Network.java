package network;


import gui.SelectFieldSize;
import data.DataContainer;
import gui.PlaceShips;
import data.Game;
import data.Ship;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.Stack;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;


/**
 * Diese Klasse ist für die Kommunikation im Netzwerk zustaendig. Sie dient
 * dem aufbau einer Host- bzw. ClientConnection.
 * Des Weiteren beinhaltet diese Klasse sämtliche Methoden zum übermitteln und
 * empfangen der relevanten Daten wie z.B. Feldgröße und Anzahl Schiffe.
 */
public class Network {

    /**
     * variable zum speichern des Ports. (besteht aus 50000 + GruppenNr)
     */
    private static int port = 50010;
    private static String addr;

    /**
     * Serversocket des Hosts
     */
    private static ServerSocket ss;

    /**
     * Socket Client
     */
    private static Socket s;

    /**
     * BufferedReader der die über das Netzwerk übermittelte Daten liest.
     */
    private static BufferedReader reader;

    /**
     * Writer der der Daten über das Netzwerk übermittelt
     */
    private static Writer writer;

    /**
     * Intervalle für Socket-Recovering nach Verbindungsabbruch
     */
    private static int[] recoverIntervals = {1000, 3000, 6000, 10000, 150000}; // varying from the RFC the intervals won't be randomly generated here since we've got just one client and thus no DOS like issues

    /*
    methoden
     */

    /**
     * Mit dieser Methode wird eine HostVerbindung aufgebaut
     */
    public static void createHostConnection() {
        try {
            /*
             * Es wird ein neuen Serversocket mit uebergebenem Port erstellt.
             */
            ss = new ServerSocket(port);

            /*
             * Dem Benutzer wird ein Informationsfenster angezeigt, dass er auf
             * eine Clientverbindung warten muss.
             */
            SelectFieldSize.buildWaitingFrame();
            s = ss.accept();

            reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            writer = new OutputStreamWriter(s.getOutputStream());
        } catch (IOException e) {
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
     * Hostverbindung wird beendet
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
     * ClientVerbindung wird erstellt
     * @param ip
     */
    public static void createClientConnection(String ip) {
        addr = ip;
        try {
            /*
             * Es wird ein neuer Socket mit uebergeber Ip und Port erstellt
             */
            s = new Socket(ip, port);

            reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            writer = new OutputStreamWriter(s.getOutputStream());
        } catch (IOException e) {
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
     * Clientverbindung beenden
     */
    public static void closeClientConnection() {
        try {
            s.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Diese Methode dient der Übermittlung der Feldbreite, Feldhöhe,
     * sowie den zu setzenden Schiffslängen.
     */
    public static void sendStartData(int width, int height, Stack<Integer> lengths) {
        StringBuffer line = new StringBuffer();
        Iterator<Integer> iterator = lengths.iterator();

        /*
         * Size beinhaltet die Feldbreite und Höhe
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
     * Diese Methode liest beim Client die StartUpDaten aus
     * ( Feldgroesse und Anzahl der Schiffe mit den entsprechenden
     * Laengen
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
        Breite und Höhe werden in den DataContainer geschrieben.
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
    public static int networkShoot(int x,int y) {
        StringBuffer line = new StringBuffer();
        line.append("shot ").append(y).append(" ").append(x);
        System.out.println("Line:" + line);
        try {
            writer.write(String.format("%s%n", line));
            writer.flush();
        } catch (IOException e) {
            System.err.println("Connection lost - trying to recover!");
            try {
                recover();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
        return shootanswer();
    }
    public static int shootanswer(){
        String inputLine = ""; //1 zeichen return wert von shoot des gegners
        try {
            inputLine = reader.readLine();
        } catch (IOException e) {
            System.err.println("Connection lost - trying to recover!");
            try {
                recover();
            } catch(IOException ex) {
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
            } catch(IOException ex) {
                ex.printStackTrace();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
        return ret;
    }

    public static void networkHit(){
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
        outputLine.append(Integer.toString(Game.getHit(x,y)));
        try {
            writer.write(String.format("%s%n", outputLine));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void recover() throws IOException {
        boolean isHost = DataContainer.getIsHost();
        try {
            if (isHost) {
                recoverHostSocket();
            } else {
                recoverClientSocket(addr);
            }
        } catch(IOException e) {
            throw e;
        }

    }


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

    private static void recoverClientSocket(String ip) throws IOException {
        try {
            s.shutdownOutput();
        } catch (IOException e) {
            ;
        }

        try {
            s = new Socket(ip, port);
            System.out.println("Recovered connection");

            reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            writer = new OutputStreamWriter(s.getOutputStream());
        } catch (IOException e) {
            throw e;
        }
    }

}