package Data;

import GUI.TableView;

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
    private static String gameType = null;

    //Spielfeld
    private static int spielFeldBreite = 10;
    private static int spielFeldHoehe = 10;

    // Maximale Belegungsfaktor
    private static int occupancy = (int)((spielFeldBreite * spielFeldHoehe)*0.3);

    // Variabe für die maximale Schiffslänge
    private static int maxShipLength;

    /*
    * Dieser Stack ist für die gewaehlten schiffe des Spielers.
    * speicher die Anzahl der Schiffe mit Länge xxx
    */
    private static Stack<Integer> shipLenghts;

    /*
     * Dieser Stack ist für die gewaehlten schiffe für die AI
     * speichert die Anzahl der Schiffe mit der Länge xxx
     */
    private static Stack<Integer> shipLengthsAI;

    // Spieler Table
    private static TableView table = null;

    // ScrollPane
    JScrollPane scrollPane = null;

    // TextArea
    JTextArea textArea = null;

    // allowed variable dient zu prüfen ob man schießen darf oder nicht
    private static boolean allowed;



    /*
    Gibt die Spielfeldbreite zurueck
     */
    public static int getSpielFeldBreite(){
        return  spielFeldBreite;
    }

    /*
    setzt die Spielfeldbreite
     */
    public static void setSpielFeldBreite(int n){
        spielFeldBreite = n;
    }

    /*
    gibt die Spielfeldhoehe zurueck
     */
    public static int getSpielFeldHoehe(){
        return spielFeldHoehe;
    }

    /*
    setzt die Spielfeldhoehe
     */
    public static void setSpielFeldHoehe(int n){
        spielFeldHoehe = n;
    }


    /*
    @return Spielfeld Spieler
     */
    public static TableView getTable(){
        return table;
    }

    public static void setTable(TableView tabelle){
        table = tabelle;
    }

    /*
    gibt den gameType zurück
     */
    public static String getGameType(){
        return gameType;
    }
    /*
    setzt den gameType
     */
    public static void setGameType(String typ){
        gameType = typ;
    }


    /*
    getter für die Variable allowed
     */
    public static boolean getAllowed(){
        return allowed;
    }

    /*
    setter für die Variable allowed
     */
    public static void setAllowed(boolean x){
        allowed = x;
    }

    public static void setOccupancy(int occupancy) {
        DataContainer.occupancy = occupancy;
    }
    /*
    gibt den max Belegungsfaktor zurück
     */
    public static int getOccupancy(){
        return occupancy;
    }


    /*
   erstellt die stacks für die schiffe des Spielers und der AI
    */
    public static void setShipStack(){
        shipLenghts = new Stack<Integer>();
        shipLengthsAI = new Stack<Integer>();
    }

    public static void setMaxShipLength(){

        if(DataContainer.getSpielFeldHoehe() < DataContainer.getSpielFeldBreite()){
            maxShipLength = DataContainer.getSpielFeldBreite() / 2;
        }else{
            maxShipLength = DataContainer.getSpielFeldHoehe() / 2;
        }
    }
    //getter für maxShipLength
    public static int getMaxShipLength(){
        return maxShipLength;
    }

    public static Stack<Integer> getShipLenghts(){
        return shipLenghts;
    }
    public static Stack<Integer> getShipLengthsKI(){
        return shipLengthsAI;
    }
    public void setShipLenghts(Stack<Integer>shipLenghts){
        this.shipLenghts = shipLenghts;
    }
    public void setShipLengthsKI(Stack<Integer>shipLengthsKI){
        this.shipLengthsAI = shipLengthsKI;
    }




    public static boolean setShipTypePush(List<JSpinner> spinners, int occupancy) {

        Iterator<JSpinner> ships = spinners.iterator();

        int shipLength;
        int shipCounter = maxShipLength ;

        while (ships.hasNext()) {
            shipLength = Integer.parseInt(ships.next().getValue().toString());

            for (int i = 0; i < shipLength; i++) {
                occupancy = occupancy - shipLength;
                if (occupancy < 0)
                    return false;
                shipLenghts.push(shipCounter);
                shipLengthsAI.push(shipCounter);

            }

            shipCounter--;
        }

        return true;
    }
}


