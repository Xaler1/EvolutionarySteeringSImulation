package Helpers;

import java.awt.geom.Point2D;

import static java.lang.Math.*;

public class Vector2D {
    public double x;
    public double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Point2D point) {
        this.x = point.getX();
        this.y = point.getY();
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(x + other.x, y + other.y);
    }

    public Vector2D sub(Vector2D other) {
        return new Vector2D(x - other.x, y - other.y);
    }

    public Vector2D normalise() {
        double length = this.length();
        if (length == 0) {
            return new Vector2D(0, 0);
        }
        return new Vector2D(x / length, y / length);
    }

    public Vector2D mul(double scalar) {
        return new Vector2D(x * scalar, y * scalar);
    }

    public Vector2D div(double scalar) {
        if (scalar == 0) {
            return this;
        }
        return new Vector2D(x / scalar, y / scalar);
    }

    public Vector2D setMag(double magnitude) {
        return this.normalise().mul(magnitude);
    }

    public Vector2D limit(double limit) {
        if (this.length() > limit) {
            return this.div(this.length() / limit);
        } else {
            return this;
        }
    }

    public double length() {
        return sqrt(pow(x, 2) + pow(y, 2));
    }

    public double dot(Vector2D vector) {
        return x * vector.x + y * vector.y;
    }

    public double angleTo(Vector2D vector) {
        return acos(this.dot(vector) / (this.length() * vector.length()));
    }

    public double distanceTo(Vector2D other) {
        return sqrt(pow(x - other.x, 2) + pow(y - other.y, 2));
    }

    public double distanceToLine (Vector2D start, Vector2D end) {
        Vector2D line = end.sub(start).normalise();
        double projection_length = this.sub(start).dot(line);
        Vector2D projected_point = start.add(line.mul(projection_length));
        return distanceTo(projected_point);
    }

    public Vector2D getProjectedPoint(Vector2D start, Vector2D end) {
        Vector2D line = end.sub(start).normalise();
        double projection_length = this.sub(start).dot(line);
        return start.add(line.mul(projection_length));
    }

    public boolean isInRange(Vector2D start, Vector2D end) {
        return (((x > start.x && x < end.x) || (x < start.x && x > end.x)) && ((y > start.y && y < end.y) || (y < start.y && y > end.y)));
    }

    @Override
    public String toString() {
        return String.format("x:%.6f, y:%.6f", x, y);
    }

}
