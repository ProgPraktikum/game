package util;

public class Tuple {
    private Object[] data;

    public Tuple (Object... members) {
        this.data = members;
    }

    public Object get(int index) {
        return data[index];
    }

    public int getSize() {
        return data.length;
    }
}
