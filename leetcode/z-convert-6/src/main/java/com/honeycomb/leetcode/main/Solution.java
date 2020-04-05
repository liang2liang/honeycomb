package com.honeycomb.leetcode.main;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class Solution {

    public static void main(String[] args) {
        new Solution().convert("LEETCODEISHIRING", 4);
    }

    public String convert(String s, int numRows) {
        if(s == null){
            return s;
        }

        char[] charArray = s.toCharArray();

        int colSize = s.length() / (2 * numRows -2) * (numRows - 1) + numRows;
        int rowSize = numRows;

        char[][] array = new char[rowSize][colSize];

        int position;
        int row = 1;
        int col = -1;
        for (int i = 0, len = s.length(); i < len; i++) {
            position = i % (2 * numRows -2);
            if(position == 0){
                row--;
                col++;
            }
            if(position < numRows){
                array[row][col] = charArray[i];
                if(position < numRows - 1) {
                    row++;
                }
            }else{
                row--;
                col++;
                array[row][col] = charArray[i];
            }
            System.out.println("row is [" + row + "] ; col is [" + col + "]; " + "i is [" + i + "]");
        }

        for (int i = 0, len = array.length; i < len; i++) {
            for(int j = 0; j < array[i].length; j++){
                if('\u0000' == array[i][j]){
                    System.out.print(' ');
                }else {
                    System.out.print(array[i][j]);
                }
            }
            System.out.println();
        }
        return null;
    }
}
