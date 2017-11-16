package Data;

public class Watertile extends Abstracttile{
    private int status=0;
    private ship master;
    //konstruktor
    public Watertile(){}
    public int getStatus(){
        return status;
    }
    public void setStatus(int status){
        this.status=status;
    }
    public void hit(){}
    public ship getMaster(){return master;}
}
