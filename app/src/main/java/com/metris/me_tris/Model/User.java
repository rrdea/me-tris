package com.metris.me_tris.Model;

public class User {

    // nama, nomor telepon, password (hash), alamat, email
    private String name;
    private String password;
    private String nomorTelepon;
    private String email;
    private String alamat;

    public User() {    }

    public User(String name, String password, String nomorTelepon, String email, String alamat) {
        this.name = name;
        this.password = password;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.alamat = alamat;
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

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
