insert into organisation (name) values('Université');
insert into faculty (name, org_id) values('Faculté', 1);
insert into department (name, fac_id) values('Département', 1);
insert into class (name, fac_id) values('Classe', 1);

insert into role (name) values('ROLE_OAUTH');
insert into role (name) values('ROLE_STUDENT');
insert into role (name) values('ROLE_INSTRUCTOR');
insert into role (name) values('ROLE_LAB-ASSISTANT');
insert into role (name) values('ROLE_DEP-ADMIN');
insert into role (name) values('ROLE_FAC-ADMIN');
insert into role (name) values('ROLE_ORG-ADMIN');
insert into role (name) values('ROLE_GLOBAL-ADMIN');

insert into position (name) values('Administrateur système');
insert into position (name) values('Doyen');
insert into position (name) values('Chef');
insert into position (name) values('Professeur');
insert into position (name) values('Assistant');
insert into position (name) values('Chercheur');
insert into position (name) values('Étudiant');
insert into position (name) values('Laborantin');

insert into user (name, surname, password, email, is_active) values('Personnel','Personnel','{bcrypt}$2a$10$e.MY/qnalhvaoqI5QczLSuahfGbmthqd0QJh2NJ/38nB7LOZCW7d.','personnel.personnel@example.com', 1);
insert into user_role(user_id, role_id) VALUES (1, 8);
insert into staff (staff_id, dep_id, pos_id) values(1, 1, 1);

insert into user (name, surname, password, email, is_active) values('Élève','Élève','{bcrypt}$2a$10$e.MY/qnalhvaoqI5QczLSuahfGbmthqd0QJh2NJ/38nB7LOZCW7d.','student.student@example.com', 1);
insert into user_role(user_id, role_id) VALUES (2, 2);
insert into student(stud_id, class_id, fac_id, org_id, entrance_year) values(2, 1, 1, 1, 2018);

insert into language (name, eng_abbreviation) values('English', 'en');
insert into language (name, eng_abbreviation) values('français', 'fr');

insert into question_type (type_id, eng_abbreviation, description) values (1, 'MCQ', 'Question à choix multiples');
insert into question_type (type_id, eng_abbreviation, description) values (2, 'FBSQ', 'Remplir à blanc seule question');
insert into question_type (type_id, eng_abbreviation, description) values (3, 'FBMQ', 'Remplir à blanc plusieurs question');
insert into question_type (type_id, eng_abbreviation, description) values (4, 'MQ', 'Matcher question');
insert into question_type (type_id, eng_abbreviation, description) values (5, 'SQ', 'Séquence question');

insert into complaint_type(type_id, name, description) values(1, 'Mauvaise déclaration', 'Déclaration de question incorrecte');
insert into complaint_type(type_id, name, description) values(2, 'Typo en question', 'Typo en question, erreur grammaticale');
insert into complaint_type(type_id, name, description) values(3, 'Typo en réponse(s)', 'Typo dans une ou plusieurs réponse(s)');
insert into complaint_type(type_id, name, description) values(4, 'Mauvais format de question', 'Mauvais format de question: alignement, support, positionnement, etc.');
insert into complaint_type(type_id, name, description) values(5, 'Mauvaise réponse(s) formatée', 'Formats de réponse(s) incorrect(s): alignement, support, positionnement, etc.');
insert into complaint_type(type_id, name, description) values(6, 'Autre', 'Un autre type de erreur sans nom.');

insert into access_level(name) values('dep-privé');
insert into access_level(name) values('privé');

insert into strategy(name, description)
    values('défaut','Stratégie de tri de séquence par défaut');
insert into strategy(name, description)
    values('au hasard','Stratégie de tri séquentiel aléatoire');
insert into strategy(name, description)
    values('type&niveau','Stratégie de tri séquentiel types&niveaux');

insert into grading(name, description) values('quatre-points', 'système de classement classique à 4 points {2, 3, 4, 5}');
insert into grading(name, description) values('deux-points', 'système de classement classique à 2 points {0, 1} or {passé, ou pas passé}');
insert into grading(name, description) values('libre-points', 'système de classement discret universel {min, ..., max}');

insert into four_point(name, threshold_3, threshold_4, threshold_5, staff_id, is_default, is_deleted, grading_id)
    values('défaut', 50, 70, 85, 1, 1, 0, 1);
insert into two_point(name, threshold, staff_id, is_default, is_deleted, grading_id)
    values('défaut', 50, 1, 1, 0, 2);
insert into free_point(name, min_value, pass_value, max_value, staff_id, grading_id)
    values('ECTS', 0, 60, 200, 1, 3);
insert into free_point(name, min_value, pass_value, max_value, staff_id, grading_id)
    values('LMS', 0, 0.5, 1, 1, 3);

insert into settings(name, staff_id, seconds_per_question, strict_seconds_per_question, questions_per_sheet, days_keep_result_details, level_2_coefficient, level_3_coefficient, display_percent, display_mark, display_theme_results, display_question_results, is_deleted, is_default)
    values('défaut', 1, 60, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1);
insert into mode(name, staff_id, is_helpable, is_pyramid, is_skipable, is_rightans, is_preservable, is_reportable)
    values('examen', 1, 0, 0, 0, 0, 0, 1);
insert into mode(name, staff_id, is_helpable, is_pyramid, is_skipable, is_rightans, is_preservable, is_reportable)
    values('entraînement', 1, 1, 1, 1, 1, 1, 1);

insert into settings_fbq (name, words_limit, symbols_limit, is_numeric, is_typo_allowed, is_case_sensitive, lang_id, staff_id)
    values('eng défaut', 5, 100, 0, 0, 0, 1, 1);
insert into settings_fbq (name, words_limit, symbols_limit, is_numeric, is_typo_allowed, is_case_sensitive, lang_id, staff_id)
    values('fr défaut', 5, 100, 0, 0, 0, 1, 1);