package gameboard;

import data.*;

import java.util.Arrays;

public class Board implements BoardInterface {
    /**
     * klasse die die Datenstruktur fuer das Spielfeld enthaelt und
     * entsprechende Operationen zum Spielen wie Schuesse, Treffer und das Plazieren von Schiffen
     * @author Felix
    */

    // MEMBER VARIABLES
    /**
     * zweidimensionales Array bestehend aus Tile-Objekten. Groesse entspricht der des Spielfeldes.
     */
    private Tile playerboard[][];
    /**
     * int Array um die Ergebnisse der eigenen Schuesse zu speichern.
     *  Groesse entspricht der des Spielfeldes
     */
    private int playershots[][];


	// CONSTRUCTOR

    /**@desc Konstruktor fuer die Board Klasse
     * Werte fuer Array Dimensionen werden aus Datacontainer gelesen
     * und Arrays mit entsprechenden Werten initialisiert
     */
    public Board(){
        int x = DataContainer.getGameboardWidth();
        int y = DataContainer.getGameboardHeight();
        playerboard = new Tile[y][x];
        playershots = new int[y][x];
        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++ ) {
                playerboard[j][i]= new Tile();
                playershots [j][i]= 9;
            }
        }
    }
    //methoden

    /**
     *@desc setzt Playershots-Array an der angegebenen Stelle auf den uebergebenden Wert
     * @param x X-Wert der zu aendernden Koordinate im Playershots-Array
     * @param y Y-Wert der zu aendernden Koordinate im Playershots-Array
     * @param value Wert auf den die Koordinate gesetzt werden soll
     */
    public void setPlayershots(int x, int y, int value) {
        playershots[y][x]= value;
    }

    /**
     * @desc gibt Wert an Stelle x, y im playershots-array zurueck
     * @param x X-Wert der rueckzugebenden Koordinate im Playershots-Array
     * @param y Y-Wert der rueckzugebenden Koordinate im Playershots-Array
     * @return gibt den int Wert an der x, y Stelle im Array zurueck
     */
    public int getPlayershots(int x, int y) {
        return playershots[y][x];
    }

    /**
     * @desc ueberprueft spielbrett an der stelle x, y auf Wasser bzw Schiffe.
     * Wenn ein Schiff getroffen wird wird evaluiert ob es nur getroffen oder versenkt wird
     * @param x X-Wert der zu pruefenden Koordinate im playerBoard-Array
     * @param y Y-Wert der zu pruefenden Koordinate im playerBoard-Array
     * @return gibt einen int Wert zurueck: 0 fuer Wasser, 1 fuer Treffer und 2 fuer versenkt. Im Fehlerfall wird -1 ausgegeben
     */
    public int checkboard(int x, int y){
        int i;
        if (x > DataContainer.getGameboardWidth() || x < 0) {
            return -1;
        } else if (y > DataContainer.getGameboardHeight() || y < 0) {
            return -1;
        } else {

            i = playerboard[y][x].getStatus();
			
                /*Wasser kann direkt zurueckgegeben werden, 
                bei treffer muss aber ueberprueft werden ob schiff versenkt ist*/
            switch (i) {
                case 0: //wasser
                    return 0;
                case 3: //schiff
                    Ship s = playerboard[y][x].getMaster(); //hilfsvariable um leserlichkeit zu verbessern
                    playerboard[y][x].hit();
                    if (s.getHitcounter() == 0) {//wenn schiff keine ungetroffenen felder mehr hat
                        int orr= s.getOrientation();
                        int pos;
                        switch (orr){ // ändert status der tiles die auf das versenkte schiff verweisen
                            case 0:
                                for (pos = s.getXpos(); pos >= s.getXpos() - s.getLength() + 1; pos--) {
                                    playerboard[s.getYpos()][pos].setStatus(2);
                                }
                                break;
                            case 1:
                                for (pos = s.getYpos(); pos >= s.getYpos() - s.getLength() + 1; pos--) {
                                    playerboard[pos][s.getXpos()].setStatus(2);
                                }
                                break;
                            case 2:
                                for(pos = s.getXpos(); pos <=s.getXpos()+s.getLength()-1;pos++){
                                    playerboard[s.getYpos()][pos].setStatus(2);
                                }
                                break;
                            case 3:
                                for(pos =s.getYpos(); pos <= s.getYpos()+s.getLength()-1; pos++){
                                    playerboard[pos][s.getXpos()].setStatus(2);
                                }
                        }
                        return 2; //versenkt
                    } else {
                        playerboard[y][x].setStatus(1);
                        return 1; //normaler treffer
                    }
                default:
                    return -1; //benoetigt um kompilierfehler zu verhindern
            }
        }
    }

    /**
     * @desc veraendert den Ausgangspunkt eines Schiffsobjekts auf die uebergebene Koordinate.
     * @param x X-Wert der uebergebenen Koordinate
     * @param y Y-Wert der uebergebenen Koordinate
     * @param s zu veraenderndes Schiffsobjekt
     * @return gibt bei erfolgreicher Verschiebung true zurueck, bei unmoeglicer Operation z.B ArrayIndexOutOfBounds wird false zurueckgegeben
     */
    public boolean moveShip(int x, int y, Ship s){
        int xold= s.getXpos();
        int yold =s.getYpos();
        s.setxpos(x);
        s.setypos(y);
        if(checkPlace(s)){
            return true;
        }else{
            s.setxpos(xold);
            s.setypos(yold);
            return false;
        }
    }

    /**
     * @desc rotiert Schiff auf uebergebenen Orientierung zwischen 0 und 3.
     *  Werte groesser 3 werden mit Mod 4 auf den Wertebereich eingegrenzt.
     * @param i int Wert der Mod 4 gerechnet die neue Orientierung des uebergebenen Schiffs ergibt
     * @param s uebergebenes Schiffsobjekt dessen Orrientierung geaendert wird.
     */
    public void rotateShip(int i, Ship s){
        s.setOrientation(i%4);
    }

    /**
     * @desc loescht referenzen auf uebergebenes Schiff aus playerBoard Array
     * @param s zu loeschendes Schiff
     */
    public void removeShip(Ship s) {
        switch (s.getOrientation()) {
            case 0:
                for (int i = s.getXpos(); i >= s.getXpos() - s.getLength() + 1; i--) {
                    setPlayerboardAt(i,s.getYpos(),0);
                    setPlayerboardAt(i,s.getYpos(),null);
                }
                break;
            case 1:
                for (int i = s.getYpos(); i >= s.getYpos() - s.getLength() + 1; i--) {
                    setPlayerboardAt(s.getXpos(),i,0);
                    setPlayerboardAt(s.getXpos(),i,null);
                }
                break;
            case 2:
                for (int i = s.getXpos(); i <= s.getXpos() + s.getLength() - 1; i++) {
                    setPlayerboardAt(i,s.getYpos(),0);
                    setPlayerboardAt(i,s.getYpos(),null);
                }
                break;
            case 3:
                for (int i = s.getYpos(); i <= s.getYpos() + s.getLength() - 1; i++) {
                    setPlayerboardAt(s.getXpos(),i,0);
                    setPlayerboardAt(s.getXpos(),i,null);
                }
                break;
        }
    }

    /**
     * @desc wenn der Aufruf von Checkplace true liefert wird das uebergebene Schiff an der im Schiffsobjekt
     * gespeicherten Startkoordinate entlang der Orrientierung des Schiffs und mit der entsprechenden Laenge platziert und true zurueckgegeben. Bei false wird ebenfalls false zurueckgegeben
     * @param s zu platzierendes Schiff
     * @return gibt true bei erfolgreicher Platzierung zurueck und False falls platzierung unmoeglich
     */
    public boolean place(Ship s) {
        if (checkPlace(s)){
            switch (s.getOrientation()) {
                case 0:
                    for (int i = s.getXpos(); i >= s.getXpos() - s.getLength() + 1; i--) {
                        playerboard[s.getYpos()][i].setMaster(s);
                    }
                    break;
                case 1:
                    for (int i = s.getYpos(); i >= s.getYpos() - s.getLength() + 1; i--) {
                        playerboard[i][s.getXpos()].setMaster(s);
                    }
                    break;
                case 2:
                    for (int i = s.getXpos(); i <= s.getXpos() + s.getLength() - 1; i++) {
                        playerboard[s.getYpos()][i].setMaster(s);
                    }
                    break;
                case 3:
                    for (int i = s.getYpos(); i <= s.getYpos() + s.getLength() - 1; i++) {
                        playerboard[i][s.getXpos()].setMaster(s);
                    }
                    break;
            }
            getPlayerboard();
            return true;
        }
        return false;
    }

    /**
     * @desc ueberprueft ob uebergebenes Schiff mit den im Objekt gespeicherten Parametern platziert werden darf ohne Spielregeln oder Arraygrenzen zu verletzen
     * @param s zu ueberpruefendes Schiff
     * @return gibt true zurueck wenn platzierung gueltig ist und false wenn sie ungueltig waere
     */
    public boolean checkPlace(Ship s){
        if(s.getXpos() < 0 || s.getYpos() < 0){ //checkt ob schiff ausserhalb des arrays plaziert werden will
            return false;
        }
        else if(s.getXpos()>=DataContainer.getGameboardWidth() ||s.getYpos() >= DataContainer.getGameboardHeight()){ //checkt ob schiff ausserhalb des arrays plaziert werden will
            return false;
        }
        else if (playerboard [s.getYpos()] [s.getXpos()].getStatus() == 1){ //checkt ob bereits schiff an stelle plaziert ist
            return false;
        }
        else if(s.getOrientation()==0 && s.getXpos()-s.getLength()+1 < 0){ //checkt ob schiff in  orrientation 0 arraygrenzen verlaesst
            return false;
        }
        else if(s.getOrientation()==1 && s.getYpos()-s.getLength()+1 < 0){ //checkt ob schiff in  orrientation 1 arraygrenzen verlaesst
            return false;
        }
        else if(s.getOrientation()==2 && s.getXpos()+s.getLength()-1 > DataContainer.getGameboardWidth()-1) { //checkt ob schiff in  orrientation 2 arraygrenzen verlaesst
            return false;
        }
        else if(s.getOrientation()==3 && s.getYpos()+s.getLength()-1 >DataContainer.getGameboardHeight()-1) { //checkt ob schiff in  orrientation 3 arraygrenzen verlaesst
            return false;
        }
        else{
            return collisionCheck(s); //checkt ob umliegende felder bereits bessetzt sind
        }
    }

    /**
     * @desc hilfsmethode fuer checkplace ueberprueft ob Schiff beimm Platzieren eine Kollision verursachen wuerde
     * @param s zu ueberpruefendes Schiff
     * @return gibt bei Kollision false und ohne Kollision true zurueck
     */
    private boolean  collisionCheck(Ship s){
        int xminf = 0;
        int xmaxf = 0;
        int yminf = 0;
        int ymaxf = 0;
        if(s.getOrientation() == 0) {
            if (s.getXpos() - s.getLength() + 1 == 0) { //schiff ist am xmin des arrays plaziert
                xminf = 1;
            }
            if (s.getXpos() == DataContainer.getGameboardWidth() - 1) { //schiff ist am xmax des arrays plaziert
                xmaxf = 1;
            }
            if (s.getYpos() == 0) { //schiff ist am ymin des arrays plaziert
                yminf = 1;
            }
            if (s.getYpos() == DataContainer.getGameboardHeight() - 1) { //schiff ist am ymax des arrays plaziert
                ymaxf = 1;
            }
            for (int i = s.getYpos() - 1 + yminf; i <= s.getYpos() + 1 - ymaxf; i++) { //entsprechende eingrenzung des suchbereichs
                for (int j = s.getXpos() + 1 - xmaxf; j >= s.getXpos() - s.getLength() + xminf  ;j--) {
                    if (playerboard[i][j].getStatus() == 3) { //sucht nach schiffen
                        return false; //fund
                    }
                }
            }
            return true; //keine schiffe gefunden
        }
        else if(s.getOrientation() == 1){
            if(s.getXpos() == 0){ //schiff ist am xmin des arrays plaziert
                xminf=1;
            }
            if(s.getXpos() == DataContainer.getGameboardWidth() - 1){ //schiff ist am xmax des arrays plaziert
                xmaxf=1;
            }
            if(s.getYpos() - s.getLength() + 1 == 0){ //schiff ist am ymin des arrays plaziert
                yminf=1;
            }
            if(s.getYpos() == DataContainer.getGameboardHeight() - 1){ //schiff ist am ymax des arrays plaziert
                ymaxf=1;
            }
            for(int i = s.getYpos() + 1 - ymaxf; i >= s.getYpos() - s.getLength() + yminf; i--){ //eingrenzung des suchbereichs
                for(int j = s.getXpos() - 1 + xminf; j <= s.getXpos() + 1 - xmaxf; j++){
                    if(playerboard[i][j].getStatus() == 3){ //suche nach schiffen
                        return false; //fund
                    }
                }
            }
            return true; //keine schiffe gefunden
        }
        else if(s.getOrientation() == 2){
            if(s.getXpos() == 0){
                xminf=1;
            }
            if(s.getXpos() + s.getLength() - 1 == DataContainer.getGameboardWidth() - 1){
                xmaxf=1;
            }
            if(s.getYpos() == 0){
                yminf=1;
            }
            if(s.getYpos() == DataContainer.getGameboardHeight() - 1){
                ymaxf=1;
            }
            for(int i= s.getXpos() - 1 + xminf;i <= s.getXpos()+ s.getLength() - xmaxf; i++){
                for(int j= s.getYpos() - 1 + yminf; j <= s.getYpos() + 1 - ymaxf; j++){
                    if(playerboard[j][i].getStatus()==3){
                        return  false;
                    }
                }
            }
            return true;
        }
        else if(s.getOrientation() == 3){
            if(s.getXpos() == 0){
                xminf=1;
            }
            if(s.getXpos() == DataContainer.getGameboardWidth() - 1){
                xmaxf=1;
            }
            if(s.getYpos() == 0){
                yminf=1;
            }
            if(s.getYpos() + s.getLength() - 1 == DataContainer.getGameboardHeight() - 1){
                ymaxf=1;
            }
            for(int i= s.getXpos() - 1 + xminf; i <= s.getXpos() + 1 - xmaxf; i++){
                for(int j=s.getYpos()-1+yminf; j <= s.getYpos() + s.getLength() - ymaxf; j++){
                    if(playerboard[j][i].getStatus() == 3){
                        return false;
                    }
                }
            }
            return true;
        }
        return true; //default val needed
    }

    /**
     * gibt playerboard an stelle x, y zurueck
     * @param x X-Wert der rueckzugebenden Koordinate im playerBoard-Array
     * @param y Y-Wert der rueckzugebenden Koordinate im playerBoard-Array
     * @return gibt das Tile Objekt, welches an der entsprechenden Stelle im Array liegt zurueck.
     */
    public Tile getPlayerboardAt(int x, int y){
       return playerboard[y][x];
    }

    /**
     * @desc aendert den Wert des Tile Objekts an der Stelle x,y im PlayerBoard-Array auf den uebergebenen Wert.
     * @param x X-Wert des zu aendernden Objekts im playerBoard-Array
     * @param y Y-Wert des zu aendernden Objekts im playerBoard-Array
     * @param value int Wert auf den das der Status des Objekts geaendert werden soll.
     */
    public void setPlayerboardAt(int x, int y, int value) {
                playerboard[y][x].setStatus(value);
    }

    /**
     * @desc aendert den master des Tile Objekts an der Stelle x,y im PlayerBoard-Array auf das uebergebene Objekt.
     * @param x X-Wert des zu aendernden Objekts im playerBoard-Array
     * @param y Y-Wert des zu aendernden Objekts im playerBoard-Array
     * @param master Schiffsobjekt auf das das master Attribut des Tiles gesetzt werden soll
     */
    public void setPlayerboardAt(int x, int y, Ship master){
        playerboard[y][x].setMaster(master);
    }

    /**
     * @desc gibt master des Tiles an der Stelle x,y im playerBoard zurueck
     * @param x X-Wert des zu Tile Objekts im playerBoard-Array
     * @param y Y-Wert des zu Tile Objekts im playerBoard-Array
     * @return wert des Masterattributs vom Tile an der uebergebenen Stelle im playerBoard Array.
     */
    public Ship getMasterAt(int x,int y){
        return playerboard[y][x].getMaster();
    }
    //debug functions
    public void getPlayerboard(){
        System.out.println("Arraystatus:");
        for(int y = 0; y<DataContainer.getGameboardHeight(); y++){
            for(int x = 0; x< DataContainer.getGameboardWidth(); x++){
                System.out.print(playerboard[y][x].getStatus());
            }
            System.out.println();
        }
        System.out.println("--------------");
    }
}//close class