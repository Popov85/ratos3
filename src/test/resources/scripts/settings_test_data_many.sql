insert into department (name, fac_id) values('Department #2', 1);
insert into user (name, surname, password, email) values('Dmitri','Smirnoff','855fgUwd','dmirti.smirnoff@gmail.com');
insert into user_role(user_id, role_id) VALUES (2, 6);
insert into staff (user_id, dep_id, pos_id) values(2, 2, 1);

insert into settings(name, staff_id, seconds_per_question, questions_per_sheet, days_keep_result_details, threshold_3, threshold_4, threshold_5, level_2_coefficient, level_3_coefficient, display_percent, display_mark)
values('custom #1', 1, 60, 1, 1, 79, 89, 99, 1, 1, 1, 1);
insert into settings(name, staff_id, seconds_per_question, questions_per_sheet, days_keep_result_details, threshold_3, threshold_4, threshold_5, level_2_coefficient, level_3_coefficient, display_percent, display_mark)
values('custom #2', 1, 60, 1, 1, 80, 90, 100, 1, 1, 1, 1);
insert into settings(name, staff_id, seconds_per_question, questions_per_sheet, days_keep_result_details, threshold_3, threshold_4, threshold_5, level_2_coefficient, level_3_coefficient, display_percent, display_mark)
values('classic', 1, 30, 1, 10, 80, 90, 100, 1, 1, 1, 1);

insert into settings(name, staff_id, seconds_per_question, questions_per_sheet, days_keep_result_details, threshold_3, threshold_4, threshold_5, level_2_coefficient, level_3_coefficient, display_percent, display_mark)
values('custom/year1', 2, 30, 1, 1, 50, 75, 95, 1, 1, 0, 0);
insert into settings(name, staff_id, seconds_per_question, questions_per_sheet, days_keep_result_details, threshold_3, threshold_4, threshold_5, level_2_coefficient, level_3_coefficient, display_percent, display_mark)
values('custom/year2', 2, 30, 1, 1, 50, 70, 100, 1, 1, 0, 0);
insert into settings(name, staff_id, seconds_per_question, questions_per_sheet, days_keep_result_details, threshold_3, threshold_4, threshold_5, level_2_coefficient, level_3_coefficient, display_percent, display_mark)
values('custom/foreign', 2, 30, 1, 1, 60, 70, 80, 1, 1, 0, 0);




