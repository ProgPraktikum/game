package ai;

import util.Tuple;

public class Trace {
    private static Tuple[] values;
    private static int arrTrack;

    public Trace() {
        values = new Tuple[10];
        arrTrack = 0;
        //System.out.println("Maxshipsize:" + data.DataContainer.getMaxShipLength());
    }

    public void addTile(int x, int y) {
        values[arrTrack] = new Tuple(x, y);
        arrTrack += 1;
    }

    public int getSize() {
        return values.length;
    }

    public void clear() {
        values = new Tuple[10];
        arrTrack = 0;
    }

    public Tuple[] getTrace() {
        return values;
    }
}
