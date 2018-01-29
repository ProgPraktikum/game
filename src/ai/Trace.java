package ai;

import util.Tuple;
import java.util.ArrayList;

/**
 * Helferklasse, welche einen Datentyp Trace implementiert.
 * Mit diesem werden die Treffer der Ai auf ein Schiff geloggt, um aus diesen Daten die weiteren Zuege zu bestimmen.
 */

public class Trace {
    /**
     * Liste von Werten des Typs 'Tuple', welche (hier) Koordinaten beinhalten.
     */
    private ArrayList<Tuple> values;

    /**
     * Konstruktor. Initialisiert ArrayList, welche Koordinaten als Tuples speichert.
     */
    public Trace() {
        values = new ArrayList<>();
    }

    /**
     * Fuegt eine neue Koordinate hinzu.
     * @param x X-Koordinate
     * @param y Y-Koordinate
     */
    public void addTile(int x, int y) {
        values.add(new Tuple(x, y));
    }

    /**
     * Gibt Koordinate/Feld an einem bestimmten Index 'index' zurueck.
     * @param i Index
     * @return Array von zwei Koordinaten des Typs 'Integer'.
     */
    public int[] getTile(int i) {
        int[] result = new int[2];

        Tuple value = values.get(i);
        result[0] = value.get(0);
        result[1] = value.get(1);

        return result;
    }

    /**
     * Gibt Laenge des Trace zurueck.
     * @return
     */
    public int getSize() {
        return values.size();
    }

    /**
     * Leert den Trace.
     */
    public void clear() {
        values.clear();
    }
}
