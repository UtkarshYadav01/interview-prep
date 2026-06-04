package com.utkarsh.interview.june26;

import java.util.HashMap;
import java.util.Map;

public class Deloitte04June2024 {

    //Deloitte round 2
    public static void main(String[] args) {

        Map<Student, Student> map = new HashMap<>();
        Map<Student, Student> map2 = new HashMap<>();

        Student s1 = new Student(1, "Alice", "Alice@email.com");
        Student s2 = new Student(2, "Bob", "Alice@email.com");
        Student s3 = new Student(3, "Charle", "Alice@email.com");

        map.put(s1, s1);
        map.put(s2, s2);//1
        map.put(s3, s3);

        System.out.println(map.get(s2));
        s2.setId(4);
        System.out.println(map.get(s2));

    }
}

class Student {
    private Integer id;
    private String name;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Student(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }



    // constructors, getters, setters
}




 /*

        explain project
        spring security
        how to aruhorization
        if I change user type in payload will i authorized
        how to implement rediash caching in spring app
        cache evictino policy
        what is a pod
        how to configure postgres db in spring app
        completanlefuture
        how to increse kafka throughput
        can multiple consumers read from same partition
        what is the diffrence between a step and a job in spring batch


        Write an SQL query to find the average salary for each department.

        EmployeeIDﾠNameﾠDepartmentﾠEmployeeTypeﾠEmailﾠTitle
        1ﾠJohn DoeﾠITﾠAssociateﾠjohndoe@company.comﾠSoftware Engineer
        2ﾠJane SmithﾠHRﾠAssociateﾠjanesmith@company.comﾠHR Specialist
        3ﾠSam LeeﾠFinanceﾠAssociateﾠsamlee@company.comﾠAccountant

        PayrollIDﾠEmployeeIDﾠPayDateﾠAmount
        101ﾠ1ﾠ2026-05-31ﾠ4000.00
        102ﾠ2ﾠ2026-05-31ﾠ3800.00
        103ﾠ1ﾠ2026-04-30ﾠ4000.00
        104ﾠ3ﾠ2026-05-31ﾠ4200.00
        Notes:
        EmployeeID is the primary key in the Employee table and a foreign key in the Payroll table.
                PayrollID is the primary key in the Payroll table.
        PayDate stores the date of payment.
        Amount represents the salary paid for that period.

        select e.department, average(p.Amount) as salaries
        from employee e
        join payroll p
        on e.EmployeeID=p.EmployeeID
        group by e.department

        department | salaries
        HR  |  2000
        IT  |  1500

        next question is based on the student class

        System.out.println(map.get(s2));
        s2.setId(4);
        System.out.println(map.get(s2));
        Q1. what will be the output of these lines

        Q2.
        add hashcode and equals
        now again what will happen at s2.setId(4); and what will be the outputs of these lines
        System.out.println(map.get(s2));
        s2.setId(4);
        System.out.println(map.get(s2));


        */