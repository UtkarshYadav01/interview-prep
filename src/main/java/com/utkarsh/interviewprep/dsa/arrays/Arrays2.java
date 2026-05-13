package com.utkarsh.interviewprep.dsa.arrays;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Arrays2 {

    //Q1. Reverse an Array
    int[] question1(int[] arr) {
        return arr;
    }

    //Q2. Shift array elements by 1 Position
    int[] question2(int[] arr) {
        return arr;
    }

    //Q3. Print Extreme Elements in an alternate manner
    int[] question3(int[] arr) {
        return arr;
    }

    //Q4. Find the mode of the array
    int question4(int[] arr) {
        return arr.length;
    }

    //Q5. Identify elements with Highest and Lowest Frequency
    Map<Integer, Integer> question5(int[] arr) {
        // Streams approach
        Map<Integer, Long> freqMap = Arrays.stream(arr)
                .boxed()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        Map.Entry<Integer, Long> maxEntry = freqMap.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        Map.Entry<Integer, Long> minEntry = freqMap.entrySet()
                .stream()
                .min(Map.Entry.comparingByValue())
                .orElse(null);

        Map<Integer, Integer> streamResult = new HashMap<>();
        streamResult.put(maxEntry.getKey(), maxEntry.getValue().intValue());
        streamResult.put(minEntry.getKey(), minEntry.getValue().intValue());

        // Traditional approach
        Map<Integer, Integer> map = new HashMap<>();
        int maxKey = 0, maxValue = Integer.MIN_VALUE;
        int minKey = 0, minValue = Integer.MAX_VALUE;
        for (int i : arr) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }
        for (int key : map.keySet()) {
            if (map.get(key) > maxValue) {
                maxValue = map.get(key);
                maxKey = key;
            }
            if (map.get(key) < minValue) {
                minValue = map.get(key);
                minKey = key;
            }
        }
        Map<Integer, Integer> result = new HashMap<>();
        result.put(maxKey, maxValue);
        result.put(minKey, minValue);
        return streamResult; // O(n) time O(n) space
    }

    //HomeWork
    //Q6. Shift array elements by k Position
    int[] question6(int[] arr, int k) {
        return arr;
    }

    //Q7. Print Union of Array elements
    int[] question7(int[] arr1, int[] arr2) {
        return arr1;
    }

    //https://www.programiz.com/online-compiler/9jCK0KL06vIAp
}
