INSERT INTO settings (name, seconds_per_question, strict_seconds_per_question, questions_per_sheet, days_keep_result_details, level_2_coefficient, level_3_coefficient, display_percent, display_mark, display_theme_results, display_question_results, is_deleted, is_default, created_by, belongs_to)
VALUES ('default2', 60, 0, 2, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1);
INSERT INTO mode (name, is_helpable, is_pyramid, is_skipable, is_rightans, is_preservable, is_reportable, is_default, created_by, belongs_to)
VALUES ('training2', 1, 0, 1, 1, 1, 1, 0, 1, 1);
INSERT INTO scheme (name, is_active, strategy_id, settings_id, mode_id, grading_id, course_id, created, created_by, belongs_to, is_deleted, access_id, options_id)
VALUES ('Sample scheme #1', 1, 1, 2, 3, 3, 1, CURRENT_TIMESTAMP, 1, 1, 0, 1, 2);
INSERT INTO scheme_free_point (scheme_id, free_point_id) VALUES (1, 2);
INSERT INTO theme (name, course_id, created_by, belongs_to, access_id) VALUES ('Sample theme #1', 1, 1, 1, 1);
INSERT INTO scheme_theme (scheme_id, theme_id, theme_order) VALUES (1, 1, 0);
INSERT INTO type_level (scheme_theme_id, type_id, level_1, level_2, level_3) VALUES (1, 5, 10, 0, 0);