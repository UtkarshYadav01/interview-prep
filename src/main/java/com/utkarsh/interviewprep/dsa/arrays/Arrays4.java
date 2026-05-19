package com.utkarsh.interviewprep.dsa.arrays;

import java.util.*;

public class Arrays4 {

    //Q1.Two sum
    //https://leetcode.com/problems/two-sum/description/
    public int[] twoSum(int[] nums, int target) {
        //nums = [2,7,11,15], target = 9 Output: [0,1]
        int n = nums.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{};
    }

    //Q2.Three sum
    //https://leetcode.com/problems/3sum/description/
    public List<List<Integer>> threeSum(int[] nums) {
        Set<List<Integer>> set = new HashSet<>();
        int n = nums.length;
        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        List<Integer> temp = new ArrayList<>();
                        temp.add(nums[i]);
                        temp.add(nums[j]);
                        temp.add(nums[k]);
                        Collections.sort(temp);
                        set.add(temp);
                    }
                }
            }
        }
        return new ArrayList<>(set);
    }

    //Q3.Find first repeating element in the array

    //Q4.Remove duplicates from sorted array
    //Q5.Find pivot index
}
