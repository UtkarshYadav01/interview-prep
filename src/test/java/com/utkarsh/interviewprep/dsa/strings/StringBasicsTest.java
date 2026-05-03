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

    @Test
    @DisplayName("Q6. Count consonants in a string")
    void question6() {
        test.question6("hello");
        test.question6("interview");
    }

    @Test
    @DisplayName("Q7. Convert string to uppercase without using method")
    void question7() {
        test.question7("hello");
        test.question7("java");
    }

    @Test
    @DisplayName("Q8. Find frequency of a character")
    void question8() {
        test.question8("hello", 'l');
        test.question8("interview", 'e');
    }

    @Test
    @DisplayName("Q9. Remove all spaces from string")
    void question9() {
        test.question9("hello world");
        test.question9("java is fun");
    }

    @Test
    @DisplayName("Q10. Check if string contains only digits")
    void question10() {
        test.question10("12345");
        test.question10("123a5");
    }

    @Test
    @DisplayName("Q11. Count words in a sentence")
    void question11() {
        test.question11("Hello world");
        test.question11("Java is very easy");
    }

    //https://www.programiz.com/online-compiler/55rsh6lBqVqGR
}