create table users (
    id          integer primary key,
    username    varchar(255) unique not null,
    password    varchar(255) not null,
    enabled     boolean not null default 't',
    email		varchar(255)
);

insert into users values (1, 'admin', '1234', 't','test@mail.com');
insert into users values (2, 'cysun', 'abcd', 't','cysun@localhost.localdomain');
insert into users values (3,'AI','','t','ai@ttt.com');

create table game(
	id integer primary key,
	player1 integer not null references users(id),
	player2 integer not null references users(id),
	gameboard	varchar(255) not null,
	gamestatus varchar(255),
	started timestamp,
	ended timestamp,
	saved timestamp,
	winner integer references users(id)
);

insert into game values (1,2,3,'[0,0,0,1,1,-1,-1,-1,-1]','OVER',now(),now(),null,2);
insert into game values (2,2,3,'[0,0,0,1,1,-1,-1,-1,-1]','OVER',now(),now(),null,3);
insert into game values (3,2,3,'[0,-1,-1,-1,1,-1,-1,-1,-1]','SAVED',now(),null,now(),null);

create sequence hibernate_sequence START with 100;