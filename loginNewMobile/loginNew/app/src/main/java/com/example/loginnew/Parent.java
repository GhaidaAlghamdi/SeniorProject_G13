package com.example.loginnew;

public class Parent {
    private String email;
    private String gender;
    private String id;
    private String name;
    private String password;
    private String phone;

    public Parent(){}
    public Parent(String email , String gender , String id , String name , String password, String phone)
    {
        this.email = email;
        this.id = id ;
        this.name = name;
        this.gender = gender;
        this.password = password;
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
