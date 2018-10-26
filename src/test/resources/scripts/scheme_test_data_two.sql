insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, is_deleted, is_completed, grading_id)
values('Scheme #1', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 1, 0, 1, 1);

insert into scheme_four_point(scheme_id, four_point_id) values(1, 1);

insert into four_point(name, threshold_3, threshold_4, threshold_5, staff_id, is_default, is_deleted, grading_id)
values('new grading', 60, 80, 95, 1, 0, 0, 1);