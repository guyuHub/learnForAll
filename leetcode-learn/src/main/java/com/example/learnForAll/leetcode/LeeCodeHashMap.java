package com.example.learnForAll.leetcode;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
 * <p>
 * 字母异位词 是由重新排列源单词的所有字母得到的一个新单词。
 * <p>
 * <p>
 * <p>
 * 示例 1:
 * <p>
 * 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
 * 输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
 * 示例 2:
 * <p>
 * 输入: strs = [""]
 * 输出: [[""]]
 * 示例 3:
 * <p>
 * 输入: strs = ["a"]
 * 输出: [["a"]]
 * <p>
 * <p>
 * 提示：
 * <p>
 * 1 <= strs.length <= 104
 * 0 <= strs[i].length <= 100
 * strs[i] 仅包含小写字母
 */
public class LeeCodeHashMap {

    /**
     * 1. 两数之和
     * https://leetcode.cn/problems/two-sum/description/?envType=study-plan-v2&envId=top-100-liked
     *
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> targetMap = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            int varTarget = target - nums[i];
            Integer varTargetIndex = targetMap.get(varTarget);
            if (Objects.nonNull(varTargetIndex)) {
                return new int[]{varTargetIndex, i};
            } else {
                targetMap.put(nums[i], i);
            }
        }
        return null;
    }

    /**
     * 49. 字母异位词分组
     * https://leetcode.cn/problems/group-anagrams/description/?envType=study-plan-v2&envId=top-100-liked
     *
     * @param strs
     * @return
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> result = new ArrayList<List<String>>();
        HashMap<String, List<String>> resultStore = new HashMap<>();

        Arrays.stream(strs).forEach(str -> {
            if (str == null || str == "") {
                result.add(new ArrayList<>());
                return;
            }
            String index = Arrays.toString(str.chars().sorted().toArray());
            List<String> store = resultStore.get(index);
            if (null == store) {
                store = new ArrayList<>();
                resultStore.put(index, store);
            }
            store.add(str);
        });
        resultStore.entrySet().forEach(entryKey -> {
            result.add(entryKey.getValue());
        });

        return result;
    /*    return new ArrayList<>(Arrays.stream(strs)
                .collect(Collectors.groupingBy(str -> {
                    // 返回 str 排序后的结果。
                    // 按排序后的结果来grouping by，算子类似于 sql 里的 group by。
                    char[] array = str.toCharArray();
                    Arrays.sort(array);
                    return new String(array);
                })).values());*/

    }

    /**
     * 128. 最长连续序列
     * https://leetcode.cn/problems/longest-consecutive-sequence/description/?envType=study-plan-v2&envId=top-100-liked
     *
     * @param nums
     * @return
     */
    public int longestConsecutive(int[] nums) {
        //by 官方题解，取没有前驱节点减少次数，取没有后驱节点也是一样的效果
        Set<Integer> num_set = new HashSet<>(nums.length);
        for (int num : nums) {
            num_set.add(num);
        }

        int longestStreak = 0;

        for (int num : num_set) {
            if (!num_set.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;

                while (num_set.contains(currentNum + 1)) {
                    currentNum += 1;
                    currentStreak += 1;
                }

                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }

        return longestStreak;


        /*AtomicInteger maxValue = new AtomicInteger(0);
        final AtomicInteger endIndex = new AtomicInteger(0);
        Arrays.stream(nums).sorted().reduce(
                (one, two) -> {
                    if (two == one + 1) {
                        endIndex.set(endIndex.get() + 1);
                    } else if (two == one) {

                    } else {
                        endIndex.set(0);
                    }
                    maxValue.set(Math.max(maxValue.get(), endIndex.get()));

                    return two;
                });
        return maxValue.get() + 1;*/

    }

    public static void main(String[] args) {
        LeeCodeHashMap hash = new LeeCodeHashMap();
        System.out.println(hash.longestConsecutive(new int[]{0, 3, 7, 2, 5, 8, 4, 6, 0, 1}));
    }
}
