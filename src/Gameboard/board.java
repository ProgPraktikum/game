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
        /*if (playerboard [s.getXpos()] [s.getYpos()] == 1){
            return false;
        }
        else {
            return  true;
        }*/
        return true;
    }
    public String checkship() {return "foo";}
}//close class