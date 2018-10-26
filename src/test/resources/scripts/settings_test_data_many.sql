insert into department (name, fac_id) values('Department #2', 1);
insert into user (name, surname, password, email) values('Dmitri','Smirnoff','855fgUwd','dmirti.smirnoff@gmail.com');
insert into user_role(user_id, role_id) VALUES (2, 6);
insert into staff (user_id, dep_id, pos_id) values(2, 2, 1);

insert into settings(name, staff_id, seconds_per_question, strict_seconds_per_question, questions_per_sheet, days_keep_result_details, level_2_coefficient, level_3_coefficient, display_percent, display_mark, display_theme_results, is_deleted, is_default)
values('custom #1', 1, 60, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0);

insert into settings(name, staff_id, seconds_per_question, strict_seconds_per_question, questions_per_sheet, days_keep_result_details, level_2_coefficient, level_3_coefficient, display_percent, display_mark, display_theme_results, is_deleted, is_default)
values('custom #2', 1, 30, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0);
insert into settings(name, staff_id, seconds_per_question, strict_seconds_per_question, questions_per_sheet, days_keep_result_details, level_2_coefficient, level_3_coefficient, display_percent, display_mark, display_theme_results, is_deleted, is_default)
values('classic', 1, 120, 0, 1, 10, 1, 1, 1, 1, 0, 0, 0);

insert into settings(name, staff_id, seconds_per_question, strict_seconds_per_question, questions_per_sheet, days_keep_result_details, level_2_coefficient, level_3_coefficient, display_percent, display_mark, display_theme_results, is_deleted, is_default)
values('custom/year1', 2, 60, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0);

insert into settings(name, staff_id, seconds_per_question, strict_seconds_per_question, questions_per_sheet, days_keep_result_details, level_2_coefficient, level_3_coefficient, display_percent, display_mark, display_theme_results, is_deleted, is_default)
values('custom/year2', 2, 30, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0);

insert into settings(name, staff_id, seconds_per_question, strict_seconds_per_question, questions_per_sheet, days_keep_result_details, level_2_coefficient, level_3_coefficient, display_percent, display_mark, display_theme_results, is_deleted, is_default)
values('custom/foreign', 2, 60, 0, 2, 1, 1, 1, 1, 1, 0, 0, 0);




