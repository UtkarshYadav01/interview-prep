package com.utkarsh.interview.may26;

public class VLink20thMay2026 {

    public static void main(String[] args) {


        //Q1.output
        /*a=10
        b=a++;
        sout(a and b)
        a=10
        b=++a;
        sout(a and b)

        String s="abc";
        s.concat("def")
        sout(s);*/

        // Q2.print
        String s1 = """
                * * * * *
                 * * * *
                  * * *
                   * *
                    *
                """;


        // ─────────────────────────────────────────────
        // Q1a. Post-increment: b = a++
        // ─────────────────────────────────────────────
        int a = 10;
        int b = a++;        // b gets 10, then a becomes 11
        System.out.println("Q1a (b = a++):");
        System.out.println("a = " + a); // 11
        System.out.println("b = " + b); // 10

        System.out.println();

        // ─────────────────────────────────────────────
        // Q1b. Pre-increment: b = ++a
        // ─────────────────────────────────────────────
        a = 10;
        b = ++a;            // a becomes 11, then b gets 11
        System.out.println("Q1b (b = ++a):");
        System.out.println("a = " + a); // 11
        System.out.println("b = " + b); // 11

        System.out.println();

        // ─────────────────────────────────────────────
        // Q1c. String immutability
        // ─────────────────────────────────────────────
        String s = "abc";
        s.concat("def");    // return value discarded — s is unchanged
        System.out.println("Q1c (s.concat without reassignment):");
        System.out.println("s = " + s); // abc

        System.out.println();

        // ─────────────────────────────────────────────
        // Q2. Inverted star pattern
        //* * * * *
        // * * * *
        //  * * *
        //   * *
        //    *
        // ─────────────────────────────────────────────
        // Q2. Inverted star pattern with leading spaces
        System.out.println("Q2 (Inverted star pattern):");
        int rows = 5;
        for (int i = rows; i >= 1; i--) {
            // Print leading spaces: increases each row
            for (int space = 0; space < rows - i; space++) {
                System.out.print(" ");
            }
            // Print stars
            for (int j = 1; j <= i; j++) {
                System.out.print("* ");
            }
            System.out.println();
        }
    }
}

