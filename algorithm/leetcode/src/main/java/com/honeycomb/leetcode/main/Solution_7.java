package com.honeycomb.leetcode.main;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class Solution_7 {

    public static void main(String[] args) {
        System.out.println(new Solution_7().reverse(-1230));
    }

    public int reverse(int x) {

        while(x % 10 == 0){
            x = x / 10;
        }

        String numberStr = String.valueOf(x);
        if(x < 0){
            numberStr = numberStr.substring(1);
        }

        char[] charArray = numberStr.toCharArray();
        int i = 0;
        int j = charArray.length - 1;
        while(j > i){
            char temp = charArray[i];
            charArray[i] = charArray[j];
            charArray[j] = temp;
            j--;
            i++;
        }

        numberStr = new String(charArray);
        if(x < 0){
            numberStr = "-" + numberStr;
        }

        Long result = Long.valueOf(numberStr);
        if(result > Integer.MAX_VALUE || result < Integer.MIN_VALUE){
            return 0;
        }

        return result.intValue();
    }
}
