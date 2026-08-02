package Hospital;

import Hospital.enums.Gender;

public abstract class Person{

    private final String id;
    private String name;
    private int age;
    private Gender gender;
    private String phoneNumber;

    public Person (String id, String name, int age, Gender gender, String phoneNumber){
        this.id = id;
        this.age = age;
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
