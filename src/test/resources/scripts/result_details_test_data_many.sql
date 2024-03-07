insert into scheme(name, is_active, strategy_id, settings_id, mode_id, course_id, created, created_by, belongs_to, is_deleted,  grading_id, access_id, options_id)
    values('Scheme #1', 1, 1, 1, 1, 1, CURRENT_TIMESTAMP, 1, 1, 0,  1, 1, 1);

insert into result(scheme_id, user_id, dep_id, percent, grade, is_passed, session_ended, session_lasted, is_timeouted, lms_id)
    values(1, 2, 1, 90, 5, 1, '2018-12-20 13:35:07.999999999', 400, 0, 1);
insert into result(scheme_id, user_id, dep_id, percent, grade, is_passed, session_ended, session_lasted, is_timeouted, lms_id)
    values(1, 3, 1, 85, 5, 1, '2018-12-24 10:39:14.999999999', 500, 0, 1);
insert into result(scheme_id, user_id, dep_id, percent, grade, is_passed, session_ended, session_lasted, is_timeouted, lms_id)
    values(1, 2, 1, 75, 4, 1, '2018-12-25 11:55:36.999999999', 288, 0, 1);
insert into result(scheme_id, user_id, dep_id, percent, grade, is_passed, session_ended, session_lasted, is_timeouted, lms_id)
    values(1, 3, 1, 88, 5, 1, '2018-12-29 14:44:16.999999999', 445, 0, 1);
insert into result(scheme_id, user_id, dep_id, percent, grade, is_passed, session_ended, session_lasted, is_timeouted, lms_id)
    values(1, 2, 1, 66, 3, 1, '2018-12-30 09:49:38.999999999', 411, 0, 1);

insert into result(scheme_id, user_id, dep_id, percent, grade, is_passed, session_ended, session_lasted, is_timeouted, lms_id)
    values(1, 3, 1, 92, 5, 1, '2019-01-15 08:23:55.999999999', 181, 0, 1);
insert into result(scheme_id, user_id, dep_id, percent, grade, is_passed, session_ended, session_lasted, is_timeouted, lms_id)
    values(1, 2, 1, 82, 5, 1, '2019-01-20 17:49:30.999999999', 540, 0, 1);
insert into result(scheme_id, user_id, dep_id, percent, grade, is_passed, session_ended, session_lasted, is_timeouted, lms_id)
    values(1, 3, 1, 79, 4, 1, '2019-01-25 09:52:45.999999999', 287, 0, 1);
insert into result(scheme_id, user_id, dep_id, percent, grade, is_passed, session_ended, session_lasted, is_timeouted, lms_id)
    values(1, 2, 1, 86, 5, 1, '2019-01-28 11:08:09.999999999', 435, 0, 1);
insert into result(scheme_id, user_id, dep_id, percent, grade, is_passed, session_ended, session_lasted, is_timeouted, lms_id)
    values(1, 3, 1, 62, 3, 1, '2019-01-29 08:00:17.999999999', 419, 0, 1);
insert into result(scheme_id, user_id, dep_id, percent, grade, is_passed, session_ended, session_lasted, is_timeouted, lms_id)
     values(1, 2, 1, 85, 5, 1, '2019-01-30 11:08:09.999999999', 399, 0, 1);
insert into result(scheme_id, user_id, dep_id, percent, grade, is_passed, session_ended, session_lasted, is_timeouted, lms_id)
     values(1, 3, 1, 60, 3, 1, '2019-01-31 08:00:17.999999999', 411, 0, 1);

insert into result_details(detail_id, data, when_remove) values(1, 'some result details JSON data', '2018-12-31 23:59:59.999999999');
insert into result_details(detail_id, data, when_remove) values(2, 'some result details JSON data', '2018-12-31 23:59:59.999999999');
insert into result_details(detail_id, data, when_remove) values(3, 'some result details JSON data', '2018-12-31 23:59:59.999999999');
insert into result_details(detail_id, data, when_remove) values(4, 'some result details JSON data', '2018-12-31 23:59:59.999999999');
insert into result_details(detail_id, data, when_remove) values(5, 'some result details JSON data', '2018-12-31 23:59:59.999999999');

insert into result_details(detail_id, data, when_remove) values(6, 'some result details JSON data', '2019-12-31 23:59:59.999999999');
insert into result_details(detail_id, data, when_remove) values(7, 'some result details JSON data', '2019-12-31 23:59:59.999999999');
insert into result_details(detail_id, data, when_remove) values(8, 'some result details JSON data', '2019-12-31 23:59:59.999999999');
insert into result_details(detail_id, data, when_remove) values(9, 'some result details JSON data', '2019-12-31 23:59:59.999999999');
insert into result_details(detail_id, data, when_remove) values(10, 'some result details JSON data', '2019-12-31 23:59:59.999999999');
insert into result_details(detail_id, data, when_remove) values(11, 'some result details JSON data', '2019-12-31 23:59:59.999999999');
insert into result_details(detail_id, data, when_remove) values(12, 'some result details JSON data', '2019-12-31 23:59:59.999999999');








