package com.utkarsh.interview.june26;

import java.util.Arrays;

public class Xebia03June2026 {
    static void main() {

        /*
        Xebia Round 1
        - Give introduction
        - Explain project
        - Spring Security OAuth2
        - Spring Batch
        - Multi-threading
        - Logging: Logback + Splunk

        Q: Rotate array to the right by k steps

        Input: nums = [1,2,3,4,5,6,7], k = 3 Output: [5,6,7,1,2,3,4]
        Explanation:
        rotate 1 steps to the right: [7,1,2,3,4,5,6]
        rotate 2 steps to the right: [6,7,1,2,3,4,5]
        rotate 3 steps to the right: [5,6,7,1,2,3,4]

        Approach: Reversal Algorithm — O(n) time, O(1) space
        Step 1: Reverse entire array      → [7,6,5,4,3,2,1]
        Step 2: Reverse first k elements  → [5,6,7,4,3,2,1]
        Step 3: Reverse remaining n-k     → [5,6,7,1,2,3,4]

        Note: k = k % n handles cases where k >= n (full rotations cancel out)

        - Tell Java annotations
        */

        System.out.println(Arrays.toString(rightRotate(new int[]{1, 2, 3, 4, 5, 6, 7}, 3)));
    }

    static int[] rightRotate(int[] arr, int k) {
        int n = arr.length;
        k = k % n;                  // normalize k; no-op if k < n
        reverse(arr, 0, n - 1);     // step 1: reverse full array
        reverse(arr, 0, k - 1);     // step 2: reverse first k elements
        reverse(arr, k, n - 1);     // step 3: reverse remaining n-k elements
        return arr;
    }

    static void reverse(int[] arr, int start, int end) {
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }
}