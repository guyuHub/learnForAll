package com.example.learnForAll.leetcode;

import java.util.Deque;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author guyu
 * @desc
 * @date 2024/3/27-10:54 PM
 **/
public class LeeCodeWindow {
    /**
     * 3. 无重复字符的最长子串
     * https://leetcode.cn/problems/longest-substring-without-repeating-characters/description/?envType=study-plan-v2&envId=top-100-liked
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s == "") {
            return 0;
        }
        HashMap<Character, Integer> occ = new HashMap<Character, Integer>();
        int windowLeft = 0;
        int windowRight = 0;
        int maxLength = 0;
        int n = s.length();
        for (int i = 0; i < n; i++) {
            Character current = s.charAt(windowRight);
            windowLeft = Objects.isNull(occ.get(current)) ? windowLeft : Math.max(windowLeft,occ.get(current) + 1);
            maxLength = Math.max(maxLength, windowRight - windowLeft+1);
            occ.put(current,windowRight);
            windowRight++;
        }
        return maxLength;

    }

    public int maxSubArray(int[] nums) {
        int pre = 0, maxAns = nums[0];
        for (int x : nums) {
            pre = Math.max(pre + x, x);
            maxAns = Math.max(maxAns, pre);
        }
        return maxAns;

    }

    public static void main(String[] args) {
        System.out.println('a'-'a');
        System.out.println('p'-'a');
        System.out.println(1<<('a'-'a') | 1<<('p'-'a') | 1<<('y'-'a'));
        System.out.println(1<<('p'-'a') | 1<<('a'-'a') | 1<<('y'-'a'));
        LeeCodeWindow window = new LeeCodeWindow();
        System.out.println(window.lengthOfLongestSubstring("abba"));

        System.out.println(window.maxSubArray(new int[]{-1,-2,-3,-4}));
        System.out.println(window.maxSubArray(new int[]{-1,-2,-3,4}));

    }
}
