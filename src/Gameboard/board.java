/*


	@author Felix
*/
public class board implements boardinterface{
    //feldgroesse:
    private int x;
    private int y;
    //(java int arrays werden mit 0 initialisiert)
    //fuer jeden spieler 2 arrays um abgegebene schuesse lokal zu speichern
    private int playerboard0 [] [];
    private int playerboard1 [] [];
    private int playershots0 [] [];
	private int playershots1 [] [];
//konstruktor 
    board(int x, int y){
	this.x=x;
	this.y=y;	
    }

    //methoden
    public void setx(int x){
    	this.x=x;
    }	

    public void sety(int y){
	this.y=y;
    }
    public int getx(){
	return x;
    }
    public int gety(){
	return y;
	}
    public void createboard(){
        playerboard0 = new int[x] [y];
        playerboard1 = new int[x] [y];
        playershots0 = new int[x] [y];
        playershots0 = new int[x] [y];
    }
		
    public String checkboard(int x, int y, int player){
        int i;
        if( x > this.x || x < 0){
            return "Falscher X Wert";
        }else if( y > this.y ||y < 0){
            return "Falscher Y Wert";
        }else{
            if( player == 0){
                i= playerboard0 [x] [y];
			
                /*Wasser kann direkt zurueckgegeben werden, 
                bei treffer muss aber ueberprueft werden ob schiff versenkt ist*/

                switch (i){										
                    case 0: return "Wasser";
                    case 1: return shipcheckup(x, y, player);//not implemented yet
                }
            }else if (player== 1) {
                i=playerboard1 [x] [y];
                switch (i){
                    case 0: return "Wasser";
                    case 1: return shipcheckup(x, y, player);//not implemented yet
                }
            }else{
                return "ungueltiger Spieler";
            }			
        }//close else block with correct input
    }//close function
}//close class