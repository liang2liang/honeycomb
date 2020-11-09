package com.honeycomb.leetcode.main;

import java.util.concurrent.TimeUnit;

/**
 * 36.有效的数独
 * @author maoliang
 */
public class Solution_36 {

    private static final int size = 9;

    public static void main(String[] args) throws InterruptedException {

        /**
         *   ["5","3",".",".","7",".",".",".","."],
         *   ["6",".",".","1","9","5",".",".","."],
         *   [".","9","8",".",".",".",".","6","."],
         *   ["8",".",".",".","6",".",".",".","3"],
         *   ["4",".",".","8",".","3",".",".","1"],
         *   ["7",".",".",".","2",".",".",".","6"],
         *   [".","6",".",".",".",".","2","8","."],
         *   [".",".",".","4","1","9",".",".","5"],
         *   [".",".",".",".","8",".",".","7","9"]
         */

//        int[][] array = new int[][]{
//                {5, 3, 0, 0, 7, 0, 0, 0, 0},
//                {6, 0, 0, 1, 9, 5, 0, 0, 0},
//                {0, 9, 8, 0, 0, 0, 0, 6, 0},
//                {8, 0, 0, 0, 6, 0, 0, 0, 3},
//                {4, 0, 0, 8, 0, 3, 0, 0, 1},
//                {7, 0, 0, 0, 2, 0, 0, 0, 6},
//                {0, 6, 0, 0, 0, 0, 2, 8, 0},
//                {0, 0, 0, 4, 1, 9, 0, 0, 5},
//                {0, 0, 0, 0, 8, 0, 0, 7, 9}
//        };

        int[][] array = new int[][]{
                {8, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        int[][] arrayCopy = new int[size][size];
        copy(array, arrayCopy);
        process(array, arrayCopy, 0, 0);
    }

    public static void process(int[][] array, int[][] sourceArray, int x, int y) throws InterruptedException {
        if (sourceArray[x][y] == 0) {
            for (int i = 1; i <= size; i++) {
                array[x][y] = i;
                if (isEffective(array, x, y)) {
                    printResult(array, sourceArray, x, y);
                } else {
                    array[x][y] = 0;
                }
            }
        } else {
            printResult(array, sourceArray, x, y);
        }
    }

    public static void printResult(int[][] array, int[][] sourceArray, int x, int y) throws InterruptedException {
        if (y < size - 1) {
            process(array, sourceArray, x, y + 1);
        } else if (x < size - 1) {
            y = 0;
            process(array, sourceArray, x + 1, y);
        } else {
            print(array);
            System.out.println("");
            return;
        }
    }

    public static boolean isEffective(int[][] array, int i, int j) {
        for (int k = 0; k < size; k++) {
            if (k != i && array[k][j] == array[i][j]) {
                return false;
            }
        }

        for (int k = 0; k < size; k++) {
            if (k != j && array[i][k] == array[i][j]) {
                return false;
            }
        }

        return true;
    }

    public static void print(int[][] array) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(array[i][j] + "  ");
            }
            System.out.println("");
        }
    }

    public static void copy(int[][] sourceArray, int[][] targetArray) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                targetArray[i][j] = sourceArray[i][j];
            }
        }
    }
}
