package ai;

import util.Tuple;

import java.util.ArrayList;

public class Trace {
    private ArrayList<Tuple> values;

    public Trace() {
        values = new ArrayList<>();
    }

    public void addTile(int x, int y) {
        values.add(new Tuple(x, y));
    }

    public int[] getTile(int i) {
        int[] result = new int[2];

        Tuple value = values.get(i);
        result[0] = value.get(0);
        result[1] = value.get(1);

        return result;
    }

    public int getSize() {
        return values.size();
    }

    public void clear() {
        values.clear();
    }

    public ArrayList<Tuple> getTrace() {
        return values;
    }
}
