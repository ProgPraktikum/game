package Data;
public class game implements gameinterface{
	private boolean is_online;
	//konstruktor feldgroesse, online/ singleplayer game
	private DataContainer con= new DataContainer();
	private Gameboard.board map;

	game (int x, int y, boolean o) {
		con.setSpielFeldBreite(x);
		con.setSpielFeldHoehe(y);
		is_online = o;
		map = new Gameboard.board();
	}
	//methoden

	//Plazierungs methoden

	/*erzeugt ein neues schiff mit länge l, sofern es nicht die maximale flottengröße überschreitet,
	und speichert dieses ins fleet array des DataContainers an der nächsten stelle im array
	 */
	public void buildship(int l) {
		ship s;
		if (con.getCurrFleetsize() + l <= con.getMaxFleetsize()){
			con.addCurrFleetsize(l);
			s = new ship(l);
			con.addFleet(s);
		}
	}

	/* erzeugt flotte an schiffen bei parameterloser eingabe
	wird standardflotte für 10*10 verwendet
	 */
	public void createFleet(){
		buildship(2);
		buildship(2);
		buildship(2);
		buildship(2);
		buildship(3);
		buildship(3);
		buildship(3);
		buildship(4);
		buildship(4);
		buildship(5);
	}

	/*
	methode verschiebt schiff an andere koordinate und überprüft ob die verschiebung valide ist
	ansonsten wird die position zurückgesetzt
	bei erfolg wird true ausgegeben und bei misserfolg false
	 */
	public boolean moveShip(int x, int y, ship s){
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
	public void rotateShip(ship s){
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
	public void save(int id){
		//saving foo
	}
}