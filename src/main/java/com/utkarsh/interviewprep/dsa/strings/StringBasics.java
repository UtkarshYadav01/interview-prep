package com.utkarsh.interviewprep.dsa.strings;

import java.util.Arrays;
import java.util.stream.Stream;

public class StringBasics {

    // Q1. Print each character of the String
    void question1(String str) {
        //Streams approach
        Arrays.stream(str.split(""))
                .forEach(System.out::print);

        //Traditional approach
        for (char c : str.toCharArray()) {
            System.out.print(c);
        }
        System.out.println();
        for (int i = 0; i < str.length(); i++) {
            System.out.print(str.charAt(i));
        }
    }

    // Q2. Count length of a String without using length()
    int question2(String str) {
        //Streams approach
        return (int) Stream.of(str.split(""))
                .count();
        //Traditional approach

    }

    // Q3. Count vowels in a String
    String question3(String str) {
        //Streams approach
        //Traditional approach
        return str;
    }

    // Q4. Reverse a String
    String question4(String str) {
        //Streams approach
        //Traditional approach
        return str;
    }

    // Q5. Check if a String is a palindrome or not
    String question5(String str) {
        //Streams approach
        //Traditional approach
        return str;
    }

    //Homework
    // Q6. Count consonants in a string
    void question6(String str) {
        //Streams approach
        //Traditional approach
    }

    // Q7. Convert string to uppercase without using method
    void question7(String str) {
        //Streams approach
        //Traditional approach
    }

    // Q8. Find frequency of a character
    void question8(String str, char ch) {
        //Streams approach
        //Traditional approach
    }

    // Q9. Remove all spaces from string
    void question9(String str) {
        //Streams approach
        //Traditional approach
    }

    // Q10. Check if string contains only digits
    void question10(String str) {
        //Streams approach
        //Traditional approach
    }

    // Q11. Count words in a sentence
    void question11(String str) {
        //Streams approach
        //Traditional approach
    }

    //https://www.programiz.com/online-compiler/55rsh6lBqVqGR
}