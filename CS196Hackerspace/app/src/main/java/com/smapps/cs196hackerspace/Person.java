package com.smapps.cs196hackerspace;

/**
 * Created by Sahil on 10/25/2017.
 */

public class Person {
    private String name, phone, email, address;
    private static int ID = 0;

    public Person(final String name, final String phone, final String email, final String address){
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        ID++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static int getID(){
        return ID;
    }
}
