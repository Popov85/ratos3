delete from help_resource;
delete from help;
delete from resource;
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

ALTER TABLE organisation AUTO_INCREMENT = 1;
ALTER TABLE faculty AUTO_INCREMENT = 1;
ALTER TABLE department AUTO_INCREMENT = 1;
ALTER TABLE role AUTO_INCREMENT = 1;
ALTER TABLE position AUTO_INCREMENT = 1;
ALTER TABLE user AUTO_INCREMENT = 1;
ALTER TABLE staff AUTO_INCREMENT = 1;
ALTER TABLE course AUTO_INCREMENT = 1;
ALTER TABLE language AUTO_INCREMENT = 1;
ALTER TABLE theme AUTO_INCREMENT = 1;
ALTER TABLE help AUTO_INCREMENT = 1;
ALTER TABLE resource AUTO_INCREMENT = 1;



