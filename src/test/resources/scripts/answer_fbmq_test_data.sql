insert into theme (name, course_id, created_by, access_id, belongs_to, created) values('Theme #1', 1, 1, 1, 1, CURRENT_TIMESTAMP);
insert into question (title, level, type_id, theme_id) values ('Question #1 Phrase', 2, 3, 1);

insert into phrase(phrase, staff_id, last_used) values('Phrase #1',1, '2018-07-18 10:30:15.999999999');
insert into phrase(phrase, staff_id, last_used) values('Phrase #2',1, '2018-07-18 10:32:17.999999999');
insert into phrase(phrase, staff_id, last_used) values('Phrase #3',1, '2018-07-18 10:32:19.999999999');