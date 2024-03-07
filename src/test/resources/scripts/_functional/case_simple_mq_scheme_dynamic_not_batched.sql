insert into mode(name, is_helpable, is_pyramid, is_skipable, is_rightans, is_preservable, is_reportable, is_default,
                 created_by, belongs_to)
values ('training2', 1, 0, 1, 1, 1, 1, 0, 1, 1);

insert into scheme(name, is_active, strategy_id, settings_id, mode_id, grading_id, course_id, created, created_by,
                   belongs_to, is_deleted, access_id, options_id)
values ('Sample scheme #1', 1, 1, 1, 3, 2, 1, CURRENT_TIMESTAMP, 1, 1, 0, 1, 2);

insert into scheme_two_point(scheme_id, two_point_id)
values (1, 1);

insert into theme (name, course_id, created_by, belongs_to, access_id, created)
values ('Sample theme #1', 1, 1, 1, 1, '2020-07-18 11:45:15.999999999');

insert into scheme_theme(scheme_id, theme_id, theme_order)
values (1, 1, 0);

insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3)
values (1, 4, 10, 0, 0);