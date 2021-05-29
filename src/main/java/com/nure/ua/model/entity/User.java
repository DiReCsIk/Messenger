package com.nure.ua.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity(name = "user")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = -2001940194059194014L;

    @Id
    @Setter(value = AccessLevel.NONE)
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "USER_NICKNAME")
    private String nickname;

    @Column(name = "USER_PHONE")
    private String phone;

    @Column(name = "USER_NAME")
    private String name;

    @Column(name = "USER_SURNAME")
    private String surname;

    @Column(name = "USER_PASSWORD")
    private String password;

    @Column(name = "USER_BIRTHDATE")
    private LocalDateTime birthdate;
}
