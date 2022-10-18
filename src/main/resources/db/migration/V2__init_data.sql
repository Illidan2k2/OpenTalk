insert into branch (branch_name) values ('HN1'),('HN2'),('DN'),('VINH'),('QN'),('SG1'),('SG2');
insert into role (name) values ('admin'),('employee');

insert into users (username,phone_number,email,address,status,password,user_role,user_branch) values
('Long Hoang','0396681233','long.phamhoang1@ncc.asia','12 Hoang Dao Thuy','ENABLED','$2a$12$CJ0pTyLPxJXFEks0m.r8mOwMq2IAb0RnGuxQa9OjMjgK9xG/9iRnu',1,1),
('Linh Dan','01663403287','linhdhn200168@fpt.edu.vn','72 Tran Dang Ninh','DISABLED','$2a$12$DkZyT8iNDsay3Ug9e8NzJu7XRkrQTF8K3e4IcSZyW5EoA/l3kD8B6',2,3),
('Tom Cruise','0987252198','tomcruise19@gmail.com','4 Nottingham','ENABLED','$2a$12$gxqUWcOp1RXTKVYVBkA/OOH6HrhDFZdhjQ9GR47iH7afXCtAHK13W',2,4)

-- id: 1; password: 12345
-- id: 2; password: 54321
-- id: 3; password: cruise1912