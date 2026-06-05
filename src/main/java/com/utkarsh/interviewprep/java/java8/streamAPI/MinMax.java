package com.utkarsh.interviewprep.java.java8.streamAPI;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;

public class MinMax {

    List<Integer> numbers = Arrays.asList(10, 5, 20, 8, 15);


    //1. Using Primitive Streams (Recommended for Numbers)
    int max = numbers.stream()
            .mapToInt(Integer::intValue)
            .max()
            .orElseThrow();

    int min = numbers.stream()
            .mapToInt(Integer::intValue)
            .min()
            .orElseThrow();

    //2. Finding Max/Min Object by a Field
    record Employee(String name, int salary) {
    }

    List<Employee> employees = List.of(
            new Employee("Alice", 50000),
            new Employee("Bob", 70000),
            new Employee("Charlie", 60000)
    );

    Employee highestPaid = employees.stream()
            .max(java.util.Comparator.comparing(Employee::salary))
            .orElse(null);

    Employee lowestPaid = employees.stream()
            .min(java.util.Comparator.comparing(Employee::salary))
            .orElse(null);

    //3. Java 8+ Summary Statistics (Get Both in One Pass)
    IntSummaryStatistics stats = numbers.stream()
            .mapToInt(Integer::intValue)
            .summaryStatistics();

    //System.out.println("Max: "+stats.getMax());
    //System.out.println("Min: "+stats.getMin());

    //If you don't want to use Integer::compareTo, you can provide your own comparator.
    //4. Using a Lambda
    Integer max4 = numbers.stream()
            .max((a, b) -> a - b)
            .orElse(null);

    Integer min4 = numbers.stream()
            .min((a, b) -> a - b)
            .orElse(null);

    //5. However, a - b can overflow for very large integers, so a safer version is:
    Integer max5 = numbers.stream()
            .max((a, b) -> a > b ? 1 : (a < b ? -1 : 0))
            .orElse(null);

    Integer min5 = numbers.stream()
            .min((a, b) -> a > b ? 1 : (a < b ? -1 : 0))
            .orElse(null);

    //6. Using Comparator.comparingInt
    Integer max6 = numbers.stream()
            .max(java.util.Comparator.comparingInt(x -> x))
            .orElse(null);

    Integer min6 = numbers.stream()
            .min(java.util.Comparator.comparingInt(x -> x))
            .orElse(null);

    //Best Option for Numbers
    //7. For numeric streams, avoid comparators entirely:
    int max7 = numbers.stream()
            .mapToInt(i -> i)
            .max()
            .orElseThrow();

    int min7 = numbers.stream()
            .mapToInt(i -> i)
            .min()
            .orElseThrow();
}
