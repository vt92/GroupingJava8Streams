public class Person {
    private int personId;
    private String name;
    private Double salary;;
    private Gender gender;
 
    public Person(int personId, String name, Double salary, Gender gender) {
        super();
        this.personId = personId;
        this.name = name;
        this.salary = salary;
        this.gender = gender;
    }
 
    public boolean isMale() {
        return this.gender == Gender.MALE;
    }
 
    public boolean isFemale() {
        return this.gender == Gender.FEMALE;
    }
 
    public int getPersonId() {
        return personId;
    }
 
    public void setPersonId(int personId) {
        this.personId = personId;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
   
 
    public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Gender getGender() {
        return gender;
    }
 
    public void setGender(Gender gender) {
        this.gender = gender;
    }
 
    @Override
    public String toString() {
       StringBuilder str = null;
        str = new StringBuilder();
        str.append("Person Id:- " + getPersonId() + " Gender:- " + getGender() + " Name:- " + getName() +
            " Salary:- " + getSalary());
        return str.toString();
    }
 
    public static enum Gender {
        MALE,
        FEMALE
    }
 
}


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import copied.Person.Gender;

public class Java8StreamGrouping {
    public static void main(String[] args) {
 
        try {
 
            List<Person> persons = new ArrayList<>();
            persons.add(new Person(1, "Yashwant", 25000.0, Gender.MALE));
            persons.add(new Person(2, "Mahesh", 30000.0, Gender.MALE));
            persons.add(new Person(3, "Vinay", 30000.0, Gender.MALE));
            persons.add(new Person(4, "Dinesh", 30000.0, Gender.MALE));
            persons.add(new Person(5, "Kapil", 20000.0, Gender.MALE));
            persons.add(new Person(6, "Ganesh", 25000.0, Gender.MALE));
            persons.add(new Person(7, "Nita", 30000.0, Gender.FEMALE));
            persons.add(new Person(8, "Pallavi", 15000.0, Gender.FEMALE));
            persons.add(new Person(9, "Mayuri", 30000.0, Gender.FEMALE));
            persons.add(new Person(10, "Divya", 30000.0, Gender.FEMALE));
 
            Map<Person.Gender, Long> byGender = persons.stream()
                .collect(Collectors.groupingBy(p -> p.getGender(), Collectors.counting()));
 
            Map <Object, Long> bySalary = persons.stream()
                .collect(Collectors.groupingBy(Person::getSalary, Collectors.counting()));
 
            Map<Gender, Map<Object, String>> byGenderAndSalary = persons.stream()
            		.collect(Collectors.groupingBy(Person::getGender,Collectors.groupingBy(Person::getSalary, 
            				Collectors.mapping(Person::getName, Collectors.joining(","))
            				)));
            

 
            System.out.println("Group By Gender");
            System.out.println(byGender);
            System.out.println("\nGroup By salary");
            System.out.println(bySalary);
            System.out.println("\nGroup By Gender & Salary");
            System.out.println(byGenderAndSalary);
 
        } catch (Exception e) {
            e.printStackTrace();
        }
 
    }
}
