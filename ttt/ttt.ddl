
    create table game (
        id int4 not null,
        gameboard varchar(255),
        gamestatus varchar(255),
        primary key (id)
    );

    create table users (
        id int4 not null,
        email varchar(255),
        enabled boolean not null,
        password varchar(255),
        username varchar(255),
        primary key (id)
    );

    alter table game 
        add constraint FK_mpsjj3ybt6dex356uuxfrlg3l 
        foreign key (id) 
        references users;

    create sequence hibernate_sequence;
