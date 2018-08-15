delete from fbsq_phrase;
delete from accepted_phrase;
delete from help_resource;
delete from question_help;
delete from question_resource;
delete from answer_fbsq;
delete from settings_fbq;
delete from resource;

delete from help;
delete from question;
delete from theme;
delete from language;
delete from question_type;
delete from course;
delete from staff;
delete from user;
delete from position;
delete from role;
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
ALTER TABLE language ALTER COLUMN lang_id RESTART WITH 1;
ALTER TABLE theme ALTER COLUMN theme_id RESTART WITH 1;
ALTER TABLE question ALTER COLUMN question_id RESTART WITH 1;
ALTER TABLE help ALTER COLUMN help_id RESTART WITH 1;

ALTER TABLE resource ALTER COLUMN resource_id RESTART WITH 1;
ALTER TABLE accepted_phrase ALTER COLUMN phrase_id RESTART WITH 1;
ALTER TABLE settings_fbq ALTER COLUMN set_id RESTART WITH 1;
