package com.example.learnForAll.leetcode;

import com.alibaba.fastjson2.JSON;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
 *
 * 你可以按任意顺序返回答案。
 *
 *
 *
 * 示例 1：
 *
 * 输入：nums = [2,7,11,15], target = 9
 * 输出：[0,1]
 * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
 * 示例 2：
 *
 * 输入：nums = [3,2,4], target = 6
 * 输出：[1,2]
 * 示例 3：
 *
 * 输入：nums = [3,3], target = 6
 * 输出：[0,1]
 *
 *
 * 提示：
 *
 * 2 <= nums.length <= 104
 * -109 <= nums[i] <= 109
 * -109 <= target <= 109
 * 只会存在一个有效答案
 */
public class TwoNumberSum {

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> targetMap = new HashMap<>(nums.length);
        for(int i=0;i<nums.length;i++){
            int varTarget = target - nums[i];
            Integer varTargetIndex = targetMap.get(varTarget);
            if(Objects.nonNull(varTargetIndex)){
               return new int[]{varTargetIndex,i};
            }else {
                targetMap.put(nums[i],i );
            }
        }
        return null;
    }

    public static void main(String[] args) {
        int[] nums =  twoSum(new int[]{3,2,4},6);
        System.out.println(JSON.toJSONString(nums));

        System.out.println(JSON.toJSONString(twoSum(new int[]{-3,4,3,90},0)));

    }
}
