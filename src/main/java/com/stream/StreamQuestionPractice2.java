package com.stream;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StreamQuestionPractice2 {

    public static void main(String[] args) {

        List<Employee> empList = new ArrayList<>();
        empList.add(new Employee(29, "def", 32, 646, "M", "IT", "Bengaluru", 2011, 14, "Manager"));
        empList.add(new Employee(11, "kyl", 33, 433, "M", "HR", "Hyderabad", 2022, 3, "Manager"));
        empList.add(new Employee(12, "gfh", 34, 120, "F", "Sales", "Bengaluru", 2017, 8, "Sales"));
        empList.add(new Employee(13, "whj", 35, 323, "M", "Sales", "Chennai", 2013, 12, "HR"));
        empList.add(new Employee(14, "wej", 36, 2100, "M", "HR", "Coimbatore", 2014, 11, "HR"));

        empList.add(new Employee(5, "web", 21, 323, "M", "IT", "Pune", 2011, 14, "SDE-3"));
        empList.add(new Employee(3, "fbw", 20, 332, "M", "IT", "Trivandam", 2022,3, "SDE-2"));
        empList.add(new Employee(2, "weh", 22, 322, "F", "IT", "Pune", 2016, 9, "SDE-3"));
        empList.add(new Employee(19, "ehw", 28, 222, "F", "IT", "Trivandam", 2011,14,"DevOps Engineer"));


        //Find oldest Employee by age from the Employee List
        Employee oldestEmpByAge = empList.stream()
                .max(Comparator.comparing(Employee::getAge))
                .get();
        System.out.println(oldestEmpByAge);

        //Group Employees by city from the Employee List
        Map<String, List<Employee>> groupEmpByCity = empList.stream()
                .collect(Collectors.groupingBy(Employee::getCity));
        System.out.println(groupEmpByCity);

        //Find names of all the departments in the organization
        List<String> nameOfAllDept = empList.stream()
                .map(Employee::getDeptName)
                .distinct()
                .toList();

        System.out.println(nameOfAllDept);

        //Sort the employees by salary in each department in ascending order
        Map<String, List<String>> sortEmpByySalaryEachDept = empList.stream()
                .collect(Collectors.groupingBy(Employee::getDeptName,
                        Collectors.collectingAndThen(Collectors.toList(),
                                emp1 -> emp1.stream()
                                        .sorted(Comparator.comparing(Employee::getSalary))
                                        .map(emp -> emp.getName()+" - "+emp.getAge()+" - "+emp.getDesignation())
                                        .toList())));

        System.out.println(sortEmpByySalaryEachDept);

        //Find the highest salary earning employee in the organisation.
        Employee highestEarner = empList.stream().max(Comparator.comparing(Employee::getSalary)).get();
        System.out.println(highestEarner);

        //Find highest paid salary Employee in the organisation based on gender
        Map<String, Optional<Employee>> highestEarnerEmpByGender = empList.stream()
                .collect(Collectors.groupingBy(Employee::getGender,
                        Collectors.maxBy(Comparator.comparing(Employee::getSalary))));
        System.out.println(highestEarnerEmpByGender);

        //Find highest salaried employee based on department
        Map<String, Optional<Employee>> highestSalaryEmpByDept = empList.stream()
                .collect(Collectors.groupingBy(Employee::getDeptName,
                        Collectors.maxBy(Comparator.comparing(Employee::getSalary))));
        System.out.println(highestSalaryEmpByDept);

        //Find second highest paid salary Employee based on department
        Map<String, Optional<Employee>> secondHighestSalaryEmpByDept = empList.stream()
                .collect(Collectors.groupingBy(Employee::getDeptName,
                        Collectors.collectingAndThen(Collectors.toList(),
                                list -> list.stream().
                                        sorted(Comparator.comparing(Employee::getSalary).reversed())
                                        .skip(1)
                                        .findFirst())));
        System.out.println(secondHighestSalaryEmpByDept);

        //Find the youngest female employee in the organization
        Employee youngestFemaleEmp = empList.stream()
                .filter(employee -> employee.getGender().equalsIgnoreCase("F"))
                .min(Comparator.comparing(Employee::getAge))
                .orElse(null);
        System.out.println(youngestFemaleEmp);

        //Sort an Employee List by age and name.
        List<Employee> sortEmpByAgeAndName = empList.stream()
                .sorted(Comparator.comparing(Employee::getAge)
                        .thenComparing(Employee::getName))
                .toList();
        System.out.println(sortEmpByAgeAndName);

        //Find Average age of Male and Female Employees from Employee List
        Map<String, Double> avgAgeBasedOnGender = empList.stream()
                .collect(Collectors.groupingBy(Employee::getGender,
                        Collectors.averagingDouble(Employee::getAge)));
        System.out.println(avgAgeBasedOnGender);

        //Find the department name which has the highest number of employees
        String deptWithHighestEmp = empList.stream()
                .collect(Collectors.groupingBy(Employee::getDeptName, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
        System.out.println(deptWithHighestEmp);

        //Find maximum age of an Employee from Employee List
        Integer maxAgeOfEmp = empList.stream().max(Comparator.comparing(Employee::getAge))
                .map(Employee::getAge)
                .orElse(null);
        System.out.println(maxAgeOfEmp);

        //Find the names of the departments that have more than three employees
        List<String> deptWithMoreThan3Emp = empList.stream()
                .collect(Collectors.groupingBy(Employee::getDeptName, Collectors.counting()))
                .entrySet()
                .stream()
                .filter(emp -> emp.getValue() > 3)
                .map(Map.Entry::getKey)
                .toList();
        System.out.println(deptWithMoreThan3Emp);

        //Find all employees who lives in ‘Bengaluru’ city, sort them by their name and return the names of the employees.
        List<String> bengaluruEmpByName = empList.stream()
                .filter(employee -> employee.getCity().equalsIgnoreCase("Bengaluru"))
                .sorted(Comparator.comparing(Employee::getName))
                .map(Employee::getName)
                .toList();

        System.out.println(bengaluruEmpByName);

        //Find Highest experienced employee in the organization
        Employee highestExpEmp = empList.stream()
                .min(Comparator.comparing(Employee::getYearOfJoining))
                .orElse(null);
        System.out.println(highestExpEmp);

        //Find the count of male and female employees present in the organization.
        Map<String, Long> countOfEmpByGender = empList.stream()
                .collect(Collectors.groupingBy(Employee::getGender, Collectors.counting()));
        System.out.println(countOfEmpByGender);

    }
}
