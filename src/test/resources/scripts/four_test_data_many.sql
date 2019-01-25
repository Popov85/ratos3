insert into department (name, fac_id) values('Department #2', 1);
insert into user (name, surname, password, email) values('Dmitri','Smirnoff','855fgUwd','dmirti.smirnoff@gmail.com');
insert into user_role(user_id, role_id) VALUES (4, 6);
insert into staff (staff_id, dep_id, pos_id) values(4, 2, 1);

insert into four_point(name, threshold_3, threshold_4, threshold_5, is_default, is_deleted, grading_id, staff_id)
    values('Low grading (all courses)', 50, 60, 70, 0, 0, 1, 1);

insert into four_point(name, threshold_3, threshold_4, threshold_5, is_default, is_deleted, grading_id, staff_id)
    values('High grading (all courses)', 90, 95, 100, 0, 0, 1, 1);

insert into four_point(name, threshold_3, threshold_4, threshold_5, is_default, is_deleted, grading_id, staff_id)
    values('Standard year 1', 60, 75, 85, 0, 0, 1, 1);

insert into four_point(name, threshold_3, threshold_4, threshold_5, is_default, is_deleted, grading_id, staff_id)
    values('Standard year 5', 80, 90, 100, 0, 0, 1, 1);


insert into four_point(name, threshold_3, threshold_4, threshold_5, is_default, is_deleted, grading_id, staff_id)
    values('Strict', 80, 90, 95, 0, 0, 1, 4);

insert into four_point(name, threshold_3, threshold_4, threshold_5, is_default, is_deleted, grading_id, staff_id)
    values('Strict for interns', 80, 85, 90, 0, 0, 1, 4);

insert into four_point(name, threshold_3, threshold_4, threshold_5, is_default, is_deleted, grading_id, staff_id)
    values('Standard year 3', 55, 65, 75, 0, 0, 1, 4);

insert into four_point(name, threshold_3, threshold_4, threshold_5, is_default, is_deleted, grading_id, staff_id)
    values('Standard year 6', 75, 85, 95, 0, 0, 1, 4);


