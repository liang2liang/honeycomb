package com.honeycomb.leetcode.main;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maoliang
 */
public class Solution_435 {

    public static void main(String[] args) {
//        int[][] intervals = new int[][]{{1, 2}, {2, 3}, {3, 4}, {1, 3}};
//        int[][] intervals = new int[][]{{1, 2}, {1, 2}, {1, 2}};
//        int[][] intervals = new int[][]{{1, 2}, {2, 3}};
        int[][] intervals = new int[][]{{1, 100}, {11, 22}, {1, 11}, {2, 12}};
        System.out.println(new Solution_435().eraseOverlapIntervals(intervals));
    }

    public int eraseOverlapIntervals(int[][] intervals) {
        if(intervals.length < 1){return 0;}
        int count = 0;
        List<int[]> temp = new ArrayList<>(intervals.length);
        temp.add(intervals[0]);
        for (int i = 1; i < intervals.length; i++) {
            if (needRemove(temp, intervals[i])) {
                count += 1;
            }
        }
        return count;
    }

    /**
     * 新增一个数据：没有交集直接加入，有交集肯定需要删除一个数据，只有删除范围大的才能增加更多数据
     * @param temp
     * @param two
     * @return
     */
    public boolean needRemove(List<int[]> temp, int[] two) {
        if (canNarrowDown(temp, two)) {
            return true;
        }

        // 是否有交集，有表示最少需要删除一个，因为已经不能缩小范围故直接删除该数据
        for (int i = 0; i < temp.size(); i++) {
            if (!(two[0] >= temp.get(i)[temp.get(i).length - 1] || two[two.length - 1] <= temp.get(i)[0])) {
                return true;
            }
        }
        temp.add(two);
        return false;
    }

    // 是否能缩小范围
    public boolean canNarrowDown(List<int[]> temp, int[] two) {
        for (int i = 0; i < temp.size(); i++) {
            if (two[0] >= temp.get(i)[0] && two[two.length - 1] <= temp.get(i)[temp.get(i).length - 1]) {
                temp.remove(i);
                temp.add(i, two);
                return true;
            }
        }
        return false;
    }
}
