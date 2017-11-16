package Data;

public abstract class Abstracttile {
    //atribute
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
//abstrakte methoden
    public abstract void hit();
    public abstract ship getMaster();
}


