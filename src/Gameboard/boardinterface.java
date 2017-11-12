/**
 *Arraycodierung: 0=Wasser, 1=Treffer, 2=Schiff, 3=versenktes Schiff, 4=Wassertreffer
 * @author Felix
 */

package Gameboard;
import Data.ship;

public interface boardinterface {

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

    String checkship(int x, int y, int player);
    /* checkt ob umliegende felder schiffe enthalten und ob diese getroffen sind
    wenn ja dann wird checkship mit neuer koordinate rekursiv aufgerufen um zu
    ermittlen ob schiff getroffen oder versenkt ist
    */
}
