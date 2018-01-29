package util;

/**
 * Helferklasse, welche einen Tuple-artigen Datentyp implementiert.
 * Dieser kommt alleinig in der Trace Klasse zur Verfolung der Ai-Treffer auf ein Schiff zum Einsatz.
 */
public class Tuple {
    private int[] data;

    /**
     * Konstruktor. Nimmt eine beliebige Anzahl von Integer Werten auf und speichert diese.
     * @param members Zu speichernde Werte.
     */
    public Tuple(int... members) {
        this.data = members;
    }

    /**
     * Gibt den Wert des Tuples an einem bestimmten Index 'index' zurueck.
     * @param index Index
     * @return Wert an [index]
     */
    public int get(int index) {
        return data[index];
    }

    /**
     * Gibt die Groesse des Tuples zurueck.
     * @return Groesse/Laenge
     */
    public int getSize() {
        return data.length;
    }
}
