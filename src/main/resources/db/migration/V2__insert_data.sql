---
---Insert to branches entity
---
insert into branches (branch_name) values ('HN1'),('HN2'),('DN'),('VINH'),('QN'),('SG1'),('SG2');

---
---Insert to roles entity
---
insert into roles (name) values ('admin'),('user'),('staff');
---
---Insert to users entity
---

insert into users (username,phone_number,email,address,status,password,user_role,user_branch) values
('Long Hoang','0396681233','long.phamhoang1@ncc.asia','12 Hoang Dao Thuy','ENABLED','$2a$12$bre.tACXxA6ydQ9yO6CKMeLvcAS3h0a.RBIX/pK1c/fC3itY2ZEGm',1,1),
('Linh Dan','01663403287','linhdhn200168@fpt.edu.vn','72 Tran Dang Ninh','DISABLED','$2a$12$DkZyT8iNDsay3Ug9e8NzJu7XRkrQTF8K3e4IcSZyW5EoA/l3kD8B6',2,3),
('Tom Cruise','0987252198','tomcruise19@gmail.com','4 Nottingham','ENABLED','$2a$12$gxqUWcOp1RXTKVYVBkA/OOH6HrhDFZdhjQ9GR47iH7afXCtAHK13W',2,4),
('Huy Hoang','0983891266','huyhoangpham2@gmail.com','90 Juxtapose','ENABLED','$2a$12$W0dRAVeBg.yV9ZVENbXPBuhN9xSdcrrOWblrzXfcj3wk07k1GZjhK',3,5),
('Gregory Heffley','0145735822','longphgbh200168@fpt.edu.vn','19 Lang Ha','ENABLED','$2a$12$m1SFIvLw7V89/9zFkQhUbOfjBvTTvUvfdPW1y79ZNDQ1lly2Y/zYu',3,1);

-- id: 1; password: $2a$12$bre.tACXxA6ydQ9yO6CKMeLvcAS3h0a.RBIX/pK1c/fC3itY2ZEGm (secret)
-- id: 2; password: 54321
-- id: 3; password: cruise1912
-- id: 4; password: long2k
-- id: 5; password: longbaka

---
---Insert to opentalks entity
---
insert into opentalks (topic,date,link,opentalk_branch) values
('Typescript','2022-09-27T10:00:00','https://mail.google.com/mail/u/0/#inbox/FMfcgzGqQmMRxmRrvBwsNCnKLjJqRcQb',3),
('JavaScript','2022-07-21T10:00:00','https://mail.google.com/mail/u/0/#inbox/FMfcgzGqRGSzlQPTbZbCzMSNGWKNSVCL',4),
('PHP','2022-11-11T10:00:00','https://mail.google.com/mail/u/0/#inbox/FMfcgzGqRGSzlQPTbZbCzMSNGWKNSVCL',5),
('Angular','2022-12-24T10:00:00','https://mail.google.com/mail/u/0/#inbox/FMfcgzGqRGSzlQPTbZbCzMSNGWKNSVCL',1);

---
---Insert to user_opentalk_details entity
---
insert into user_opentalk_details (opentalk_id,user_id) values
(1,2),(1,3),(2,1),(2,5),(3,4),(4,2),(4,4);

---
---Insert to staffs entity
---
insert into staffs (firstname,lastname,email,password) values
('Long','Hoang','giaodau2k2@gmail.com','$2a$12$3kY6jCKGK9Yq7PDmIuIrLO3TWmNmbaoXWBYwFldItJQRKFExqcp9S'),
('Thu','Ha','rongvang1912@gmail.com','$2a$12$fbrtwh4jx6lvVR1phF5DK.XIV.hbNOsWhC76GmcQS.06yesCroM4y');

--- id: 1; password: longplus2002
--- id: 2; password: longkun1912

---
---Insert to mails entity
---
insert into mails (mail_title,message_content,date,sender_id) values
('Greeting','Good moring my dear fellow! Hope you get yourself an epic day!','2022-11-20T07:00:00',1);

insert into user_mail_details (receiver_id,mail_id) values
(2,1),(3,1),(4,1);