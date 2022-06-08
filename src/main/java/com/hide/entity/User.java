package com.hide.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class User implements Serializable {

    public User() {}
    private int id;
    private String name;
    private String nickName;
    private int age;
    private String hobby;
}
