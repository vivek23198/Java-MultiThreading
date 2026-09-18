package DesignPatterns;

import java.util.ArrayList;
import java.util.List;

final class ImmutableObj {
    private final String name;
    private final int age;
    private final String address;
    private final List<String> skillSet;

    public ImmutableObj(String name, int age, String address, List<String> skillSet) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.skillSet = new ArrayList<>(skillSet);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public List<String> getSkillSet() {
        return new ArrayList<>(skillSet);
    }
}

public  class ImmutableClass {

    public static void main(String[] args) {
        List<String> skillsSet = new ArrayList<>();
        skillsSet.add("Java");
        skillsSet.add("Springboot");

        ImmutableObj obj = new ImmutableObj("Vivek", 28, "Mumbai", skillsSet);
        System.out.println(obj.getName());
        System.out.println(obj.getAge());
        System.out.println(obj.getAddress());
        System.out.println(obj.getSkillSet());

        skillsSet.add("Kafka");
        System.out.println("After Modifying a SkillSets");
        System.out.println(obj.getName());
        System.out.println(obj.getAge());
        System.out.println(obj.getAddress());
        System.out.println(obj.getSkillSet());
    }
}
