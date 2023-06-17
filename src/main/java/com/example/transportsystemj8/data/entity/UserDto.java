package com.example.transportsystemj8.data.entity;

public class UserDto {
    private String username;
    private String password;

//    public UserDto() {
//    }
//
//    public UserDto(String username, String password) {
//        this.username = username;
//        this.password = password;
//    }


    @Override
    public String toString() {
        return super.toString();
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
}
