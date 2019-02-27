insert into organisation (name) values('Университет');
insert into faculty (name, org_id) values('Факультет', 1);
insert into department (name, fac_id) values('Кафедра', 1);
insert into class (name, fac_id) values('Группа', 1);

insert into role (name) values('ROLE_OAUTH');
insert into role (name) values('ROLE_STUDENT');
insert into role (name) values('ROLE_INSTRUCTOR');
insert into role (name) values('ROLE_LAB-ASSISTANT');
insert into role (name) values('ROLE_DEP-ADMIN');
insert into role (name) values('ROLE_FAC-ADMIN');
insert into role (name) values('ROLE_ORG-ADMIN');
insert into role (name) values('ROLE_GLOBAL-ADMIN');

insert into position (name) values('Сис. админ');
insert into position (name) values('Декан');
insert into position (name) values('Зав. каф.');
insert into position (name) values('Профессор');
insert into position (name) values('Преподаватель');
insert into position (name) values('Исследователь');
insert into position (name) values('Аспирант');
insert into position (name) values('Лаборант');

insert into user (name, surname, password, email, is_active) values('Персонал','Персонал','{bcrypt}$2a$10$e.MY/qnalhvaoqI5QczLSuahfGbmthqd0QJh2NJ/38nB7LOZCW7d.','staff.staff@example.com', 1);
insert into user_role(user_id, role_id) VALUES (1, 8);
insert into staff (staff_id, dep_id, pos_id) values(1, 1, 1);

insert into user (name, surname, password, email, is_active) values('Студент','Студент','{bcrypt}$2a$10$e.MY/qnalhvaoqI5QczLSuahfGbmthqd0QJh2NJ/38nB7LOZCW7d.','student.student@example.com', 1);
insert into user_role(user_id, role_id) VALUES (2, 2);
insert into student(stud_id, class_id, fac_id, org_id, entrance_year) values(2, 1, 1, 1, 2019);

insert into language (name, eng_abbreviation) values('English', 'en');
insert into language (name, eng_abbreviation) values('українська', 'ua');
insert into language (name, eng_abbreviation) values('русский', 'ru');

insert into question_type (type_id, eng_abbreviation, description) values (1, 'ВМВ', 'Вопрос со множественным выбором');
insert into question_type (type_id, eng_abbreviation, description) values (2, 'ВЗОП', 'Вопрос на заполнение одного пропуска');
insert into question_type (type_id, eng_abbreviation, description) values (3, 'ВЗМП', 'Вопрос на заполнение множества пропусков');
insert into question_type (type_id, eng_abbreviation, description) values (4, 'ВС', 'Вопрос на сопоставление');
insert into question_type (type_id, eng_abbreviation, description) values (5, 'ВП', 'Вопрос на последовательность');

insert into complaint_type(type_id, name, description) values(1, 'Неверная постановка вопроса', 'Неверная или некоректная поставнка вопроса');
insert into complaint_type(type_id, name, description) values(2, 'Опечатка в вопросе', 'Опечатка или грамматическая ошибка в загловоке вопроса');
insert into complaint_type(type_id, name, description) values(3, 'Опечатка в ответе(ах)', 'Опечатка или грамматическая ошибка в одном или нескольких вариантах ответов');
insert into complaint_type(type_id, name, description) values(4, 'Ошибка в форматирования вопроса', 'Неверное выравнивание текста, не отображается мультимедиа, неправильное размещение фрагмента, пр.');
insert into complaint_type(type_id, name, description) values(5, 'Ошибка в форматирования ответа(ов)', 'Неверное выравнивание текста, не отображается мультимедиа, неправильное размещение фрагмента, пр.');
insert into complaint_type(type_id, name, description) values(6, 'Другое', 'Другой тип проблемы');

insert into access_level(name) values('каф.-приватный');
insert into access_level(name) values('приватный');

insert into strategy(name, description) values('по-умолчанию','Стратегия сортировки последовательсноти вопросов по умолчанию');
insert into strategy(name, description) values('случайная','Случайная стратегия сортировки последовательности вопросов');
insert into strategy(name, description) values('тип&уровень','Сортировка последовательсности вопросов по типу затем по уровню сложности');

insert into grading(name, description) values('4-х бальная', 'Классическая 4-х бальная шкала оценивания {2, 3, 4, 5}');
insert into grading(name, description) values('2-х бальная', 'Классическая 2-х бальная шкала оценивания {0, 1} или {зачтено, не зачтено}');
insert into grading(name, description) values('универсальная', 'Универсальная дискретная шкала оценивания {min, ..., max}');

insert into four_point(name, threshold_3, threshold_4, threshold_5, staff_id, is_default, is_deleted, grading_id)
    values('4-х бальная по-умолчанию', 50, 70, 85, 1, 1, 0, 1);
insert into two_point(name, threshold, staff_id, is_default, is_deleted, grading_id)
    values('2-х бальная по-умолчанию', 50, 1, 1, 0, 2);
insert into free_point(name, min_value, pass_value, max_value, staff_id, grading_id)
    values('ECTS', 0, 60, 200, 1, 3);
insert into free_point(name, min_value, pass_value, max_value, staff_id, grading_id)
    values('LMS', 0, 0.5, 1, 1, 3);

insert into settings(name, staff_id, seconds_per_question, strict_seconds_per_question, questions_per_sheet, days_keep_result_details, level_2_coefficient, level_3_coefficient, display_percent, display_mark, display_theme_results, display_question_results, is_deleted, is_default)
  values('по-умолчанию', 1, 60, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1);

insert into mode(name, staff_id, is_helpable, is_pyramid, is_skipable, is_rightans, is_preservable, is_reportable)
    values('экзамен', 1, 0, 0, 0, 0, 0, 1);
insert into mode(name, staff_id, is_helpable, is_pyramid, is_skipable, is_rightans, is_preservable, is_reportable)
    values('тренировка', 1, 1, 1, 1, 1, 1, 1);

insert into settings_fbq (name, words_limit, symbols_limit, is_numeric, is_typo_allowed, is_case_sensitive, lang_id, staff_id)
    values('eng по-умолчанию', 5, 100, 0, 0, 0, 1, 1);
insert into settings_fbq (name, words_limit, symbols_limit, is_numeric, is_typo_allowed, is_case_sensitive, lang_id, staff_id)
    values('ua по-умолчанию', 5, 100, 0, 0, 0, 2, 1);
insert into settings_fbq (name, words_limit, symbols_limit, is_numeric, is_typo_allowed, is_case_sensitive, lang_id, staff_id)
    values('ru по-умолчанию', 5, 100, 0, 0, 0, 3, 1);

