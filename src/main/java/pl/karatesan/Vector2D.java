package pl.karatesan;

import java.util.Objects;

public class Vector2D {
    public double x;
    public double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2D add(Vector2D v1, Vector2D v2){
        return new Vector2D(v1.x - v2.x, v1.y - v2.y);
    }

    public static Vector2D subtract(Vector2D v1, Vector2D v2){
        return new Vector2D(v1.x + v2.x, v1.y+v2.y);
    }

    public static double calculateDistance(Vector2D v1, Vector2D v2){
        return Math.sqrt(Math.pow(v1.x - v2.x, 2) + Math.pow(v1.y - v2.y, 2));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D vector2D = (Vector2D) o;
        return Double.compare(x, vector2D.x) == 0 && Double.compare(y, vector2D.y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
