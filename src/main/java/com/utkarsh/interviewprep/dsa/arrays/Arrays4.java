package com.utkarsh.interviewprep.dsa.arrays;

import java.util.*;
import java.util.stream.Collectors;

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
    //https://leetcode.com/problems/remove-duplicates-from-sorted-array/
    public int removeDuplicates(int[] nums) {
        //2 pointers
        int low = 0, high = 1;
        while (high < nums.length) {
            if (nums[low] == nums[high]) {
                high++;
            } else {
                low++;
                nums[low] = nums[high];
                high++;
            }
        }
        return low + 1;
    }

    //Q4.Remove duplicates from sorted array
    public int findDuplicate(int[] nums) {
        //stream approach
        Arrays.stream(nums)
                .boxed()
                .collect(Collectors.groupingBy(
                        e -> e,
                        Collectors.counting()
                )).entrySet()
                .stream().filter(e -> e.getValue() > 1)
                .mapToInt(Map.Entry::getKey).findFirst().getAsInt();

        //standard approach
        Map<Integer, Integer> map = new LinkedHashMap<>();
        for (int n : nums) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }
        for (Map.Entry<Integer, Integer> e : map.entrySet()) {
            if (e.getValue() > 1) {
                return e.getKey();
            }
        }
        return -1;
    }

    //Q5.Find pivot index
    //https://leetcode.com/problems/find-pivot-index/description/
    public int pivotIndex(int[] nums) {
        int rsum=0, lsum=0;
        for(int n:nums){
            rsum+=n;
        }
        for(int i=0;i<nums.length;i++){
            rsum-=nums[i];
            if(rsum==lsum){
                return i;
            }
            lsum+=nums[i];
        }
        return -1;
    }
}
