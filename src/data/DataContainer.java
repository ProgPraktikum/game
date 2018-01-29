package data;

import ai.Ai;
import gui.TableView;

import javax.swing.*;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Diese Klasse beinhaltet saemtliche Werte wie z.B.<!-- --> Spielfedgroesse. Ebenso enthaelt diese
 * Klasse Getter und Setter Methoden fuer saemtliche Werte.
 *
 * @author Christopher Kisch, Jan Riedel, Felix Graeber
 */
public class DataContainer {

    //Gametyp
    /**
     * "ss" = schnelles Spiel
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

    /**
     * maximaler Belegungsfaktor
     */
    private static int occupancy = ((width * height) * 30 / 100);

    /**
     * zaehlt wie viele gegnericsche Schiffe, bis zum Sieg des Spielers, veresenkt werden muessen.
     */
    private static int playerWins;

    /**
     * zaehlt wie viele Schiffe, bis zum Sieg des Gegners, veresenkt werden muessen.
     */
    private static int opponentWins;

    /**
     * Setzt PlayerWins und OpponentWins Zaehler auf die groesse des fleet Stacks.
     * Sollte vor beginn der Platzierung verwendet werden, da ansonsten ein falscher wert entsteht.
     */
    public static void setWinCounters() {
        opponentWins = playerWins = fleet.size();
    }

    /**
     * Dekrementiert bei Uebergabewert 1 die playerWins Variable und bei 2 die opponentWins Variable
     *
     * @param player 1 fuer Spieler und 2 fuer Gegner
     */
    public static int decreaseCounter(int player) {
        if (player == 1) {
            return --playerWins;

        } else if (player == 2) {
            return --opponentWins;
        }
        return -1;
    }

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
     * Stack mit den Schiffsobjekten zur Platzierung auf dem Spielfeld
     */
    private static Stack<Ship> fleet;

    /**
     * Dieser Stack ist fuer die gewaehlten schiffe fuer die AI
     * speichert die Anzahl der Schiffe mit der Laenge xxx
     * /*flotte der ai identisch zur spielerflotte
     */
    private static Stack<Ship> aiFleet;

    /**
     * Ai objekt
     */
    private static Ai ai;

    /**
     * Dieser Stack ist für die gewaehlten schiffe für die Ai
     * speichert die Anzahl der Schiffe mit der Länge xxx
     */
    private static Stack<Integer> shipLengthsAI;

    /**
     * aktuelles Schiff fuer die plazierung.
     */
    private static Ship selectedShip;

    /**
     * Spieler Tabelle fuer die Oberflaeche. Stellt eigene Schiffe dar.
     */
    private static TableView table = null;

    /**
     * Spieler Tabelle fuer die Oberflaeche. Stellt eigene Schuesse dar.
     */
    private static TableView playerShootTable = null;

    /**
     * JTextArea zur in-Game Anzeige von Mitteilungen.
     */
    private static JTextArea textArea = null;

    /**
     * allowed Variable dient zu pruefen ob man schiessen darf oder nicht
     * true wenn Spieler an der Reihe ist und false wenn nicht.
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

    /**
     * Methode gibt playerShootTable vom Typ TableView zurueck
     *
     * @return playerShootTable vom Typ Tableview.
     */
    public static TableView getPlayerShootTable() {
        return playerShootTable;
    }

    /**
     * Speichert die Tableview t als playeShootTable im Datacontainer ab.
     *
     * @param t Tableview die als playerShoottable gespeichert werden soll.
     */
    public static void setPlayerShootTable(TableView t) {
        playerShootTable = t;
    }

    public static void setTextArea(JTextArea text) {
        textArea = text;
    }

    /**
     * gibt isHost Variable zurueck
     *
     * @return isHost Variable
     */
    public static boolean getIsHost() {
        return isHost;
    }

    /**
     * setzt isHost Variable
     *
     * @param b Wert auf den IsHost gesetzt wird.
     */
    public static void setIsHost(boolean b) {
        isHost = b;
    }

    /**
     * gibt Wert der isClient
     *
     * @return isClient Variable
     */
    public static boolean getIsClient() {
        return isClient;
    }

    /**
     * setzt isclient Variable
     *
     * @param b neuer Wert der isCient Variable
     */
    public static void setIsClient(boolean b) {
        isClient = b;
    }

    /**
     * gibt NetworkIP zurueck
     *
     * @return NetworkIP Variable
     */
    public static String getNetworkIP() {
        return networkIP;
    }

    /**
     * Setzt NetworkIp Variable auf uebergebenen Wert
     *
     * @param s neuer Wert fuer NetworkIP
     */
    public static void setNetworkIP(String s) {
        networkIP = s;
    }

    /**
     * Gibt die Spielfeldbreite zurueck
     */
    public static int getGameboardWidth() {
        return width;
    }

    /**
     * setzt die Spielfeldbreite
     */
    public static void setGameboardWidth(int n) {
        width = n;
    }

    /**
     * gibt die Spielfeldhoehe zurueck
     */
    public static int getGameboardHeight() {
        return height;
    }

    /**
     * setzt die Spielfeldhoehe
     */
    public static void setGameboardHeight(int n) {
        height = n;
    }

    /**
     * gibt das Spielfeld des Spielers zurueck
     *
     * @return Spielfeld Spieler
     */
    public static TableView getTable() {
        return table;
    }

    public static void setTable(TableView tabelle) {
        table = tabelle;
    }

