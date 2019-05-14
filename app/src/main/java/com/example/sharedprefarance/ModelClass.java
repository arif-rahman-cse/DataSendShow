package com.example.sharedprefarance;

public class ModelClass {

    String firstName;
    String lastName;
    String email;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }



    public ModelClass(String firstName, String lastName, String email){

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;


    }
}
