package com.honeycomb.leetcode.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author maoliang
 */
public class Solution_45 {

    public static final Map<Integer, List<Integer>> cache = new HashMap<>(16);

    public static void main(String[] args) {
        int[] array = new int[]{2,3,1,1,4};
        System.out.println(jump1(0, array, array.length - 1));
        System.out.println("ok");
    }

    public static int jump(int n, int[] array){
        if(n > 0){
            int len = array[n];
            List<Integer> list = new ArrayList<>(len);
            for(int i = 1; i <= len; i++){
                int jumpCount = jump(n - i, array);
                if(jumpCount > -1) {
                    list.add(jumpCount + 1);
                }
            }
            cache.put(n, list);
            return list.stream().reduce((a, b) -> a > b ? b:a).orElse(0);
        }else if(n == 0){
            return 0;
        }else {
            return -1;
        }
    }

    public static int jump1(int n, int [] array, int target){
        if(n == target){
            return 0;
        }else if(n > target){
            return -1;
        }else {
            int len = array[n];
            List<Integer> list = new ArrayList<>(len);
            for(int i = 1; i <= len; i++){
                int jumpCount = jump1(n + i, array, target);
                if(jumpCount > -1){
                    list.add(jumpCount + 1);
                }
            }
            cache.put(n, list);
            return list.stream().reduce((a, b) -> a > b ? b:a).orElse(0);
        }
    }
}
