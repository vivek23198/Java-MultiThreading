package com.stream.question;

import com.stream.Employee;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PracticeQ {

    public static void main(String[] args) {
        List<Employee> empList = new ArrayList<>();
        empList.add(new Employee(29, "def", 32, 646, "M", "IT", "Bengaluru", 2011, 14, "Manager"));
        empList.add(new Employee(11, "kyl", 33, 433, "M", "HR", "Hyderabad", 2022, 3, "Manager"));
        empList.add(new Employee(12, "gfh", 34, 120, "F", "Sales", "Bengaluru", 2017, 8, "Sales"));
        empList.add(new Employee(13, "whj", 35, 323, "M", "Sales", "Chennai", 2013, 12, "HR"));
        empList.add(new Employee(14, "wej", 36, 233, "M", "HR", "Coimbatore", 2014, 11, "HR"));

        empList.add(new Employee(5, "web", 21, 323, "M", "IT", "Pune", 2011, 14, "SDE-3"));
        empList.add(new Employee(3, "fbw", 20, 332, "M", "IT", "Trivandam", 2022,3, "SDE-2"));
        empList.add(new Employee(2, "weh", 22, 322, "F", "IT", "Pune", 2016, 9, "SDE-3"));
        empList.add(new Employee(19, "ehw", 28, 222, "F", "IT", "Trivandam", 2011,14,"DevOps Engineer"));


        //Find oldest Employee by age from the Employee List.
        Employee oldestEmp = empList.stream().max(Comparator.comparing(Employee::getAge)).orElse(null);
        System.out.println(oldestEmp);

        //Group Employees by city from the Employee List
        Map<String, List<Employee>> groupEmpByAge = empList.stream()
                .collect(Collectors.groupingBy(Employee::getCity));
        System.out.println(groupEmpByAge);

        // Find names of all the departments in the organization
        List<String> deptName = empList.stream().map(Employee::getDeptName).distinct().toList();
        System.out.println(deptName);

        //Sort the employees by salary in each department in ascending order
        Map<String, List<Employee>> sortBySalaryForEachDept = empList.stream()
                .collect(Collectors.groupingBy(Employee::getDeptName,
                        Collectors.collectingAndThen(Collectors.toList(),
                                emp -> emp.stream()
                                        .sorted(Comparator.comparing(Employee::getSalary))
                                        .toList())));

        System.out.println(sortBySalaryForEachDept);

        //Find the highest salary earning employee in the organisation.
        Employee empWithMaxSalary = empList.stream().max(Comparator.comparing(Employee::getSalary)).orElse(null);
        System.out.println(empWithMaxSalary);

        //Find highest paid salary Employee in the organisation based on gender
        Map<String, Optional<Employee>> highestPaidEmpByGender = empList.stream()
                .collect(Collectors.groupingBy(Employee::getGender,
                        Collectors.maxBy(Comparator.comparing(Employee::getSalary))));
        System.out.println(highestPaidEmpByGender);

        //Find highest salaried employee based on department.

        Map<String, Employee> highestSalaryEmpByDept = empList.stream()
                .collect(Collectors.groupingBy(Employee::getDeptName,
                        Collectors.collectingAndThen(Collectors.toList(),
                                employees -> employees.stream()
                                        .max(Comparator.comparing(Employee::getSalary)
                                                )
                                        .orElse(null))));
        System.out.println(highestSalaryEmpByDept);

    }
}
