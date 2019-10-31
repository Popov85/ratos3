insert into scheme(name, is_active, strategy_id, settings_id, mode_id, grading_id, course_id, created, created_by, belongs_to, is_deleted, access_id, options_id)
    values('Sample scheme #1', 1, 1, 1, 1, 1, 1, '2019-08-02 15:35:20.999999999', 1, 1, 0, 1, 1);

insert into session_preserved(uuid, scheme_id, user_id, data, when_preserved) values('123456781', 1, 2, 'JSON SessionData #1', CURRENT_TIMESTAMP);
insert into session_preserved(uuid, scheme_id, user_id, data, when_preserved) values('123456782', 1, 2, 'JSON SessionData #2', CURRENT_TIMESTAMP);
insert into session_preserved(uuid, scheme_id, user_id, data, when_preserved) values('123456783', 1, 2, 'JSON SessionData #3', CURRENT_TIMESTAMP);
insert into session_preserved(uuid, scheme_id, user_id, data, when_preserved) values('123456784', 1, 2, 'JSON SessionData #4', CURRENT_TIMESTAMP);
insert into session_preserved(uuid, scheme_id, user_id, data, when_preserved) values('123456785', 1, 2, 'JSON SessionData #5', CURRENT_TIMESTAMP);

insert into session_preserved(uuid, scheme_id, user_id, data, when_preserved) values('123456786', 1, 3, 'JSON SessionData #6', CURRENT_TIMESTAMP);
insert into session_preserved(uuid, scheme_id, user_id, data, when_preserved) values('123456787', 1, 3, 'JSON SessionData #7', CURRENT_TIMESTAMP);
insert into session_preserved(uuid, scheme_id, user_id, data, when_preserved) values('123456788', 1, 3, 'JSON SessionData #8', CURRENT_TIMESTAMP);
insert into session_preserved(uuid, scheme_id, user_id, data, when_preserved) values('123456789', 1, 3, 'JSON SessionData #9', CURRENT_TIMESTAMP);
insert into session_preserved(uuid, scheme_id, user_id, data, when_preserved) values('1234567810', 1, 3, 'JSON SessionData #10', CURRENT_TIMESTAMP);







