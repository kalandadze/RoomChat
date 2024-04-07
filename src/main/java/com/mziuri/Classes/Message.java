package com.mziuri.Classes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private String sender;
    private String message;
    private Date date;

    public Message(String sender, String message, Date date) {
        this.sender = sender;
        this.message = message;
        this.date = date;
    }
}
