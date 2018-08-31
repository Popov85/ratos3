insert into organisation (name) values('Open IT-university');
insert into faculty (name, org_id) values('Programming faculty', 1);
insert into department (name, fac_id) values('Enterprise programming', 1);
insert into role (name) values('Department instructor');
insert into position (name) values('Instructor');
insert into user (name, surname, password, email) values('Clara','Denis','hY45lKj4','clara.denis@gmail.com');
insert into staff (user_id, dep_id, pos_id, role_id) values(1, 1, 1, 1);
insert into course (name, created, created_by, dep_id) values('Java for web developers', CURRENT_TIMESTAMP, 1, 1);
insert into question_type (type_id, eng_abbreviation, description) values (4, 'MQ', 'Matcher question');
insert into language (name, eng_abbreviation) values('English', 'en');
insert into theme (name, course_id) values('Java persistence', 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema01.jpg', 'Schema#1', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema02.jpg', 'Schema#2', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema03.jpg', 'Schema#3', 1);

insert into question (title, level, type_id, theme_id, lang_id) values ('Match the following concepts', 2, 4, 1, 1);



