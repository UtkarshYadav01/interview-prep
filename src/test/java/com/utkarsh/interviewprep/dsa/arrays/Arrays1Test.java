package com.utkarsh.interviewprep.dsa.arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class Arrays1Test {

    Arrays1 test = new Arrays1();

    @Test
    @DisplayName("Q1.Find the average of array elements")
    void question1() {
        System.out.println(test.question1(new int[]{2, 4, 3, 3}));
        System.out.println(test.question1(new int[]{2, 4, 1, 3}));
    }

    @Test
    @DisplayName("Q2.Multiply each element of array by 10")
    void question2() {
        System.out.println(Arrays.toString(test.question2(new int[]{2, 4, 3, 3})));
    }

    @Test
    @DisplayName("Q3.Search for an element in an Array {Linear Search}")
    void question3() {
        System.out.println(test.question3(new int[]{2, 5, 3, 1, 7}, 7));
        System.out.println(test.question3(new int[]{2, 5, 3, 1, 7}, 65));
    }

    @Test
    @DisplayName("Q4.Find the maximum element in an array")
    void question4() {
        System.out.println(test.question4(new int[]{9, 2, 5, 7, 12}));
    }

    @Test
    @DisplayName("Q5.Return Sum of +ve and -ve numbers")
    void question5() {
        System.out.println(test.question5(new int[]{2, -3, -1, 4, 6, -9}));
    }

    @Test
    @DisplayName("Q6.Count the number of Zeroes and Ones")
    void question6() {
        System.out.println(test.question6(new int[]{0, 1, 1, 0, 1, 1, 1}));
    }

    @Test
    @DisplayName("Q7.Find first Unsorted Element in Array")
    void question7() {
        System.out.println(test.question7(new int[]{2, 3, 9, 5, 13}));
        System.out.println(test.question7(new int[]{1, 2, 5, 4, 9}));
        System.out.println(test.question7(new int[]{1, 2, 3, 4, 9}));
    }

    @Test
    @DisplayName("Q8.Swap Alternate Elements in an Array")
    void question8() {
        System.out.println(Arrays.toString(test.question8(new int[]{1, 2, 3, 4, 5, 6})));
    }

    @Test
    @DisplayName("Q9.Print Array Intersection element")
    void question9() {
        /*System.out.println(
                Arrays.toString(*/
                        test.question9(
                                new int[]{1, 2, 3, 4, 5},
                                new int[]{2, 5, 6, 7, 8}
                        );//));
    }

    @Test
    @DisplayName("Q10.Print Alternate Extreme elements of an Array")
    void question10() {
        test.question10(new int[]{1, 2, 3, 4, 5, 6});
    }

    @Test
    @DisplayName("Future Questions")
    void question() {
        test.question();
        System.out.println("https://www.programiz.com/online-compiler/8RcD8a2q5CBn9");
    }
}