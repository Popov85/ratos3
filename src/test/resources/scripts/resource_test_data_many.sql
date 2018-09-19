insert into department (name, fac_id) values('Department #2', 1);
insert into user (name, surname, password, email) values('Yuri','Smirnoff','5Pf1yrtE','yuri.smirnoff@gmail.com');
insert into staff (user_id, dep_id, pos_id, role_id) values(2, 2, 1, 1);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema01.jpg', 'Schema#1', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema02.jpg', 'Schema#2', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema03.jpg', 'Schema#3', 2);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema04.jpg', 'Schema#4', 2);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema05.jpg', 'Schema#5', 2);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/image01.jpg', 'Image#1', 2);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/image02.jpg', 'Image#2', 2);
