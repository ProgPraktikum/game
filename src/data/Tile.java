package data;

/**
 * @author: Felix Graeber
 * @desc: Hilfsklasse um einzelne Spielbrettfelder zu erzeugen. ermoeglicht Verknuepfung
 * von mehreren Stellen im Array mit demselben Schiff, sodass Treffer korrekt verknuepft werden koennen.
 * Enthaelt ausserdem einen Statuswert, der Agibt was sich an der entsprechenden Stelle auf dem Spielfeld befindet.
 */
public class Tile {
    /**
     * Statuswert: 0 Wasser, 1 Treffer, 2 Versenkt,3 Schiff
     */
    private int status;
    /**
     * zum Tile gehoeries Schiffsobjekt
     */
    private Ship master;

    /**
     * Konstruktor, setzt Status auf 0 und Master auf null, erzeugt also ein leeres Tile auf dem Spielfeld
     */
    public Tile() {
        status = 0;
        master = null;
    }

    /**
     * gibt Statuswert zurueck
     *
     * @return Statuswert des Tiles
     */
    public int getStatus() {
        return status;
    }

    /**
     * Setzt Statusattribut auf uebergebenen Wert
     *
     * @param status Wert auf den das Statusattribut gesetzt wird.
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * methode um Tile zu treffen. gibt wenn kein master gesetzt ist passiert nichts. Ansonsten wird die hit-methode des masters aufgerufen.
     */
    public void hit() {
        if (status == 3) {
            master.hit();
        }
    }

    /**
     * gibt Master zurueck
     *
     * @return master des Tiles
     */
    public Ship getMaster() {
        return master;
    }

    /**
     * Setzt master Attribut auf uebergebenen Wert.
     * Status wird entsprechend auch auf 0 oder 3 gesetzt.
     *
     * @param master Wert auf den master gesetzt werden soll.
     */
    public void setMaster(Ship master) {
        this.master = master;
        if (master != null) {
            setStatus(3);
        } else {
            setStatus(0);
        }
    }
}


