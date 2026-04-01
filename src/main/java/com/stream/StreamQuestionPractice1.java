package com.stream;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class StreamQuestionPractice1 {

    public static long factorial(int num) {
        long fact = 1;
        for(int i=2; i<=num; i++) {
            fact *= num;
        }
        return fact;
    }

    public static void main(String[] args) {
        List<Integer> listOfIntegers = Arrays.asList(23,78,95,73,23,56,12,90,334,71,95,73,23,561,12,90,33);

        //Given a list of integers, separate the odd and even numbers from the list?
        Map<String, List<Integer>> oddAndEvenList = listOfIntegers.stream()
                .collect(Collectors.groupingBy(elem -> (elem%2 == 0) ? "Even" : "Odd"));
        System.out.println("Even and Odd Partitioned List :: "+oddAndEvenList);

        //How do you find frequency of each character in a string using Java 8 streams
        String inputString = "Java is an awesome programming language";
        List<Character> ch = inputString.chars().mapToObj(i -> (char)i).toList();
        Map<Character, Long> frequencyOfCharacter = ch.stream()
                .filter(character -> character != ' ')
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(frequencyOfCharacter);

        //How do you sort the given list in the reverse order
        List<Integer> reverseOrder = listOfIntegers.stream()
                .sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        System.out.println(reverseOrder);

        //How do you remove duplicate elements from a list using Java 8 streams
        List<Integer> distinctElement = listOfIntegers.stream().distinct().collect(Collectors.toList());
        System.out.println(distinctElement);

        //How do you find frequency of each element in a list
        Map<Integer, Long> frequencyOfElem = listOfIntegers.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(frequencyOfElem);

        //How do you find the last element from a list
        Integer lastElement = listOfIntegers.get(listOfIntegers.size()-1);
        System.out.println(lastElement);

        //Find first non-repeating character in a string
        String str = "java is a just a awesome programming language and it's platform independent";

        Stream<Character> characterStream = str.chars().mapToObj(c -> (char) c);
        Character firstNonRepeatingCharacter = characterStream.filter(character -> character != ' ')
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() == 1)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(' ');

        System.out.println(firstNonRepeatingCharacter);

        //Find sum of all digits of a number
        int digits = 1234;
        Integer in = Arrays.stream(String.valueOf(digits).split(""))
                .mapToInt(Integer::parseInt)
                .sum();
        System.out.println(in);

        //Given a list of strings, find out those strings which start with a number
        List<String> listOfStrings = Arrays.asList("One", "2wo", "3hree", "Four", "5ive", "6ix","45ro,'7ko");

        List<String> stringStartWithNum = listOfStrings.stream()
                .filter(strr -> strr.matches("^[0-9].*"))
                .collect(Collectors.toList());
        System.out.println(stringStartWithNum);

        //Given a list of integers, find max and min of those numbers
        listOfIntegers.stream().max(Comparator.naturalOrder()).ifPresent(System.out::println);
        listOfIntegers.stream().min(Comparator.naturalOrder()).ifPresent(System.out::println);

        //Java 8 program to check if two strings are anagrams or not
        String s1 = "saurav";
        String s2 = "vauras";

        s1 = Stream.of(s1.split("")).map(String::toLowerCase).sorted().collect(Collectors.joining());
        s2 = Stream.of(s2.split("")).map(String::toLowerCase).sorted().collect(Collectors.joining());
        System.out.println(s1+ " and "+s2+" are anagram ? "+s1.equals(s2));

        //find out all the duplicates characters from a string
        Set<Integer> st = new HashSet<>();
        List<Integer> duplicateElement = listOfIntegers.stream()
                .filter(elem -> !st.add(elem))
                .collect(Collectors.toList());

        System.out.println(duplicateElement);

        //Given a list of strings, sort them according to length in ascending order
        List<String> sortString = listOfStrings.stream().sorted(Comparator.comparing(String::length)).collect(Collectors.toList());
        System.out.println(sortString);

        //How do you merge two unsorted arrays into single sorted array without duplicates?
        int[] a = new int[] {6,0,4,5};
        int[] b = new int[] {1,0,9,3};

        int[] intStream = IntStream.concat(Arrays.stream(a), Arrays.stream(b)).toArray();
        System.out.println(Arrays.toString(intStream));

        //Find second largest number in an array
        listOfIntegers.stream()
                .sorted(Comparator.reverseOrder())
                .skip(1)
                .findFirst()
                .ifPresent(System.out::println);

        //Given an array, find sum and average of all elements
        int sum = listOfIntegers.stream().reduce(0, Integer::sum);
        OptionalDouble average = listOfIntegers.stream().mapToInt(Integer::intValue).average();
        System.out.println("Sum :: " +sum);
        System.out.println("Average :: "+average);

        //Reverse each word of a string using Java
        String reverseEachWord = Arrays.stream(str.split(" "))
                .map(word -> new StringBuilder(word).reverse().toString())
                        .collect(Collectors.joining(" "));
        System.out.println(reverseEachWord);

        //How to find only duplicate elements with its count from the list in Java8

        List<String> names = Arrays.asList("Rahul", "Rohan", "Rahul", "Keshav", "Rohan", "Vivek", "Vivek");

        Map<String, Long> duplicateElementWithCount = names.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .filter(elem -> elem.getValue() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        System.out.println(duplicateElementWithCount);

        //How to Reverse elements of a Parallel Stream in Java

        List<Integer> list= Arrays.asList(4,1,12,13,14,15,16,17,18,19,20);
        List<Integer> reverseList = list.parallelStream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        System.out.println(reverseList);

        //Use Optional to Avoid NullPointerException
        Optional<Integer> opt = list.stream().filter(elem -> elem > 1000).findFirst();
        System.out.println(opt);

        //How to Flatten a List of Lists
        List<List<Integer>> listOfList= Arrays.asList(Arrays.asList(1,2,3), Arrays.asList(4,5,6), Arrays.asList(7,8,9));
        List<Integer> flattenedList = listOfList.stream().flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println(listOfList);
        System.out.println(flattenedList);

        //How do you get three maximum numbers and three minimum numbers from the given list of integers
        list.stream().sorted(Comparator.naturalOrder()).limit(3).forEach(System.out::println);
        list.stream().sorted(Comparator.reverseOrder()).limit(3).forEach(System.out::println);

        //How do you find the factorial of a number stream
        System.out.println("********************************");
        int factorialOfNum = IntStream.rangeClosed(1, 5).reduce(1, (num1, num2) -> num1*num2);
        System.out.println(factorialOfNum);

        //Find all the numbers which are multiple of 5 and divisible by 3 from the given list of integers?
        List<Integer> multipleOf5AndDivisibleBy3 = listOfIntegers.stream()
                .filter(elem -> elem % 5 ==0 && elem % 3 == 0)
                .distinct()
                .collect(Collectors.toList());

        System.out.println(multipleOf5AndDivisibleBy3);
    }
}
