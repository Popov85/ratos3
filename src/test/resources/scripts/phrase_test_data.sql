insert into user (name, surname, password, email) values('Dmitri','Smirnoff','855fgUwd','dmirti.smirnoff@gmail.com');
insert into user_role(user_id, role_id) VALUES (2, 6);
insert into staff (user_id, dep_id, pos_id) values(2, 1, 1);

insert into phrase(phrase, staff_id, last_used) values('access', 1, '2018-07-18 10:30:15.999999999');
insert into phrase(phrase, staff_id, last_used) values('assert', 1, '2018-06-06 15:02:13.999999999');
insert into phrase(phrase, staff_id, last_used) values('implements', 1, '2018-06-20 12:59:10.999999999');
insert into phrase(phrase, staff_id, last_used) values('extends', 2, '2018-04-01 08:11:44.999999999');
insert into phrase(phrase, staff_id, last_used) values('container', 1, '2018-06-27 13:35:10.999999999');
insert into phrase(phrase, staff_id, last_used) values('transaction', 2, '2018-06-27 13:35:10.999999999');
insert into phrase(phrase, staff_id, last_used) values('transformer', 2, '2018-06-27 13:35:10.999999999');

