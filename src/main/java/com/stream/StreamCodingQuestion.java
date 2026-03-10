package com.stream;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.averagingDouble;
import static java.util.stream.Collectors.groupingBy;

public class StreamCodingQuestion {

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


        //Find oldest Employee by age from the Employee List.
//        Employee oldestEmployee = empList.stream()
//                .max(Comparator.comparing(Employee::getAge))
//                .orElse(null);
//
//        System.out.println("Oldest Employee :: "+oldestEmployee);
//
//        //Group Employees by city from the Employee List
//        Map<String, List<Employee>> groupEmployeeByCity = empList.stream()
//                .collect(Collectors.groupingBy(Employee::getCity));
//
//        System.out.println("Grouping Employee Based on City :: "+groupEmployeeByCity);
//
//        //Find names of all the departments in the organization
//
//        List<String> namesOfAllDepartment = empList.stream()
//                .map(Employee::getDeptName)
//                .distinct()
//                .collect(Collectors.toList());
//
//        System.out.println("Name of All Department :: "+namesOfAllDepartment);
//
//        //Sort the employees by salary in each department in ascending order
//        Map<String, List<Employee>> sortEmpBySalaryInEachDept = empList.stream()
//                .collect(Collectors.groupingBy(Employee::getDeptName,
//                        Collectors.collectingAndThen(Collectors.toList(),
//                                employeesList -> employeesList.stream()
//                                        .sorted(Comparator.comparing(Employee::getSalary))
//                                        .collect(Collectors.toList()))));
//
//        System.out.println(sortEmpBySalaryInEachDept);
//
//        //Find the highest salary earning employee in the organisation
//
//        Employee highestSalaryEarningEmp = empList.stream()
//                .max(Comparator.comparing(Employee::getSalary))
//                .orElse(null);
//        System.out.println("Highest Salary Earning Employee :: "+highestSalaryEarningEmp);
//
//        //Find highest paid salary Employee in the organisation based on gender
//        Map<String, Employee> highestSalaryEmpByGender = empList.stream()
//                .collect(Collectors.groupingBy(Employee::getGender,
//                        Collectors.collectingAndThen(Collectors.toList(),
//                                employeeList -> employeeList.stream()
//                                        .max(Comparator.comparing(Employee::getSalary))
//                                        .orElse(null)
//                                        )));
//
//        System.out.println("Highest Paid Employee Based on Gender :: "+highestSalaryEmpByGender);
//
//        //Find highest salaried employee based on department.
//        Map<String, Optional<Employee>> highestSalaryEmpByDept = empList.stream()
//                .collect(Collectors.groupingBy(Employee::getDeptName,
//                        Collectors.maxBy(Comparator.comparing(Employee::getSalary))
//                                ));
//        System.out.println("Highest Salary Employee By Department :: "+highestSalaryEmpByDept);
//
//        //Find second highest paid salary Employee based on department.
//        Map<String, Employee> secondHighestPaidEmpByDept = empList.stream()
//                .collect(Collectors.groupingBy(Employee::getDeptName,
//                        Collectors.collectingAndThen(Collectors.toList(),
//                                employeeLists -> employeeLists.stream()
//                                        .sorted(Comparator.comparing(Employee::getSalary).reversed())
//                                        .skip(1)
//                                        .findFirst()
//                                        .orElse(null))));
//
//        System.out.println("Second Highest Salary Of Employee By Department :: "+secondHighestPaidEmpByDept);


        //Find oldest Employee by age from the Employee List.
        Employee oldestEmployee = empList.stream().max(Comparator.comparing(Employee::getAge)).orElse(null);
        System.out.println(oldestEmployee);

        //Group Employees by city from the Employee List
        Map<String, List<Employee>> groupEmpByCity = empList.stream().collect(Collectors.groupingBy(Employee::getCity));
        System.out.println(groupEmpByCity);

        //Find names of all the departments in the organization
        Set<String> listOfAllDept = empList.stream().map(Employee::getDeptName).collect(Collectors.toSet());
        System.out.println(listOfAllDept);

        //Sort the employees by salary in each department in ascending order
        Map<String, List<Employee>> sortEmpBySalaryInEachDept = empList.stream().collect(Collectors.groupingBy(Employee::getDeptName,
                Collectors.collectingAndThen(Collectors.toList(),
                        emp -> emp.stream().sorted(Comparator.comparing(Employee::getSalary)).collect(Collectors.toList()))));
        System.out.println(sortEmpBySalaryInEachDept);

        // Find the highest salary earning employee in the organisation.
        Employee highestSalaryEarningEmp = empList.stream().max(Comparator.comparing(Employee::getSalary)).orElse(null);
        System.out.println(highestSalaryEarningEmp);


        //Find highest paid salary Employee in the organisation based on gender
        Map<String, Employee> highestPaidEmpByGender = empList.stream().collect(Collectors.groupingBy(Employee::getGender,
                Collectors.collectingAndThen(Collectors.toList(), list -> list.stream().max(Comparator.comparing(Employee::getSalary)).orElse(null))));
        System.out.println(highestPaidEmpByGender);

        //Find highest salaried employee based on department
        Map<String, Optional<Employee>> highestSalariedEmpByDept = empList.stream().collect(groupingBy(Employee::getDeptName,
                Collectors.maxBy(Comparator.comparing(Employee::getSalary))));
        System.out.println(highestSalariedEmpByDept);

