package Data;
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
	public void setxpos(int xpos){
		this.xpos = xpos;
	}
	public void setypos(int ypos){
		this.ypos = ypos;
	}
	public void setLength(int length){
		this.length = length;
	}
	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	public int getXpos(){
		return xpos;
	}
	public int getYpos(){
		return ypos;
	}
	public int getLength() {
		return length;
	}

    public int getOrientation() {
        return orientation;
    }
}