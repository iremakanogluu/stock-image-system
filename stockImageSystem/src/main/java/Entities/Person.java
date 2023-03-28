package Entities;

import java.sql.Date;

public class Person {
    private int personId;
    private String firstName;
    private String lastName;
    private String userName;
    private String gender;
    private Date birthdate;

    public Person(int personId, String firstName, String lastName, String userName, String gender, Date birthdate) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.gender = gender;
        this.birthdate = birthdate;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return
                firstName + "\n" +lastName + '\n' +
                userName + '\n' +
                gender + '\n' +
                birthdate;
    }
}
