/*


	@author Felix
*/
public class board implements boardinterface{
    //(java int arrays werden mit 0 initialisiert)
    //fuer jeden spieler 2 arrays um abgegebene schuesse lokal zu speichern
    private int playerboard0 [] [];
    private int playerboard1 [] [];
    private int playershots0 [] [];
	private int playershots1 [] [];
//konstruktor 
    board(){}

    //methoden

    public void createboard(){
        int x= Datacontiner.getSpielFeldBreite();
        int y= Datacontainer.getSpielFeldHoehe();
        playerboard0 = new int[x] [y];
        playerboard1 = new int[x] [y];
        playershots0 = new int[x] [y];
        playershots0 = new int[x] [y];
    }
		
    public String checkboard(int x, int y, int player){
        int i;
        if( x > DataContainer.getSpielFeldBreite() || x < 0){
            return "Falscher X Wert";
        }else if( y > Datacontainer.getSpielFeldHoehe() ||y < 0){
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
    return "you shouldn't be here";}//close function //missing return function laut compiler
}//close class