package Data;
public class game {
	private boolean is_online;
	//konstruktor feldgroesse, online/ singleplayer game
	DataContainer con = new DataContainer();
	Gameboard.board map;

	game (int x, int y, boolean o) {
		con.setSpielFeldBreite(x);
		con.setSpielFeldHoehe(y);
		is_online = o;
		map = new Gameboard.board();
	}
	//methoden

	//Plazierungs methoden
	/*
	methode verschiebt schiff an andere koordinate
	 */
	/*boolean moveShip(int x, int y, ship s){ //NOT FULLY IMPLEMENTED YET
		int xold= x;
		int yold =y;
		s.setxpos(x);
		s.setypos(y);

	}*/
	//aendert orientierung des schiffs
	void rotateShip(ship s){
		if(s.getOrientation()==0){
			s.setOrientation(1);
		}
		else{
			s.setOrientation(0);
		}
	}
	/*
	plaziert schiff auf spielbrett
		 */
	public boolean placeShip(ship s){
		return map.place(s);
	}

	//public void buildship()

	/* erzeugt flotte an schiffen bei parameterloser eingabe
	wird standardflotte für 10*10 verwendet
	 */
	/*void createFleet(){ //NOT IMPLEMENTED YET
	}*/

}