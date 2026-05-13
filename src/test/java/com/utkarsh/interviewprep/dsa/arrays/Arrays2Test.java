package com.utkarsh.interviewprep.dsa.arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class Arrays2Test {

    Arrays2 test = new Arrays2();

    @Test
    @DisplayName("Q1. Reverse an Array")
    void question1() {
        System.out.println(Arrays.toString(test.question1(new int[]{1, 2, 3, 4})));
    }

    @Test
    @DisplayName("Q2. Shift array elements by 1 Position")
    void question2() {
        System.out.println(Arrays.toString(test.question2(new int[]{2, 4, 3, 3})));
    }

    @Test
    @DisplayName("Q3. Print Extreme Elements in alternate manner")
    void question3() {
        System.out.println(Arrays.toString(test.question3(new int[]{1, 2, 3, 4, 5, 6})));
    }

    @Test
    @DisplayName("Q4. Find the mode of the array")
    void question4() {
        System.out.println(test.question4(new int[]{1, 2, 2, 3, 3, 3, 4}));
    }

    @Test
    @DisplayName("Q5. Highest and Lowest Frequency elements")
    void question5() {
        System.out.println(test.question5(new int[]{2, 1, 3, 1, 3, 1, 4, 1, 4, 1, 4, 1}));
    }

    @Test
    @DisplayName("Q6. Shift array elements by k Position")
    void question6() {
        System.out.println(Arrays.toString(test.question6(new int[]{1, 2, 3, 4, 5}, 2)));
    }

    @Test
    @DisplayName("Q7. Print Union of Array elements")
    void question7() {
        System.out.println(Arrays.toString(test.question7(new int[]{1, 2, 3}, new int[]{3, 4, 5})));
    }

    //https://www.programiz.com/online-compiler/9jCK0KL06vIAp
}