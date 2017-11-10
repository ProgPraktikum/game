/*
@author: Felix
desc: Datenklasse zum zusammenfassen der schiffsatribute beim plazieren
*/
public class ship{

//atribute
	private int length = 0;
	private int xpos = 0;
	private int ypos = 0;
	private int orientation =0;
//konstruktor
	public ship(int o, int l){
		if (o == 0 || o==1){
			orientation = o;
		}
		if (l != 0){
			length = l;
		}
	}
//methoden *not fully implemented yet
	public void setxpos(int x){
		xpos = x;
	}
	public void setypos(int y){ ypos = y;
	}
}