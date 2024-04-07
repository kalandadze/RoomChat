package com.mziuri.Classes;

import lombok.Data;

import java.util.Date;

@Data
public class JoinExitMessage {
    private String username;
    private boolean hasJoined;
    private String date;

    public JoinExitMessage(String username, boolean hasJoined, String date) {
        this.username = username;
        this.hasJoined = hasJoined;
        this.date = date;
    }
}
