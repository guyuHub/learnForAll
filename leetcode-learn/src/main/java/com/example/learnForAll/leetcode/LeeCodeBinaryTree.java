package com.example.learnForAll.leetcode;

import lombok.Builder;
import lombok.Data;
import org.apache.flink.table.planner.expressions.In;

import java.util.*;

/**
 * 力扣二叉树
 *
 * @author guyu
 * @desc
 * @date 2024/3/7-7:45 PM
 **/
public class LeeCodeBinaryTree {
    /**
     * 中序遍历
     * 94 https://leetcode.cn/problems/binary-tree-inorder-traversal/submissions/508919677/?envType=study-plan-v2&envId=top-100-liked
     *
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<Integer>();
        if (root == null) {
            return result;
        }
        if (Objects.nonNull(root.left)) {
            result.addAll(inorderTraversal(root.left));
        }
        result.add(root.val);
        if (Objects.nonNull(root.right)) {
            result.addAll(inorderTraversal(root.right));
        }
        return result;
    }

    /**
     * 二叉树的最大深度
     * 104:https://leetcode.cn/problems/maximum-depth-of-binary-tree/?envType=study-plan-v2&envId=top-100-liked
     *
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    /**
     * 翻转二叉树
     * 226 https://leetcode.cn/problems/invert-binary-tree/?envType=study-plan-v2&envId=top-100-liked
     *
     * @param root
     * @return
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        if (root.left == null && root.right == null) {
            return root;
        }
        invertTree(root.left);
        invertTree(root.right);

        TreeNode left = root.left;
        root.left = root.right;
        root.right = left;

        return root;

    }

    public static void main(String[] args) {
        LeeCodeBinaryTree tree = new LeeCodeBinaryTree();
        TreeNode root = TreeNode.builder().val(1)
                .left(TreeNode.builder().val(2).left(TreeNode.builder().val(2).build()).build())
                .right(TreeNode.builder().val(2).left(TreeNode.builder().val(2).build()).build())
                .build();
        System.out.println(tree.isSymmetric(root));
    }

    /**
     * 对称二叉树
     * 101 https://leetcode.cn/problems/symmetric-tree/?envType=study-plan-v2&envId=top-100-liked
     *
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        return isSymmetricBad(root) || isSymmetricGood(root);
    }

    /**
     * 不好的对称二叉树判断方法
     *
     * @param root
     * @return
     */
    public boolean isSymmetricBad(TreeNode root) {

        if (root == null) {
            return true;
        }

        ArrayDeque<String> leftQueue = new ArrayDeque(128);
        ArrayDeque<String> rightQueue = new ArrayDeque(128);
        traverseTreeWithDirection(root.left, 1, leftQueue, true);
        traverseTreeWithDirection(root.right, 1, rightQueue, false);
        if (leftQueue.size() != rightQueue.size()) {
            return false;
        }
        if (rightQueue.size() == 0) {
            return true;
        }
        while (leftQueue.size() != 0) {
            String leftValue = leftQueue.poll();
            String rightValue = rightQueue.poll();
            if (!leftValue.equals(rightValue)) {
                return false;
            }
        }
        return true;

    }

    /**
     * 根据指定方向遍历树
     *
     * @param root
     * @param deque
     * @param leftFirst
     */
    private void traverseTreeWithDirection(TreeNode root, int depth, ArrayDeque deque, boolean leftFirst) {
        if (root == null) {
            deque.push(depth + String.valueOf((Object) null));
            return;
        }
        if (leftFirst && Objects.nonNull(root.left)) {
            traverseTreeWithDirection(root.left, ++depth, deque, true);
        } else if (!leftFirst && Objects.nonNull(root.right)) {
            traverseTreeWithDirection(root.right, ++depth, deque, false);
        }
        deque.push(root.val + "");

        if (leftFirst) {
            traverseTreeWithDirection(root.right, ++depth, deque, true);
        } else if (!leftFirst) {
            traverseTreeWithDirection(root.left, ++depth, deque, false);
        }
    }

    public boolean isSymmetricGood(TreeNode root) {
        if (root == null) return true;
        return dfs(root.left, root.right);
    }

    public boolean dfs(TreeNode n1, TreeNode n2) {
        if (n1 == null && n2 == null) return true;
        if (n1 == null || n2 == null || n1.val != n2.val) {
            return false;
        }
        return dfs(n1.left, n2.right) && dfs(n1.right, n2.left);
    }

    /**
     * 二叉树的直径
     * 543 https://leetcode.cn/problems/diameter-of-binary-tree/description/?envType=study-plan-v2&envId=top-100-liked
     *
     * @Note: 未自己解出
     * @param root
     * @return
     */
    private int diameter = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null || (root.right == null && root.left == null)) {
            return 0;
        }
        return diameter;
    }

    /**
     * 求某个节点的最大深度
     *
     * @param node
     * @return
     */
    private int depth(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int rightDepth = depth(node.right);
        int leftDepth = depth(node.left);
        diameter = Math.max(diameter, rightDepth + leftDepth);
        return Math.max(rightDepth, leftDepth) + 1;
    }

    /**
     * 102. 二叉树的层序遍历
     * https://leetcode.cn/problems/binary-tree-level-order-traversal/description/?envType=study-plan-v2&envId=top-100-liked
     */
    private ArrayDeque<TreeNode> levelOrderDequeOne = new ArrayDeque<>();
    private ArrayDeque<TreeNode> levelOrderDequeTwo = new ArrayDeque<>();

    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> result = new ArrayList<>();
        levelOrderDequeOne.offer(root);
        bfsForLevelOrder(root, result);
        return result;
    }

    private void bfsForLevelOrder(TreeNode root, List<List<Integer>> result) {
        if (root == null) {
            return;
        }

        while (true) {
            if (levelOrderDequeTwo.isEmpty() && levelOrderDequeOne.isEmpty()) {
                return;
            }
            List<Integer> storeFoeOne = new ArrayList<>();
            while (!levelOrderDequeOne.isEmpty()) {
                TreeNode currentNode = levelOrderDequeOne.poll();
                if (Objects.nonNull(currentNode)) {
                    storeFoeOne.add(currentNode.val);
                }
                if (Objects.nonNull(currentNode.left)) {
                    levelOrderDequeTwo.offer(currentNode.left);
                }
                if (Objects.nonNull(currentNode.right)) {
                    levelOrderDequeTwo.offer(currentNode.right);
                }
            }
            if(!storeFoeOne.isEmpty()) {
                result.add(storeFoeOne);
            }

            List<Integer> storeFoeTwo = new ArrayList<>();
            while (!levelOrderDequeTwo.isEmpty()) {
                TreeNode currentNode = levelOrderDequeTwo.poll();
                if (Objects.nonNull(currentNode)) {
                    storeFoeTwo.add(currentNode.val);
                }
                if (Objects.nonNull(currentNode.left)) {
                    levelOrderDequeOne.offer(currentNode.left);
                }
                if (Objects.nonNull(currentNode.right)) {
                    levelOrderDequeOne.offer(currentNode.right);
                }
            }
            if(!storeFoeTwo.isEmpty()) {
                result.add(storeFoeTwo);
            }
        }
    }


    @Builder
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