    /**
     * gibt den gameType zurueck
     */
    public static String getGameType() {
        return gameType;
    }

    /**
     * setzt den gameType
     */
    public static void setGameType(String typ) {
        gameType = typ;
    }

    /**
     * getter fuer die Variable allowed
     */
    public static boolean getAllowed() {
        return allowed;
    }

    /**
     * setter fuer die Variable allowed
     *
     * @param x Boolean
     */
    public static void setAllowed(boolean x) {
        allowed = x;
    }

    /**
     * Setzt occupancy Variable auf 30% der Spielfeldgroesse(width * height * 0,3).
     */
    public static void setOccupancy() {
        DataContainer.occupancy = (width * height) * 30 / 100;
    }

    /**
     * gibt den max Belegungsfaktor zurueck
     *
     * @return Integer mit maximalem belegungsfaktor.
     */
    public static int getOccupancy() {
        return occupancy;
    }

    /**
     * erstellt die stacks vom Typ Integer fuer die schiffe des Spielers und der AI
     */
    public static void setShipStack() {
        shipLenghts = new Stack<>();
        shipLengthsAI = new Stack<>();
    }

    /**
     * berechnet maximale Schiffslaenge aus Hoehe und Breite des Spielfeldes
     */
    public static void setMaxShipLength() {
        int lengths[] = Game.recomendation();
        int i = lengths.length - 1;
        while (lengths[i] == 0) {
            i--;
        }
        maxShipLength = i + 2;
    }

    /**
     * Speichert übegebenen Stack vom typ Integer im Datacontainer als shipLengths Stack ab.
     * Der stack enthält Werte die zwischen 2 und maxShipLength liegen in sortierter Reihenfolge.
     *
     * @param shipLenght Stack vom Typ Integer.
     */
    public static void setShipLenghts(Stack<Integer> shipLenght) {
        shipLenghts = shipLenght;
    }

    /**
     * Speichert übegebenen Stack vom typ Integer im Datacontainer als shipLengthsAI Stack ab.
     * Der stack enthält Werte die zwischen 2 und maxShipLength liegen in sortierter Reihenfolge.
     *
     * @param shipLengthsKI Stack vom Typ Integer
     */
    public static void setShipLengthsAI(Stack<Integer> shipLengthsKI) {
        shipLengthsAI = shipLengthsKI;
    }

    public static void setFleets() {

        fleet = new Stack<>();
        aiFleet = new Stack<>();
    }

    /**
     * gibt Maximale Schiffslaenge zurueck
     *
     * @return Integer
     */
    public static int getMaxShipLength() {
        return maxShipLength;
    }

    /**
     * gibt Stack vom Typ Integer mit den Werten fuer die Schiffslaengen zurueck.
     *
     * @return Stack von Typ Integer
     */
    public static Stack<Integer> getShipLenghts() {
        return shipLenghts;
    }

    /**
     * gibt Stack vom Typ Integer mit den Werten fuer die Schiffslaengen der AI zurueck.
     *
     * @return Stack von Typ Integer
     */
    public static Stack<Integer> getShipLengthsAI() {
        return shipLengthsAI;
    }

    public static Stack<Ship> getAiFleet() {
        return aiFleet;
    }

    /**
     * get fuer fleet stack
     *
     * @return
     */
    public static Stack<Ship> getfleet() {
        return fleet;
    }

    /**
     * set fuer selectedShip
     */
    public static void setSelectedShip() {
        selectedShip = fleet.pop();
    }

    /**
     * get fuer selectedShip
     *
     * @return selectedShip Variable
     */
    public static Ship getSelectedShip() {
        return selectedShip;
    }

    /**
     * Speichert die ausgewaehlten Schiffslaengen in die Stacks shipLengths und shipLengthsKi
     * parallel dazu werden Schiffe mit den entsprechenden Laengen gespeichert.
     * Des Weiteren wird geprueft ob die maximal zu waehlenden anzahl an Schiffen ueberschritten wird.
     *
     * @param spinners
     * @param count
     * @return Gibt true zurueck wenn erfolgreich, ansonsten wird false zurueckgegeben.
     */
    public static boolean setShipLengthPush(List<JSpinner> spinners, int count) {

        Iterator<JSpinner> spin = spinners.iterator();

        int shipLength;
        int shipCounter = maxShipLength;
        for (int i = 0; i < spinners.size(); i++) {
            int number = (int) spinners.get(spinners.size() - 1 - i).getValue();
            int value = i + 2;
            for (int j = 0; j < number; j++) {
                count -= value;
                if (count < 0) {
                    return false;
                }
                shipLenghts.push(value);
                shipLengthsAI.push(value);
                Ship s = new Ship(value);
                fleet.push(s);
                Ship a = new Ship(value);
                aiFleet.push(a);
            }
        }
        return true;
    }

    /**
     * Reset static member variables to provide a clear environment for the next game.
     */
    public static void reset() {
        gameType = null;

        width = 10;
        height = 10;

        occupancy = ((width * height) * 30 / 100);

        playerWins = 0;
        opponentWins = 0;

        maxShipLength = 0;
        shipLenghts = new Stack<>();

        fleet = new Stack<>();
        aiFleet = new Stack<>();

        ai = null;

        shipLengthsAI = new Stack<>();
        selectedShip = null;

        table = null;
        playerShootTable = null;

        textArea = null;

        allowed = false;
        isHost = false;
        isClient = false;

        networkIP = null;
    }
}


