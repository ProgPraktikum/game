package data;

import ai.Ai;
import gui.TableView;
import gui.VictoryScreen;
import gameboard.Board;
import network.Network;

import javax.swing.text.BadLocationException;
import javax.xml.crypto.Data;
import java.util.Random;
import java.util.Stack;

/**@author Felix
 * @desc Statische Klasse welche die Huaptlogik des Spiels enthaelt und Netzwerk, AI und gui verknuepft
 *
 */
public class Game {
	/**
	 * board Objekt fuer den Spieler
	 */
	//private static Board map = new Board();
	private static Board map;
	private static boolean success=true;
	private static Ship s;

	//methoden

	//Plazierungs methoden


    /*
    methode verschiebt schiff an andere koordinate und ueberprueft ob die verschiebung valide ist
    ansonsten wird die position zurueckgesetzt
    bei erfolg wird true ausgegeben und bei misserfolg false
     */
	public static void setMap(){
		map = new Board();
	}
	public static void setMap(Board newMap) {
		map = newMap;
	}

	/**
	 * bewegt das selectedShip aus dem Datacontainer an Stelle x,y
	 * @param x X-Zielkoordinate
	 * @param y Y-Zielkoordinate
	 * @return gibt true bei erfolgreicher Platzierung zurueck und false bei falscher Platzierung.
	 */
	public static boolean moveShip(int x, int y) {
		Ship s = DataContainer.getSelectedShip();
		return map.moveShip(x, y, s);

	}
	//aendert orientierung des schiffs

	/**
	 * aendert Platzierung des selectedShip aus dem Datacontainer auf den Wert i mod 4.
	 * @param i neue Orrientierung zwischen 0 und 3
	 */
	public static void rotateShip(int i) {
		Ship s = DataContainer.getSelectedShip();
		map.rotateShip(i, s);
	}
	/*
	plaziert schiff auf spielbrett
		 */

	/**
	 * Platziert uebergebenes Schiff s auf dem Board.
	 * @param s zu platzierendes Schiff
	 * @return gibt true bei erfolgreicher Platzierung und false bei fehler zurueck.
	 */
	public static boolean placeShip(Ship s) {
		return map.place(s);
	}

	/**
	 * algoritzmus zur generierung von Flottenvorschlaegen
	 */
	public static void generatefleet() {
		int occ = DataContainer.getOccupancy();
		Stack<Integer> lengths = new Stack<>();
		int highlen = 2;
		occ -= DataContainer.getMaxShipLength();
		while (occ > 0) {
			for (int i = 2; i <= highlen; i++) {
				if (occ - i >= 0) {
					lengths.push(i);
					occ -= i;
				} else if (occ - i == -1) {
					lengths.push(i - 1);
					occ -= (i - 1);
				}
			}
			highlen++;
		}
		lengths.push(DataContainer.getMaxShipLength()); //groesstes schiff soll oben auf stack liegen
	}


	//spielmethoden

	/**
	 * @param x X-Zielkoordinate
	 * @param y Y-Zielkoordinate
	 * @return gibt entweder 0 fuer wasser, 1 fuer treffer oder 2 fuer versenkt zurueck.
	 * @desc Methode des Spielers um auf seinen Gegner zu schiessen, abhaengig vom Spielmodus entweder Ai oder Netzwerkgegner.
	 */
	public static int shoot(int x, int y, Ai ai) {
		if(DataContainer.getAllowed()) {
			int val;

			if (DataContainer.getGameType().equals("ss") || DataContainer.getGameType().equals("bdf") || DataContainer.getGameType().equals("bdf-loaded")) { // im einzelspieler hit der ai aufrufen
				val = ai.hit(x, y);
                if (val == -1) {
                    return -1; // shoot failed due to generic issues
                } else if (val == 0) {
                    DataContainer.setAllowed(false);
                }
			}
			else if (DataContainer.getGameType().equals("mp")) {
				val = Network.networkShoot(x,y);
				if (val == -1) {
				    return -1; // shoot failed due to network issues
                } else if (val == 0){
					DataContainer.setAllowed(false);
				}
			}
			else {
				return -1;
			}
			switch(val){
				case -1:
					return val;
				case 0:
					map.setPlayershots(x,y,7);
					DataContainer.getPlayerShootTable().setValueAt("X",y,x);
					break;

				case 1:
					map.setPlayershots(x,y,1);
					DataContainer.getPlayerShootTable().setValueAt(1,y,x);
					break;
				case 2:
					map.setPlayershots(x,y,2);
					DataContainer.getPlayerShootTable().setValueAt(2,y,x);
					displayHits(x,y,0,DataContainer.getPlayerShootTable());
          if (DataContainer.decreaseCounter(1) == 0) {
              new VictoryScreen(true);
          }
					break;
			}
			return val;
		}
		else {
			return -2;
		}
	}

