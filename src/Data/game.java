package Data;

import javax.xml.crypto.Data;

/**
 * enthält Hauptlogik des spiels
 */
public class game implements gameinterface{
	private boolean is_online;
	//konstruktor feldgroesse, online/ singleplayer game
	private Gameboard.board map;

	public game (boolean o) {
		is_online = o;
		map = new Gameboard.board();
	}
	//methoden

	//Plazierungs methoden

	/*erzeugt ein neues schiff mit länge l, sofern es nicht die maximale flottengröße überschreitet,
	und speichert dieses ins fleet array des DataContainers an der nächsten stelle im array
	 */
	public void buildship() {
		int l =  DataContainer.getShipLenghts().peek();
		ship s =new ship(l);
		DataContainer.getfleet().push(s);
	}

    /*
    methode verschiebt schiff an andere koordinate und überprüft ob die verschiebung valide ist
    ansonsten wird die position zurückgesetzt
    bei erfolg wird true ausgegeben und bei misserfolg false
     */
    public boolean moveShip(int x, int y){
        ship s = DataContainer.getSelectedShip();
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
		ship s = DataContainer.getSelectedShip();
		s.setOrientation(i);
	}
	/*
	plaziert schiff auf spielbrett
		 */
	public boolean placeShip(ship s){
		return map.place(s);
	}
	//NOT IMPLEMENTED YET
	public void placeFleet(int player) {
		//NOT IMPLEMENTED YET
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

	public Abstracttile getplayereboard(int x,int y){
		return map.getPlayerboardAt(x,y);
	}
	public void removeship(ship s) {
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
	public void getboard(){
	    map.getPlayerboard();
    }
}