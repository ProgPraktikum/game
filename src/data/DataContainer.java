package data;

import GUI.TableView;
import gameboard.Board;

import javax.swing.*;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Diese Klasse beinhaltet sämtliche Werte, wie z.B. Spielfedgroesse. Ebenso enthaelt diese
 * Klasse Getter und Setter Methoden für saemtliche Werte.
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
    private static int width = 10;
    private static int height = 10;

    // Maximale Belegungsfaktor
    private static int occupancy =  ((width * height)*30/100);

    // Variabe für die maximale Schiffslänge
    private static int maxShipLength;

    /*
    * Dieser Stack ist für die gewaehlten schiffe des Spielers.
    * speicher die Anzahl der Schiffe mit Länge xxx
    */
    private static Stack<Integer> shipLenghts;
/*
*Stack speichert längen in umgekehrter reihenfolge zu fleet, um dafür zu sorgen dass in fleet das größte schiff
 *  als letztes zum stack hinzugefügt wird
 */
    private static Stack<Integer> shipLengthsInverted;

    /*
    *Dieser stack ist für die aus den schiffslängen generierten schiffe
    *
    */
    private static Stack<Ship> fleet;

    /*
     * Dieser Stack ist für die gewaehlten schiffe für die AI
     * speichert die Anzahl der Schiffe mit der Länge xxx
     */
    private static Stack<Integer> shipLengthsAI;

    //aktuelles schiff für plazierung
    private static Ship selectedShip;

    // Spieler Table
    private static TableView table = null;
    private static TableView playerShootTable=null;

    //Debug board
    public static Board debugOpponent = new Board();
    // ScrollPane
    JScrollPane scrollPane = null;

    // TextArea
    JTextArea textArea = null;

    /**
      *  allowed variable dient zu prüfen ob man schießen darf oder nicht
      */

    private static boolean allowed;

    /**
     * Für Netzwerk benötigte Variablen. IP, und ob man Host oder Client ist.
     * Sowie die jeweiligen getter/setter Methoden
     */
    private static boolean isHost = false;

    private static boolean isClient = false;

    private static String networkIP = null;

    public static TableView getPlayerShootTable(){
        return playerShootTable;
    }
    public static void setPlayerShootTable(TableView t){
        playerShootTable = t;
    }
    public static boolean getIsHost(){
        return isHost;
    }

    public static void setIsHost(boolean b){
        isHost = b;
    }

    public static boolean getIsClient(){
        return isClient;
    }

    public static void setIsClient(boolean b){
        isClient = b;
    }

    public static String getNetworkIP(){
        return networkIP;
    }

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
    gibt den gameType zurück
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
    getter für die Variable allowed
     */
    public static boolean getAllowed(){
        return allowed;
    }

    /**
    setter für die Variable allowed
     */
    public static void setAllowed(boolean x){
        allowed = x;
    }

    public static void setOccupancy(int occupancy) {
        DataContainer.occupancy = occupancy;
    }
    /**
    gibt den max Belegungsfaktor zurück
     */
    public static int getOccupancy(){
        return occupancy;
    }



    /**
   erstellt die stacks für die schiffe des Spielers und der AI
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
    public static Stack<Integer> getShipLengthsInverted(){
        return shipLengthsInverted;
    }

    public static void setShipLengtsInverted(Stack<Integer>inverted){
        shipLengthsInverted = inverted;
    }

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
     * getter für maxShipLength
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
     * get für fleet stack
     * @return
     */
    public static Stack<Ship> getfleet(){
        return fleet;
    }

    /**
     * fügt schiff zu stack fleet hinzu
     * @param l
     */
    public static void addShip(int l){
        Ship s = new Ship(l);
        fleet.push(s);
    }

    /**
     * set für selectedShip
     */
    public static void setSelectedShip(){
        selectedShip= fleet.pop();
    }

    /**
     * get für selectedShip
     * @return
     */
    public static Ship getSelectedShip(){
        return selectedShip;
    }


    /**
     * Speichert die ausgewählten Schiffslängen in die Stacks shipLengths und shipLengthsKi
     * parallel dazu werden Schiffe mit den entsprechenden Längen gespeichert.
     * Des Weiteren wird geprüft ob die maximal zu wählenden anzahl an Schiffen überschritten wird.
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
                //fuegt paralell zu schiffslaengen
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


