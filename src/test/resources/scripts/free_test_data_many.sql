insert into department (name, fac_id) values('Department #2', 1);
insert into user (name, surname, password, email) values('Dmitri','Smirnoff','855fgUwd','dmirti.smirnoff@gmail.com');
insert into user_role(user_id, role_id) VALUES (4, 6);
insert into staff (staff_id, dep_id, pos_id) values(4, 2, 1);

insert into free_point(name, min_value, pass_value, max_value, grading_id, staff_id)
    values('Low grading (all courses)', 0, 100, 200, 3, 1);

insert into free_point(name, min_value, pass_value, max_value, grading_id, staff_id)
    values('High grading (all courses)', 0, 150, 200, 3, 1);

insert into free_point(name, min_value, pass_value, max_value, grading_id, staff_id)
    values('Standard year 1', 0, 90, 200, 3, 1);

insert into free_point(name, min_value, pass_value, max_value, grading_id, staff_id)
    values('Standard year 5', 0, 140, 200, 3, 1);


insert into free_point(name, min_value, pass_value, max_value, grading_id, staff_id)
    values('Strict', 0, 180, 200, 3, 4);

insert into free_point(name, min_value, pass_value, max_value, grading_id, staff_id)
    values('Strict for interns', 0, 170, 200, 3, 4);

insert into free_point(name, min_value, pass_value, max_value, grading_id, staff_id)
    values('Standard year 3', 0, 90, 200, 3, 4);

insert into free_point(name, min_value, pass_value, max_value, grading_id, staff_id)
    values('Standard year 6', 0, 140, 200, 3, 4);




