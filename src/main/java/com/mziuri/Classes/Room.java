package com.mziuri.Classes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int maxMembers;
    private String chatName;

    public Room(int id, int maxMembers, String chatName) {
        this.id = id;
        this.maxMembers = maxMembers;
        this.chatName = chatName;
    }
}
