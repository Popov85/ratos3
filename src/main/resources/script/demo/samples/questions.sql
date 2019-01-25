insert into department (name, fac_id) values('IT department #2', 1);

insert into user (name, surname, password, email) values('Dmitri','Smirnoff','855fgUwd','dmirti.smirnoff@gmail.com');
insert into user_role(user_id, role_id) VALUES (4, 6);
insert into staff (staff_id, dep_id, pos_id) values(4, 2, 1);

insert into course (name, created, created_by, dep_id, access_id) values('IT course #1 (D1)', CURRENT_TIMESTAMP, 1, 1, 1);
insert into course (name, created, created_by, dep_id, access_id) values('Med course #1 (D2)', CURRENT_TIMESTAMP, 4, 2, 1);
insert into course (name, created, created_by, dep_id, access_id) values('Pharm course #1 (D2)', CURRENT_TIMESTAMP, 4, 2, 1);

insert into theme (name, course_id, created_by, access_id) values('IT theme #1', 1, 1, 1);
insert into theme (name, course_id, created_by, access_id) values('IT theme #2', 1, 1, 1);
insert into theme (name, course_id, created_by, access_id) values('IT theme #3 (advanced)', 1, 1, 1);
insert into theme (name, course_id, created_by, access_id) values('IT theme #4 (advanced)', 1, 1, 1);
insert into theme (name, course_id, created_by, access_id) values('IT theme #5', 1, 1, 1);
insert into theme (name, course_id, created_by, access_id) values('IT theme #6', 1, 1, 1);
insert into theme (name, course_id, created_by, access_id) values('IT theme #7', 2, 1, 1);
insert into theme (name, course_id, created_by, access_id) values('IT theme #8', 2, 1, 1);
insert into theme (name, course_id, created_by, access_id) values('IT theme #9', 2, 1, 1);
insert into theme (name, course_id, created_by, access_id) values('IT theme #10', 2, 1, 1);

insert into theme (name, course_id, created_by, access_id) values('Med theme #1 (required)', 3, 4, 1);
insert into theme (name, course_id, created_by, access_id) values('Med theme #2', 3, 4, 1);
insert into theme (name, course_id, created_by, access_id) values('Med theme #3 (required)', 3, 4, 1);
insert into theme (name, course_id, created_by, access_id) values('Med theme #4 (required)', 3, 4, 1);
insert into theme (name, course_id, created_by, access_id) values('Med theme #5', 3, 4, 1);
insert into theme (name, course_id, created_by, access_id) values('Med theme #6 (advanced)', 4, 4, 1);
insert into theme (name, course_id, created_by, access_id) values('Med theme #7', 4, 4, 1);
insert into theme (name, course_id, created_by, access_id) values('Med theme #8', 4, 4, 1);
insert into theme (name, course_id, created_by, access_id) values('Med theme #9 (advanced)', 4, 4, 1);
insert into theme (name, course_id, created_by, access_id) values('Med theme #10', 4, 4, 1);


insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #1 (T1)', 1, 1, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #2 (T1)', 2, 1, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #3 (T1)', 3, 1, 1, 1);

insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #1 (T1)', 1, 5, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #2 (T1)', 3, 5, 1, 1);

insert into question (title, level, type_id, theme_id, lang_id) values ('FBS question #4 (T2)', 2, 2, 2, 1);

insert into question (title, level, type_id, theme_id, lang_id) values ('FBS question #5 (T3)', 2, 2, 3, 1);

insert into question (title, level, type_id, theme_id, lang_id) values ('FBM question #6 (T4)', 1, 3, 4, 1);

insert into question (title, level, type_id, theme_id, lang_id) values ('FBM question #7 (T5)', 3, 3, 5, 1);

insert into question (title, level, type_id, theme_id, lang_id) values ('Matcher question #8 (T6)', 1, 4, 6, 1);

insert into question (title, level, type_id, theme_id, lang_id) values ('Matcher question #9 (T7)', 1, 4, 7, 1);

insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #10 (T8)', 2, 5, 8, 1);

insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #11 (T9)', 2, 5, 9, 1);

insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #1 (T10)', 1, 1, 10, 1);




insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #2 (T11)', 2, 1, 11, 1);

insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #3 (T12)', 3, 1, 12, 1);

insert into question (title, level, type_id, theme_id, lang_id) values ('FBS question #4 (T13)', 1, 2, 13, 1);

insert into question (title, level, type_id, theme_id, lang_id) values ('FBS question #5 (T14)', 2, 2, 14, 1);

insert into question (title, level, type_id, theme_id, lang_id) values ('FBM question #6 (T15)', 1, 3, 15, 1);

insert into question (title, level, type_id, theme_id, lang_id) values ('FBM question #7 (T16)', 3, 3, 16, 1);

insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #1 (T17)', 1, 1, 17, 1);

insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #2 (T18)', 2, 1, 18, 1);

insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #3 (T19)', 3, 1, 19, 1);