	/**
	 * @param x X-Zielkoordinate
	 * @param y Y-Zielkoordinate
	 * @return gibt 0 fuer Wasser, 1 fuer Treffer und 2 fuer Versenkt zurueck.
	 * @desc Methode um auf den Spieler zu schiessen. wird bei netzwerkspiel bzw von AI aufgerufen.
	 */
	public static int getHit(int x, int y){
		int i = map.checkboard(x, y);
		if (i == 0){
            DataContainer.getTable().setValueAt("X", y, x);
			DataContainer.setAllowed(true);
			map.getPlayerboardAt(x,y).setStatus(7);
		} else if (i == 1) {
            DataContainer.getTable().setValueAt(1, y, x);
    } else if (i == 2){
        DataContainer.getTable().setValueAt(2, y, x);
			  displayHits(x, y, 0, DataContainer.getTable());
        if (DataContainer.decreaseCounter(2) == 0) {
            new VictoryScreen(false);
        }
		}
		return i;
	}

	public static boolean hitloop() {
		while(!DataContainer.getAllowed()){
			Network.networkHit();
		}
		return true;
	}

	/**
	 * @param x X-Wert des zurueckzugebenden Objekts
	 * @param y Y-Wert des zurueckzugebenden Objekts
	 * @return gibt ein Tile Objekt zurueck, welches an der entsprechenden x,y stelle im array steht
	 * @desc gibt Tile Objekt an Stelle x,y des playerBoards aus dem Board zurueck
	 */
	public static Tile getPlayerboard(int x, int y){
		return map.getPlayerboardAt(x,y);
	}

	/**
	 * ruft removeShip im Board mit dem uebergebenen Schiff s auf.
	 * @param s zu loeschendes Schiff, dass an removeShip uebergeben wird.
	 */
	public static void removeShip(Ship s) {
		map.removeShip(s);
	}

