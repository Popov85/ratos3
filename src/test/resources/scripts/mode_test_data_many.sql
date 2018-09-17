insert into organisation (name) values('Open IT-university');
insert into faculty (name, org_id) values('Programming faculty', 1);
insert into department (name, fac_id) values('Enterprise programming', 1);
insert into department (name, fac_id) values('Applied programming', 1);
insert into role (name) values('Department instructor');
insert into position (name) values('Instructor');
insert into user (name, surname, password, email) values('Clara','Denis','hY45lKj4','clara.denis@gmail.com');
insert into user (name, surname, password, email) values('Yuri','Smirnoff','5Pf1yrtE','yuri.smirnoff@gmail.com');
insert into staff (user_id, dep_id, pos_id, role_id) values(1, 1, 1, 1);
insert into staff (user_id, dep_id, pos_id, role_id) values(2, 2, 1, 1);

insert into mode(name, staff_id, is_helpable, is_pyramid, is_skipable, is_rightans, is_resultdetails, is_pauseable, is_preservable, is_reportable, is_default)
values('exam/ee/1', 1, 0, 0, 0, 0, 0, 0, 0, 1, 1);
insert into mode(name, staff_id, is_helpable, is_pyramid, is_skipable, is_rightans, is_resultdetails, is_pauseable, is_preservable, is_reportable)
values('preparation/ee/2', 1, 0, 0, 0, 1, 0, 0, 0, 1);
insert into mode(name, staff_id, is_helpable, is_pyramid, is_skipable, is_rightans, is_resultdetails, is_pauseable, is_preservable, is_reportable)
values('presentation/ee/2', 1, 0, 0, 0, 0, 1, 0, 0, 1);

insert into mode(name, staff_id, is_helpable, is_pyramid, is_skipable, is_rightans, is_resultdetails, is_pauseable, is_preservable, is_reportable)
values('training/app/2', 2, 1, 1, 1, 1, 1, 1, 1, 1);
insert into mode(name, staff_id, is_helpable, is_pyramid, is_skipable, is_rightans, is_resultdetails, is_pauseable, is_preservable, is_reportable)
values('module/app/2', 2, 1, 1, 0, 1, 0, 1, 0, 1);
insert into mode(name, staff_id, is_helpable, is_pyramid, is_skipable, is_rightans, is_resultdetails, is_pauseable, is_preservable, is_reportable)
values('control/app/2', 2, 1, 1, 1, 0, 1, 0, 1, 1);
insert into mode(name, staff_id, is_helpable, is_pyramid, is_skipable, is_rightans, is_resultdetails, is_pauseable, is_preservable, is_reportable)
values('test/app/2', 2, 1, 1, 1, 1, 1, 1, 0, 0);




