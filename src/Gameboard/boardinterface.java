package battleship;

/**
 *Arraycodierung: 0=Wasser, 1=Schiff, 2=getroffenes Schiff, 3=versenktes Schiff, 4=Wassertreffer
 * @author Felix
 */
public interface boardinterface {
   void setx();
   /* setzt x größe eingabe: int
   */
   
   void sety();
   //setzt y größe eingabe: int
   
   int getx();
   //gibt x zurück
   
   int gety();
   //gibt y zurück
   void createboard();
   /*erzeugt arrays aus x,y werten des objekts für schiffe und schüsse der gegner
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
   /*ckeckt koordinate und spieler ob wasser oder schiff plaziert ist
   bei schiff wird checkship aufgerufen
   */
   
   String checkship();
   /* checkt ob umliegende felder schiffe enthalten und ob diese getroffen sind
   wenn ja dann wird checkship mit neuer koordinate rekursiv aufgerufen um zu
   ermittlen ob schiff getroffen oder versenkt ist
   */
}
