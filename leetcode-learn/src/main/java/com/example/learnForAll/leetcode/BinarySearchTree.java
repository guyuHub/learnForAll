package com.example.learnForAll.leetcode;



import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.Objects;

    /**
     * 二叉搜索树实现
     */
    public class BinarySearchTree {

        private byte[] LOCK = new byte[0];

        private BSFNode root;

        public BinarySearchTree() {
            this.root = null;
        }

        @Getter
        @Setter
        public class BSFNode {
            /**
             * 父节点
             */
            protected BSFNode parentNode;
            //左节点
            protected BSFNode left;
            //右节点
            protected BSFNode right;
            //值
            protected BSFNodeValue nodeValue;

            public BSFNode(BSFNode parentNode, BSFNode left, BSFNode right, BSFNodeValue nodeValue) {
                this.parentNode = parentNode;
                this.left = left;
                this.right = right;
                this.nodeValue = nodeValue;
            }
        }

        public class BSFNodeValue  {

            private int value;

            public BSFNodeValue(int cvalue) {
                this.value = cvalue;
            }

            public String formatValue() {
                return value + "";
            }

            public int compareTo(@NotNull Object target) {
                if (Objects.isNull(target)) {
                    return 1;
                }
                if (target instanceof BSFNodeValue) {
                    BSFNodeValue targetBsfNode = (BSFNodeValue) target;
                    return value - targetBsfNode.value;
                }
                if (target instanceof Integer) {
                    int compareValue = Objects.isNull(target) ? 0 : (Integer) ((Integer) target).intValue();
                    return value - compareValue;
                }
                return 1;
            }
        }


        public BinarySearchTree insert(Integer treeNodeValue) {
            synchronized (LOCK) {
                if (Objects.isNull(treeNodeValue)) {
                    return null;
                }
                BSFNodeValue nodeValue = new BSFNodeValue(treeNodeValue);
                if (Objects.isNull(root)) {
                    root = new BSFNode(null, null, null, nodeValue);
                    return this;
                } else {
                    return insertNode(root, nodeValue);
                }
            }
        }

        /**
         * 插入 递归执行
         *
         * @param treeNodeValue 待插入节点
         * @param parentNode    父节点
         * @return
         */
        private BinarySearchTree insertNode(BSFNode parentNode, BSFNodeValue treeNodeValue) {
            if (Objects.isNull(parentNode) || Objects.isNull(treeNodeValue)) {
                return null;
            }
            if (parentNode.getNodeValue().compareTo(treeNodeValue) < 0) {
                //当前节点值小于等于待插入节点值 右插入
                if (Objects.isNull(parentNode.getRight())) {
                    parentNode.right = new BSFNode(parentNode, null, null, treeNodeValue);
                    return this;
                }
                return insertNode(parentNode.right, treeNodeValue);
            } else {
                //当前节点值大于根节点值 右插入
                if (Objects.isNull(parentNode.getLeft())) {
                    parentNode.left = new BSFNode(parentNode, null, null, treeNodeValue);
                    return this;
                }
                return insertNode(parentNode.left, treeNodeValue);
            }

        }


        /**
         * 深度优先遍历
         */

        public enum DFSType {
            PRE_ORDER("先序遍历,当前节点->左节点->右节点"),
            IN_ORDER("中序遍历,左节点->当前节点->右节点"),
            POST_ORDER("后序遍历,左节点->右节点->当前节点"),
            ;
            private String desc;

            DFSType(String cdesc) {
                this.desc = cdesc;
            }
        }

        public void DFS(DFSType type) {
            if (Objects.isNull(root)) {
                return;
            }
            switch (type) {
                case PRE_ORDER:
                    preOrderDFS(root);
                    break;
                case IN_ORDER:
                    inOrderDFS(root);
                    break;
                case POST_ORDER:
                    postOrderDFS(root);
                    break;
                default:
                    return;
            }
        }

        public void preOrderDFS(BSFNode node) {
            if (Objects.nonNull(node)) {
                System.out.print(node.getNodeValue().formatValue() + "-->");
                preOrderDFS(node.left);
                preOrderDFS(node.right);
            }

        }

        public void inOrderDFS(BSFNode node) {
            if (Objects.nonNull(node)) {
                inOrderDFS(node.left);
                System.out.print(node.getNodeValue().formatValue() + "-->");
                inOrderDFS(node.right);
            }
        }

        public void postOrderDFS(BSFNode node) {
            if (Objects.nonNull(node)) {
                postOrderDFS(node.left);
                postOrderDFS(node.right);
                System.out.print(node.getNodeValue().formatValue() + "-->");
            }
        }

        /**
         * 广度优先遍历
         */
        public void BFS() {
            LinkedList<BSFNode> storeTreeNode = new LinkedList<BSFNode>();
            if (Objects.nonNull(root)) {
                storeTreeNode.offer(root);
            }
            while (!storeTreeNode.isEmpty()) {
                BSFNode node = storeTreeNode.poll();
                System.out.print(node.getNodeValue().formatValue() + "-->");
                if (Objects.nonNull(node.left)) {
                    storeTreeNode.offer(node.left);
                }
                if (Objects.nonNull(node.right)) {
                    storeTreeNode.offer(node.right);
                }
            }
        }

        /**
         * 搜索
         *
         * @param value            待搜索值
         * @param printSearchRoute 是否打印搜索路径 默认不打印
         * @return
         */
        public BSFNode search(int value, boolean printSearchRoute) {
            return search(root, value, printSearchRoute);
        }

        private BSFNode search(BSFNode node, int value, boolean printSearchRoute) {
            if (Objects.isNull(node)) {
                return null;
            }
            if (printSearchRoute) {
                System.out.println(node.getNodeValue().formatValue());
            }

            if (node.getNodeValue().compareTo(value) == 0) {
                return node;
            } else if (node.getNodeValue().compareTo(value) < 0) {
                return search(node.right, value, printSearchRoute);
            } else if (node.getNodeValue().compareTo(value) > 0) {
                return search(node.left, value, printSearchRoute);
            } else {
                return null;
            }
        }


        public boolean remove(int value) {
            synchronized (LOCK) {
                BSFNode waitRemoveNode = search(value, false);
                if (Objects.isNull(waitRemoveNode)) {
                    return false;
                }
                //无子
                if (Objects.isNull(waitRemoveNode.left) && Objects.isNull(waitRemoveNode.right)) {
                    waitRemoveNode = null;
                    return true;
                }
                //双子 寻找中序前驱节点(删除节点的左子树最大节点) 或中序后继节点(删除节点的右子树的最小节点) 好处是这两个节点的儿子节点肯定<=1 本实现中使用中序前驱节点替换删除节点
                else if (Objects.nonNull(waitRemoveNode.left) && Objects.nonNull(waitRemoveNode.right)) {
                    //寻找中序前驱节点
                    BSFNode inOrderPreNode = waitRemoveNode.left;
                    if (Objects.isNull(inOrderPreNode.right)) {
                        waitRemoveNode.nodeValue = inOrderPreNode.nodeValue;
                        waitRemoveNode.left = inOrderPreNode.left;
                        if(Objects.nonNull(waitRemoveNode.left)){
                            waitRemoveNode.left.parentNode = waitRemoveNode;
                        }
                    } else {
                        while (Objects.nonNull(inOrderPreNode.right)) {
                            inOrderPreNode = inOrderPreNode.right;
                        }
                        waitRemoveNode.nodeValue = inOrderPreNode.nodeValue;
                        inOrderPreNode.parentNode.right = null;
                    }
                }
                //单子  孙字节点变为儿子节点
                else {
                    //无左儿子节点
                    if(Objects.isNull(waitRemoveNode.left)){
                        waitRemoveNode.nodeValue = waitRemoveNode.right.nodeValue;
                        waitRemoveNode.right = waitRemoveNode.right.right;
                        return true;
                    }
                    //无右儿子节点
                    else if(Objects.isNull(waitRemoveNode.right)){
                        waitRemoveNode.nodeValue = waitRemoveNode.left.nodeValue;
                        waitRemoveNode.left = waitRemoveNode.left.left;
                    }
                }
                return true;
            }
        }

        public static void main(String[] args) {
            BinarySearchTree tree = new BinarySearchTree();
            tree.insert(5).insert(2).insert(8).insert(1)
                    .insert(3).insert(7).insert(9);
            tree.DFS(DFSType.PRE_ORDER);
            System.out.println("=============================");
            tree.DFS(DFSType.IN_ORDER);
            System.out.println("=============================");
            tree.DFS(DFSType.POST_ORDER);
            System.out.println("=============================");
            tree.BFS();

        }
    }

