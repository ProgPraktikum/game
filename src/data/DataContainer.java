package data;

import GUI.TableView;
import gameboard.Board;

import javax.swing.*;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Diese Klasse beinhaltet saemtliche Werte, wie z.B. Spielfedgroesse. Ebenso enthaelt diese
 * Klasse Getter und Setter Methoden fuer saemtliche Werte.
 *
 *
 * @author Christopher Kisch, Jan Riedel, Felix Graeber
 *
 */
public class DataContainer {

    //Gametyp
    /**"ss" = schnelles Spiel
     * "bdf" = benutzerdefinert
     * "mp" = multiplayer
     * "mps"= Ki gegen Ki modus
     */
    private static String gameType = null;

    //Spielfeld
    /**
     * Spielfeldbreite
     */
    private static int width = 10;
    /**
     * Spielfeldhoehe
     */
    private static int height = 10;

    // Maximale Belegungsfaktor
    /**
     * maximaler Belegungsfaktor
     */
    private static int occupancy =  ((width * height)*30/100);

    // Variabe fuer die maximale Schiffslaenge
    /**
     * maximale Schiffslaenge
     */
    private static int maxShipLength;

    /**
    * Dieser Stack ist fuer die gewaehlten schiffe des Spielers.
    * Er speichert die Anzahl der Schiffe mit Laenge xxx
    */
    private static Stack<Integer> shipLenghts;
    /**
    *Stack speichert laengen in umgekehrter reihenfolge zu fleet, um dafuer zu sorgen dass in fleet das groesste schiff
    *  als letztes zum stack hinzugefuegt wird
    */
    private static Stack<Integer> shipLengthsInverted;

    /**
    *Stack mit den Schiffsobjekten zur Platzierung auf dem Spielfeld
    */
    private static Stack<Ship> fleet;

    /*
     * Dieser Stack ist fuer die gewaehlten schiffe fuer die AI
     * speichert die Anzahl der Schiffe mit der Laenge xxx
     */
    private static Stack<Integer> shipLengthsAI;

    /**
     *aktuelles schiff fuer plazierung
     */
    private static Ship selectedShip;

    // Spieler Table
    private static TableView table = null;
    private static TableView playerShootTable=null;

    //Debug board
    //public static Board debugOpponent = new Board();
    // ScrollPane
    JScrollPane scrollPane = null;

    // TextArea
    JTextArea textArea = null;

    /**
      *  allowed variable dient zu pruefen ob man schiessen darf oder nicht
      */

    private static boolean allowed;


    /**
     * gibt an ob der Spieler die Host rolle hat.
     */
    private static boolean isHost = false;
    /**
     * gibt an ob der Spieler die Client Rolle hat
     */
    private static boolean isClient = false;
    /**
     * Speicherort fuer IP adresse
     */
    private static String networkIP = null;

    public static TableView getPlayerShootTable(){
        return playerShootTable;
    }
    public static void setPlayerShootTable(TableView t){
        playerShootTable = t;
    }

    /**
     * gibt isHost Variable zurueck
     * @return isHost Variable
     */
    public static boolean getIsHost(){
        return isHost;
    }

    /**
     * setzt isHost Variable
     * @param b Wert auf den IsHost gesetzt wird.
     */
    public static void setIsHost(boolean b){
        isHost = b;
    }

    /**
     * gibt Wert der isClient
     * @return isClient Variable
     */
    public static boolean getIsClient(){
        return isClient;
    }

    /**
     * setzt isclient Variable
     * @param b neuer Wert der isCient Variable
     */
    public static void setIsClient(boolean b){
        isClient = b;
    }

    /**
     * gibt NetworkIP zurueck
     * @return NetworkIP Variable
     */
    public static String getNetworkIP(){
        return networkIP;
    }

    /**
     *Setzt NetworkIp Variable auf uebergebenen Wert
     * @param s neuer Wert fuer NetworkIP
     */
    public static void setNetworkIP(String s){
        networkIP = s;
    }

    /**
    Gibt die Spielfeldbreite zurueck
     */
    public static int getGameboardWidth(){
        return width;
    }

    /**
    setzt die Spielfeldbreite
     */
    public static void setGameboardWidth(int n){
        width = n;
    }

    /**
    gibt die Spielfeldhoehe zurueck
     */
    public static int getGameboardHeight(){
        return height;
    }

    /**
    setzt die Spielfeldhoehe
     */
    public static void setGameboardHeight(int n){
        height = n;
    }


    /**
    @return Spielfeld Spieler
     */
    public static TableView getTable(){
        return table;
    }

    public static void setTable(TableView tabelle){
        table = tabelle;
    }

