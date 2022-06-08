package com.hide.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Integer oid;
    private Integer uid;
    private Integer goodId;
    private String orderTime;
}
