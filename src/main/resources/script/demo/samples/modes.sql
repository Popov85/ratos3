insert into department (name, fac_id) values('Department #2', 1);
insert into user (name, surname, password, email) values('Dmitri','Smirnoff','855fgUwd','dmirti.smirnoff@gmail.com');
insert into user_role(user_id, role_id) VALUES (4, 6);
insert into staff (staff_id, dep_id, pos_id) values(4, 2, 1);

insert into mode(name, staff_id, is_helpable, is_pyramid, is_skipable, is_rightans, is_resultdetails, is_pauseable, is_preservable, is_reportable, is_default)
values('exam/ee/1', 1, 0, 0, 0, 0, 0, 0, 0, 1, 1);
insert into mode(name, staff_id, is_helpable, is_pyramid, is_skipable, is_rightans, is_resultdetails, is_pauseable, is_preservable, is_reportable)
values('preparation/ee/2', 1, 0, 0, 0, 1, 0, 0, 0, 1);
insert into mode(name, staff_id, is_helpable, is_pyramid, is_skipable, is_rightans, is_resultdetails, is_pauseable, is_preservable, is_reportable)
values('presentation/ee/2', 1, 0, 0, 0, 0, 1, 0, 0, 1);

insert into mode(name, staff_id, is_helpable, is_pyramid, is_skipable, is_rightans, is_resultdetails, is_pauseable, is_preservable, is_reportable)
values('training/app/2', 4, 1, 1, 1, 1, 1, 1, 1, 1);
insert into mode(name, staff_id, is_helpable, is_pyramid, is_skipable, is_rightans, is_resultdetails, is_pauseable, is_preservable, is_reportable)
values('module/app/2', 4, 1, 1, 0, 1, 0, 1, 0, 1);
insert into mode(name, staff_id, is_helpable, is_pyramid, is_skipable, is_rightans, is_resultdetails, is_pauseable, is_preservable, is_reportable)
values('control/app/2', 4, 1, 1, 1, 0, 1, 0, 1, 1);
insert into mode(name, staff_id, is_helpable, is_pyramid, is_skipable, is_rightans, is_resultdetails, is_pauseable, is_preservable, is_reportable)
values('test/app/2', 4, 1, 1, 1, 1, 1, 1, 0, 0);




