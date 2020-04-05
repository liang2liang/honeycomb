package com.honeycomb.leetcode.main;

public class Solution {

    public static void main(String[] args) {
        String s = "aba";
        System.out.println(new Solution().longestPalindrome(s));
    }

    public String longestPalindrome(String s) {
        if(s == null || s.length() == 1){
            return s;
        }

        int longestPalindromeLength = 0;
        int start = 0;
        int end = 1;
        char[] charArray = s.toCharArray();
        for(int i = 0, len = charArray.length; i < len; i++){
            for (int j = i + 1; j < len; j++) {
                if(isPalindrome(charArray, i, j)){
                    if((j - i + 1) > longestPalindromeLength){
                        longestPalindromeLength = j - i + 1;
                        start = i;
                        end = j;
                    }
                }
            }
        }

        return s.substring(start, end + 1);
    }

    public boolean isPalindrome(char[] charArray, int start, int end){
        if(charArray == null){
            return false;
        }

        if(charArray.length == 1){
            return true;
        }

        while(end > start){
            if(charArray[start] != charArray[end]){
                return false;
            }
            end--;
            start++;
        }
        return true;
    }
}