    /**
    gibt den gameType zurueck
     */
    public static String getGameType(){
        return gameType;
    }

    /**
    setzt den gameType
     */
    public static void setGameType(String typ){
        gameType = typ;
    }


    /**
    getter fuer die Variable allowed
     */
    public static boolean getAllowed(){
        return allowed;
    }

    /**
    setter fuer die Variable allowed
     */
    public static void setAllowed(boolean x){
        allowed = x;
    }

    public static void setOccupancy(int occupancy) {
        DataContainer.occupancy = occupancy;
    }
    /**
    gibt den max Belegungsfaktor zurueck
     */
    public static int getOccupancy(){
        return occupancy;
    }



    /**
   erstellt die stacks fuer die schiffe des Spielers und der AI
    */
    public static void setShipStack(){
        shipLenghts = new Stack<>();
        shipLengthsAI = new Stack<>();
    }

    /**
    *erstellt schiffsstack
    */
    public static void  setFleet(){
        shipLengthsInverted= new Stack<>();
        fleet = new Stack<>();
    }

    /**
     * gibt shiplengthsInverted Stack zurueck
     * @return shiplengthsInverted
     */
    public static Stack<Integer> getShipLengthsInverted(){
        return shipLengthsInverted;
    }

    public static void setShipLengtsInverted(Stack<Integer>inverted){
        shipLengthsInverted = inverted;
    }

    /**
     * berechnet maximale Schiffslaenge aus Hoehe und Breite des Spielfeldes
     */
    public static void setMaxShipLength(){

        if(DataContainer.getGameboardHeight() < DataContainer.getGameboardWidth()){
            maxShipLength = DataContainer.getGameboardWidth() / 2;
        }else{
            maxShipLength = DataContainer.getGameboardHeight() / 2;
        }
    }
    public static void setShipLenghts(Stack<Integer>shipLenght){
        shipLenghts = shipLenght;
    }
    public static void setShipLengthsAI(Stack<Integer>shipLengthsKI){
        shipLengthsAI = shipLengthsKI;
    }

    /**
     * getter fuer maxShipLength
     */

    public static int getMaxShipLength(){
        return maxShipLength;
    }

    public static Stack<Integer> getShipLenghts(){
        return shipLenghts;
    }
    public static Stack<Integer> getShipLengthsAI(){
        return shipLengthsAI;
    }



    /**
     * get fuer fleet stack
     * @return
     */
    public static Stack<Ship> getfleet(){
        return fleet;
    }

    /**
     * fuegt schiff zu stack fleet hinzu
     * @param l laende des neu hinzuefuegten Schiffes
     */
    public static void addShip(int l){
        Ship s = new Ship(l);
        fleet.push(s);
    }

    /**
     * set fuer selectedShip
     */
    public static void setSelectedShip(){
        selectedShip= fleet.pop();
    }

    /**
     * get fuer selectedShip
     * @return selectedShip Variable
     */
    public static Ship getSelectedShip(){
        return selectedShip;
    }


    /**
     * Speichert die ausgewaehlten Schiffslaengen in die Stacks shipLengths und shipLengthsKi
     * parallel dazu werden Schiffe mit den entsprechenden Laengen gespeichert.
     * Des Weiteren wird geprueft ob die maximal zu waehlenden anzahl an Schiffen ueberschritten wird.
     * @param spinners
     * @param count
     * @return
     */
    public static boolean setShipLengthPush(List<JSpinner> spinners, int count) {

        Iterator<JSpinner> spin = spinners.iterator();

        int shipLength;
        int shipCounter =maxShipLength ;

        while (spin.hasNext()) {
            shipLength = (int)(spin.next().getValue());

            for (int i = 0; i < shipLength; i++) {
                count -=shipLength;
                if (count < -2)
                    return false;
                shipLenghts.push(shipCounter);
                shipLengthsAI.push(shipCounter);
                //fuegt parallel zu schiffslaengen
                // entsprechende schiffe in stack zur spaeteren plazierung ein
                shipLengthsInverted.push(shipCounter);
                //addShip(shipCounter);
            }

            shipCounter--;
        }
        while( !(shipLengthsInverted.isEmpty()) ){
            addShip(shipLengthsInverted.pop());
        }
        return true;
    }

    /**
     * erzeugt aus den Laengen von Shiplengths Schiffe und speichert diese in den fleet Stack
     */
    public static void createShips(){
        while( !(shipLengthsInverted.isEmpty()) ){
            addShip(shipLengthsInverted.pop());
        }
    }

    //testfunktionen
    public static void setSelectedShip(Ship s){
        selectedShip = s;
    }
}


