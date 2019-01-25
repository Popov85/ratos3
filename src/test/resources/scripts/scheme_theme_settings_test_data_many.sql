insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, is_deleted, grading_id, access_id)
values('Scheme #1', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 1, 0, 1, 1);
insert into theme (name, course_id, created_by, access_id) values('Theme #1', 1, 1, 1);
insert into scheme_theme(scheme_id, theme_id, theme_order) values(1, 1, 0);

insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(1, 1, 20, 20, 20);
insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(1, 3, 20, 20, 20);
insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(1, 4, 20, 20, 20);
insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(1, 5, 20, 20, 20);






