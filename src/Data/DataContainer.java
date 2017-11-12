package Data;

import GUI.TableView;

import javax.swing.*;

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
    /**
     * Diese Methode setzt das uebergebene JScrollPane, welches unter den
     * Spielfeldern angezeigt wird
     *
     * @param scrollPane
     */
    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    /**
     * Diese Methode gibt das JScrollPane zurueck
     *
     * @return scrollPane
     */
    public JScrollPane getScrollPane() {
        return scrollPane;
    }
    /**
     * Diese Methode setzt die JTextArea, welche unter den Spielfeldern
     * angezeigt wird
     *
     * @param textArea
     */
    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    /**
     * Diese Methode gibt die JTextArea zurueck, welche unter den Spielfeldern
     * angezeigt wird
     *
     * @return
     */
    public JTextArea getTextArea() {
        return textArea;
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
}
