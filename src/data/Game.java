package data;


import gameboard.Board;

import java.util.Stack;

/**
 * enthält Hauptlogik des spiels
 */
public class Game implements GameInterface {
	private boolean is_online;
	//konstruktor feldgroesse, online/ singleplayer Game
	private Board map;

	public Game(boolean o) {
		is_online = o;
		map = new Board();
	}
	//methoden

	//Plazierungs methoden


    /*
    methode verschiebt schiff an andere koordinate und überprüft ob die verschiebung valide ist
    ansonsten wird die position zurückgesetzt
    bei erfolg wird true ausgegeben und bei misserfolg false
     */
    public boolean moveShip(int x, int y){
        Ship s = DataContainer.getSelectedShip();
        int xold= s.getXpos();
        int yold =s.getYpos();
        s.setxpos(x);
        s.setypos(y);
        if(map.checkPlace(s)){
            return true;
        }else{
            s.setxpos(xold);
            s.setypos(yold);
            return false;
        }

    }
	//aendert orientierung des schiffs
	public void rotateShip(int i){
		Ship s = DataContainer.getSelectedShip();
		s.setOrientation(i);
	}
	/*
	plaziert schiff auf spielbrett
		 */
	public boolean placeShip(Ship s){
		return map.place(s);
	}

	public void generatefleet(){
		int occ=DataContainer.getOccupancy();
		Stack<Integer> lengths= new Stack<>();
		int highlen= 2;
		occ -=DataContainer.getMaxShipLength();
		while(occ>0){
			for(int i = 2; i<=highlen;i++){
				if(occ - i >= 0){
					lengths.push(i);
					occ -= i;
				}
				else if(occ - i == -1){
					lengths.push(i - 1);
					occ -= (i-1);
				}
			}
			highlen++;
		}
		lengths.push(DataContainer.getMaxShipLength()); //größtes schiff soll oben auf stack liegen
	}


	//spielmethoden
	public int shoot(int x, int y, int player) {
		if(player==0){
			return map.checkboard(x,y);
		}
		else if(player==1){
			//network foo
			int val=1; //platzhalter für netzwerkinput
			map.setPlayershots(x,y,val);//playershots speichert entsprechendes ergebniss
			return val;
		}
		else{
			return -1;
		}
	}

	public AbstractTile getPlayerboard(int x, int y){
		return map.getPlayerboardAt(x,y);
	}
	public void removeShip(Ship s) {
		switch (s.getOrientation()) {
			case 0:
				for (int i = s.getXpos(); i >= s.getXpos() - s.getLength() + 1; i--) {
					map.setPlayerboardAt(i,s.getYpos(),0);
					map.setPlayerboardAt(i,s.getYpos(),null);
				}
				break;
			case 1:
				for (int i = s.getYpos(); i >= s.getYpos() - s.getLength() + 1; i--) {
					map.setPlayerboardAt(s.getXpos(),i,0);
					map.setPlayerboardAt(s.getXpos(),i,null);
				}
				break;
			case 2:
				for (int i = s.getXpos(); i <= s.getXpos() + s.getLength() - 1; i++) {
					map.setPlayerboardAt(i,s.getYpos(),0);
					map.setPlayerboardAt(i,s.getYpos(),null);
				}
				break;
			case 3:
				for (int i = s.getYpos(); i <= s.getYpos() + s.getLength() - 1; i++) {
					map.setPlayerboardAt(s.getXpos(),i,0);
					map.setPlayerboardAt(s.getXpos(),i,null);
				}
				break;
		}
	}
	public void save(int id){
		//saving foo
	}

	public void getBoard(){
	    map.getPlayerboard();
    }
}