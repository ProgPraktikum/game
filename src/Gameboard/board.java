/**
*    @author Felix
**/

package Gameboard;

import Data.DataContainer;

import javax.xml.crypto.Data;

public class board implements boardinterface {
    /** (Java int Arrays werden default mit 0 initialisiert)
    fuer jeden Spieler 2 Arrays um abgegebene Schuesse lokal zu speichern **/

    // MEMBER VARIABLES
    private int playerboard0[][];
    private int playerboard1[][];
    private int playershots0[][];
	private int playershots1[][];

	private DataContainer con = new DataContainer();

	// CONSTRUCTOR

    board(){
        int x = con.getSpielFeldBreite();
        int y = con.getSpielFeldHoehe();
        playerboard0 = new int[x][y];
        playerboard1 = new int[x][y];
        playershots0 = new int[x][y];
        playershots1 = new int[x][y];
    }


    // PUBLIC METHODS

    public String checkboard(int x, int y, int player) {
        int i;
        if (x > con.getSpielFeldBreite() || x < 0) {
            return "Falscher X Wert";
        }
        else if (y > con.getSpielFeldHoehe() ||y < 0) {
            return "Falscher Y Wert";
        }
        else {
            if (player == 0) {
                i = playerboard0[x][y];
			
                /*Wasser kann direkt zurueckgegeben werden, 
                bei treffer muss aber ueberprueft werden ob schiff versenkt ist*/

                switch (i) {
                    case 0: return "Wasser";
                    // case 1: return shipcheck(x, y, player); // NOT IMPLEMENTED YET => COMMENT ME OUT SO I DONT FAIL THE BUILD
                }
            }
            else if (player == 1) {
                i = playerboard1[x][y];
                switch (i) {
                    case 0: return "Wasser";
                    // case 1: return shipcheck(x, y, player); // NOT IMPLEMENTED YET => COMMENT ME OUT SO I DONT FAIL THE BUILD
                }
            }
            else {
                return "ungueltiger Spieler";
            }			
        } //close else block with correct input
        return "you shouldn't be here";
    }//close function //missing return function laut compiler

    public boolean place(int x, int y, int player) {
        return true;}
    public boolean checkplace() {return true;}
    public String checkship(int x, int y, int player) {return "foo";}
}//close class