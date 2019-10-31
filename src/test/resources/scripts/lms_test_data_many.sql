insert into organisation(name, is_deleted) values('Organisation #2', 0);

insert into lti_credentials(lti_consumer_key, lti_client_secret) values('ratos_consumer_key_3', 'ratos_client_secret_3');
insert into lti_credentials(lti_consumer_key, lti_client_secret) values('ratos_consumer_key_4', 'ratos_client_secret_4');
insert into lti_credentials(lti_consumer_key, lti_client_secret) values('ratos_consumer_key_5', 'ratos_client_secret_5');

insert into lms(name, lti_version_id, org_id, credentials_id) values('Moodle-1', 1, 2, 4);

insert into lms(name, lti_version_id, org_id, credentials_id) values('Moodle-2 local', 1, 2, 5);

insert into lms(name, lti_version_id, org_id, credentials_id) values('Moodle-3 local', 1, 2, 6);



