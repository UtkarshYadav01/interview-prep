package com.utkarsh.interviewprep.dsa.strings;

import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StringBasicsTest {

    StringBasics test = new StringBasics();

    @Test
    @Order(1)
    @DisplayName("Q1. Print each character of the String")
    void question1() {
        test.question1("Babbar");
    }

    @Test
    @Order(2)
    @DisplayName("Q2. Count length without using length()")
    void question2() {
        System.out.println(test.question2("hello"));
        System.out.println(test.question2("java"));
    }

    @Test
    @Order(3)
    @DisplayName("Q3. Count vowels in a String")
    void question3() {
        System.out.println(test.question3("hello"));
        System.out.println(test.question3("interview"));
    }

    @Test
    @Order(4)
    @DisplayName("Q4. Reverse a String")
    void question4() {
        System.out.println(test.question4("hello"));
        System.out.println(test.question4("world"));
    }

    @Test
    @Order(5)
    @DisplayName("Q5. Check palindrome")
    void question5() {
        System.out.println(test.question5("madam"));
        System.out.println(test.question5("hello"));
    }

    @Test
    @Order(6)
    @DisplayName("Q6. Count consonants in a string")
    void question6() {
        test.question6("hello");
        test.question6("interview");
    }

    @Test
    @Order(7)
    @DisplayName("Q7. Convert string to uppercase manually")
    void question7() {
        test.question7("hello");
        test.question7("java");
    }

    @Test
    @Order(8)
    @DisplayName("Q8. Find frequency of a character")
    void question8() {
        test.question8("hello", 'l');
        test.question8("interview", 'e');
    }

    @Test
    @Order(9)
    @DisplayName("Q9. Remove all spaces from string")
    void question9() {
        test.question9("hello world");
        test.question9("java is fun");
    }

    @Test
    @Order(10)
    @DisplayName("Q10. Check if string contains only digits")
    void question10() {
        test.question10("12345");
        test.question10("123a5");
    }

    @Test
    @Order(11)
    @DisplayName("Q11. Count words in a sentence")
    void question11() {
        test.question11("Hello world");
        test.question11("Java is very easy");
    }

    //https://www.programiz.com/online-compiler/55rsh6lBqVqGR
}