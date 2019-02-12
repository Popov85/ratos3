insert into scheme(name, is_active, strategy_id, settings_id, mode_id, grading_id, course_id, created, created_by, belongs_to, is_deleted, access_id)
    values('Sample scheme #1', 1, 1, 1, 1, 1, 1, '2019-08-01 10:30:15.999999999', 1, 1, 0, 1);

insert into theme (name, course_id, created_by, access_id) values('Sample theme #1 (All MCQ)', 1, 1, 1);
insert into theme (name, course_id, created_by, access_id) values('Sample theme #2 (All FBSQ)', 1, 1, 1);
insert into theme (name, course_id, created_by, access_id) values('Sample theme #3 (All FBMQ)', 1, 1, 1);
insert into theme (name, course_id, created_by, access_id) values('Sample theme #4 (All MQ)', 1, 1, 1);
insert into theme (name, course_id, created_by, access_id) values('Sample theme #5 (All SQ)', 1, 1, 1);

insert into scheme_theme(scheme_id, theme_id, theme_order) values(1, 1, 0);
insert into scheme_theme(scheme_id, theme_id, theme_order) values(1, 2, 1);
insert into scheme_theme(scheme_id, theme_id, theme_order) values(1, 3, 2);
insert into scheme_theme(scheme_id, theme_id, theme_order) values(1, 4, 3);
insert into scheme_theme(scheme_id, theme_id, theme_order) values(1, 5, 4);

insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(1, 1, 1, 0, 0);
insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(2, 2, 1, 0, 0);
insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(3, 3, 1, 0, 0);
insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(4, 4, 1, 0, 0);
insert into type_level(scheme_theme_id, type_id, level_1, level_2, level_3) values(5, 5, 1, 0, 0);