package com.smartpullup.smartpullup;

/**
 * Created by Jorren on 1/03/2018.
 */

public class User {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String weight;
    private String height;
    private String dateBirth;


    public User(String id, String firstName, String lastName, String email, String height, String weight, String dateBirth) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.weight = weight;
        this.height = height;
        this.dateBirth = dateBirth;

    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getHeight() {return height;}

    public String getWeght() {
        return weight;
    }

    public String getDateBirth() {
        return dateBirth;
    }
}
