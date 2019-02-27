insert into theme (name, course_id, created_by, access_id, belongs_to, created) values('Theme #1', 1, 1, 1, 1, CURRENT_TIMESTAMP);
insert into theme (name, course_id, created_by, access_id, belongs_to, created) values('Theme #2', 1, 1, 1, 1, CURRENT_TIMESTAMP);
insert into theme (name, course_id, created_by, access_id, belongs_to, created) values('Theme #3', 1, 1, 1, 1, CURRENT_TIMESTAMP);

insert into question (title, level, type_id, theme_id, lang_id) values ('Multiple choice question #1', 1, 1, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Fill blank single question #1', 1, 2, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Fill blank multiple question #1', 2, 3, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Fill blank multiple question #2', 2, 3, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Fill blank multiple question #3', 2, 3, 1, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Matcher question #1', 2, 4, 2, 1);
insert into question (title, level, type_id, theme_id, lang_id) values ('Sequence question #1', 3, 5, 2, 1);
