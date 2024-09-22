package org.logic;

import java.util.ArrayList;
import java.util.List;

public class PathFinder {
    public int rows_;
    public int cols_;
    public boolean[][] columns_;
    private int count_;
    private int[][] wave_matrix_;

    public PathFinder(int[][] field) {
        rows_ = field.length;
        cols_ = field[0].length;
        columns_ = new boolean[rows_][cols_];

        for(int i = 0; i < rows_; i++) {
            for(int j = 0; j < cols_; j++) {
                if(field[i][j] > 2) {
                    columns_[i][j] = true;
                }
            }
        }

//        for(int i = 0; i < rows_; i++) {
//            for(int j = 0; j < cols_; j++) {
//                if(columns_[i][j]) {
//                    System.out.print("1 ");
//                } else {
//                    System.out.print("0 ");
//                }
//            }
//            System.out.println();
//        }
//
//        for(int i = 0; i < field.length; i++) {
//            for(int j = 0; j < field[0].length; j++) {
//                System.out.print(field[i][j] + " ");
//            }
//            System.out.println();
//        }
    }

    public List<Point> getPath(Point start, Point end) {
        if (start.row < 0 || start.col < 0 || end.row < 0 || end.col < 0) {
            System.err.println("Неверные координаты точек.");
            System.exit(1);
        }
        count_ = 1;
        wave_matrix_ = new int[rows_][cols_];
        for (int i = 0; i < rows_; i++) {
            for (int j = 0; j < cols_; j++) {
                wave_matrix_[i][j] = 0;
            }
        }
        wave_matrix_[start.row][start.col] = count_;
        boolean hasPath = true;
        while (wave_matrix_[end.row][end.col] == 0 && (hasPath = getWave())) {
            count_++;
        }
        return findPath(end, hasPath);
    }

    private boolean getWave() {
        boolean flag = false;

        for (int i = 0; i < rows_; ++i) {
            for (int j = 0; j < cols_; ++j) {
                if (wave_matrix_[i][j] == count_) {
                    flag = true;
                    if (j < cols_ - 1 && wave_matrix_[i][j + 1] == 0 && !columns_[i][j + 1]) {
                        wave_matrix_[i][j + 1] = count_ + 1;
                    }
                    if (j > 0 && wave_matrix_[i][j - 1] == 0 && !columns_[i][j - 1]) {
                        wave_matrix_[i][j - 1] = count_ + 1;
                    }
                    if (i > 0 && wave_matrix_[i - 1][j] == 0 && !columns_[i - 1][j]) {
                        wave_matrix_[i - 1][j] = count_ + 1;
                    }
                    if (i < rows_ - 1 && wave_matrix_[i + 1][j] == 0 && !columns_[i + 1][j]) {
                        wave_matrix_[i + 1][j] = count_ + 1;
                    }
                }
            }
        }
        return flag;
    }

    private List<Point> findPath(Point pos, boolean hasPath) {
        List<Point> path = new ArrayList<>();
        int count = count_;

        if (hasPath) {
            path.add(new Point(pos.row, pos.col));
        }

        while (count != 1 && hasPath) {
            count--;
            if (pos.col < cols_ - 1 && wave_matrix_[pos.row][pos.col + 1] == count &&
                    !columns_[pos.row][pos.col + 1]) {
                pos.col++;
            } else if (pos.col > 0 && wave_matrix_[pos.row][pos.col - 1] == count &&
                    !columns_[pos.row][pos.col - 1]) {
                pos.col--;
            } else if (pos.row > 0 && wave_matrix_[pos.row - 1][pos.col] == count &&
                    !columns_[pos.row - 1][pos.col]) {
                pos.row--;
            } else if (pos.row < rows_ - 1 &&
                    wave_matrix_[pos.row + 1][pos.col] == count &&
                    !columns_[pos.row + 1][pos.col]) {
                pos.row++;
            }
            path.add(new Point(pos.row, pos.col));
        }
        return path;
    }

    public void printWaveMatrix() {
        System.out.println("Волновая матрица:");
        for (int i = 0; i < rows_; i++) {
            for (int j = 0; j < cols_; j++) {
                System.out.print(wave_matrix_[i][j] + " ");
            }
            System.out.println();
        }
    }

}
