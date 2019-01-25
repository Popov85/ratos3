insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, is_deleted, grading_id, access_id)
values('Scheme #1', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 1, 0, 1, 1);
insert into theme (name, course_id, created_by, access_id) values('Theme #1', 1, 1, 1);
insert into scheme_theme(scheme_id, theme_id, theme_order) values(1, 1, 0);






