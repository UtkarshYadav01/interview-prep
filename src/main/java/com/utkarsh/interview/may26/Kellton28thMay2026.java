package com.utkarsh.interview.may26;

import java.util.HashMap;
import java.util.Map;

public class Kellton28thMay2026 {

    // ─────────────────────────────────────────────
    // 1. Longest Substring Without Repeating Characters
    //    "abcabcbb" → 3 ("abc")
    //    Sliding Window + HashMap
    //    Time: O(n)  |  Space: O(min(n, charset))
    // ─────────────────────────────────────────────
    public static int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> lastSeen = new HashMap<>();
        int maxLen = 0;
        int left = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);

            // If char was seen AND is inside the current window → shrink from left
            if (lastSeen.containsKey(c) && lastSeen.get(c) >= left) {
                left = lastSeen.get(c) + 1;
            }

            lastSeen.put(c, right);
            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }

    // ─────────────────────────────────────────────
    // 2. Second Largest in an Integer Array
    //    {3, 1, 4, 1, 5, 9, 2, 6} → 6
    //    Single pass, no sorting
    //    Time: O(n)  |  Space: O(1)
    // ─────────────────────────────────────────────
    public static int secondLargest(int[] arr) {
        if (arr == null || arr.length < 2) {
            throw new IllegalArgumentException("Array must have at least 2 elements");
        }

        int first = Integer.MIN_VALUE;
        int second = Integer.MIN_VALUE;

        for (int num : arr) {
            if (num > first) {
                second = first;   // old first drops to second
                first = num;
            } else if (num > second && num != first) {  // distinct second largest
                second = num;
            }
        }

        if (second == Integer.MIN_VALUE) {
            throw new IllegalStateException("No distinct second largest element");
        }
        return second;
    }

    // ─────────────────────────────────────────────
    // Driver
    // ─────────────────────────────────────────────
    public static void main(String[] args) {

        // Q1
        System.out.println(lengthOfLongestSubstring("abcabcbb")); // 3
        System.out.println(lengthOfLongestSubstring("bbbbb"));    // 1
        System.out.println(lengthOfLongestSubstring("pwwkew"));   // 3

        // Q2
        System.out.println(secondLargest(new int[]{3, 1, 4, 1, 5, 9, 2, 6})); // 6
        System.out.println(secondLargest(new int[]{10, 10, 9}));               // 9
        System.out.println(secondLargest(new int[]{5, 1}));                    // 1
    }
}