	/**
	 * @param x         X-Wert von dem aus die umliegenden Felder geprueft werden
	 * @param y         Y-Wert von dem aus die umliegenden Felder geprueft werden
	 * @param direction Richtung, die bei mehrmaligen aufrufen uebergeben wird um die suche effizienter durchzufuehren
	 * @param table     Tableview auf der die Operation durchgefuehrt werden soll
	 * @desc rekursive Funktion um Schiffe auf der Oberflaeche als versenkt anzuzeigen.
	 */
	private static void displayHits(int x, int y, int direction, TableView table) {
		//todo optimize corner checks
		table.setValueAt(2, y, x);
		map.setPlayershots(x,y,2);
		if (x - 1 >= 0) {
			if (table.getValueAt(y, x - 1).equals(1) && (direction == 0 || direction == 1)) {
				displayHits(x - 1, y, 1, table);
			} else if (table.getValueAt(y, x - 1).equals(9)) {
				table.setValueAt(0, y, x - 1);
				map.setPlayershots(x-1,y,0);
			}
		}
		if (x + 1 < DataContainer.getGameboardWidth()) {
			if (table.getValueAt(y, x + 1).equals(1) && (direction == 0 || direction == 2)) {
				displayHits(x + 1, y, 2, table);
			} else if (table.getValueAt(y, x + 1).equals(9)) {
				table.setValueAt(0, y, x + 1);
				map.setPlayershots(x+1,y,0);
			}
		}
		if (y - 1 >= 0) {
			if (table.getValueAt(y - 1, x).equals(1) && (direction == 0 || direction == 3)) {
				displayHits(x, y - 1, 3, table);
			} else if (table.getValueAt(y - 1, x).equals(9)) {
				table.setValueAt(0, y - 1, x);
				map.setPlayershots(x,y-1,0);
			}
		}
		if (y + 1 < DataContainer.getGameboardHeight()) {
			if (table.getValueAt(y + 1, x).equals(1) && (direction == 0 || direction == 4)) {
				displayHits(x, y + 1, 4, table);
			} else if (table.getValueAt(y + 1, x).equals(9)) {
				table.setValueAt(0, y + 1, x);
				map.setPlayershots(x,y + 1,0);
			}
		}
		if (x - 1 >= 0 && y - 1 >= 0) {
			if (table.getValueAt(y - 1, x - 1).equals(9)) {
				table.setValueAt(0, y - 1, x - 1);
				map.setPlayershots(x-1,y-1,0);
			}
		}
		if (x + 1 < DataContainer.getGameboardWidth() && y - 1 >= 0) {
			if (table.getValueAt(y - 1, x + 1).equals(9)) {
				table.setValueAt(0, y - 1, x + 1);
				map.setPlayershots(x+1,y-1,0);
			}
		}
		if (x + 1 < DataContainer.getGameboardWidth() && y + 1 < DataContainer.getGameboardHeight()) {
			if (table.getValueAt(y + 1, x + 1).equals(9)) {
				table.setValueAt(0, y + 1, x + 1);
				map.setPlayershots(x+1,y+1,0);
			}
		}
		if (x - 1 >= 0 && y + 1 < DataContainer.getGameboardHeight()) {
			if (table.getValueAt(y + 1, x - 1).equals(9)) {
				table.setValueAt(0, y + 1, x - 1);
				map.setPlayershots(x-1,y+1,0);
			}
		}
	}

	/**
	 * Berechnet Anzahl der Schiffsgroessen fuer eine empfohlene Flotte. Die Laenge der jeweiligen Schiffe entspricht dem Arrayindex+2.
	 * Die Anzahl der jeweiligen Schiffe entspricht der Zahl am entsprechenden Arrayindex
	 * @return Integer-Array mit Anzahlen der Schiffe beginnend bei Laenge 2 (Index 0).
	 */
	public static int[] recomendation(){
		int sizes[] = new int[10];
		int breite=DataContainer.getGameboardWidth();
		int hoehe=DataContainer.getGameboardHeight();
		int occupancy=DataContainer.getOccupancy();
		int currentsize=2;
		int prev=0;
		while(occupancy>0){
			for(int i=2; i<= currentsize; i++){
				if(occupancy-i>=0){		//restgröße größer 0 füge weiteres Schiff ein
					occupancy-=i;
					sizes[i-2]++;
					prev=i;
				}
				else if(occupancy-i < 0){	// restgröße kleiner 0
					if(occupancy >=2){		//restgröße groß genug um schiff mit verbleibender größe einzufügen
						sizes[occupancy-2]++;
						occupancy-=occupancy;
					}
					else if(occupancy-i == -1){ // restgröße 1
						sizes[prev-2]--; 		//lösche vorheriges element aus liste und füge ein neues mit alter länge +1 ein
						occupancy += prev;
						sizes[prev-1]++;
						occupancy -= prev+1;
					}
				}
			}
			currentsize++;
		}
		return sizes;
	}

