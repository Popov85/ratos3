insert into theme (name, course_id, created_by, access_id) values('Theme #1', 1, 1, 1);

insert into phrase(phrase, staff_id, last_used) values ('accepted phrase #1', 1, CURRENT_TIMESTAMP);
insert into phrase(phrase, staff_id, last_used) values ('accepted phrase #2', 1, CURRENT_TIMESTAMP);
insert into phrase(phrase, staff_id, last_used) values ('accepted phrase #3', 1, CURRENT_TIMESTAMP);

insert into help (name, text, staff_id) values('Help name', 'Help text',  1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema01.jpg', 'Schema#1', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema02.jpg', 'Schema#2', 1);


