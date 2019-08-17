package z.ue;

/**
 *
 */
public class Vec {
    public int x;
    public int y;

    public Vec() {
    }

    public Vec(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vec set(float x, float y) {
        this.x = (int) x;
        this.y = (int) y;
        return this;
    }

    @Override
    public String toString() {
        return "Vec{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
