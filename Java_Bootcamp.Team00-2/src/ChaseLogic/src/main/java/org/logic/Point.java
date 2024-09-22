package org.logic;

public class Point {
    int row;
    int col;

    public Point(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Point getPoint() {
        return new Point(row, col);
    }
}
