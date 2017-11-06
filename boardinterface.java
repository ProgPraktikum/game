package battleship;

/**
 *
 * @author Felix
 */
public interface boardinterface {
   void setsize();
   /* setzt feldgröße auf x,y
   */
    boolean place(); 
   /*place plaziert ein schiff im spielfeld an der stelle x, y mit orientation o
   und länge l und gibt bei erfolg true und bei fehler false zurück
   */
   boolean checkplace();
   /* checkplace überprüft ob ein schiff an stelle x,y mit orientation o und
   länge l ins feld plaziert werden kann
   */
   String checkboard();
   /*ckeckt koordinate ob wasser oder schiff plaziert ist
   bei schiff wird checkship aufgerufen
   */
   String checkship();
   /* checkt ob umliegende felder schiffe enthalten und ob diese getroffen sind
   wenn ja dann wird checkship mit neuer koordinate rekursiv aufgerufen um zu
   ermittlen ob schiff getroffen oder versenkt ist
   */
}
