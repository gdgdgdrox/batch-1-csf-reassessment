package com.secondhand.server.model;

import java.sql.Date;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Item {
    private String postingId = UUID.randomUUID().toString().substring(0,8);
    private Date postingDate = new Date(System.currentTimeMillis());
    private String name;
    private String email;
    private String phone;
    private String title;
    private String description;
    private String image;


}
