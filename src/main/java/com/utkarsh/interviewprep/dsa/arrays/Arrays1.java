package com.utkarsh.interviewprep.dsa.arrays;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Arrays1 {

    //Q1.Find the average of array elements
    double question1(int[] arr) {
        //Streams approach
        Arrays.stream(arr)
                .average().orElse(0);

        //Traditional approach
        double sum = 0;// Use double for sum to avoid integer division issues
        for (int i : arr) {
            sum += i;
        }
        return sum / arr.length;//O(n)time O(1)space
    }

    //Q2.Multiply each element of array by 10
    int[] question2(int[] arr) {
        //Streams approach
        Arrays.stream(arr)
                .map(e -> e * 10)
                .toArray();

        //Traditional approach
        int[] result = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i] * 10;
        }
        return result;//O(n)time O(n)space
    }

    //Q3.Search for an element in an Array {Linear Search}
    Boolean question3(int[] arr, int target) {
        //Streams approach
        Arrays.stream(arr)
                .anyMatch(e -> e == target);

        //Traditional approach
        for (int i : arr) {
            if (i == target) {
                return true;
            }
        }
        return false;//O(n)time O(1)space
    }

    //Q4.Find the maximum element in an array
    int question4(int[] arr) {
        //Streams approach
        Arrays.stream(arr)
                .max().orElse(0);

        //Traditional approach
        int max = Integer.MIN_VALUE;
        for (int i : arr) {
            if (i > max) {
                max = i;
            }
        }
        for (int i : arr) {
            max = Math.max(i, max);
        }
        return max;//O(n)time O(1)space
    }

    //Q5.Return Sum of +ve and -ve numbers
    Map<String, Integer> question5(int[] arr) {
        //Streams approach
        Arrays.stream(arr)
                .boxed()
                .collect(Collectors.groupingBy(
                        e -> e > 0 ? "+ve" : "-ve",
                        Collectors.summingInt(e -> e)
                ));

        //Traditional approach
        int positive = 0, negative = 0;
        for (int i : arr) {
            if (i > 0) {
                positive += i;
            } else {
                negative += i;
            }
        }
        Map<String, Integer> result = new HashMap<>();
        result.put("+ve", positive);
        result.put("-ve", negative);
        return result;//O(n)time O(1)space
    }

    //Q6.Count the number of Zeroes and Ones
    Map<Integer, Long> question6(int[] arr) {
        //Streams approach
        Arrays.stream(arr)
                .boxed()
                .collect(Collectors.groupingBy(
                        e -> e == 0 ? 0 : 1,
                        Collectors.counting()
                ));

        //Traditional approach
        Long zeros = 0L, ones = 0L;
        for (int i : arr) {
            if (i == 0) {
                zeros++;
            } else {
                ones++;
            }
        }
        Map<Integer, Long> result = new HashMap<>();
        result.put(0, zeros);
        result.put(1, ones);
        return result;//O(n)time O(1)space
    }

    //Q7.Find first Unsorted Element in Array
    int question7(int[] arr) {
        //Streams approach
        IntStream.range(0, arr.length - 1)
                .filter(e -> arr[e] > arr[e + 1])
                .map(e -> arr[e + 1])
                .findFirst()
                .orElse(-1);

        //Traditional approach
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return arr[i + 1];
            }
        }
        return -1;//O(n)time O(1)space
    }

    //HW
    //Q8.Swap Alternate Elements in an Array
    int[] question8(int[] arr) {
        //Streams approach
        //loops better here

        //Traditional approach
        for (int i = 0; i < arr.length - 1; i += 2) {
            int temp = arr[i];
            arr[i] = arr[i + 1];
            arr[i + 1] = temp;
        }
        return arr;//O(n)time O(1)space
    }

    //Q9.Print Array Intersection element
    void question9(int[] arr1, int[] arr2) {
        //Traditional approach
        for (int i : arr1) {
            for (int j : arr2) {
                if (i == j) {
//                    System.out.println(i);//O(nm)time O(1)space
                }
            }
        }

        //Streams approach
        Set<Integer> set = Arrays.stream(arr1)
                .boxed()
                .collect(Collectors.toSet());

        Arrays.stream(arr2)
                .filter(set::contains);
//                .forEach(System.out::println);

        //Better Traditional approach
        Set<Integer> set1 = new HashSet<>();
        for (int i : arr1) {
            set1.add(i);
        }
        for (int i : arr2) {
            if (set1.contains(i)) {
                System.out.println(i);
            }
        }
    }

    //Q10.Print Alternate Extreme elements of an Array
    //1,2,3,4,5,6;//1,6,2,5,3,4
    void question10(int[] arr) {
        //Streams approach

        //Traditional approach
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            System.out.print(arr[left]);
            left++;
            System.out.print(arr[right]);
            right--;
        }
    }

    void question() {

    }
    //https://www.programiz.com/online-compiler/8RcD8a2q5CBn9
    //in function format
    //https://www.programiz.com/online-compiler/4bPB5ATYWx6hu
}
