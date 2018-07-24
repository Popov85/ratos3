insert into organisation (name) values('Open IT-university');
insert into faculty (name) values('Programming faculty');
insert into department (name, org_id, fac_id) values('Enterprise programming', 1, 1);
insert into role (name) values('Department instructor');
insert into position (name) values('Instructor');
insert into user (name, surname, password, email) values('Clara','Denis','hY45lKj4','clara.denis@gmail.com');
insert into staff (user_id, dep_id, pos_id, role_id) values(1, 1, 1, 1);

insert into accepted_phrase(phrase, staff_id, last_used) values('access', 1, '2018-07-18 10:30:15.999999999');
insert into accepted_phrase(phrase, staff_id, last_used) values('assert', 1, '2018-06-06 15:02:13.999999999');
insert into accepted_phrase(phrase, staff_id, last_used) values('implements', 1, '2018-06-20 12:59:10.999999999');
insert into accepted_phrase(phrase, staff_id, last_used) values('extends', 1, '2018-04-01 08:11:44.999999999');
insert into accepted_phrase(phrase, staff_id, last_used) values('container', 1, '2018-06-27 13:35:10.999999999');
insert into accepted_phrase(phrase, staff_id, last_used) values('transaction', 1, '2018-06-27 13:35:10.999999999');

