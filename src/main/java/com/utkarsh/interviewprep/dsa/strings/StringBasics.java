package com.utkarsh.interviewprep.dsa.strings;

import java.util.Arrays;

public class StringBasics {

    // Q1. Print each character of the String
    void question1(String str) {
        //Streams approach
        Arrays.stream(str.split(""))
                .forEach(System.out::print);

        //Traditional approach
        for(char c : str.toCharArray()){
            System.out.print(c);
        }
        System.out.println();
        for(int i=0;i<str.length();i++){
            System.out.print(str.charAt(i));
        }
    }

    // Q2. Count length of a String without using length()
    String question2(String str) {
        return str;
    }

    // Q3. Count vowels in a String
    String question3(String str) {
        return str;
    }

    // Q4. Reverse a String
    String question4(String str) {
        return str;
    }

    // Q5. Check if a String is a palindrome or not
    String question5(String str) {
        return str;
    }

    //https://www.programiz.com/online-compiler/3ENquaKcIkbZ1
}