package com.utkarsh.interviewprep.java.java8.streamAPI.practice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

class StreamPracticeApril2026Test {

    StreamPracticeApril2026 test = new StreamPracticeApril2026();

    List<StreamPracticeApril2026.Employee> employees = List.of(
            new StreamPracticeApril2026.Employee("Alice", "HR", 6000),
            new StreamPracticeApril2026.Employee("Alice", "Sales", 6000),
            new StreamPracticeApril2026.Employee("Alice", "Marketing", 6000),
            new StreamPracticeApril2026.Employee("Bob", "IT", 21400),
            new StreamPracticeApril2026.Employee("Charlie", "IT", 25000),
            new StreamPracticeApril2026.Employee("Dave", "IT", 33000),
            new StreamPracticeApril2026.Employee("Eric", "Salse", 90000),
            new StreamPracticeApril2026.Employee("Eric", "IT", 90000),
            new StreamPracticeApril2026.Employee("Eric", "Marketing", 90000),
            new StreamPracticeApril2026.Employee("Eric", "HR", 90000),
            new StreamPracticeApril2026.Employee("Adam", "Salse", 70000));

    List<StreamPracticeApril2026.Person> persons = List.of(
            new StreamPracticeApril2026.Person("Alice", 25),
            new StreamPracticeApril2026.Person("Bob", 30),
            new StreamPracticeApril2026.Person("Charlie", 28),
            new StreamPracticeApril2026.Person("David", 35));

    List<StreamPracticeApril2026.Transaction> transactions = Arrays.asList(
            new StreamPracticeApril2026.Transaction("T1", LocalDate.of(2024, 1, 1), 300),
            new StreamPracticeApril2026.Transaction("T2", LocalDate.of(2024, 1, 1), 200),
            new StreamPracticeApril2026.Transaction("T3", LocalDate.of(2024, 1, 2), 500),
            new StreamPracticeApril2026.Transaction("T4", LocalDate.of(2024, 1, 2), 300),
            new StreamPracticeApril2026.Transaction("T5", LocalDate.of(2024, 1, 3), 100)
    );

    @Test
    @DisplayName("Q1.Sort List of Employees By Salary")
    void question1() {
        System.out.println(test.question1(employees));
    }

    @Test
    @DisplayName("Q2.Calculate the avg age of a list of person Objects Using java Streams")
    void question2() {
        System.out.println(test.question2(persons));
    }

    @Test
    @DisplayName("Q3.Partition numbers in Even and Odd List")
    void question3() {
        System.out.println(test.question3(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)));
    }

    @Test
    @DisplayName("Q4.Group a list of words by their length using streams")
    void question4() {
        System.out.println(test.question4(Arrays.asList("Apple", "Banana", "Orange", "Pineapple", "Watermelon")));
    }

    @Test
    @DisplayName("Q5.Count the occurrence of each element in a list")
    void question5() {
        System.out.println(test.question5(Arrays.asList("Apple", "Banana", "Orange", "Orange", "Orange", "Orange", "Pineapple", "Watermelon", "Apple", "Banana")));
    }

    @Test
    @DisplayName("Q6.Group employees by department and calculate average salary")
    void question6() {
        System.out.println(test.question6(employees));
    }

    @Test
    @DisplayName("Q7.Find the highest-paid employee in each department")
    void question7() {
        System.out.println(test.question7(employees));
    }

    @Test
    @DisplayName("Q8.Find All Departments with More than 2 Employees")
    void question8() {
        System.out.println(test.question8(employees));
    }

    @Test
    @DisplayName("Q9.Find department with the highest average salary")
    void question9() {
        System.out.println(test.question9(employees));
    }

    @Test
    @DisplayName("Q10.Find most frequent character in string")
    void question10() {
        System.out.println(test.question10("employees"));
    }

    @Test
    @DisplayName("Q11.Find First non-repeating character in string")
    void question11() {
        System.out.println(test.question11("employees"));
    }

    //hard questions
    @Test
    @DisplayName("Q12.Find the most common first letter among all employee names")
    void question12() {
        System.out.println(test.question12(employees));
    }

    @Test
    @DisplayName("Q13.Given a list of integers compute the average of every 3 element sliding window")
    void question13() {
        System.out.println(test.question13(Arrays.asList(4, 8, 15, 16, 23, 42), 3));
    }

    @Test
    @DisplayName("Q14.Find the longest word in a sentence ignoring case and punctuation")
    void question14() {
        System.out.println(test.question14("The quick, brown fox jumped over the lazy dog!"));
    }

    @Test
    @DisplayName("Q15.Find top 3 most frequent words in a paragraph")
    void question15() {
        System.out.println(test.question15("Java is great great. Java is object oriented. Java streams are powerful, and Java is fun!"));
    }

    @Test
    @DisplayName("Q16.Reverse each word in a sentence using streams")
    void question16() {
        System.out.println(test.question16("The quick, brown fox jumped over the lazy dog!"));
    }

    @Test
    @DisplayName("Q17.From a list of transactions, find the day with the highest total spend")
    void question17() {
        System.out.println(test.question17(transactions));
    }

    @Test
    @DisplayName("Q18.Categorize employees based on their salary (Low/Medium/High) Using streams.")
    void question18() {
        System.out.println(test.question18(employees));
    }

    @Test
    @DisplayName("Q19.Group characters by uppercase vs lowercase vs digit vs other")
    void question19() {
        System.out.println(test.question19(Arrays.asList('A', 'b', '3', 'Z', 'x', '#', '7', 'm', '@')));
    }

    @Test
    @DisplayName("Q20.Find all employees who worked in 3+ Departments")
    void question20() {
        System.out.println(test.question20(employees));
    }

    @Test
    @DisplayName("Q21.Find bigram frequency(Pairs of consecutive words) in Paragraphs")
    void question21() {
        System.out.println(test.question21("Java is great and Java is fun. Java is powerful!"));
    }

    @Test
    @DisplayName("Future Questions")
    void question() {
        test.question();
        System.out.println("https://www.programiz.com/online-compiler/8mouLR64TzOu2");
    }

}