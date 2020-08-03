package com.honeycomb.leetcode.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class TreeNode {

    /**
     * 父节点ID
     */
    private int parentId;

    /**
     * 节点ID
     */
    private int id;

    /**
     * 子节点集合
     */
    private List<TreeNode> childrenNodeList;

    public TreeNode(int parentId, int id) {
        this.parentId = parentId;
        this.id = id;
        childrenNodeList = new ArrayList<>();
    }

    public void add(TreeNode treeNode) {
        childrenNodeList.add(treeNode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeNode treeNode = (TreeNode) o;
        return id == treeNode.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
