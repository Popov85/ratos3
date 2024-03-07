INSERT INTO scheme (name, is_active, strategy_id, settings_id, mode_id, grading_id, course_id, created, created_by,
                    belongs_to, is_deleted, access_id, options_id)
VALUES ('Sample scheme #1', 1, 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 1, 1, 0, 1, 2);
INSERT INTO scheme_four_point (scheme_id, four_point_id)
VALUES (1, 1);
INSERT INTO theme (name, course_id, created_by, belongs_to, access_id, created)
VALUES ('Sample theme #1', 1, 1, 1, 1, '2020-07-18 11:45:15.999999999');
INSERT INTO scheme_theme (scheme_id, theme_id, theme_order)
VALUES (1, 1, 0);
INSERT INTO type_level (scheme_theme_id, type_id, level_1, level_2, level_3)
VALUES (1, 5, 10, 0, 0);