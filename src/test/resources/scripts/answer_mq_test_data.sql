insert into theme (name, course_id, created_by, access_id, belongs_to, created)
values ('Theme #1', 1, 1, 1, 1, CURRENT_TIMESTAMP);
insert into resource (hyperlink, description, staff_id, width, height, last_used)
values ('https://image.slidesharecdn.com/schema01.jpg', 'Schema#1', 1, 640, 480, '2020-07-18 11:45:15.999999999');
insert into resource (hyperlink, description, staff_id, width, height, last_used)
values ('https://image.slidesharecdn.com/schema02.jpg', 'Schema#2', 1, 640, 480, '2020-07-18 11:45:15.999999999');
insert into resource (hyperlink, description, staff_id, width, height, last_used)
values ('https://image.slidesharecdn.com/schema03.jpg', 'Schema#3', 1, 640, 480, '2020-07-18 11:45:15.999999999');
insert into question (title, level, type_id, theme_id)
values ('Question #1', 2, 4, 1);

insert into phrase(phrase, staff_id, last_used)
values ('left phrase', 1, '2020-07-18 11:45:15.999999999');
insert into phrase(phrase, staff_id, last_used)
values ('right phrase', 1, '2020-07-18 11:45:15.999999999');
insert into phrase(phrase, staff_id, last_used)
values ('right phrase updated', 1, '2020-07-18 11:45:15.999999999');



