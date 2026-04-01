package com.coding;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DuplicateWords {

    public static void main(String[] args) {
        String str = "java is great and java is powerful";

        String duplicateWord = Stream.of(str.split(" "))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 1)
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(null);

        System.out.println(duplicateWord);

    }
}
