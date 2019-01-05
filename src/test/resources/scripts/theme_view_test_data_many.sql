insert into organisation (name) values('University #2');

insert into faculty (name, org_id) values('Programming faculty #2', 1);
insert into faculty (name, org_id) values('Medical faculty #1', 2);
insert into faculty (name, org_id) values('Pharmaceutical faculty #2', 2);

insert into department (name, fac_id) values('IT department #1', 1);
insert into department (name, fac_id) values('IT department #2', 1);
insert into department (name, fac_id) values('IT department #3', 2);
insert into department (name, fac_id) values('IT department #4', 2);

insert into department (name, fac_id) values('Med department #1', 3);
insert into department (name, fac_id) values('Med department #2', 3);
insert into department (name, fac_id) values('Pharm department #1', 4);
insert into department (name, fac_id) values('Pharm department #2', 4);

insert into user (name, surname, password, email) values('Dmitri','Smirnoff','855fgUwd','dmirti.smirnoff@gmail.com');
insert into user_role(user_id, role_id) VALUES (2, 6);
insert into staff (user_id, dep_id, pos_id) values(2, 2, 1);

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
insert into theme (name, course_id) values('Advanced unique IT theme #2', 1);
insert into theme (name, course_id) values('Advanced unique IT theme #3', 1);

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
insert into theme (name, course_id) values('Med theme special #9', 16);
insert into theme (name, course_id) values('Med theme #10', 17);

insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #1 (T1)', 1, 1, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #2 (T1)', 2, 1, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #3 (T1)', 3, 1, 1, 1);

insert into question (title, level, type_id, theme_id, lang_id) values ('FBS question #4 (T1)', 1, 2, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('FBS question #5 (T1)', 2, 2, 1, 1);

insert into question (title, level, type_id, theme_id, lang_id) values ('FBM question #6 (T1)', 1, 3, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('FBM question #7 (T1)', 3, 3, 1, 1);

insert into question (title, level, type_id, theme_id, lang_id) values ('Matcher question #8 (T2)', 1, 4, 2, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Matcher question #9 (T2)', 1, 4, 2, 1);

insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #10 (T3)', 2, 5, 3, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #11 (T3)', 2, 5, 3, 1);


insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #1 (T4)', 1, 1, 4, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #2 (T4)', 2, 1, 4, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #3 (T4)', 3, 1, 4, 1);

insert into question (title, level, type_id, theme_id, lang_id) values ('FBS question #4 (T4)', 1, 2, 4, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('FBS question #5 (T4)', 2, 2, 4, 1);

insert into question (title, level, type_id, theme_id, lang_id) values ('FBM question #6 (T4)', 1, 3, 4, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('FBM question #7 (T4)', 3, 3, 4, 1);

insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #1 (T20)', 1, 1, 20, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #2 (T20)', 2, 1, 20, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #3 (T20)', 3, 1, 20, 1);

insert into question (title, level, type_id, theme_id, lang_id) values ('FBS question #4 (T21)', 1, 2, 21, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('FBS question #5 (T21)', 2, 2, 21, 1);


