/**
*    @author Felix
**/

package Gameboard;

import Data.DataContainer;
import Data.ship;
import javax.xml.crypto.Data;

public class board implements boardinterface {
    /** (Java int Arrays werden default mit 0 initialisiert)
    fuer jeden Spieler 2 Arrays um abgegebene Schuesse lokal zu speichern **/

    // MEMBER VARIABLES
    private int playerboard[][];
    private int playershots[][];

	private DataContainer con = new DataContainer();

	// CONSTRUCTOR

    board(){
        int x = con.getSpielFeldBreite();
        int y = con.getSpielFeldHoehe();
        playerboard = new int[x][y];
        playershots = new int[x][y];
    }
		
    public String checkboard(int x, int y) {
        int i;
        if (x > con.getSpielFeldBreite() || x < 0) {
            return "Falscher X Wert";
        } else if (y > con.getSpielFeldHoehe() || y < 0) {
            return "Falscher Y Wert";
        } else {
            i = playerboard[x][y];
			
                /*Wasser kann direkt zurueckgegeben werden, 
                bei treffer muss aber ueberprueft werden ob schiff versenkt ist*/
            switch (i) {
                case 0:
                    return "Wasser";
                // case 1: return checkship(x, y); // NOT IMPLEMENTED YET => COMMENT ME OUT SO I DONT FAIL THE BUILD
            }
        }//close else block with correct input
        return "you shouldn't be here";
    }//close function //missing return function laut compiler

    public String checkboard() {return "foo";}
    public boolean place(ship s) {
        return checkPlace(s);
    }

    public boolean checkPlace(ship s){
        if(s.getXpos() < 0 || s.getYpos() < 0){ //checkt ob schiff ausserhalb des arrays plaziert werden will
            return false;
        }
        else if(s.getXpos()>con.getSpielFeldBreite() ||s.getYpos() > con.getSpielFeldHoehe()){ //checkt ob schiff ausserhalb des arrays plaziert werden will
            return false;
        }
        else if (playerboard [s.getXpos()] [s.getYpos()] == 1){ //checkt ob bereits schiff an stelle plaziert ist
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

    public String checkship(int x, int y) {return "foo";}
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
                    if (playerboard[i][j] == 1) { //sucht nach schiffen
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
                    if(playerboard[i][j]== 1){ //suche nach schiffen
                        return true; //fund
                    }
                }
            }
            return false; //keine schiffe gefunden
        }
    return false;
    }
}//close class