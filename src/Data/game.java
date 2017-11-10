package Data;
public class game {
	boolean is_online;
	//konstruktor feldgroesse, online/ singleplayer game
	DataContainer con = new DataContainer();

	game (int x, int y, boolean o) {
		con.setSpielFeldBreite(x);
		con.setSpielFeldHoehe(y);
		is_online = o;
	} 
}