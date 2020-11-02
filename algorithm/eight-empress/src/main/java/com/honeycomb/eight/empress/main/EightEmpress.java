package com.honeycomb.eight.empress.main;

/**
 * @author maoliang
 */
public class EightEmpress {

    public static int count = 0;

    public static int col = 8;

    public static void main(String[] args) {
        int[][] chessboard = new int[col][col];
        canChess(chessboard, 0);
        System.out.println(count);
    }

    // 回溯法
    public static void canChess(int[][] chessboard, int x) {
        if(x < col) {
            for (int i = 0; i < col; i++) {
                if(isOk(x, i, chessboard)){
                    chessboard[x][i] = 1;
                    if(x == col - 1) {
                        print(chessboard);
                        count++;
                    }else{
                        canChess(chessboard, x + 1);
                    }
                    chessboard[x][i] = 0;
                }
            }
        }
    }

    public static boolean isOk(int x, int y, int[][] chessboard) {
        for (int i = 0; i < col; i++) {
            if (i != y && chessboard[x][i] != 0) {
                return false;
            }
        }

        for (int i = 0; i < col; i++) {
            if (i != x && chessboard[i][y] != 0) {
                return false;
            }
        }

        int i = x + 1, j = y + 1;
        while (i < col && j < col) {
            if (chessboard[i][j] != 0) {
                return false;
            }
            i += 1;
            j += 1;
        }

        i = x - 1;
        j = y - 1;
        while (i >= 0 && j >= 0) {
            if (chessboard[i][j] != 0) {
                return false;
            }
            i -= 1;
            j -= 1;
        }

        i = x + 1;
        j = y - 1;
        while (i < col && j >= 0) {
            if (chessboard[i][j] != 0) {
                return false;
            }
            i += 1;
            j -= 1;
        }

        i = x - 1;
        j = y + 1;
        while (i >= 0 && j < col) {
            if (chessboard[i][j] != 0) {
                return false;
            }
            i -= 1;
            j += 1;
        }


        return true;
    }

    public static void print(int[][] chessboard){
        for(int i = 0; i < chessboard.length; i++){
            for(int j = 0; j < chessboard[i].length; j++){
                System.out.print(chessboard[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }
}
