create table branch (
    id int primary key generated by default as identity,
    branch_name varchar(5)
);

create table role (
    id int primary key generated by default as identity,
    name varchar(50)
);

create table chat(
    id int primary key generated by default as identity,
    sender varchar(100),
    message varchar(500)
);

create table users(
    id int primary key generated by default as identity,
    username varchar(200),
    email varchar(200),
    phone_number varchar(11),
    address varchar(200),
    password varchar(100),
    status varchar(10),
    user_role int not null,
    user_branch int not null,
    foreign key(user_role) references role(id),
    foreign key(user_branch) references branch(id)
);

create table opentalk(
    id int primary key generated by default as identity,
    topic varchar(100),
    date timestamp,
    link varchar(1000),
    branch_id int not null,
    foreign key(branch_id) references branch(id)
);

create table user_opentalk_detail(
    user_id int not null generated by default as identity,
    opentalk_id int not null,
    foreign key (user_id) references users(id),
    foreign key(opentalk_id) references opentalk(id)
);