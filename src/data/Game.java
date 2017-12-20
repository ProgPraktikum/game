package data;


import GUI.TableView;
import gameboard.Board;

import java.util.Stack;

/**
 * enthält Hauptlogik des spiels
 */
public class Game{


	private static Board map= new Board();


	//methoden

	//Plazierungs methoden


    /*
    methode verschiebt schiff an andere koordinate und überprüft ob die verschiebung valide ist
    ansonsten wird die position zurückgesetzt
    bei erfolg wird true ausgegeben und bei misserfolg false
     */
    public static boolean moveShip(int x, int y){
        Ship s = DataContainer.getSelectedShip();
        return map.moveShip(x,y,s);

    }
	//aendert orientierung des schiffs
	public static void rotateShip(int i){
		Ship s = DataContainer.getSelectedShip();
		map.rotateShip(i, s);
	}
	/*
	plaziert schiff auf spielbrett
		 */
	public static boolean placeShip(Ship s){
		return map.place(s);
	}

	public static void generatefleet(){
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
	public static int shoot(int x, int y) {
		if(DataContainer.getAllowed()) {
			//getHit(x,y);
			int val;
			if(DataContainer.getGameType().equals("ss") ||DataContainer.getGameType().equals("bdf")) {
				val=0;
				//DEBUG
				val = map.checkboard(x,y);
			}
			else if (DataContainer.getGameType().equals("mp")) {
				val=0;
			}
			else{
				return -1;
			}
			map.setPlayershots(x, y, val);
			DataContainer.getPlayerShootTable().setValueAt(val,y,x);

			if(val==2) {
				displayHits(x,y,0,DataContainer.getPlayerShootTable());
			}
			return val;
		}
		else{
			return -1;
		}
	}

	public static int getHit(int x, int y){
		int i = map.checkboard(x,y);
		DataContainer.getTable().setValueAt(i,y,x);
		if(i==0){
			DataContainer.setAllowed(true);
		}
		if(i==2){
			displayHits(x,y,0,DataContainer.getTable());
		}
		return i;
	}

	public static Tile getPlayerboard(int x, int y){
		return map.getPlayerboardAt(x,y);
	}
	public static void removeShip(Ship s) {
		map.removeShip(s);
	}

	/**
	 *
	 * @param x
	 * @param y
	 * @param direction
	 * @param table
	 */
	private static void displayHits(int x, int y,int direction, TableView table) {
		//todo optimize corner checks
		table.setValueAt(2, y, x);
		if (x - 1 >= 0) {
			if (table.getValueAt(y, x - 1).equals(1) && (direction == 0 || direction == 1)) {
				displayHits(x - 1, y, 1, table);
			} else if (table.getValueAt(y, x - 1).equals(9)) {
				table.setValueAt(0, y, x - 1);
			}
		}
		if (x + 1 < DataContainer.getGameboardWidth()) {
			if (table.getValueAt(y, x + 1).equals(1) && (direction == 0 || direction == 2)) {
				displayHits(x + 1, y, 2, table);
			} else if (table.getValueAt(y, x + 1).equals(9)) {
				table.setValueAt(0, y, x + 1);
			}
		}
		if (y - 1 >= 0) {
			if (table.getValueAt(y - 1, x).equals(1) && (direction == 0 || direction == 3)) {
				displayHits(x, y - 1, 3, table);
			} else if (table.getValueAt(y - 1, x).equals(9)) {
				table.setValueAt(0, y - 1, x);
			}
		}
		if (y + 1 < DataContainer.getGameboardHeight()) {
			if (table.getValueAt(y + 1, x).equals(1) && (direction == 0 || direction == 4)) {
				displayHits(x, y + 1, 4, table);
			} else if (table.getValueAt(y + 1, x).equals(9)) {
				table.setValueAt(0, y + 1, x);
			}
		}
		if (x - 1 >= 0 && y - 1 >= 0) {
			if (table.getValueAt(y - 1, x - 1).equals(9)) {
				table.setValueAt(0, y - 1, x - 1);
			}
		}
		if (x + 1 < DataContainer.getGameboardWidth() && y - 1 >= 0) {
			if (table.getValueAt(y - 1, x + 1).equals(9)) {
				table.setValueAt(0, y - 1, x + 1);
			}
		}
		if (x + 1 < DataContainer.getGameboardWidth() && y + 1 < DataContainer.getGameboardHeight()) {
			if (table.getValueAt(y + 1, x + 1).equals(9)) {
				table.setValueAt(0, y + 1, x + 1);
			}
		}
		if (x - 1 >= 0 && y + 1 < DataContainer.getGameboardHeight()) {
			if (table.getValueAt(y + 1, x - 1).equals(9)) {
				table.setValueAt(0, y + 1, x - 1);
			}
		}
	}

	public static Board getMap(){
	return map;
	}
}