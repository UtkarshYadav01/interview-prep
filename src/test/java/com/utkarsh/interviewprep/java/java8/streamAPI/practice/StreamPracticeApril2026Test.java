package com.utkarsh.interviewprep.java.java8.streamAPI.practice;

import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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

    @Test @Order(1)
    @DisplayName("Q1. Sort List of Employees By Salary")
    void question1() {
        System.out.println(test.question1(employees));
    }

    @Test @Order(2)
    @DisplayName("Q2. Calculate average age of Person objects using Streams")
    void question2() {
        System.out.println(test.question2(persons));
    }

    @Test @Order(3)
    @DisplayName("Q3. Partition numbers into Even and Odd lists")
    void question3() {
        System.out.println(test.question3(Arrays.asList(1,2,3,4,5,6,7,8,9,10)));
    }

    @Test @Order(4)
    @DisplayName("Q4. Group words by their length")
    void question4() {
        System.out.println(test.question4(Arrays.asList("Apple","Banana","Orange","Pineapple","Watermelon")));
    }

    @Test @Order(5)
    @DisplayName("Q5. Count occurrence of each element in a list")
    void question5() {
        System.out.println(test.question5(Arrays.asList("Apple","Banana","Orange","Orange","Orange","Orange","Pineapple","Watermelon","Apple","Banana")));
    }

    @Test @Order(6)
    @DisplayName("Q6. Group employees by department and average salary")
    void question6() {
        System.out.println(test.question6(employees));
    }

    @Test @Order(7)
    @DisplayName("Q7. Highest paid employee in each department")
    void question7() {
        System.out.println(test.question7(employees));
    }

    @Test @Order(8)
    @DisplayName("Q8. Departments with more than 2 employees")
    void question8() {
        System.out.println(test.question8(employees));
    }

    @Test @Order(9)
    @DisplayName("Q9. Department with highest average salary")
    void question9() {
        System.out.println(test.question9(employees));
    }

    @Test @Order(10)
    @DisplayName("Q10. Most frequent character in a string")
    void question10() {
        System.out.println(test.question10("employees"));
    }

    @Test @Order(11)
    @DisplayName("Q11. First non-repeating character")
    void question11() {
        System.out.println(test.question11("employees"));
    }

    @Test @Order(12)
    @DisplayName("Q12. Most common first letter among employee names")
    void question12() {
        System.out.println(test.question12(employees));
    }

    @Test @Order(13)
    @DisplayName("Q13. Sliding window average (size 3)")
    void question13() {
        System.out.println(test.question13(Arrays.asList(4,8,15,16,23,42), 3));
    }

    @Test @Order(14)
    @DisplayName("Q14. Longest word ignoring punctuation")
    void question14() {
        System.out.println(test.question14("The quick, brown fox jumped over the lazy dog!"));
    }

    @Test @Order(15)
    @DisplayName("Q15. Top 3 most frequent words")
    void question15() {
        System.out.println(test.question15("Java is great great. Java is object oriented. Java streams are powerful, and Java is fun!"));
    }

    @Test @Order(16)
    @DisplayName("Q16. Reverse each word using Streams")
    void question16() {
        System.out.println(test.question16("The quick, brown fox jumped over the lazy dog!"));
    }

    @Test @Order(17)
    @DisplayName("Q17. Day with highest transaction sum")
    void question17() {
        System.out.println(test.question17(transactions));
    }

    @Test @Order(18)
    @DisplayName("Q18. Categorize employees by salary")
    void question18() {
        System.out.println(test.question18(employees));
    }

    @Test @Order(19)
    @DisplayName("Q19. Group characters (uppercase/lowercase/digit/other)")
    void question19() {
        System.out.println(test.question19(Arrays.asList('A','b','3','Z','x','#','7','m','@')));
    }

    @Test @Order(20)
    @DisplayName("Q20. Employees working in 3+ departments")
    void question20() {
        System.out.println(test.question20(employees));
    }

    @Test @Order(21)
    @DisplayName("Q21. Bigram frequency in paragraph")
    void question21() {
        System.out.println(test.question21("Java is great and Java is fun. Java is powerful!"));
    }

    @Test @Order(22)
    @DisplayName("Future Questions")
    void futureQuestions() {
        test.question();
        System.out.println("https://www.programiz.com/online-compiler/8mouLR64TzOu2");
    }
}