delete from answer_mcq_resource;
delete from help_resource;
delete from question_resource;
delete from fbsq_phrase;
delete from fbmq_phrase;
delete from phrase_resource;
delete from question_help;
delete from resource;
delete from help;
delete from answer_mcq;
delete from answer_fbsq;
delete from answer_fbmq;
delete from answer_mq;
delete from answer_sq;
delete from settings_fbq;
delete from phrase;
delete from question;
delete from language;

delete from type_level;
delete from scheme_theme;
delete from theme;
delete from question_type;
delete from scheme_four_point;
delete from scheme_two_point;
delete from scheme_free_point;
delete from scheme;
delete from mode;
delete from settings;
delete from strategy;
delete from four_point;
delete from two_point;
delete from free_point;
delete from grading;

delete from course;
delete from staff;
delete from user_role;
delete from user;
delete from role;
delete from position;
delete from department;
delete from faculty;
delete from organisation;

ALTER TABLE organisation ALTER COLUMN org_id RESTART WITH 1;
ALTER TABLE faculty ALTER COLUMN fac_id RESTART WITH 1;
ALTER TABLE department ALTER COLUMN dep_id RESTART WITH 1;
ALTER TABLE role ALTER COLUMN role_id RESTART WITH 1;
ALTER TABLE position ALTER COLUMN pos_id RESTART WITH 1;
ALTER TABLE user ALTER COLUMN user_id RESTART WITH 1;
ALTER TABLE staff ALTER COLUMN staff_id RESTART WITH 1;
ALTER TABLE course ALTER COLUMN course_id RESTART WITH 1;
ALTER TABLE scheme ALTER COLUMN scheme_id RESTART WITH 1;
ALTER TABLE mode ALTER COLUMN mode_id RESTART WITH 1;
ALTER TABLE settings ALTER COLUMN set_id RESTART WITH 1;
ALTER TABLE strategy ALTER COLUMN str_id RESTART WITH 1;
ALTER TABLE grading ALTER COLUMN grading_id RESTART WITH 1;
ALTER TABLE four_point ALTER COLUMN four_id RESTART WITH 1;
ALTER TABLE two_point ALTER COLUMN two_id RESTART WITH 1;
ALTER TABLE free_point ALTER COLUMN free_id RESTART WITH 1;

ALTER TABLE scheme_theme ALTER COLUMN scheme_theme_id RESTART WITH 1;
ALTER TABLE type_level ALTER COLUMN type_level_id RESTART WITH 1;
ALTER TABLE theme ALTER COLUMN theme_id RESTART WITH 1;

ALTER TABLE question ALTER COLUMN question_id RESTART WITH 1;
ALTER TABLE help ALTER COLUMN help_id RESTART WITH 1;
ALTER TABLE resource ALTER COLUMN resource_id RESTART WITH 1;
ALTER TABLE language ALTER COLUMN lang_id RESTART WITH 1;
ALTER TABLE settings_fbq ALTER COLUMN set_id RESTART WITH 1;
ALTER TABLE answer_mcq ALTER COLUMN answer_id RESTART WITH 1;
ALTER TABLE answer_fbmq ALTER COLUMN answer_id RESTART WITH 1;
ALTER TABLE phrase ALTER COLUMN phrase_id RESTART WITH 1;
ALTER TABLE answer_mq ALTER COLUMN answer_id RESTART WITH 1;
ALTER TABLE answer_sq ALTER COLUMN answer_id RESTART WITH 1;



