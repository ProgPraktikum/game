package gameboard;

import data.*;
import java.util.Arrays;
public class Board implements BoardInterface {
    /** (Java int Arrays werden default mit 0 initialisiert)
     * klasse die die datenstruktur für das spielfeld enthält und
     * entsprechende operationen zum spielen wie schüsse, treffer und das plazieren von schiffen
     * @author Felix
    */

    // MEMBER VARIABLES
    private Tile playerboard[][];
    private int playershots[][];


	// CONSTRUCTOR

    public Board(){
        int x = DataContainer.getGameboardWidth();
        int y = DataContainer.getGameboardHeight();
        playerboard = new Tile[y][x];
        Arrays.fill(playerboard, new Tile());
        //feld wird mit wasser gefüllt
       /* for(int i = 0; i< x; i++){
            for(int j= 0; j < y; j++){
                playerboard [j][i]=new Tile();
            }
        }
*/
        playershots = new int[y][x];
        Arrays.fill(playershots, 9);
    }
    //methoden
    public void setPlayershots(int x, int y, int value) {
        playershots[y][x]= value;
    }

    public int getPlayershots(int x, int y) {
        return playershots[y][x];
    }

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
                        switch (orr){
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
                    return -1; //benötigt um kompilierfehler zu verhindern
            }
        }
    }

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
        else if(s.getOrientation()==0 && s.getXpos()-s.getLength()+1 < 0){ //checkt ob schiff in waagerechter orrientation arraygrenzen verlaesst
            return false;
        }
        else if(s.getOrientation()==1 && s.getYpos()-s.getLength()+1 < 0){ //checkt ob schiff in senkrechter orrientation arraygrenzen verlaesst
            return false;
        }
        else if(s.getOrientation()==2 && s.getXpos()+s.getLength()-1 > DataContainer.getGameboardWidth()-1) {
            return false;
        }
        else if(s.getOrientation()==3 && s.getYpos()+s.getLength()-1 >DataContainer.getGameboardHeight()-1) {
            return false;
        }
        else{
            return collisionCheck(s); //checkt ob umliegende felder bereits bessetzt sind
        }
    }

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

    public Tile getPlayerboardAt(int x, int y){
       return playerboard[y][x];
    }

    public void setPlayerboardAt(int x, int y, int value) {
                playerboard[y][x].setStatus(value);
    }
    public void setPlayerboardAt(int x, int y, Ship master){
        playerboard[y][x].setMaster(master);
    }

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