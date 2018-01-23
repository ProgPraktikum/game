package util;

public class Tuple {
    private int[] data;

    public Tuple (int... members) {
        this.data = members;
    }

    public int get(int index) {
        return data[index];
    }

    public int getSize() {
        return data.length;
    }
}
