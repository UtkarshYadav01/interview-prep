package com.utkarsh.interviewprep.dsa.arrays;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Arrays3Test {

    Arrays3 test = new Arrays3();

    // =========================================================
    // Q1. Segregate 0s and 1s
    // =========================================================

    @ParameterizedTest
    @CsvSource({
            "'0,1,0,1,0,0,1,1,1,0', '0,0,0,0,0,1,1,1,1,1'",
            "'1,1', '1,1'"
    })
    @Order(1)
    @DisplayName("Q1. Segregate 0s and 1s")
    void segregate0and1(String input, String expected) {

        int[] arr = Arrays.stream(input.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        int[] expectedArr = Arrays.stream(expected.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        test.segregate0and1(arr);

        assertArrayEquals(expectedArr, arr);
    }

    // =========================================================
    // Q2. Find Unique Element
    // =========================================================

    @ParameterizedTest
    @CsvSource({
            "3, '6,2,5,2,2,6,6', 5",
            "4, '2,2,2,10,2', 10"
    })
    @Order(2)
    @DisplayName("Q2. Find Unique Element")
    void findUnique(int k, String input, int expected) {

        int[] arr = Arrays.stream(input.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        assertEquals(expected, test.findUnique(k, arr));
    }

    // =========================================================
    // Q3. Missing Number
    // =========================================================

    @ParameterizedTest
    @CsvSource({
            "'3,0,1', 2",
            "'0,1', 2",
            "'9,6,4,2,3,5,7,0,1', 8"
    })
    @Order(3)
    @DisplayName("Q3. Missing Number (Sum)")
    void missingNumber(String input, int expected) {

        int[] nums = Arrays.stream(input.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        assertEquals(expected, test.missingNumber(nums));
    }

    // =========================================================
    // Q3. Missing Number XOR
    // =========================================================

    @ParameterizedTest
    @CsvSource({
            "'3,0,1', 2",
            "'0,1', 2",
            "'9,6,4,2,3,5,7,0,1', 8"
    })
    @Order(4)
    @DisplayName("Q3. Missing Number (XOR)")
    void missingNumber2(String input, int expected) {

        int[] nums = Arrays.stream(input.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        assertEquals(expected, test.missingNumber2(nums));
    }

    // =========================================================
    // Q4. Dutch National Flag Sort
    // =========================================================

    @ParameterizedTest
    @CsvSource({
            "'2,0,2,1,1,0', '0,0,1,1,2,2'",
            "'2,0,1', '0,1,2'"
    })
    @Order(5)
    @DisplayName("Q4. Dutch National Flag Sort")
    void sortColors(String input, String expected) {

        int[] nums = Arrays.stream(input.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        int[] expectedArr = Arrays.stream(expected.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        test.sortColors(nums);

        assertArrayEquals(expectedArr, nums);
    }
}