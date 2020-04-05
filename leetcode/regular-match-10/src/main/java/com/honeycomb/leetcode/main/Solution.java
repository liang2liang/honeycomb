package com.honeycomb.leetcode.main;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class Solution {

    public static void main(String[] args) {
        String source = "aa";
        String target = "a*";
        System.out.println(new Solution().isMatch(source, target));
    }

    public boolean isMatch(String s, String p) {
        char[] sourceArray = s.toCharArray();
        char[] targetArray = p.toCharArray();
        for (int i = 0, len = s.length(); i < len; i++) {
            if (targetArray[i] == '.') {
                continue;
            }
            if (targetArray[i] == '*') {
                int j = i;
                while (targetArray[--j] == '*') {
                }
                if (targetArray[j] != sourceArray[i] && targetArray[j] != '.') {
                    return false;
                }
            } else {
                if (targetArray[i] != sourceArray[i]) {
                    return false;
                }
            }
        }
        return true;
    }
}
