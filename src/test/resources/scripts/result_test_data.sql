insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, is_deleted, is_completed, grading_id)
values('Scheme #1', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 1, 0, 1, 1);
insert into scheme_four_point(scheme_id, four_point_id) values(1, 1);

insert into theme(name, is_deleted, course_id) values('Theme#1', 0, 1);
insert into theme(name, is_deleted, course_id) values('Theme#2', 0, 1);
insert into theme(name, is_deleted, course_id) values('Theme#3', 0, 1);
insert into theme(name, is_deleted, course_id) values('Theme#4', 0, 1);

insert into scheme_theme(scheme_id, theme_id, theme_order) values(1, 1, 0);
insert into scheme_theme(scheme_id, theme_id, theme_order) values(1, 2, 1);
insert into scheme_theme(scheme_id, theme_id, theme_order) values(1, 3, 2);
insert into scheme_theme(scheme_id, theme_id, theme_order) values(1, 4, 3);

insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(1, 1, 5, 0, 0);
insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(2, 1, 5, 0, 0);
insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(3, 1, 5, 0, 0);
insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(4, 1, 5, 0, 0);