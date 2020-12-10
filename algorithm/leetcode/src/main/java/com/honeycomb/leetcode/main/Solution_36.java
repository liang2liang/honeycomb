package com.honeycomb.leetcode.main;

import java.util.concurrent.TimeUnit;

/**
 * 36.有效的数独
 *
 * @author maoliang
 */
public class Solution_36 {

    private static final int size = 9;

    private static int count = 0;

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

        int[][] array = new int[][]{
//                {0,0,0,2,1,0,0,9,0},
//                {0,0,0,0,0,9,8,0,0},
//                {0,0,3,0,0,8,1,4,0},
//                {2,0,0,4,3,0,0,0,5},
//                {4,0,0,0,0,0,2,0,9},
//                {6,7,0,0,0,0,0,0,8},
//                {0,4,0,0,0,2,0,0,0},
//                {0,8,2,0,0,6,0,7,0},
//                {0,0,6,7,8,0,0,0,0}

//                {0,0,0,0,0,1,0,7,3},
//                {9,0,0,8,0,0,0,2,0},
//                {4,0,0,7,0,0,0,0,0},
//                {0,0,2,0,0,0,0,9,0},
//                {0,0,4,0,0,6,0,0,7},
//                {0,3,0,0,0,0,0,0,1},
//                {0,8,1,0,0,3,0,0,0},
//                {0,4,0,0,8,0,9,0,0},
//                {0,0,0,0,2,0,5,0,0}

//                {5,0,9,0,0,0,0,0,0},
//                {0,0,0,6,0,0,0,0,0},
//                {0,0,0,4,0,0,0,0,2},
//                {0,4,0,2,0,0,0,0,0},
//                {0,0,0,0,0,0,7,5,0},
//                {0,0,0,0,0,0,0,3,0},
//                {1,0,0,0,5,0,0,0,0},
//                {3,0,0,0,9,7,0,0,0},
//                {0,8,0,0,0,0,0,0,6}

                {3,  0,  5,  0,  4,  0,  1,  0,  0},
                {0,  4,  1,  0,  9,  5,  0,  8,  0},
                {7,  0,  9,  0,  0,  1,  5,  4,  0},
                {0,  9,  4,  0,  0,  6,  0,  3,  8},
                {0,  7,  0,  0,  8,  0,  0,  5,  4},
                {8,  5,  0,  4,  0,  0,  0,  0,  1},
                {9,  0,  8,  0,  2,  4,  0,  6,  0},
                {4,  0,  7,  0,  0,  3,  8,  1,  0},
                {5,  0,  0,  0,  0,  0,  4,  0,  0}

        };

        int[][] arrayCopy = new int[size][size];
        copy(array, arrayCopy);
        process(array, arrayCopy, 0, 0);
    }

    public static void process(int[][] array, int[][] sourceArray, int x, int y) throws InterruptedException {
        if (sourceArray[x][y] == 0) {
            for (int i = 1; i <= size; i++) {
                if (isEffective(array, x, y, i)) {
                    array[x][y] = i;
                    if(!printResult(array, sourceArray, x, y))
                        array[x][y] = 0;
                }
            }
        } else {
            printResult(array, sourceArray, x, y);
        }
    }

    public static boolean printResult(int[][] array, int[][] sourceArray, int x, int y) throws InterruptedException {
        if (y < size - 1) {
            process(array, sourceArray, x, y + 1);
        } else if (x < size - 1) {
            process(array, sourceArray, x + 1, 0);
        } else {
            print(array);
            return true;
        }
        return false;
    }

    public static boolean isEffective(int[][] array, int i, int j, int n) {
        for (int k = 0; k < size; k++) {
            if (k != i && array[k][j] == n) {
                return false;
            }
        }

        for (int k = 0; k < size; k++) {
            if (k != j && array[i][k] == n) {
                return false;
            }
        }

        for (int k = i / 3 * 3, count = k + 3; k < count; k++) {
            for (int m = j / 3 * 3, count1 = m + 3; m < count1; m++) {
                if (array[k][m] == n && (k != i || m != j)) {
                    return false;
                }
            }
        }

//        for (int k = 0; k < size; k++) {
//            if(array[ i / 3 * 3 + i/3][j / 3 * 3 + i % 3] == array[i][j]){
//                return false;
//            }
//        }

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


//class Solution {
//    boolean [][]row=new boolean[9][10];
//    boolean [][]col=new boolean[9][10];
//    boolean [][]box=new boolean[9][10];
//    int m;
//    int n;
//
//    public static void main(String[] args) {
//        char[][] array = new char[][]{
//                {'5','.','9','.','.','.','.','.','.'},
//                {'.','.','.','6','.','.','.','.','.'},
//                {'.','.','.','4','.','.','.','.','2'},
//                {'.','4','.','2','.','.','.','.','.'},
//                {'.','.','.','.','.','.','7','5','.'},
//                {'.','.','.','.','.','.','.','3','.'},
//                {'1','.','.','.','5','.','.','.','.'},
//                {'3','.','.','.','9','7','.','.','.'},
//                {'.','8','.','.','.','.','.','.','6'}
//        };
//        new Solution().solveSudoku(array);
//        print(array);
//    }
//
//    public static void print(char[][] array) {
//        for (int i = 0; i < 9; i++) {
//            for (int j = 0; j < 9; j++) {
//                System.out.print(array[i][j] + "  ");
//            }
//            System.out.println("");
//        }
//    }
//
//    public void solveSudoku(char[][] board) {
//        m=board.length;
//        n=board[0].length;
//        for(int i=0;i<m;i++){
//            for(int j=0;j<n;j++){
//                if(board[i][j]!='.'){
//                    int num=board[i][j]-'0';
//                    int idx=3*(i/3)+j/3;
//                    row[i][num]=col[j][num]=box[idx][num]=true;
//                }
//            }
//        }
//        dfs(board,0,0);
//
//    }
//    public boolean dfs(char [][]board,int x,int y){
//        if(x==9){
//            return true;
//        }
//        int new_x=y<8?x:x+1;
//        int new_y=y<8?y+1:0;
//        if(board[x][y]=='.'){
//            int idx=3*(x/3)+y/3;
//            for(char num='1';num<='9';num++){
//                if(row[x][num-'0']==false&&col[y][num-'0']==false&&box[idx][num-'0']==false){
//                    //放数
//                    board[x][y]=num;
//                    row[x][num-'0']=col[y][num-'0']=box[idx][num-'0']=true;
//
//                    // 递归
//                    boolean flag=dfs(board,new_x,new_y);
//                    // 找到正确结果 直接返回
//                    if(flag==true){
//                        return true;
//                    }
//
//                    // 回溯
//                    board[x][y]='.';
//                    row[x][num-'0']=col[y][num-'0']=box[idx][num-'0']=false;
//                }
//            }
//        }
//        else{
//            return dfs(board,new_x,new_y);
//        }
//        return  false;
//    }
//}