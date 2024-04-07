package com.mziuri.Classes;

import lombok.Data;

import java.util.Date;

@Data
public class JoinExitMessage {
    private String username;
    private boolean hasJoined;
    private Date date;

    public JoinExitMessage(String username, boolean hasJoined, Date date) {
        this.username = username;
        this.hasJoined = hasJoined;
        this.date = date;
    }
}
