create table users (
 id        bigint primary key auto_increment,
 username  varchar(30) not null unique,
 password  varchar(80) not null,
 email     varchar(50) unique,
 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

create table roles (
 id        int primary key auto_increment,
 name      varchar(50) not null,
 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

create table users_roles (
 user_id   bigint not null,
 role_id   int not null,
 foreign key (user_id) references users (id),
 foreign key (role_id) references roles (id)
);

insert into roles (name)
values
('ROLE_USER'), ('ROLE_ADMIN');

insert into users (username, password, email)
values
('user1', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'user1@gmail.com'),
('user2', '$2a$12$NnGbs88zdSPhBChQboBMfu7PyK08irFpkigGqEHyxb4xJOZHAQvfq', 'user2@gmail.com'),
('user3', '$2a$12$L3bFnBI5AUIJUjk3y7FYROCbtaSsqtCufb5xtof9DVOwe6968VgPa', 'user3@gmail.com');

insert into users_roles (user_id, role_id)
values
(1, 1), (2, 2), (3, 1), (3, 2);