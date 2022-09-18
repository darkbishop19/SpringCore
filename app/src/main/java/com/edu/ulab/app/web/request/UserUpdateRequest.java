package com.edu.ulab.app.web.request;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private Long id;
    private String fullName;
    private String title;
    private int age;

    public UserUpdateRequest(Long id, String fullName, String title, int age){
        this.id = id;
        this.fullName = fullName;
        this.title = title;
        this.age = age;

    }
    public UserUpdateRequest(){

    }
}
