package com.utkarsh.interviewprep.dsa.arrays;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Arrays4Test {

    Arrays4 test = new Arrays4();

    @Test
    @Order(1)
    @DisplayName("Q1. Two Sum")
    void twoSum() {
        int[] input = {2, 7, 11, 15};
        int target = 9;
        int[] result = test.twoSum(input, target);

        System.out.println("Q1. Two Sum");
        System.out.println("  Input    : nums = " + java.util.Arrays.toString(input) + ", target = " + target);
        System.out.println("  Output   : " + java.util.Arrays.toString(result));
        System.out.println("  Expected : [0, 1]");

        assertArrayEquals(new int[]{0, 1}, result);
    }

    @Test
    @Order(2)
    @DisplayName("Q2. Three Sum")
    void threeSum() {
        int[] input = {-1, 0, 1, 2, -1, -4};
        List<List<Integer>> result = test.threeSum(input);

        System.out.println("Q2. Three Sum");
        System.out.println("  Input    : nums = " + java.util.Arrays.toString(input));
        System.out.println("  Output   : " + result);
        System.out.println("  Expected : [[-1, -1, 2], [-1, 0, 1]]");

        assertTrue(result.contains(List.of(-1, -1, 2)));
        assertTrue(result.contains(List.of(-1, 0, 1)));
        assertEquals(2, result.size());
    }

    @Test
    @Order(3)
    @DisplayName("Q3. Remove Duplicates From Sorted Array")
    void removeDuplicates() {

        int[] nums = {1, 1, 2};

        int k = test.removeDuplicates(nums);

        System.out.println("Q3. Remove Duplicates From Sorted Array");
        System.out.println("Input    : [1, 1, 2]");
        System.out.println("Output   : k = " + k);

        System.out.print("Modified : [");
        for (int i = 0; i < k; i++) {
            System.out.print(nums[i]);
            if (i < k - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");

        System.out.println("Expected : k = 2, nums = [1, 2]");

        assertEquals(2, k);
        assertEquals(1, nums[0]);
        assertEquals(2, nums[1]);
    }

    @Test
    @Order(4)
    @DisplayName("Q4. Find Duplicate Element")
    void findDuplicate() {
        int[] input = {1, 3, 4, 2, 2};
        int result = test.findDuplicate(input);

        System.out.println("Q4. Find Duplicate Element");
        System.out.println("  Input    : nums = " + java.util.Arrays.toString(input));
        System.out.println("  Output   : " + result);
        System.out.println("  Expected : 2");

        assertEquals(2, result);
    }

    @Test
    @Order(5)
    @DisplayName("Q5. Find Pivot Index")
    void pivotIndex() {
        int[] input = {1, 7, 3, 6, 5, 6};
        int result = test.pivotIndex(input);

        System.out.println("Q5. Find Pivot Index");
        System.out.println("  Input    : nums = " + java.util.Arrays.toString(input));
        System.out.println("  Output   : " + result);
        System.out.println("  Expected : 3");

        assertEquals(3, result);
    }
}