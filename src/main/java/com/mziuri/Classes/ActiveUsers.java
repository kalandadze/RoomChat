package com.mziuri.Classes;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActiveUsers {
    private Room room;
    private int active;
}
