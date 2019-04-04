insert into department (name, fac_id) values('Department #2', 1);
insert into user (name, surname, password, email) values('Dmitri','Smirnoff','855fgUwd','dmirti.smirnoff@gmail.com');
insert into user_role(user_id, role_id) VALUES (4, 6);
insert into staff (staff_id, dep_id, pos_id) values(4, 2, 1);

insert into mode(name, is_helpable, is_pyramid, is_skipable, is_rightans, is_preservable, is_reportable, is_default, created_by, belongs_to)
values('exam/ee/1', 0, 0, 0, 0, 0, 0, 1, 1, 1);
insert into mode(name, is_helpable, is_pyramid, is_skipable, is_rightans, is_preservable, is_reportable, is_default, created_by, belongs_to)
values('preparation/ee/2', 0, 0, 0, 0, 1, 0, 0, 1, 1);
insert into mode(name, is_helpable, is_pyramid, is_skipable, is_rightans, is_preservable, is_reportable, is_default, created_by, belongs_to)
values('presentation/ee/2', 0, 0, 0, 0, 0, 1, 0, 1, 1);

insert into mode(name, is_helpable, is_pyramid, is_skipable, is_rightans, is_preservable, is_reportable, is_default, created_by, belongs_to)
values('training/app/2', 1, 1, 1, 1, 1, 1, 0, 4, 2);
insert into mode(name, is_helpable, is_pyramid, is_skipable, is_rightans, is_preservable, is_reportable, is_default, created_by, belongs_to)
values('module/app/2', 1, 1, 0, 1, 0, 1, 0, 4, 2);
insert into mode(name, is_helpable, is_pyramid, is_skipable, is_rightans, is_preservable, is_reportable, is_default, created_by, belongs_to)
values('control/app/2', 1, 1, 1, 0, 1, 1, 0, 4, 2);
insert into mode(name, is_helpable, is_pyramid, is_skipable, is_rightans, is_preservable, is_reportable, is_default, created_by, belongs_to)
values('test/app/2', 1, 1, 1, 1, 0, 0, 0, 4, 2);




