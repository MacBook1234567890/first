package com.example.first;

public class UserHelper {
    String name ,cnic, email, phoneNO, password;

    public UserHelper() {}
    public UserHelper(String name,String cnic,String email,String phoneNO,String password) {
        this.name=name;
        this.cnic=cnic;
        this.email=email;
        this.phoneNO=   phoneNO;
        this.password=password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNO() {
        return phoneNO;
    }

    public void setPhoneNO(String phoneNO) {
        this.phoneNO = phoneNO;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
