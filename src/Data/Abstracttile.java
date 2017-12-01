package Data;

public class Abstracttile {
    //atribute
    private int status;
    private ship master;

    public Abstracttile(){
        status=0;
        master=null;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
//abstrakte methoden
    public void hit(){
        if(status==3){
            master.hit();
        }
    }
    public ship getMaster(){
        return master;
    }
    public void setMaster(ship master){
        this.master=master;
        if(master!= null){
            setStatus(3);
        }
        else{
           setStatus(0);
        }
    }
}


