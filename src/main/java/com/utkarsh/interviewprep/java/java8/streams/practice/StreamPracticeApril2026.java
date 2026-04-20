package com.utkarsh.interviewprep.java.java8.streams.practice;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamPracticeApril2026 {

    record Employee(String name, String department, double salary) {
    }

    record Person(String name, int age) {
    }

    record Transaction(String id, LocalDate date, double amount) {
    }

    //Q1.Sort List of Employees By Salary
    List<Employee> question1(List<Employee> list) {

        //in reverse
        list.stream()
                .sorted(Comparator.comparing(Employee::salary).reversed())
                .toList();

        return list.stream()
                .sorted(Comparator.comparing(Employee::salary))
                .toList();

    }

    //Q2.Calculate the avg age of a list of person Objects Using java Streams
    double question2(List<Person> list) {
        return list.stream()
                .mapToInt(Person::age)
                .average()
                .orElse(0.0);
    }

    //Q3.Partition numbers in Even and Odd List
    Map<Boolean, List<Integer>> question3(List<Integer> list) {
        list.stream()
                .collect(Collectors.groupingBy(n -> n % 2 == 0 ? "Even" : "Odd"));
        return list.stream()
                .collect(Collectors.partitioningBy(n -> n % 2 == 0));

    }

    //Q4.Group a list of words by their length using streams
    Map<Integer, List<String>> question4(List<String> list) {
        return list.stream()
                .collect(Collectors.groupingBy(String::length));
    }

    //Q5.Count the occurrence of each element in a list
    Map<String, Long> question5(List<String> list) {
        return list.stream()
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
    }

    //Q6.Group employees by department and calculate average salary
    Map<String, Double> question6(List<Employee> list) {

        return list.stream()
                .collect(Collectors.groupingBy(Employee::department,
                        Collectors.averagingDouble(Employee::salary)));
    }

    //Q7.Find the highest-paid employee in each department
    Map<String, Optional<Employee>> question7(List<Employee> list) {
        return list.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.maxBy(Comparator.comparing(Employee::salary))
                ));
    }

    //Q8.Find All Departments with More than 2 Employees
    String question8(List<Employee> list) {

        list.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.counting()))
                .entrySet()
                .stream()
                .filter(e -> e.getValue() > 2)
                .map(e -> e.getKey())
                .findFirst().get();

        list.stream()
                .collect(Collectors.groupingBy(Employee::department
                        , Collectors.counting()
                ))
                .entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey)
                .toList();

        return list.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.counting()
                ))
                .entrySet().stream()
                .filter(e -> e.getValue() > 2)
                .findFirst()
                .get()
                .getKey();
    }

    //Q9.Find department with the highest average salary
    String question9(List<Employee> list) {

        list.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.averagingDouble(Employee::salary)))
                .entrySet()
                .stream()
                .max(Comparator.comparing(e -> e.getValue()))
                .orElseThrow();

        Map.Entry<String, Double> stringDoubleEntry = list.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.averagingDouble(Employee::salary)
                )).entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow();

        return list.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.averagingDouble(Employee::salary)
                )).entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).get();
    }

    //Q10.Find most frequent character in string
    Character question10(String str) {

        str.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(
                        c -> c, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow();

        Arrays.stream(str.split(""))
                .collect(Collectors.groupingBy(
                        c -> c, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue());

        String input = "banana";

        //  "banana" → ['b', 'a', 'n', 'a', 'n', 'a']

        Map.Entry<Character, Long> characterLongEntry = input.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                )).entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow();
        System.out.println(characterLongEntry);

        return str.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow().getKey();
    }

    //Q11.Find First non-repeating character in string
    Character question11(String str) {

        str.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(
                        c -> c,
                        LinkedHashMap::new,
                        Collectors.counting()))
                .entrySet()
                .stream()
                .filter(e -> e.getValue() == 1)
                .findFirst();

        String string = "swiss";

        Optional<Map.Entry<Character, Long>> first = str.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        LinkedHashMap::new
                        , Collectors.counting()
                )).entrySet()
                .stream()
                .filter(entry -> entry.getValue() == 1)
                .findFirst();

        return str.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(
                        c -> c,
                        LinkedHashMap::new,
                        Collectors.counting()
                )).entrySet().stream()
                .filter(e -> (e.getValue() == 1))
                .findFirst().orElseThrow().getKey();
    }
    //hard questions

    //Q12.Find the most common first letter among all employee names
    Optional<Map.Entry<Character, Long>> question12(List<Employee> list) {
        return list.stream()
                .map(Employee::name)
                .collect(Collectors.groupingBy(
                        e -> e.charAt(0),
                        Collectors.counting()
                )).entrySet().stream()
                .max(Map.Entry.comparingByValue());
    }

    //Q13.Given a list of integers compute the average of every 3 element sliding window
    List<Double> question13(List<Integer> list, int window) {

        IntStream.range(0, list.size() - 2)
                .mapToObj(i -> (list.get(i) + list.get(i + 1) + list.get(i + 2)) / 3.0)
                .toList();

        return IntStream.range(0, list.size() - (window - 1))
                .mapToObj(i -> list.subList(i, i + window))
                .map(e -> e.stream().mapToInt(i -> i).average().orElse(0))
                .toList();
    }

    //Q14.Find the longest word in a sentence ignoring case and punctuation
    String question14(String sentence) {
        return Arrays.stream(sentence.toLowerCase().replaceAll("[^a-z\\s]", "").split(" "))
                .max(Comparator.comparing(String::length)).orElse("");
    }

    //Q15.Find top 3 most frequent words in a paragraph
    List<Map.Entry<String, Long>> question15(String paragraph) {
        return Arrays.stream(paragraph.toLowerCase().replaceAll("[^a-z\\s]", "").split(" "))
                .collect(Collectors.groupingBy(
                        e -> e,
                        Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed()).limit(3).toList();
    }

    //Q16.Reverse each word in a sentence using streams
    String question16(String sentence) {

        //basic with StringBuilder
        Arrays.stream(sentence.split(" "))
                .map(e -> new StringBuilder(e).reverse().toString())
                .collect(Collectors.joining(" "));

        //Advanced char by char
        return Arrays.stream(sentence.split(" "))
                .map(e -> Arrays.stream(e.split(""))
                        .reduce("", (a, b) -> b + a))
                .collect(Collectors.joining(" "));
    }

    //Q17.From a list of transactions, find the day with the highest total spend
    Map.Entry<LocalDate, Double> question17(List<Transaction> list) {
        return list.stream()
                .collect(Collectors.groupingBy(
                        Transaction::date,
                        Collectors.summingDouble(Transaction::amount)
                )).entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow();
    }

    //Q18.Categorize employees based on their salary (Low/Medium/High) Using streams.
    Map<String, List<Employee>> question18(List<Employee> list) {
        return list.stream()
                .collect(Collectors.groupingBy(
                        e -> {
                            if (e.salary() < 30000) return "Low";
                            if (e.salary() < 50000) return "Medium";
                            else return "High";
                        }
                ));
    }

    //Q19.Group characters by uppercase vs lowercase vs digit vs other
    Map<String, List<Character>> question19(List<Character> list) {
        return list.stream()
                .collect(Collectors.groupingBy(
                        e -> {
                            if (Character.isUpperCase(e)) return "uppercase";
                            if (Character.isLowerCase(e)) return "lowercase";
                            if (Character.isDigit(e)) return "digit";
                            else return "other";
                        }
                ));
    }

    //Q20.Find all employees who worked in 3+ Departments
    List<Map.Entry<String, Long>> question20(List<Employee> list) {
        return list.stream()
                .collect(Collectors.groupingBy(
                        Employee::name,
                        Collectors.counting()
                )).entrySet().stream()
                .filter(e -> e.getValue() > 2)
                .toList();
    }

    //Q21.Find bigram frequency(Pairs of consecutive words) in Paragraphs
    Map<String, Long> question21(String paragraphs) {
        String[] words = paragraphs.toLowerCase().replaceAll("[^a-z\\s]", "").split(" ");

        return IntStream.range(0, words.length - 1)
                .mapToObj(i -> words[i] + " " + words[i + 1])
                .collect(Collectors.groupingBy(
                        e -> e,
                        Collectors.counting()
                ));
    }

    void question() {

    }
//    https://www.programiz.com/online-compiler/8mouLR64TzOu2
}


