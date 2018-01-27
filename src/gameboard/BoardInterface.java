/**
 *Arraycodierung: 0=Wasser, 1=treffer, 2=versenkt, 3=schiff, 4=Wassertreffer
 * @author Felix
 */

package gameboard;
import data.*;

/**
 * @author Felix
 * zugehoeriges Interface zum Board
 */
public interface BoardInterface {

    int getPlayershots(int x, int y);
    /* gibt wert an x,y stelle im array playershots aus
     */

    void setPlayershots(int x, int y, int value);
    /*setzt wert an x,y stelle in playershots auf value
    */

    boolean place(Ship s);
    /*place plaziert ein schiff im spielfeld an der stelle x, y mit orientation o
    und laenge l und gibt bei erfolg true und bei fehler false zurueck
    */

    boolean checkPlace(Ship s);
    /* checkplace ueberprueft ob ein schiff an stelle x,y mit orientation o und
    laenge l ins feld plaziert werden kann
    */
    
    int checkboard(int x, int y);
    /*ckeckt koordinate und spieler ob wasser oder schiff plaziert ist
    bei schiff wird checkship aufgerufen
    */
}
