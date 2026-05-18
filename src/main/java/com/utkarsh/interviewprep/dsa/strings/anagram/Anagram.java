package com.utkarsh.interviewprep.dsa.strings.anagram;

import java.util.Arrays;

public class Anagram {
    //https://www.geeksforgeeks.org/problems/anagram-1587115620/1
    //https://leetcode.com/problems/valid-anagram/description/
    //https://www.hackerrank.com/challenges/anagram/problem

    public static boolean isAnagram(String s1, String s2) {
        // If lengths differ, cannot be anagrams
        if (s1.length() != s2.length()) return false;

        char[] arr1 = s1.toCharArray();
        char[] arr2 = s2.toCharArray();

        Arrays.sort(arr1);
        Arrays.sort(arr2);

        return Arrays.equals(arr1, arr2);
    }
    //Most Frequent Character
    //https://www.geeksforgeeks.org/problems/maximum-occuring-character-1587115620/1
}