        //Find second highest paid salary Employee based on department.
        Map<String, Optional<Employee>> secondHighestPaidEmpByDept = empList.stream().collect(Collectors.groupingBy(Employee::getDeptName,
                Collectors.collectingAndThen(Collectors.toList(), list -> list.stream()
                        .sorted(Comparator.comparing(Employee::getSalary).reversed())
                        .skip(1)
                        .findFirst())));
        System.out.println(secondHighestPaidEmpByDept);

        //Find the youngest female employee in the organization.
        Employee youngestFemaleEmp = empList.stream()
                .filter(emp -> emp.getGender().equals("F"))
                .min(Comparator.comparing(Employee::getAge))
                .orElse(null);
        System.out.println(youngestFemaleEmp);

        //Find the number of employees in the organisation
        Long numberOfEmpInOrg = empList.stream().count();
        System.out.println(numberOfEmpInOrg);

        //Sort an Employee List by age and name.
        List<Employee> sortAnEmpByAgeAndName = empList.stream()
                .sorted(Comparator.comparing(Employee::getAge)
                        .thenComparing(Employee::getName)).collect(Collectors.toList());
        System.out.println(sortAnEmpByAgeAndName);

        //Find Average age of Male and Female Employees from Employee List.

        Map<String, Double> averageAgeOfEmpByGender = empList.stream().collect(Collectors.groupingBy(Employee::getGender,
                Collectors.collectingAndThen(Collectors.toList(), list -> list.stream().collect(Collectors.averagingDouble(Employee::getAge)))));
        System.out.println(averageAgeOfEmpByGender);

        //Find the department name which has the highest number of employees.
        Map.Entry<String, Long> deptWithNumberOfEmp = empList.stream().collect(Collectors.groupingBy(Employee::getDeptName, Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue()).get();
        System.out.println(deptWithNumberOfEmp);

        //Find maximum age of an Employee from Employee List
        int maxAgeOfEmp = empList.stream().max(Comparator.comparing(Employee::getAge)).get().getAge();
        System.out.println(maxAgeOfEmp);

        //Find the names of the departments that have more than three employees
        List<String> nameOfDeptHavingMoreThan3Emp = empList.stream().collect(Collectors.groupingBy(Employee::getDeptName, Collectors.counting()))
                .entrySet().stream().filter(entry -> entry.getValue()>3).map(Map.Entry::getKey).collect(Collectors.toList());
        System.out.println(nameOfDeptHavingMoreThan3Emp);

        //Find all employees who lives in ‘Bengaluru’ city, sort them by their name and return the names of the employees
        List<String> empLivingInBengaluru = empList.stream().filter(employee -> employee.getCity().equalsIgnoreCase("Bengaluru")
                ).sorted(Comparator.comparing(Employee::getName)).map(Employee::getName).collect(Collectors.toList());
        System.out.println(empLivingInBengaluru);

        // Find Highest experienced employee in the organization
        Employee highestExpEmp = empList.stream().min(Comparator.comparing(Employee::getYearOfJoining)).orElse(null);
        System.out.println(highestExpEmp);

        //Find the count of male and female employees present in the organization.
        Map<String, Long> countOfMaleAndFemaleEmp = empList.stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.counting()));
        System.out.println(countOfMaleAndFemaleEmp);

        //Find if there are any employees from IT Department
        Employee anyEmpFromIT = empList.stream().filter(employee -> employee.getDeptName().equalsIgnoreCase("IT")).findAny().orElse(null);
        System.out.println(anyEmpFromIT);

        //Find average and total salary of the organization
        double averageOfSalaryInTheOrg = empList.stream().mapToDouble(Employee::getSalary).average().orElse(0.0);
        long sumOfSalary = empList.stream().mapToLong(Employee::getSalary).sum();
        System.out.println("Average Of Salary :: "+averageOfSalaryInTheOrg+" Sum Of Salary :: "+sumOfSalary);

        //Find employees whose age is greater than 32 and less than 32
        Map<Boolean, List<Employee>> empAgeGreaterThan32AndLessThan32  = empList.stream().collect(Collectors.partitioningBy(employee -> employee.getAge() > 32));
        System.out.println(empAgeGreaterThan32AndLessThan32);

        //Find average salary of each department
        Map<String, Double> avgSalaryOfEachDept = empList.stream().collect(Collectors.groupingBy(Employee::getDeptName,
                Collectors.averagingDouble(Employee::getSalary)));
        System.out.println(avgSalaryOfEachDept);

        //Fnd the number of Male and Female employees in each department
        Map<String, Map<String, Long>> numOfMaleAndFemaleEmp = empList.stream().collect(Collectors.groupingBy(Employee::getDeptName,
                Collectors.groupingBy(Employee::getGender, Collectors.counting())));

        System.out.println(numOfMaleAndFemaleEmp);

        //Find the nth Highest salary of the organization

        int n = 3;
        Long nthHighestSalary = empList.stream().sorted(Comparator.comparing(Employee::getSalary).reversed())
                .skip(n-1)
                .findFirst()
                .map(Employee::getSalary)
                .orElse(null);
        System.out.println(nthHighestSalary);

        //Find the total salary of the paid in the organization where salary paid > 400
        Long totalSalaryPaidByOrg = empList.stream().map(Employee::getSalary)
                .filter(salary -> salary >400)
                .reduce(0L, Long::sum);
        System.out.println(totalSalaryPaidByOrg);

        //Find a list of employees from each department whose salary is higher than the average salary of that department
    }

}
