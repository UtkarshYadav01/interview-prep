package com.utkarsh.interviewprep.dsa.strings;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StringProblemsTest {

    StringProblems test = new StringProblems();

    @Test
    @Order(1)
    @DisplayName("1. Sum of all substrings of a number")
    void sumOfSubstrings() {
        assertEquals(8421, test.sumOfSubstrings("6759"));
    }

    @Test
    @Order(2)
    @Disabled
    @DisplayName("2. Beauty sum of all substrings")
    void beautySum() {
        assertEquals(5, test.beautySum("aabcb"));
    }
}