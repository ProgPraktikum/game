package Data;
public class game{
	boolean is_online;
	//konstruktor feldgroesse, online/ singleplayer game
	game(int x, int y, boolean o){
		DataContainer.setSpielFeldBreite(x);
		DataContainer.setSpielfeldHoehe(y);
		is_online= o;
	} 
}