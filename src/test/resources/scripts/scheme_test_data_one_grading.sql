insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, is_deleted, grading_id, access_id)
values('Scheme #1', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 1, 0, 3, 1);

insert into scheme_free_point(scheme_id, free_point_id) values(1, 1);