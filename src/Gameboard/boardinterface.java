/**
 *Arraycodierung: 0=Wasser, 1=Schiff, 2=getroffenens schiff, 3=versenktes Schiff, 4=Wassertreffer
 * @author Felix
 */

package Gameboard;
import Data.*;

public interface boardinterface {

    int getPlayershots(int x, int y);
    /* gibt wert an x,y stelle im array playershots aus
     */

    void setPlayershots(int x, int y, int value);
    /*setzt wert an x,y stelle in playershots auf value
    */

    boolean place(ship s);
    /*place plaziert ein schiff im spielfeld an der stelle x, y mit orientation o
    und länge l und gibt bei erfolg true und bei fehler false zurück
    */

    boolean checkPlace(ship s);
    /* checkplace überprüft ob ein schiff an stelle x,y mit orientation o und
    länge l ins feld plaziert werden kann
    */
    
    String checkboard(int x, int y);
    /*ckeckt koordinate und spieler ob wasser oder schiff plaziert ist
    bei schiff wird checkship aufgerufen
    */
}
