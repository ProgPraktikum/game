package Data;
/*klasse, die einen status enthält und einen verweis auf ein schiff
wird ins board array gespeichert
 */
public class hullpiece extends Abstracttile{
    private int status=3;
    private ship master;

    public hullpiece(ship master){
        this.master=master;
    }
    public int getStatus(){
        return status;
    }
    public ship getMaster(){
        return master;
    }
    public void setStatus(int status){
        this.status=status;
    }
    public void hit(){
        master.hit();
    }
}
