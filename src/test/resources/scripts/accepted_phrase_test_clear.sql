delete from accepted_phrase;

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

ALTER TABLE accepted_phrase ALTER COLUMN phrase_id RESTART WITH 1;



