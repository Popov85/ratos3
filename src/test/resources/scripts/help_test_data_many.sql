insert into help(name, text, staff_id) values('help name #1', 'Please, refer to schema #1', 1);
insert into help(name, text, staff_id) values('assist scheme #2', 'Please, refer to schema #2', 1);
insert into help(name, text, staff_id) values('assist scheme #3', 'Please, refer to schema #3', 1);
insert into help(name, text, staff_id) values('help name #4', 'Please, refer to schema #4', 2);
insert into help(name, text, staff_id) values('help name #5', 'Please, refer to schema #5', 2);
insert into help(name, text, staff_id) values('help name #6', 'Please, refer to schema #6', 2);
insert into help(name, text, staff_id) values('help name #7', 'Please, refer to schema #7', 2);

insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema03.jpg', 'Schema#3', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema04.jpg', 'Schema#4', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema05.jpg', 'Schema#5', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema06.jpg', 'Schema#6', 1);
insert into resource (hyperlink, description, staff_id) values('https://image.slidesharecdn.com/schema07.jpg', 'Schema#7', 1);

insert into help_resource(help_id, resource_id) values(1, 1);
insert into help_resource(help_id, resource_id) values(2, 2);
insert into help_resource(help_id, resource_id) values(3, 3);
insert into help_resource(help_id, resource_id) values(4, 4);
insert into help_resource(help_id, resource_id) values(5, 5);
insert into help_resource(help_id, resource_id) values(6, 6);
insert into help_resource(help_id, resource_id) values(7, 7);

