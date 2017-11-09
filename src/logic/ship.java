/*
@author: Felix
desc: Datenklasse zum zusammenfassen der schiffsatribute beim plazieren
*/
public class ship{

//atribute
	final public int length;
	final public int xpos;
	final public int ypos;
	final public int origin;
//konstruktor
	public ship(int o, int l){
		if (o == 0 || o==1){
			origin = o;
		}
		if (l != 0){
			length = l;
		}
	}
//methoden *not fully implemented yet
	public void setxpos(int x){
		xpos = x;
	}
	public void setypos(int y){
		ypos = y;
	}
}