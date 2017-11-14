/**
*    @author Felix
**/

package Gameboard;

import Data.*;
import javax.xml.crypto.Data;

public class board implements boardinterface {
    /** (Java int Arrays werden default mit 0 initialisiert)
    fuer jeden Spieler 2 Arrays um abgegebene Schuesse lokal zu speichern **/

    // MEMBER VARIABLES
    private hullpiece playerboard[][];
    private int playershots[][];

	private DataContainer con = new DataContainer();

	// CONSTRUCTOR

    board(){
        int x = con.getSpielFeldBreite();
        int y = con.getSpielFeldHoehe();
        playerboard = new hullpiece [x][y];
        playershots = new int[x][y];
    }
    //methoden
    public void setPlayershots(int x, int y, int value) {
        playershots[x][y]= value;
    }

    public int getPlayershots(int x, int y) {
        return playershots[x][y];
    }

    public String checkboard(int x, int y) {
        int i;
        if (x > con.getSpielFeldBreite() || x < 0) {
            return "Falscher X Wert";
        } else if (y > con.getSpielFeldHoehe() || y < 0) {
            return "Falscher Y Wert";
        } else {
            ship s= playerboard[x][y].getMaster(); //hilfsvariable um leserlichkeit zu verbessern
            i = playerboard[x][y].getStatus();
			
                /*Wasser kann direkt zurueckgegeben werden, 
                bei treffer muss aber ueberprueft werden ob schiff versenkt ist*/
            switch (i) {
                case 0:
                    return "Wasser";
                case 1:
                    playerboard[x][y].hit();
                    if(s.getHitcounter()==0){
                        if(s.getOrientation()==0){
                            for(int j=s.getXpos();j==s.getXpos()-s.getLength()+1;j--){
                                playerboard[j][s.getYpos()].setStatus(3);
                            }
                        }
                        else if(s.getOrientation() == 1){
                            for(int k=s.getYpos();k==s.getYpos()-s.getLength()+1;k++){
                                playerboard[s.getXpos()][k].setStatus(3);
                            }
                        }
                        return "Versenkt";
                    }
                    else {
                        return "Treffer";
                    }
                default:
                    return "Fehler"; //i am needed to prevent an error
            }
        }
    }

    public boolean place(ship s) {
        if (checkPlace(s)){
            if(s.getOrientation()==0){
                for(int i=s.getXpos();i==s.getXpos()-s.getLength()+1;i--){
                    playerboard[i][s.getYpos()] = new hullpiece(s);
                }
                return true;
            }
            else if(s.getOrientation()==1){
                for(int i=s.getYpos();i==s.getYpos()-s.getLength()+1;i--){
                    playerboard[s.getXpos()][i] = new hullpiece(s);
                }
                return true;
            }
        }
        return false;
    }

    public boolean checkPlace(ship s){
        if(s.getXpos() < 0 || s.getYpos() < 0){ //checkt ob schiff ausserhalb des arrays plaziert werden will
            return false;
        }
        else if(s.getXpos()>con.getSpielFeldBreite() ||s.getYpos() > con.getSpielFeldHoehe()){ //checkt ob schiff ausserhalb des arrays plaziert werden will
            return false;
        }
        else if (playerboard [s.getXpos()] [s.getYpos()].getStatus() == 1){ //checkt ob bereits schiff an stelle plaziert ist
            return false;
        }
        else if(s.getOrientation()==0 && s.getXpos()-s.getLength() < 0){ //checkt ob schiff in waagerechter orrientation arraygrenzen verlaesst
            return false;
        }
        else if(s.getOrientation()==1 && s.getYpos()-s.getLength() < 0){ //checkt ob schiff in senkrechter orrientation arraygrenzen verlaesst
            return false;
        }
        else {
            return collisionCheck(s); //checkt ob umliegende felder bereits bessetzt sind
        }
    }

    private boolean  collisionCheck(ship s){
        int xminf = 0;
        int xmaxf = 0;
        int yminf = 0;
        int ymaxf = 0;
        if(s.getOrientation()== 0) {
            if (s.getXpos() - s.getLength() + 1 == 0) { //schiff ist am xmin des arrays plaziert
                xminf = 1;
            }
            if (s.getXpos() == con.getSpielFeldBreite() - 1) { //schiff ist am xmax des arrays plaziert
                xmaxf = 1;
            }
            if (s.getYpos() == 0) { //schiff ist am ymin des arrays plaziert
                yminf = 1;
            }
            if (s.getYpos() == con.getSpielFeldHoehe() - 1) { //schiff ist am ymax des arrays plaziert
                ymaxf = 1;
            }
            for (int i = s.getXpos()+1-xmaxf; i == s.getXpos()-s.getLength()-1+xmaxf+xminf; i--) { //entsprechende eingrenzung des suchbereichs
                for (int j = s.getYpos() - 1 + yminf; j == s.getYpos() + 1 - ymaxf;j++) {
                    if (playerboard[i][j].getStatus() == 1) { //sucht nach schiffen
                        return true; //fund
                    }
                }
            }
            return false; //keine schiffe gefunden
        }
        else if(s.getOrientation()==1){
            if(s.getXpos()==0){ //schiff ist am xmin des arrays plaziert
                xminf=1;
            }
            if(s.getXpos()==con.getSpielFeldBreite()-1){ //schiff ist am xmax des arrays plaziert
                xmaxf=1;
            }
            if(s.getYpos()-s.getLength()+1==0){ //schiff ist am ymin des arrays plaziert
                yminf=1;
            }
            if(s.getYpos()==con.getSpielFeldHoehe()-1){ //schiff ist am ymax des arrays plaziert
                ymaxf=1;
            }
            for(int i= s.getXpos()-1+xminf;i==s.getXpos()+1-xmaxf;i++){ //eingrenzung des suchbereichs
                for(int j=s.getYpos()+1-ymaxf;j== s.getYpos()-s.getLength()-1+yminf+ymaxf;j++){
                    if(playerboard[i][j].getStatus()== 1){ //suche nach schiffen
                        return true; //fund
                    }
                }
            }
            return false; //keine schiffe gefunden
        }
    return false;
    }
}//close class