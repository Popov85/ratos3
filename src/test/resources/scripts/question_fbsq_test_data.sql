insert into theme (name, course_id, created_by, access_id, belongs_to, created)
values ('Theme #1', 1, 1, 1, 1, CURRENT_TIMESTAMP);

insert into phrase(phrase, staff_id, last_used)
values ('accepted phrase #1', 1, '2020-07-18 11:45:15.999999999');
insert into phrase(phrase, staff_id, last_used)
values ('accepted phrase #2', 1, '2020-07-18 11:45:15.999999999');
insert into phrase(phrase, staff_id, last_used)
values ('accepted phrase #3', 1, '2020-07-18 11:45:15.999999999');

insert into help (name, text, staff_id)
values ('Help name', 'Help text', 1);
insert into resource (hyperlink, description, staff_id, width, height, last_used)
values ('https://image.slidesharecdn.com/schema01.jpg', 'Schema#1', 1, 640, 480, '2020-07-18 11:45:15.999999999');