	/**
	 * methode die für die Ai randomPlacement auf einem Boardobjekt durchführt und dieses dann zurück gibt
	 * @return Board Objekt mit platzierten Schiffen aus der Ai-Flotte
	 */
	public static Board aiRandomPlace(){
		Board dummy = new Board();
		boolean end=false;
		while(!end){
			end=random(dummy);
			if(!end){
				reset(dummy);
			}
		}
		return dummy;
	}

	/**
	 * platziert das naechste Schiff auf dem Stack in das dummy Board und ruft sich selbst bei erfolg rekursiv auf bis der Stack leer ist.
	 * @param dummy erhaelt das dummy Board auf das Platziert werden soll als parameter
	 * @return gibt ture bei erfolgreicher olatzierung und false bei nicht erfolgreicher platzierung zurueck
	 */
	private static boolean random(Board dummy){
		if (!(DataContainer.getAiFleet().isEmpty()) && success) {
			s = DataContainer.getAiFleet().pop();
		}
		Random rand = new Random();
		success = false;
		int count = 0;
		if(s != null) {
			while (!success && count < DataContainer.getGameboardWidth() * DataContainer.getGameboardHeight()) {
				int randomX = rand.nextInt(DataContainer.getGameboardWidth());
				int randomY = rand.nextInt(DataContainer.getGameboardHeight());	//zufallszahlen innerhalb der Spielfeldgrenzen werden generiert
				int startorr = rand.nextInt(4);
				s.setOrientation(startorr);
				int i =0;
				while(i <4 && !success){ //schiff wird versucht nacheinander in verschiedenen Richtungen zu platzieren bis erfolgreich
					s.setOrientation((startorr + i)%4);
					dummy.moveShip(randomX,randomY,s);
					success= dummy.place(s);
					i++;
				}
				count++;
			}
		}
		if (success) {
			s=null;
			//DataContainer.getShipLenghts().remove(DataContainer.getShipLenghts().firstElement());
			//DataContainer.getShipLenghts().pop();
			if (! DataContainer.getAiFleet().isEmpty() ) {
				return random(dummy);
			}
			return true;
		} else {
			reset(dummy);
			return false;
		}
	}

	/**
	 * setzt dummy board zurueck und schhreibt Schiffsobjekte die darauf platziert waren zurueck in den Stack
	 * @param dummy dummy Board aus dem die Schiffe entfernt werden
	 */
	public static void reset(Board dummy){
		int currentlength = 2;
		boolean fieldempty = false;
		int rowempty;
		int columnempty;
		while (!(fieldempty)) {
			columnempty=0;
			for (int i = 0; i < DataContainer.getGameboardHeight(); i++) {
				rowempty=0;
				for (int j = 0; j < DataContainer.getGameboardWidth(); j++) {
					if (dummy.getPlayerboardAt(j,i).getStatus()==3) {
						rowempty =1;
						if (currentlength == dummy.getPlayerboardAt(j, i).getMaster().getLength()) {
							Ship rem = dummy.getPlayerboardAt(j,i).getMaster();
							dummy.removeShip(rem);
							rem.setxpos(0);
							rem.setypos(0);
							rem.setOrientation(0);
							DataContainer.getAiFleet().push(rem);
						}
					} else {
						rowempty += 0;
					}
				}
				//wenn treffer in reihe, dann wird er in spalte übertragen
				if(rowempty==1){
					columnempty = 1;
				}
				else{
					columnempty +=0;
				}
			}
			// wenn kein spaltentreffer, dann ist feld leer -> SCHLEIFENABBRUCH
			if(columnempty==0){
				fieldempty=true;
			}
			//schiffslänge wird nach komplettem durchlauf durch feld erhöht
			// (alle schiffe der vorherigen länge wurden bereits entfernt
			currentlength++;
		}
		success= true;
	}

	/**
	 * gibt board objekt des Games zurueck
	 * @return Board Objekt des Games
	 */
	public static Board getMap(){
	return map;
	}
}