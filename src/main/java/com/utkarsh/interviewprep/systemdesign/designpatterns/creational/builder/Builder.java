package com.utkarsh.interviewprep.systemdesign.designpatterns.creational.builder;

public class Builder {
    public static void main(String[] args) {

        // Creating object step-by-step using Builder
        Student student = new Student.Builder()
                .Age(23)
                .Name("Alex")
                .build();
        System.out.println(student);
    }
}

class Student {
    // final → object is immutable after creation
    private final String name;
    private final int age;

    // Private constructor → prevents direct object creation
    private Student(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
    }

    // Static inner Builder class
    public static class Builder {

        private String name;
        private int age;

        // method chaining → returns same Builder object
        public Builder Name(String name) {
            this.name = name;
            return this;
        }

        public Builder Age(int age) {
            this.age = age;
            return this;
        }

        // final step → builds the actual object
        public Student build() {
            return new Student(this);
        }
    }

    @Override
    public String toString() {
        return "Student [name=" + name + ", age=" + age + "]";
    }
}