insert into department (name, fac_id) values('Department #2', 1);
insert into department (name, fac_id) values('Department #3', 1);

insert into user (name, surname, password, email) values('Dmitri','Smirnoff','855fgUwd','dmitri.smirnoff@gmail.com');
insert into user_role(user_id, role_id) VALUES (4, 6);
insert into staff (staff_id, dep_id, pos_id) values(4, 2, 1);

insert into user (name, surname, password, email) values('Semen','Fine','pOt67n21','semen.fine@gmail.com');
insert into user_role(user_id, role_id) VALUES (5, 6);
insert into staff (staff_id, dep_id, pos_id) values(5, 3, 1);

insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, is_deleted, grading_id, access_id)
values('Scheme #1', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 1, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, is_deleted,  grading_id, access_id)
values('Scheme #2', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 1, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, is_deleted,  grading_id, access_id)
values('Scheme #3 for trainee', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 1, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, is_deleted,  grading_id, access_id)
values('Scheme #4', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 1, 0,  1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, is_deleted,  grading_id, access_id)
values('Scheme #5', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 1, 0,  1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, is_deleted,  grading_id, access_id)
values('Scheme #6 for trainee', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 1, 0,  1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, is_deleted,  grading_id, access_id)
values('Scheme #7', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 1, 0,  1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, is_deleted,  grading_id, access_id)
values('Scheme #8', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 1, 0,  1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, is_deleted,  grading_id, access_id)
values('Scheme #9 for trainee', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 1, 0,  1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, is_deleted,  grading_id, access_id)
values('Scheme #10 for trainee', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 1, 0, 1, 1);

insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, is_deleted,  grading_id, access_id)
values('Scheme #11 for exam', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 4, 0,  1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, is_deleted,  grading_id, access_id)
values('Scheme #12 for exam', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 4, 0,  1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, is_deleted,  grading_id, access_id)
values('Scheme #13 for exam', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 4, 0,  1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, is_deleted,  grading_id, access_id)
values('Scheme #14', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 4, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, is_deleted,  grading_id, access_id)
values('Scheme #15', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 4, 0, 1, 1);

insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, is_deleted,  grading_id, access_id)
values('Scheme #16', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 5, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, is_deleted,  grading_id, access_id)
values('Scheme #17', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 5, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, is_deleted,  grading_id, access_id)
values('Scheme #18', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 5, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, is_deleted,  grading_id, access_id)
values('Scheme #19', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 5, 0, 1, 1);
insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, is_deleted,  grading_id, access_id)
values('Scheme #20', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 5, 0, 1, 1);

insert into theme (name, course_id, created_by, access_id) values('Theme #1', 1, 1, 1);
insert into theme (name, course_id, created_by, access_id) values('Theme #2', 1, 1, 1);
insert into theme (name, course_id, created_by, access_id) values('Theme #3', 1, 1, 1);
insert into theme (name, course_id, created_by, access_id) values('Theme #4', 1, 1, 1);
insert into theme (name, course_id, created_by, access_id) values('Theme #5', 1, 1, 1);

insert into theme (name, course_id, created_by, access_id) values('Theme #6', 1, 1, 1);
insert into theme (name, course_id, created_by, access_id) values('Theme #7', 1, 1, 1);
insert into theme (name, course_id, created_by, access_id) values('Theme #8', 1, 1, 1);
insert into theme (name, course_id, created_by, access_id) values('Theme #9', 1, 1, 1);
insert into theme (name, course_id, created_by, access_id) values('Theme #10', 1, 1, 1);

insert into scheme_four_point(scheme_id, four_point_id) values(1, 1);


insert into scheme_theme(scheme_id, theme_id, theme_order) values(1, 1, 0);
insert into scheme_theme(scheme_id, theme_id, theme_order) values(1, 2, 1);
insert into scheme_theme(scheme_id, theme_id, theme_order) values(1, 3, 2);
insert into scheme_theme(scheme_id, theme_id, theme_order) values(1, 4, 3);
insert into scheme_theme(scheme_id, theme_id, theme_order) values(1, 5, 4);

insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(1, 1, 5, 0, 0);
insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(2, 2, 5, 0, 0);
insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(3, 3, 5, 0, 0);
insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(4, 4, 5, 0, 0);
insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(5, 5, 5, 0, 0);

insert into groups(name, is_enabled, staff_id) values('Group #1', 1, 1);
insert into groups(name, is_enabled, staff_id) values('Group #2', 1, 1);
insert into groups(name, is_enabled, staff_id) values('Group #3', 1, 1);
insert into groups(name, is_enabled, staff_id) values('Group #4', 1, 1);
insert into groups(name, is_enabled, staff_id) values('Group #5', 1, 1);

insert into groups(name, is_enabled, staff_id) values('Group #6', 1, 1);
insert into groups(name, is_enabled, staff_id) values('Group #7', 1, 1);
insert into groups(name, is_enabled, staff_id) values('Group #8', 1, 1);
insert into groups(name, is_enabled, staff_id) values('Group #9', 1, 1);
insert into groups(name, is_enabled, staff_id) values('Group #10', 1, 1);

insert into group_scheme(group_id, scheme_id) values(1, 1);
insert into group_scheme(group_id, scheme_id) values(2, 1);
insert into group_scheme(group_id, scheme_id) values(3, 1);
insert into group_scheme(group_id, scheme_id) values(4, 1);
insert into group_scheme(group_id, scheme_id) values(5, 1);



