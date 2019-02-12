insert into organisation (name) values('University #2');
insert into faculty (name, org_id) values('Faculty #2', 2);
insert into faculty (name, org_id) values('Faculty #3', 2);
insert into department (name, fac_id) values('Department #2', 2);
insert into department (name, fac_id) values('Department #3', 3);

insert into user (name, surname, password, email) values('Dmitri','Smirnoff','855fgUwd','dmitri.smirnoff@gmail.com');
insert into user_role(user_id, role_id) VALUES (3, 6);
insert into staff (staff_id, dep_id, pos_id) values(3, 2, 1);

insert into user (name, surname, password, email) values('Semen','Fine','pOt67n21','semen.fine@gmail.com');
insert into user_role(user_id, role_id) VALUES (4, 6);
insert into staff (staff_id, dep_id, pos_id) values(4, 3, 1);

insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, belongs_to, is_deleted, grading_id, access_id)
    values('Scheme #1', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 1, 1, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, belongs_to, is_deleted,  grading_id, access_id)
    values('Scheme #2', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 1, 1, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, belongs_to, is_deleted,  grading_id, access_id)
    values('Scheme #3', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 1, 1, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, belongs_to, is_deleted,  grading_id, access_id)
    values('Scheme #4', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 1, 1, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, belongs_to, is_deleted,  grading_id, access_id)
    values('Scheme #5', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 1, 1, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, belongs_to, is_deleted,  grading_id, access_id)
    values('Scheme #6', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 1, 1, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, belongs_to, is_deleted,  grading_id, access_id)
    values('Scheme #7', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 1, 1, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, belongs_to, is_deleted,  grading_id, access_id)
    values('Scheme #8', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 1, 1, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, belongs_to, is_deleted,  grading_id, access_id)
    values('Scheme #9', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 1, 1, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, belongs_to, is_deleted,  grading_id, access_id)
    values('Scheme #10', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 1, 1, 0, 1, 1);

insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, belongs_to, is_deleted,  grading_id, access_id)
    values('Scheme #11', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 3, 2, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, belongs_to, is_deleted,  grading_id, access_id)
    values('Scheme #12', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 3, 2, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, belongs_to, is_deleted,  grading_id, access_id)
    values('Scheme #13', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 3, 2, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, belongs_to, is_deleted,  grading_id, access_id)
    values('Scheme #14', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 3, 2, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, belongs_to, is_deleted,  grading_id, access_id)
    values('Scheme #15', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 3, 2, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, belongs_to, is_deleted,  grading_id, access_id)
    values('Scheme #16', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 3, 2, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, belongs_to, is_deleted,  grading_id, access_id)
    values('Scheme #17', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 3, 2, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, belongs_to, is_deleted,  grading_id, access_id)
    values('Scheme #18', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 3, 2, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, belongs_to, is_deleted,  grading_id, access_id)
    values('Scheme #19', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 3, 2, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, belongs_to, is_deleted,  grading_id, access_id)
    values('Scheme #20', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 3, 2, 0, 1, 1);

insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, belongs_to, is_deleted,  grading_id, access_id)
    values('Scheme #21', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 4, 3, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, belongs_to, is_deleted,  grading_id, access_id)
    values('Scheme #22', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 4, 3, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, belongs_to, is_deleted,  grading_id, access_id)
    values('Scheme #23', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 4, 3, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, belongs_to, is_deleted,  grading_id, access_id)
    values('Scheme #24', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 4, 3, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, belongs_to, is_deleted,  grading_id, access_id)
    values('Scheme #25', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 4, 3, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, belongs_to, is_deleted,  grading_id, access_id)
    values('Scheme #26', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 4, 3, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, belongs_to, is_deleted,  grading_id, access_id)
    values('Scheme #27', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 4, 3, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, belongs_to, is_deleted,  grading_id, access_id)
    values('Scheme #28', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 4, 3, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, belongs_to, is_deleted,  grading_id, access_id)
    values('Scheme #29', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 4, 3, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, belongs_to, is_deleted,  grading_id, access_id)
    values('Scheme #30', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 4, 3, 0, 1, 1);



