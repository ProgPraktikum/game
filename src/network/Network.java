package network;


import GUI.SelectFieldSize;
import data.DataContainer;
import GUI.PlaceShips;
import data.Game;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import java.util.Stack;
import java.util.Iterator;


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

    /**
     * Serversocket des Hosts
     */
    private static  ServerSocket ss;

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
        try {
            /*
             * Es wird ein neuer Socket mit uebergeber Ip und Port erstellt
             */
            s = new Socket(ip, port);

            reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            writer = new OutputStreamWriter(s.getOutputStream());
        } catch (IOException e) {
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
        line.append("size " + width + " " + height + ", ");
        line.append("ships " + lengths.size());
        while (iterator.hasNext()) {
            line.append(", ship " + iterator.next());
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
     */
    public static void recieveStartData() {
        String line = "";

        try {
            line = reader.readLine();
        } catch (SocketException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
         * Der String wird an den Leerzeilen getrennt und die Werte werden im
         * DataContainer gespeichert.
         */
        String[] startData = line.split(", ");
        String[] size = startData[0].split(" ");
        /*
        Breite und Höhe wird in den DataContainer geschrieben.
         */
        DataContainer.setGameboardWidth(Integer.parseInt(size[1]));
        DataContainer.setGameboardHeight(Integer.parseInt(size[2]));


        
        Stack<Integer> shipStack = new Stack<Integer>();
        for (int i = 2; i < startData.length; i++) {
            String[] ship = startData[i].split(" ");
            shipStack.push(Integer.parseInt(ship[1]));


            DataContainer.getShipLenghts().push(Integer.parseInt(ship[1]));
            DataContainer.getShipLengthsAI().push(Integer.parseInt(ship[1]));
            DataContainer.getShipLengthsInverted().push(Integer.parseInt(ship[1]));
        }

        while( !(DataContainer.getShipLengthsInverted().isEmpty()) ){
            DataContainer.addShip(DataContainer.getShipLengthsInverted().pop());
        }
        new PlaceShips();
    }
    public static int networkShoot(int x,int y){
        StringBuffer line = new StringBuffer();
        line.append("shot "+y+" "+x);
        try {
            writer.write(String.format("%s%n", line));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*String inputLine = ""; //1 zeichen return wert von shoot des gegners

        try {
            inputLine = reader.readLine();
        } catch (SocketException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(inputLine); */
        return 0;
    }

    public static void networkHit(){
        String inputLine = "";
        try {
            inputLine = reader.readLine();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] input =inputLine.split(" ");
        int y = Integer.parseInt(input[1]);
        int x = Integer.parseInt(input[2]);
        System.out.print(y);
        System.out.println();
        System.out.println(x);
        //int value = Game.getHit(x,y);
        //String outputLine = Integer.toString(Game.getHit(x,y));
        StringBuffer outputLine= new StringBuffer();
        outputLine.append(Integer.toString(Game.getHit(x,y)));
        try {
            writer.write(String.format("%s%n", outputLine));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
