package com.honeycomb.leetcode;


import com.honeycomb.leetcode.entity.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author maoliang
 */
public class AVTree {

    /**
     * 根节点
     */
    private Node root;

    private int size;

    public void addData(int data){
        Node node = new Node();
        node.data = data;
        addNode(node);
    }

    /**
     * 增加节点
     * @param node
     */
    private void addNode(Node node){
        if(Objects.isNull(root)){
            this.root = node;
            size ++;
        }else{
            addNode(root, node);
        }
    }

    private void addNode(Node current, Node node){
        if(node.data < current.data){
            if(Objects.isNull(current.left)){
                current.left = node;
                node.parent = current;
                size ++;
            }else {
                addNode(current.left, node);
            }
        }else if(node.data > current.data){
            if(Objects.isNull(current.right)){
                current.right = node;
                node.parent = current;
                size ++;
            }else {
                addNode(current.right, node);
            }
        }
    }

    public void delete(int data){
        Node node = find(data);
        if(Objects.nonNull(node)){
            deleteNode(node);
        }
    }

    public void update(int oldData, int newdata){
        Node node = find(oldData);
        deleteNode(node);
        addData(newdata);
    }

    private Node find(int data){
        if(Objects.isNull(root)){
            return null;
        }
        Node node = root;
        while(Objects.nonNull(node)){
            if(node.data == data){
                return node;
            }

            if(node.data > data){
                node = node.left;
                continue;
            }

            node = node.right;
        }
        return null;
    }


    public void deleteNode(Node node){
        Node newTreeRoot;
        if(Objects.isNull(node.right)){
            newTreeRoot = node.left;
        }else if(Objects.isNull(root.left)){
            newTreeRoot = node.right;
        }else{
            /**
             * 删除root节点，分成两棵树，以右树为新树。
             * 左树挂在右树最小节点左边。
             */
            Node left = node.left;
            newTreeRoot = node.right;
            Node min = findMin(newTreeRoot);
            min.left = left;
        }

        if(Objects.nonNull(node.parent)){
            node.parent.updateSup(node, newTreeRoot);
        }else if(node == root) {
            root = newTreeRoot;
        }
    }

    public Node findMin(){
        return findMin(root);
    }

    private Node findMin(Node node){
        if(Objects.isNull(root)){
            return null;
        }
        while(Objects.nonNull(node.left)){
            node = node.left;
        }
        return node;
    }

    /**
     * 后序
     */
    public void afterOrder(){
        if(Objects.nonNull(root)){
            root.afterOrder();
        }
    }

    /**
     * 中序
     */
    public void midOrder(){
        if(Objects.nonNull(root)){
            root.midOrder();
        }
    }

    /**
     * 先序
     */
    public void preOrder(){
        if(Objects.nonNull(root)){
            root.preOrder();
        }
    }

    @Override
    public String toString() {
        if(Objects.isNull(root)){
            return "";
        }
        List<Node> treeNodeList = new ArrayList<>();
        int lastAddCount = 1;
        treeNodeList.add(root);
        while(treeNodeList.size() < size) {
            lastAddCount = addCount(treeNodeList, lastAddCount);
        }
       return treeNodeList.toString();
    }

    private int addCount(List<Node> nodeList, int lastCount){
        int addCount = 0;
        int currentSize = nodeList.size();
        for(int i = currentSize - lastCount; i < currentSize; i++){
            addCount += addSize(nodeList, nodeList.get(i));
        }
        return addCount;
    }

    private int addSize(List<Node> nodeList, Node node){
        int result = 0;
        if(node.left != null){
            nodeList.add(node.left);
            result++;
        }
        if(node.right != null){
            nodeList.add(node.right);
            result++;
        }
        return result;
    }
}
