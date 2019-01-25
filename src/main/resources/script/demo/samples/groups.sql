insert into groups(name, is_enabled, staff_id) values('Group #1', 1, 1);
insert into groups(name, is_enabled, staff_id) values('Group #2', 1, 1);
insert into groups(name, is_enabled, staff_id) values('Group #3', 1, 1);
insert into groups(name, is_enabled, staff_id) values('Group #4', 1, 1);
insert into groups(name, is_enabled, staff_id) values('Group #5', 1, 1);
insert into groups(name, is_enabled, staff_id) values('Group #6', 1, 1);
insert into groups(name, is_enabled, staff_id) values('Group #7', 1, 1);
insert into groups(name, is_enabled, staff_id) values('Group #8', 1, 1);
insert into groups(name, is_enabled, staff_id) values('Group #9', 1, 1);
insert into groups(name, is_enabled, staff_id) values('Group #10', 1, 1);

insert into user (name, surname, password, email, is_active) values('Student4','Student4','{bcrypt}$2a$10$e.MY/qnalhvaoqI5QczLSuahfGbmthqd0QJh2NJ/38nB7LOZCW7d.','student4@example.com', 1);
insert into user_role(user_id, role_id) VALUES (4, 2);
insert into student(stud_id, class_id, entrance_year) values(4, 1, 2019);

insert into user (name, surname, password, email, is_active) values('Student5','Student5','{bcrypt}$2a$10$e.MY/qnalhvaoqI5QczLSuahfGbmthqd0QJh2NJ/38nB7LOZCW7d.','student5@example.com', 1);
insert into user_role(user_id, role_id) VALUES (5, 2);
insert into student(stud_id, class_id, entrance_year) values(5, 1, 2019);

insert into user (name, surname, password, email, is_active) values('Student6','Student6','{bcrypt}$2a$10$e.MY/qnalhvaoqI5QczLSuahfGbmthqd0QJh2NJ/38nB7LOZCW7d.','student6@example.com', 1);
insert into user_role(user_id, role_id) VALUES (6, 2);
insert into student(stud_id, class_id, entrance_year) values(6, 1, 2019);

insert into user (name, surname, password, email, is_active) values('Student7','Student7','{bcrypt}$2a$10$e.MY/qnalhvaoqI5QczLSuahfGbmthqd0QJh2NJ/38nB7LOZCW7d.','student7@example.com', 1);
insert into user_role(user_id, role_id) VALUES (7, 2);
insert into student(stud_id, class_id, entrance_year) values(7, 1, 2019);

insert into user (name, surname, password, email, is_active) values('Student8','Student8','{bcrypt}$2a$10$e.MY/qnalhvaoqI5QczLSuahfGbmthqd0QJh2NJ/38nB7LOZCW7d.','student8@example.com', 1);
insert into user_role(user_id, role_id) VALUES (8, 2);
insert into student(stud_id, class_id, entrance_year) values(8, 1, 2019);

insert into user (name, surname, password, email, is_active) values('Student9','Student9','{bcrypt}$2a$10$e.MY/qnalhvaoqI5QczLSuahfGbmthqd0QJh2NJ/38nB7LOZCW7d.','student9@example.com', 1);
insert into user_role(user_id, role_id) VALUES (9, 2);
insert into student(stud_id, class_id, entrance_year) values(9, 1, 2019);

insert into user (name, surname, password, email, is_active) values('Student10','Student10','{bcrypt}$2a$10$e.MY/qnalhvaoqI5QczLSuahfGbmthqd0QJh2NJ/38nB7LOZCW7d.','student10@example.com', 1);
insert into user_role(user_id, role_id) VALUES (10, 2);
insert into student(stud_id, class_id, entrance_year) values(10, 1, 2019);

insert into user (name, surname, password, email, is_active) values('Student11','Student11','{bcrypt}$2a$10$e.MY/qnalhvaoqI5QczLSuahfGbmthqd0QJh2NJ/38nB7LOZCW7d.','student11@example.com', 1);
insert into user_role(user_id, role_id) VALUES (11, 2);
insert into student(stud_id, class_id, entrance_year) values(11, 1, 2019);

insert into student_group(stud_id, group_id) values(11, 1);
insert into student_group(stud_id, group_id) values(2, 1);
insert into student_group(stud_id, group_id) values(6, 1);
insert into student_group(stud_id, group_id) values(7, 1);
insert into student_group(stud_id, group_id) values(8, 1);

insert into student_group(stud_id, group_id) values(3, 2);
insert into student_group(stud_id, group_id) values(4, 2);
insert into student_group(stud_id, group_id) values(8, 2);
insert into student_group(stud_id, group_id) values(9, 2);
insert into student_group(stud_id, group_id) values(10, 2);

insert into student_group(stud_id, group_id) values(5, 3);
insert into student_group(stud_id, group_id) values(7, 3);
insert into student_group(stud_id, group_id) values(8, 3);
insert into student_group(stud_id, group_id) values(4, 3);
insert into student_group(stud_id, group_id) values(9, 3);

insert into student_group(stud_id, group_id) values(11, 4);
insert into student_group(stud_id, group_id) values(3, 4);
insert into student_group(stud_id, group_id) values(5, 4);
insert into student_group(stud_id, group_id) values(7, 4);
insert into student_group(stud_id, group_id) values(9, 4);

insert into student_group(stud_id, group_id) values(3, 5);
insert into student_group(stud_id, group_id) values(4, 5);
insert into student_group(stud_id, group_id) values(5, 5);
insert into student_group(stud_id, group_id) values(6, 5);
insert into student_group(stud_id, group_id) values(7, 5);

insert into student_group(stud_id, group_id) values(11, 6);
insert into student_group(stud_id, group_id) values(4, 6);
insert into student_group(stud_id, group_id) values(5, 6);
insert into student_group(stud_id, group_id) values(7, 6);
insert into student_group(stud_id, group_id) values(8, 6);

insert into student_group(stud_id, group_id) values(5, 7);
insert into student_group(stud_id, group_id) values(6, 7);
insert into student_group(stud_id, group_id) values(8, 7);
insert into student_group(stud_id, group_id) values(9, 7);
insert into student_group(stud_id, group_id) values(10, 7);

insert into student_group(stud_id, group_id) values(11, 8);
insert into student_group(stud_id, group_id) values(3, 8);
insert into student_group(stud_id, group_id) values(5, 8);
insert into student_group(stud_id, group_id) values(6, 8);
insert into student_group(stud_id, group_id) values(7, 8);

insert into student_group(stud_id, group_id) values(4, 9);
insert into student_group(stud_id, group_id) values(6, 9);
insert into student_group(stud_id, group_id) values(7, 9);
insert into student_group(stud_id, group_id) values(9, 9);
insert into student_group(stud_id, group_id) values(10, 9);

insert into student_group(stud_id, group_id) values(11, 10);
insert into student_group(stud_id, group_id) values(2, 10);
insert into student_group(stud_id, group_id) values(6, 10);
insert into student_group(stud_id, group_id) values(7, 10);
insert into student_group(stud_id, group_id) values(10, 10);