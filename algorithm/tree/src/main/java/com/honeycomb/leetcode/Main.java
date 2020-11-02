package com.honeycomb.leetcode;

import com.honeycomb.leetcode.entity.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class Main {

    public static void main(String[] args) {
        Random random = new Random();
        AVTree avTree = new AVTree();
        List<Integer> dataList = new ArrayList<>(10);
        for (int i = 0; i < 10; i++){
            int j = random.nextInt(100);
            dataList.add(j);
            avTree.addData(j);
        }
        System.out.println(dataList);
//        avTree.preOrder();
//        System.out.println();
//        avTree.midOrder();
//        System.out.println();
//        avTree.afterOrder();
//        System.out.println();
        System.out.println(avTree.toString());
    }
}
