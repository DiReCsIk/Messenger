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
@Entity(name = "message")
public class Message implements Serializable  {
    @Serial
    private static final long serialVersionUID = -4512341564567129054L;

    @Id
    @Setter(value = AccessLevel.NONE)
    @Column(name = "MESSAGE_ID")
    private long messageId;

    @Column(name = "MESSAGE_TIME")
    private LocalDateTime time;

    @Column(name = "MESSAGE_TEXT")
    private String text;

    @Column(name = "SENDER_ID")
    private long senderId;

    @Column(name = "RECEIVER_ID")
    private long receiverId;
}
