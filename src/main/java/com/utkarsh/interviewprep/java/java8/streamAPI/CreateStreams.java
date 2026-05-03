package com.utkarsh.interviewprep.java.java8.streamAPI;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CreateStreams {
    public static void main(String[] args) {

        // ── FROM COLLECTION / ARRAY ──────────────────────────────────────

        List<String> names = Arrays.asList("alice","bob");
        //1. List -> Stream
        Stream<String> stream = names.stream();

        String[] arr = {"Java", "Python", "C++"};
        //2. Array -> Stream
        Stream<String> stream1 = Arrays.stream(arr);
        //3. Stream of
        Stream<Integer> integerStream = Stream.of(1, 2, 3);
        //4. stream generate
        Stream<Double> limit = Stream.generate(Math::random).limit(5);

        // ── FROM STRING ("banana") ───────────────────────────────────────

        String word = "banana";

        // IntStream — must cast to char
        word.chars()
                .mapToObj(c -> (char) c)
                .forEach(System.out::print);

        // Stream<String> — no cast needed
        Arrays.stream(word.split(""))
                .forEach(System.out::print);

        // IntStream — Unicode-safe (use for emoji / multilingual)
        word.codePoints()
                .mapToObj(cp -> String.valueOf((char) cp))
                .forEach(System.out::print);

        // Stream<String> — 1 element (the whole string, NOT chars)
        Stream<String> wholeWord = Stream.of(word);

        // index-based — use when you need the position too
        IntStream.range(0, word.length())
                .mapToObj(word::charAt)
                .forEach(System.out::print);
    }
}
