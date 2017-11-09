public class game{
	int x=0;
	int y=0;
	int fleetsize=0;
	boolean is_online;
	//konstruktor feldgröße, online/ singleplayer game
	game(int x, int y,boolean o,){
		this.x=x;
		this.y=y;
		is_online= o;
        fleetsize= (int)(x*y*0.3);
	} 
}