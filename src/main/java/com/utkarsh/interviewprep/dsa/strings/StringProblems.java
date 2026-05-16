package com.utkarsh.interviewprep.dsa.strings;

public class StringProblems {

    // https://www.geeksforgeeks.org/problems/sum-of-all-substrings-of-a-number-1587115621/1
    // Date: 16-05-2026
    public int sumOfSubstrings(String s) {
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j <= s.length(); j++) {
                int n = Integer.parseInt(s.substring(i, j));
                sum += n;
            }
        }
        //https://www.programiz.com/online-compiler/17DeqntYcOcNA
        return sum;
    }

    // https://leetcode.com/problems/sum-of-beauty-of-all-substrings/
    // Difficulty: Medium | Date: 16-05-2026
    public int beautySum(String s) {

        return 0;
    }

}