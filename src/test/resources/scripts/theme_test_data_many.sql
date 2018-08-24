insert into organisation (name) values('Open IT-university');
insert into organisation (name) values('Medical university');

insert into faculty (name) values('Programming faculty #1');
insert into faculty (name) values('Programming faculty #2');
insert into faculty (name) values('Medical faculty #1');
insert into faculty (name) values('Pharmaceutical faculty #2');

insert into department (name, org_id, fac_id) values('IT department #1', 1, 1);
insert into department (name, org_id, fac_id) values('IT department #2', 1, 1);
insert into department (name, org_id, fac_id) values('IT department #3', 1, 2);
insert into department (name, org_id, fac_id) values('IT department #4', 1, 2);

insert into department (name, org_id, fac_id) values('Med department #1', 2, 3);
insert into department (name, org_id, fac_id) values('Med department #2', 2, 3);
insert into department (name, org_id, fac_id) values('Pharm department #1', 2, 4);
insert into department (name, org_id, fac_id) values('Pharm department #2', 2, 4);

insert into role (name) values('Department instructor');
insert into position (name) values('Instructor');
insert into user (name, surname, password, email) values('Clara','Denis','hY45lKj4','clara.denis@gmail.com');
insert into user (name, surname, password, email) values('Yuri','Smirnoff','5Pf1yrtE','yuri.smirnoff@gmail.com');
insert into staff (user_id, dep_id, pos_id, role_id) values(1, 1, 1, 1);
insert into staff (user_id, dep_id, pos_id, role_id) values(2, 2, 1, 1);

insert into course (name, created, created_by, dep_id) values('IT course #1 (D1)', CURRENT_TIMESTAMP, 1, 1);
insert into course (name, created, created_by, dep_id) values('IT course #2 (D1)', CURRENT_TIMESTAMP, 1, 1);
insert into course (name, created, created_by, dep_id) values('IT course #3 (D2)', CURRENT_TIMESTAMP, 1, 1);

insert into course (name, created, created_by, dep_id) values('IT course #1 (D2)', CURRENT_TIMESTAMP, 1, 2);
insert into course (name, created, created_by, dep_id) values('IT course #2 (D2)', CURRENT_TIMESTAMP, 1, 2);

insert into course (name, created, created_by, dep_id) values('IT course #1 (D3)', CURRENT_TIMESTAMP, 1, 3);
insert into course (name, created, created_by, dep_id) values('IT course #2 (D3)', CURRENT_TIMESTAMP, 1, 3);

insert into course (name, created, created_by, dep_id) values('IT course #1 (D4)', CURRENT_TIMESTAMP, 1, 4);
insert into course (name, created, created_by, dep_id) values('IT course #2 (D4)', CURRENT_TIMESTAMP, 1, 4);


insert into course (name, created, created_by, dep_id) values('Med course #1 (D1)', CURRENT_TIMESTAMP, 2, 5);
insert into course (name, created, created_by, dep_id) values('Med course #2 (D1)', CURRENT_TIMESTAMP, 2, 5);

insert into course (name, created, created_by, dep_id) values('Med course #1 (D2)', CURRENT_TIMESTAMP, 2, 6);
insert into course (name, created, created_by, dep_id) values('Med course #2 (D2)', CURRENT_TIMESTAMP, 2, 6);

insert into course (name, created, created_by, dep_id) values('Pharm course #1 (D3)', CURRENT_TIMESTAMP, 2, 7);
insert into course (name, created, created_by, dep_id) values('Pharm course #2 (D3)', CURRENT_TIMESTAMP, 2, 7);

insert into course (name, created, created_by, dep_id) values('Pharm course #1 (D4)', CURRENT_TIMESTAMP, 2, 8);
insert into course (name, created, created_by, dep_id) values('Pharm course #2 (D4)', CURRENT_TIMESTAMP, 2, 8);

insert into theme (name, course_id) values('IT theme #1', 1);
insert into theme (name, course_id) values('IT theme #2', 1);
insert into theme (name, course_id) values('IT theme #3', 1);

insert into theme (name, course_id) values('IT theme #4', 2);
insert into theme (name, course_id) values('IT theme #5', 3);
insert into theme (name, course_id) values('IT theme #6', 4);
insert into theme (name, course_id) values('IT theme #7', 5);
insert into theme (name, course_id) values('IT theme #8', 6);
insert into theme (name, course_id) values('IT theme #9', 7);
insert into theme (name, course_id) values('IT theme #10', 8);
insert into theme (name, course_id) values('IT theme #11', 9);


insert into theme (name, course_id) values('Med theme #1', 10);
insert into theme (name, course_id) values('Med theme #2', 10);
insert into theme (name, course_id) values('Med theme #3', 10);

insert into theme (name, course_id) values('Med theme #4', 11);
insert into theme (name, course_id) values('Med theme #5', 12);
insert into theme (name, course_id) values('Med theme #6', 13);
insert into theme (name, course_id) values('Med theme #7', 14);
insert into theme (name, course_id) values('Med theme #8', 15);
insert into theme (name, course_id) values('Med theme #9', 16);
insert into theme (name, course_id) values('Med theme #10', 17);



