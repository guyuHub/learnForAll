package com.example.learnForAll.leetcode;

import java.util.Arrays;

/**
 * 双指针
 *
 * @author guyu
 * @desc
 * @date 2024/3/12-12:48 PM
 **/
public class LeeCodeDoublePoints {
    /**
     * 283. 移动零
     * https://leetcode.cn/problems/move-zeroes/description/?envType=study-plan-v2&envId=top-100-liked
     *
     * @param nums
     */
    public void moveZeroes2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }
 /*       int waitSwapIndex = nums.length - 1;
        for (int i = waitSwapIndex; i >= 0; i--) {
            if (nums[i] == 0 && i != waitSwapIndex) {
                for (int j = i; j < waitSwapIndex; j++) {
                    int waitSwapValue = nums[j + 1];
                    nums[j + 1] = nums[j];
                    nums[j] = waitSwapValue;
                }
                waitSwapIndex--;
            }
        }*/
        int left = 0, right = 0;
        int length = nums.length;

    }

    public void moveZeroes(int[] nums) {
        int n = nums.length, left = 0, right = 0;
        while (right < n) {
            if (nums[right] != 0) {
                if (left != right) {
                    swap(nums, left, right);
                }
                left++;
            }
            right++;
        }
    }

    private void swap(int[] nums, int left, int right) {
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
    }


    /**
     * 11. 盛最多水的容器
     * https://leetcode.cn/problems/container-with-most-water/description/?envType=study-plan-v2&envId=top-100-liked
     *
     * @param height
     * @return
     */
    public int maxArea(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        int maxArea = 0, left = 0, right = height.length - 1;
        while (left < right) {
            int currentHeight = Math.min(height[left], height[right]);
            int currentWeight = right - left;
            maxArea = Math.max(currentWeight * currentHeight, maxArea);
            if (height[left] > height[right]) {
                right--;
            } else {
                left++;
            }
        }
        return maxArea;
    }

    /**
     * 42. 接雨水
     * https://leetcode.cn/problems/trapping-rain-water/description/?envType=study-plan-v2&envId=top-100-liked
     *
     * @param height
     * @return
     */
    public int trap(int[] height) {
        if (height == null || height.length < 2) {
            return 0;
        }
        int maxTrap = 0, left = 0;
        int waitSubTrap = 0;
        for (int right = 1; right < height.length; right++) {
            if (height[left] == 0) {
                left++;
                continue;
            }
            if (height[right] == 0) {
                continue;
            }
            //待减去的体积
            if (height[right] < height[left]) {
                waitSubTrap = waitSubTrap +  height[right];
                System.out.println("待减去--"+waitSubTrap);
                continue;
            }

            if (height[right] >= height[left]) {
                maxTrap = maxTrap + (right - left-1) * height[left] - waitSubTrap;
                System.out.println("新体积汇总--"+maxTrap+"待减去--"+waitSubTrap);
                waitSubTrap = 0;
                left = right;
            }
        }

        return maxTrap;
    }

    public static void main(String[] args) {
        LeeCodeDoublePoints test = new LeeCodeDoublePoints();
/*        int[] move = new int[]{1, 3, 0, 4, 12};
        test.moveZeroes(move);
        System.out.println(Arrays.toString(move));*/

        int[] testEg1 = new int[]{0,1,0,2,1,0,1,3,2,1,2,1};
        test.trap(testEg1);
    }
}
