package data;

public class Tile {
    //atribute
    private int status;
    private Ship master;

    public Tile(){
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
    public Ship getMaster(){
        return master;
    }
    public void setMaster(Ship master){
        this.master=master;
        if(master!= null){
            setStatus(3);
        }
        else{
           setStatus(0);
        }
    }
}


