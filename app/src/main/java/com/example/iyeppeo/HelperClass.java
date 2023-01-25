package com.example.iyeppeo;
//dashboard
public class HelperClass {
    String nama, username, password;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HelperClass(String nama, String username, String password) {
        this.nama = nama;
        this.username = username;
        this.password = password;
    }

    public HelperClass() {
    }
}

