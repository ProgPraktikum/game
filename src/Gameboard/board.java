package Gameboard;

import Data.*;
import javax.xml.crypto.Data;

public class board implements boardinterface {
    /** (Java int Arrays werden default mit 0 initialisiert)
     * klasse die die datenstruktur für das spielfeld enthält und
     * entsprechende operationen zum spielen wie schüsse, treffer und das plazieren von schiffen
     * @author Felix
    */

    // MEMBER VARIABLES
    private Abstracttile playerboard[][];
    private int playershots[][];


	// CONSTRUCTOR

    public board(){
        int x = DataContainer.getSpielFeldBreite();
        int y = DataContainer.getSpielFeldHoehe();
        playerboard = new Abstracttile[y][x];
        //feld wird mit wasser gefüllt
        for(int i = 0; i< x; i++){
            for(int j= 0; j < y; j++){
                playerboard [j][i]=new Abstracttile();
            }
        }

        playershots = new int[y][x];
    }
    //methoden
    public void setPlayershots(int x, int y, int value) {
        playershots[y][x]= value;
    }

    public int getPlayershots(int x, int y) {
        return playershots[y][x];
    }

    public int checkboard(int x, int y) {
        int i;
        if (x > DataContainer.getSpielFeldBreite() || x < 0) {
            return -1;
        } else if (y > DataContainer.getSpielFeldHoehe() || y < 0) {
            return -1;
        } else {

            i = playerboard[y][x].getStatus();
			
                /*Wasser kann direkt zurueckgegeben werden, 
                bei treffer muss aber ueberprueft werden ob schiff versenkt ist*/
            switch (i) {
                case 0: //wasser
                    return 0;
                case 3: //schiff
                    ship s = playerboard[y][x].getMaster(); //hilfsvariable um leserlichkeit zu verbessern
                    playerboard[y][x].hit();
                    if (s.getHitcounter() == 0) {//wenn schiff keine ungetroffenen felder mehr hat
                        if (s.getOrientation() == 0) {
                            for (int j = s.getXpos(); j >= s.getXpos() - s.getLength() + 1; j--) {
                                playerboard[s.getYpos()][j].setStatus(2);
                                System.out.println(playerboard[s.getYpos()][j]);
                            }
                        } else if (s.getOrientation() == 1) {
                            for (int k = s.getYpos(); k >= s.getYpos() - s.getLength() + 1; k++) {
                                playerboard[k][s.getXpos()].setStatus(2);
                            }
                        }
                        return 2; //versenkt
                    } else {
                        return 1; //normaler treffer
                    }
                //} //close redundant if
                default:
                    return -1; //benötigt um kompilierfehler zu verähindern
            }
        }
    }

    public boolean place(ship s) {
        if (checkPlace(s)){
            if(s.getOrientation()==0){
                for(int i=s.getXpos();i>=s.getXpos()-s.getLength()+1;i--){
                    playerboard[s.getYpos()][i].setMaster(s);
                }
                return true;
            }
            else if(s.getOrientation()==1){
                for(int i=s.getYpos();i>=s.getYpos()-s.getLength()+1;i--){
                    playerboard[i][s.getXpos()].setMaster(s);
                }
                return true;
            }
            getPlayerboard();
        }
        return false;
    }

    public boolean checkPlace(ship s){
        if(s.getXpos() < 0 || s.getYpos() < 0){ //checkt ob schiff ausserhalb des arrays plaziert werden will
            return false;
        }
        else if(s.getXpos()>=DataContainer.getSpielFeldBreite() ||s.getYpos() >= DataContainer.getSpielFeldHoehe()){ //checkt ob schiff ausserhalb des arrays plaziert werden will
            return false;
        }
        else if (playerboard [s.getYpos()] [s.getXpos()].getStatus() == 1){ //checkt ob bereits schiff an stelle plaziert ist
            return false;
        }
        else if(s.getOrientation()==0 && s.getXpos()-s.getLength()+1 < 0){ //checkt ob schiff in waagerechter orrientation arraygrenzen verlaesst
            return false;
        }
        else if(s.getOrientation()==1 && s.getYpos()-s.getLength()+1 < 0){ //checkt ob schiff in senkrechter orrientation arraygrenzen verlaesst
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
            if (s.getXpos() == DataContainer.getSpielFeldBreite() - 1) { //schiff ist am xmax des arrays plaziert
                xmaxf = 1;
            }
            if (s.getYpos() == 0) { //schiff ist am ymin des arrays plaziert
                yminf = 1;
            }
            if (s.getYpos() == DataContainer.getSpielFeldHoehe() - 1) { //schiff ist am ymax des arrays plaziert
                ymaxf = 1;
            }
            for (int i = s.getYpos()-1+yminf; i <= s.getYpos()+1-yminf; i++) { //entsprechende eingrenzung des suchbereichs
                for (int j = s.getXpos() - 1 + xmaxf; j >= s.getXpos()-s.getLength()+xminf  ;j--) {
                    if (playerboard[i][j].getStatus() == 1) { //sucht nach schiffen
                        return false; //fund
                    }
                }
            }
            return true; //keine schiffe gefunden
        }
        else if(s.getOrientation()==1){
            if(s.getXpos()==0){ //schiff ist am xmin des arrays plaziert
                xminf=1;
            }
            if(s.getXpos()==DataContainer.getSpielFeldBreite()-1){ //schiff ist am xmax des arrays plaziert
                xmaxf=1;
            }
            if(s.getYpos()-s.getLength()+1==0){ //schiff ist am ymin des arrays plaziert
                yminf=1;
            }
            if(s.getYpos()==DataContainer.getSpielFeldHoehe()-1){ //schiff ist am ymax des arrays plaziert
                ymaxf=1;
            }
            for(int i= s.getYpos()+1-ymaxf;i>=s.getYpos()-s.getLength()+yminf;i--){ //eingrenzung des suchbereichs
                for(int j=s.getXpos()-1+xminf;j<= s.getXpos()+1-xmaxf;j++){
                    if(playerboard[i][j].getStatus()== 1){ //suche nach schiffen
                        return false; //fund
                    }
                }
            }
            return true; //keine schiffe gefunden
        }
        return true; //default val needed
    }
    //debug functions
    public void getPlayerboard(){
        System.out.println("Arraystatus:");
        for(int y=0;y<DataContainer.getSpielFeldHoehe();y++){
            for(int x=0; x< DataContainer.getSpielFeldBreite();x++){
                System.out.print(playerboard[y][x].getStatus());
            }
            System.out.println();
        }
        System.out.println("--------------");
    }
}//close class