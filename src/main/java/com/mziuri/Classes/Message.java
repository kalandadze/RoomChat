package com.mziuri.Classes;

import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private String sender;
    private String message;
    private Date date;
}
