insert into organisation (name) values('Open IT-university');
insert into faculty (name) values('Programming faculty');
insert into department (name, org_id, fac_id) values('Enterprise programming', 1, 1);
insert into role (name) values('Department instructor');
insert into position (name) values('Instructor');
insert into user (name, surname, password, email) values('Clara','Denis','hY45lKj4','clara.denis@gmail.com');
insert into staff (user_id, dep_id, pos_id, role_id) values(1, 1, 1, 1);
insert into course (name, created, created_by, dep_id) values('Java for Professionals', CURRENT_TIMESTAMP, 1, 1);
insert into question_type (type_id, eng_abbreviation, description) values (5, 'SQ', 'Sequence question');
insert into language (name, eng_abbreviation) values('English', 'en');
insert into theme (name, course_id) values('Maven', 1);

insert into help (name, text, staff_id) values('Maven doc', 'See https://maven.apache.org/guides/',  1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema01.jpg', 'Stage#1', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema02.jpg', 'Stage#2', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema03.jpg', 'Stage#3', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema04.jpg', 'Stage#4', 1);

