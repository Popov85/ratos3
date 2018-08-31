delete from type_level;
delete from scheme_theme;
delete from theme;
delete from question_type;
delete from scheme;
delete from mode;
delete from settings;
delete from strategy;

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

ALTER TABLE scheme ALTER COLUMN scheme_id RESTART WITH 1;
ALTER TABLE mode ALTER COLUMN mode_id RESTART WITH 1;
ALTER TABLE settings ALTER COLUMN set_id RESTART WITH 1;
ALTER TABLE strategy ALTER COLUMN str_id RESTART WITH 1;
ALTER TABLE scheme_theme ALTER COLUMN scheme_theme_id RESTART WITH 1;
ALTER TABLE type_level ALTER COLUMN type_level_id RESTART WITH 1;

ALTER TABLE theme ALTER COLUMN theme_id RESTART WITH 1;




