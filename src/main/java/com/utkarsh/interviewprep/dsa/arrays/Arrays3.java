package com.utkarsh.interviewprep.dsa.arrays;

import java.util.HashMap;
import java.util.Map;

public class Arrays3 {

    //Q1.Sort an array of 0s and 1s
    //https://www.geeksforgeeks.org/problems/segregate-0s-and-1s5106/1
    void segregate0and1(int[] arr) {
        // code here
        int n = arr.length;
        int i = 0;
        int j = n - 1;
        while (i < j) {
            if (arr[i] == 1 && arr[j] == 0) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            } else if (arr[i] == 0) {
                i++;
            } else {
                j--;
            }
        }
    }

    //Q2.Find Unique Elements
    //https://leetcode.com/problems/unique-number-of-occurrences/description/
    //https://www.geeksforgeeks.org/problems/find-unique-element2632/1
    public int findUnique(int k, int[] arr) {
        // code here
        Map<Integer,Integer> map= new HashMap<>();

        for(int i: arr){
            map.put(i,map.getOrDefault(i,0)+1);
        }
        for(Map.Entry<Integer,Integer> e: map.entrySet()){
            if(e.getValue()==1){
                return e.getKey();
            }
        }
        return -1;
    }

    //Q3.Find Missing Number
    //https://leetcode.com/problems/missing-number/
    //easy
    public int missingNumber(int[] nums) {
        int n = nums.length;
        int sum = 0;
        int sum2 = 0;
        for (int i = 0; i <= n; i++) {
            sum += i;
        }
        for (int i : nums) {
            sum2 += i;
        }

        return sum - sum2;
    }

    //crazy
    public int missingNumber2(int[] nums) {
        int n = nums.length;
        int xorsum = 0;
        for (int i : nums) {
            xorsum ^= i;
        }
        for (int i = 0; i <= n; i++) {
            xorsum ^= i;
        }

        return xorsum;
    }
    //HoweWork
    //Q4.Dutch national flag sort 0,1,2
    //https://www.geeksforgeeks.org/problems/sort-an-array-of-0s-1s-and-2s4231/1
    //https://leetcode.com/problems/sort-colors/description/
}
