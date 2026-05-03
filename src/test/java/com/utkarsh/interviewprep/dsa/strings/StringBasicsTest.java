package com.utkarsh.interviewprep.dsa.strings;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StringBasicsTest {

    StringBasics test = new StringBasics();

    @Test
    @DisplayName("Q1. Print each character of the String")
    void question1() {
        test.question1("Babbar");
    }

    @Test
    @DisplayName("Q2. Count length of a String without using length()")
    void question2() {
        System.out.println(test.question2("hello"));
        System.out.println(test.question2("java"));
    }

    @Test
    @DisplayName("Q3. Count vowels in a String")
    void question3() {
        System.out.println(test.question3("hello"));
        System.out.println(test.question3("interview"));
    }

    @Test
    @DisplayName("Q4. Reverse a String")
    void question4() {
        System.out.println(test.question4("hello"));
        System.out.println(test.question4("world"));
    }

    @Test
    @DisplayName("Q5. Check if a String is a palindrome or not")
    void question5() {
        System.out.println(test.question5("madam"));
        System.out.println(test.question5("hello"));
    }

    //https://www.programiz.com/online-compiler/3ENquaKcIkbZ1
}