insert into organisation (name, is_deleted) values('Organisation #2', 0);
insert into faculty (name, org_id) values('Faculty #2', 2);
insert into department (name, fac_id) values('Department #2', 2);
insert into class (name, fac_id) values('Class #2', 2);

insert into user (name, surname, password, email) values('John','Emmerson','995fgpRd','john.bucket@gmail.com');
insert into user_role(user_id, role_id) VALUES (4, 2);
insert into student(stud_id, class_id, entrance_year) values(4, 1, 2019);

insert into user (name, surname, password, email) values('Emma','Barton','705irUwO','emma.barton@gmail.com');
insert into user_role(user_id, role_id) VALUES (5, 2);
insert into student(stud_id, class_id, entrance_year) values(5, 1, 2019);



insert into user (name, surname, password, email) values('Isaak','Jefferson','018oYEwq','isaak.jefferson@gmail.com');
insert into user_role(user_id, role_id) VALUES (6, 2);
insert into student(stud_id, class_id, entrance_year) values(6, 2, 2019);

insert into user (name, surname, password, email) values('Amanda','Robertson','715oTtwa','amanda.robertson@gmail.com');
insert into user_role(user_id, role_id) VALUES (7, 2);
insert into student(stud_id, class_id, entrance_year) values(7, 2, 2019);

insert into user (name, surname, password, email) values('Denis','Suarez','y15opLxa','denis.suarez@gmail.com');
insert into user_role(user_id, role_id) VALUES (8, 2);
insert into student(stud_id, class_id, entrance_year) values(8, 2, 2019);

insert into user (name, surname, password, email) values('Liza','Bucket','725OIysc','liza.soberano@gmail.com');
insert into user_role(user_id, role_id) VALUES (9, 2);
insert into student(stud_id, class_id, entrance_year) values(9, 2, 2019);








