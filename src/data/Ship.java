package data;
/**
@author: Felix
@desc: Datenklasse zur zusammenfassung der Attribute eines Schiffs. Enthaelt Groesse, Position, Ausrichtung,
sowie Methoden zur veraenderung dieser Werte und zum Beschuss des Objekts
*/
public class Ship{

//atribute
	/**
	 * Laenge des Schiffes
	 */
	private int length = 0;
	/**
	 * X-Position des Schiffes Auf dem Spielfeld.
	 */
	private int xpos = 0;
	/**
	 * Y-Position des Schiffes Auf dem Spielfeld.
	 */
	private int ypos = 0;
	/**
	 * Ausrichtung des Schiffes
	 */
	private int orientation =0;
	/**
	 * Variable um Trefferpunkte des Schiffes bei Treffern herunterzuzaehlen.
	 */
	private int hitcounter =0;
//konstruktor

	/**
	 * Konstruktor des Schiffes. benoetigt Laenge als Attribut.
	 * Alle Anderen Werte werdenn standardmaessig auf 0 initialisiert.
	 * Der hitcounter wird length geichgesetzt.
	 * @param l Laenge des erzeugten Schiffes
	 */
	public Ship(int l){
		if (l != 0){
			length = l;
			hitcounter=l;
		}
	}
//methoden *not fully implemented yet

	/**
	 * veraendert X-Position des Schiffes.
	 * @param xpos neuer X-Wert
	 */
	public void setxpos(int xpos){
		this.xpos = xpos;
	}

	/**
	 * veraendert Y-Position des Schiffes.
	 * @param ypos neuer Y-Wert
	 */
	public void setypos(int ypos){
		this.ypos = ypos;
	}

	/**
	 * veraendert Laenge des Schiffes.
	 * @param length neue Laenge
	 */
	public void setLength(int length){
		this.length = length;
	}

	/**
	 * veraendert Ausrichtug des Schiffes
	 * @param orientation neue Ausrichtung
	 */
	public void setOrientation(int orientation) {
        this.orientation = orientation;
	}

	/**
	 * gibt X-Position zurueck
	 * @return X-Position des Schiffes
	 */
	public int getXpos()
    {
		return xpos;
	}

	/**
	 * gibt Y-Position des Schiffes zurueck
	 * @return Y-Position des Schiffes
	 */
	public int getYpos(){
		return ypos;
	}

	/**
	 * gibt Laenge des Schiffes zurueck
	 * @return Laenge des Schiffes
	 */
	public int getLength() {
		return length;
	}

	/**
	 * gibt Ausrichtung des Schiffes zurueck
	 * @return Ausrichtung des Schiffes
	 */
    public int getOrientation() {
        return orientation;
    }

	/**
	 * gibt Aktuellen hitcounter des Schiffes zurueck.
	 * @return gibt hitcounter zurueck
	 */
    public int getHitcounter() {
        return hitcounter;
    }

	/**
	 * dekrementiert hitcounter
	 */
	public void hit(){
	    hitcounter--;
    }
}