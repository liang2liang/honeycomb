package com.honeycomb.leetcode.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 474. 一和零
 * @author maoliang
 */
public class Solution_474 {

    public static void main(String[] args) {
        String[] array = new String[]{"10", "1", "0"};
        List<Statistics> collect = Arrays.stream(array).map(Solution_474::statistc).collect(Collectors.toList());
        System.out.println(obtainCount(1, 1, collect, 1));
    }

    public static int obtainCount(int m, int n, List<Statistics> statisticsList, int start) {
        Statistics statistics = statisticsList.get(start);
        int surplusOneCount = m - statistics.oneCount;
        int surplusZeroCount = n - statistics.zeroCount;
        if (surplusOneCount == 0 && surplusZeroCount == 0) {
            return 1;
        } else if (surplusOneCount >= 0 && surplusZeroCount >= 0) {
            List<Integer> list = new ArrayList<>();
            for (int i = start + 1, len = statisticsList.size(); i < len; i++) {
                int count = obtainCount(surplusOneCount, surplusZeroCount, statisticsList, i) + 1;
                if(count > 0) {
                    list.add(count);
                }
            }
            return list.stream().max(Integer::compareTo).orElse(-1000);
        }
        return -1000;
    }

    public static Statistics statistc(String str) {
        char[] array = str.toCharArray();
        int oneCount = 0;
        int zeroCount = 0;
        for (int i = 0, len = array.length; i < len; i++) {
            if (array[i] == '1') {
                oneCount++;
            } else {
                zeroCount++;
            }
        }
        return new Statistics(oneCount, zeroCount);
    }

    public static class Statistics {

        public int oneCount;

        public int zeroCount;

        public Statistics(int oneCount, int zeroCount) {
            this.oneCount = oneCount;
            this.zeroCount = zeroCount;
        }

        @Override
        public String toString() {
            return "Statistics{" +
                    "oneCount=" + oneCount +
                    ", zeroCount=" + zeroCount +
                    '}';
        }
    }
}
