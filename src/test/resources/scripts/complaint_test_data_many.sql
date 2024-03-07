insert into department (name, fac_id) values('Department #2', 1);

insert into theme (name, course_id, created_by, access_id, belongs_to, created) values('Test theme #1', 1, 1, 1, 1, CURRENT_TIMESTAMP);

insert into question (title, level, type_id, theme_id) values ('Multiple choice question #1 T#1', 1, 1, 1);
insert into question (title, level, type_id, theme_id) values ('Multiple choice question #2 T#1', 1, 1, 1);
insert into question (title, level, type_id, theme_id) values ('Multiple choice question #3 T#1', 1, 1, 1);
insert into question (title, level, type_id, theme_id) values ('Multiple choice question #4 T#1', 1, 1, 1);
insert into question (title, level, type_id, theme_id) values ('Multiple choice question #5 T#1', 1, 1, 1);
insert into question (title, level, type_id, theme_id) values ('Multiple choice question #6 T#1', 1, 1, 1);
insert into question (title, level, type_id, theme_id) values ('Multiple choice question #7 T#1', 1, 1, 1);
insert into question (title, level, type_id, theme_id) values ('Multiple choice question #8 T#1', 1, 1, 1);
insert into question (title, level, type_id, theme_id) values ('Multiple choice question #9 T#1', 1, 1, 1);
insert into question (title, level, type_id, theme_id) values ('Multiple choice question #10 T#1', 1, 1, 1);

insert into complaint(question_id, dep_id, ctype_id, last_complained, times_complained) values(1, 1, 1, CURRENT_TIMESTAMP, 14);
insert into complaint(question_id, dep_id, ctype_id, last_complained, times_complained) values(1, 1, 2, CURRENT_TIMESTAMP, 71);
insert into complaint(question_id, dep_id, ctype_id, last_complained, times_complained) values(2, 1, 1, CURRENT_TIMESTAMP, 55);
insert into complaint(question_id, dep_id, ctype_id, last_complained, times_complained) values(3, 1, 5, CURRENT_TIMESTAMP, 96);
insert into complaint(question_id, dep_id, ctype_id, last_complained, times_complained) values(4, 1, 6, CURRENT_TIMESTAMP, 22);

insert into complaint(question_id, dep_id, ctype_id, last_complained, times_complained) values(5, 2, 1, CURRENT_TIMESTAMP, 1);
insert into complaint(question_id, dep_id, ctype_id, last_complained, times_complained) values(6, 2, 2, CURRENT_TIMESTAMP, 19);
insert into complaint(question_id, dep_id, ctype_id, last_complained, times_complained) values(6, 2, 1, CURRENT_TIMESTAMP, 8);
insert into complaint(question_id, dep_id, ctype_id, last_complained, times_complained) values(7, 2, 5, CURRENT_TIMESTAMP, 44);
insert into complaint(question_id, dep_id, ctype_id, last_complained, times_complained) values(8,  2, 1, CURRENT_TIMESTAMP, 112);


