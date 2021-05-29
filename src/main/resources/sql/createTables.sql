create table message
(
    MESSAGE_ID   int auto_increment
        primary key,
    MESSAGE_TIME timestamp   not null,
    MESSAGE_TEXT text        not null,
    SENDER_ID    varchar(45) not null,
    RECEIVER_ID  varchar(45) not null
);

create table user
(
    USER_ID        int auto_increment,
    USER_NICKNAME  varchar(32) not null,
    USER_PHONE     varchar(10) not null,
    USER_NAME      varchar(32) not null,
    USER_SURNAME   varchar(32) not null,
    USER_PASSWORD  varchar(32) not null,
    USER_BIRTHDATE date        null,
    constraint USER_ID_UNIQUE
        unique (USER_ID),
    constraint USER_NICKNAME_UNIQUE
        unique (USER_NICKNAME)
);

alter table user
    add primary key (USER_ID);
