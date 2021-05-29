package com.nure.ua.data_container;

import com.nure.ua.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Session {
    private User user;
    private int id;
}
