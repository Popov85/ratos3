insert into theme (name, course_id) values('Theme #1', 1);
insert into question(title, level, type_id, theme_id, lang_id, is_deleted) values('Question #1', 1, 2, 1, 1, 0);
insert into answer_fbsq(answer_id, set_id) values(1, 1);
insert into phrase(phrase, staff_id, last_used) values('Phrase #1',1, '2018-07-18 10:30:15.999999999');
insert into phrase(phrase, staff_id, last_used) values('Phrase #2',1, '2018-07-18 10:32:17.999999999');
insert into phrase(phrase, staff_id, last_used) values('Phrase #3',1, '2018-07-18 10:32:19.999999999');
insert into fbsq_phrase(phrase_id, answer_id) values(1, 1);
insert into fbsq_phrase(phrase_id, answer_id) values(2, 1);
