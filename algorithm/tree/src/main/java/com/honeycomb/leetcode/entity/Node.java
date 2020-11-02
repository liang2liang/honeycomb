package com.honeycomb.leetcode.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class Node {

    /**
     * 数据
     */
    public int data;

    /**
     * 左节点
     */
    public Node left;

    /**
     * 右节点
     */
    public Node right;

    /**
     * 父节点
     */
    public Node parent;

    public void updateSup(Node oldNode, Node newNode){
        if(Objects.nonNull(oldNode)){
            if(this.left.data == oldNode.data){
                this.left = newNode;
            }else if(this.right.data == oldNode.data){
                this.right = newNode;
            }
        }
    }

    /**
     * 后序
     */
    public void afterOrder(){
        if(Objects.nonNull(left)) {
            left.afterOrder();
        }
        if(Objects.nonNull(right)) {
            right.afterOrder();
        }
        System.out.printf(data + " ");
    }

    /**
     * 中序
     */
    public void midOrder(){
        if(Objects.nonNull(left)) {
            left.midOrder();
        }
        System.out.printf(data + " ");
        if(Objects.nonNull(right)) {
            right.midOrder();
        }
    }

    /**
     * 先序
     */
    public void preOrder(){
        System.out.printf(data + " ");
        if(Objects.nonNull(left)) {
            left.preOrder();
        }
        if(Objects.nonNull(right)) {
            right.preOrder();
        }
    }

    @Override
    public String toString() {
        return data + "";
    }
}
