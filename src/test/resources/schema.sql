-- -----------------------------------------------------
-- Schema ratos3
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS ratos3;
-- -----------------------------------------------------
-- Table   user
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS user (
  user_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  surname VARCHAR(100) NOT NULL,
  password VARCHAR(61) NOT NULL,
  email VARCHAR(200) NOT NULL,
  PRIMARY KEY (user_id),
  UNIQUE INDEX email_UNIQUE (email ASC));

-- -----------------------------------------------------
-- Table   organisation
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS organisation  (
  org_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name  VARCHAR(500) NOT NULL,
  is_deleted  TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY ( org_id ))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   faculty
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   faculty  (
  fac_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name  VARCHAR(500) NOT NULL,
  org_id INT UNSIGNED NOT NULL,
  PRIMARY KEY ( fac_id ),
  INDEX fk_faculty_organisation_fac_id_idx (org_id ASC),
  CONSTRAINT fk_faculty_organisation_fac_id
  FOREIGN KEY (org_id)
  REFERENCES organisation (org_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;



-- -----------------------------------------------------
-- Table   department
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   department  (
  dep_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name  VARCHAR(500) NOT NULL,
  fac_id  INT UNSIGNED NOT NULL,
  PRIMARY KEY ( dep_id ),
  INDEX  fk_department_faculty_fac_id_idx  ( fac_id  ASC),
  CONSTRAINT  fk_department_faculty_fac_id
  FOREIGN KEY ( fac_id )
  REFERENCES   faculty  ( fac_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table   position
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   position  (
  pos_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name  VARCHAR(500) NOT NULL,
  PRIMARY KEY ( pos_id ))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   role
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   role  (
  role_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name  VARCHAR(200) NOT NULL,
  PRIMARY KEY ( role_id ))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   staff
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   staff  (
  staff_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  user_id  INT UNSIGNED NOT NULL,
  dep_id  INT UNSIGNED NOT NULL,
  pos_id  INT UNSIGNED NOT NULL,
  role_id  INT UNSIGNED NOT NULL,
  PRIMARY KEY ( staff_id ),
  INDEX  fk_staff_user_user_id_idx  ( user_id  ASC),
  INDEX  fk_staff_department_dep_id_idx  ( dep_id  ASC),
  INDEX  fk_staff_position_id_idx  ( pos_id  ASC),
  INDEX  fk_staff_role_role_id_idx  ( role_id  ASC),
  CONSTRAINT  fk_staff_user_user_id
  FOREIGN KEY ( user_id )
  REFERENCES   user  ( user_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_staff_department_dep_id
  FOREIGN KEY ( dep_id )
  REFERENCES   department  ( dep_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_staff_position_pos_id
  FOREIGN KEY ( pos_id )
  REFERENCES   position  ( pos_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_staff_role_role_id
  FOREIGN KEY ( role_id )
  REFERENCES   role  ( role_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   course
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   course  (
  course_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name  VARCHAR(500) NOT NULL,
  created  TIMESTAMP NOT NULL,
  created_by  INT UNSIGNED NOT NULL,
  dep_id  INT UNSIGNED NOT NULL,
  PRIMARY KEY ( course_id ),
  INDEX  fk_course_staff_created_by_idx  ( created_by  ASC),
  INDEX  fk_course_department_dep_id_idx  ( dep_id  ASC),
  CONSTRAINT  fk_course_staff_created_by
  FOREIGN KEY ( created_by )
  REFERENCES   staff  ( staff_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_course_department_dep_id
  FOREIGN KEY ( dep_id )
  REFERENCES   department  ( dep_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   theme
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   theme  (
  theme_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name  VARCHAR(200) NOT NULL,
  is_deleted  TINYINT(1) NOT NULL DEFAULT 0,
  course_id  INT UNSIGNED NOT NULL,
  PRIMARY KEY ( theme_id ),
  INDEX  fk_theme_course_course_id_idx  ( course_id  ASC),
  CONSTRAINT  fk_theme_course_course_id
  FOREIGN KEY ( course_id )
  REFERENCES   course  ( course_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   question_type
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   question_type  (
  type_id  INT UNSIGNED NOT NULL,
  eng_abbreviation  VARCHAR(50) NOT NULL,
  description  TEXT(1000) NOT NULL,
  PRIMARY KEY ( type_id ))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   language
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   language  (
  lang_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name  VARCHAR(50) NOT NULL,
  eng_abbreviation  VARCHAR(10) NOT NULL,
  PRIMARY KEY ( lang_id ),
  UNIQUE INDEX  eng_abbreviation_UNIQUE  ( eng_abbreviation  ASC),
  UNIQUE INDEX  name_UNIQUE  ( name  ASC))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   question
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   question  (
  question_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  title  VARCHAR(1000) NOT NULL,
  level  TINYINT(1) NOT NULL DEFAULT 1,
  type_id  INT UNSIGNED NOT NULL,
  theme_id  INT UNSIGNED NOT NULL,
  lang_id  INT UNSIGNED NOT NULL,
  is_deleted  TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY ( question_id ),
  INDEX  fk_question_theme_theme_id_idx  ( theme_id  ASC),
  INDEX  fk_question_question_type_type_id_idx  ( type_id  ASC),
  INDEX  fk_question_language_lang_id_idx  ( lang_id  ASC),
  CONSTRAINT  fk_question_theme_theme_id
  FOREIGN KEY ( theme_id )
  REFERENCES   theme  ( theme_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_question_question_type_type_id
  FOREIGN KEY ( type_id )
  REFERENCES   question_type  ( type_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_question_language_lang_id
  FOREIGN KEY ( lang_id )
  REFERENCES   language  ( lang_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table   answer_mcq
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   answer_mcq  (
  answer_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  answer  VARCHAR(500) NOT NULL,
  percent  TINYINT(3) NOT NULL,
  is_required  TINYINT(1) NOT NULL DEFAULT 0,
  is_deleted  TINYINT(1) NOT NULL DEFAULT 0,
  question_id  INT UNSIGNED NOT NULL,
  PRIMARY KEY ( answer_id ),
  INDEX  fk_answer_mcq_question_question_id_idx  ( question_id  ASC),
  CONSTRAINT  fk_answer_mcq_question_question_id
  FOREIGN KEY ( question_id )
  REFERENCES   question  ( question_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   settings_fbq
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   settings_fbq  (
  set_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name  VARCHAR(200) NOT NULL,
  words_limit  INT UNSIGNED NOT NULL,
  symbols_limit  INT UNSIGNED NOT NULL,
  is_numeric  TINYINT(1) NOT NULL,
  is_typo_allowed  TINYINT(1) NOT NULL,
  is_case_sensitive  TINYINT(1) NOT NULL,
  lang_id  INT UNSIGNED NOT NULL,
  staff_id  INT UNSIGNED NOT NULL,
  PRIMARY KEY ( set_id ),
  INDEX  fk_settings_fbq_language_lang_id_idx  ( lang_id  ASC),
  INDEX  fk_settings_fbq_staff_staff_id_idx  ( staff_id  ASC),
  UNIQUE INDEX  fbq_name_staff_UNIQUE  ( name  ASC,  staff_id  ASC),
  CONSTRAINT  fk_settings_fbq_language_lang_id
  FOREIGN KEY ( lang_id )
  REFERENCES   language  ( lang_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_settings_fbq_staff_staff_id
  FOREIGN KEY ( staff_id )
  REFERENCES   staff  ( staff_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   answer_fbsq
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   answer_fbsq  (
  answer_id  INT UNSIGNED NOT NULL,
  set_id  INT UNSIGNED NOT NULL,
  INDEX  fk_answer_fbsq_settings_set_id_idx  ( set_id  ASC),
  PRIMARY KEY ( answer_id ),
  CONSTRAINT  fk_answer_fbsq_settings_set_id
  FOREIGN KEY ( set_id )
  REFERENCES   settings_fbq  ( set_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_answer_fbsq_question_answer_id
  FOREIGN KEY ( answer_id )
  REFERENCES   question  ( question_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   answer_mq
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   answer_mq  (
  answer_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  left_phrase  VARCHAR(200) NOT NULL,
  right_phrase  VARCHAR(200) NOT NULL,
  question_id  INT UNSIGNED NOT NULL,
  is_deleted  TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY ( answer_id ),
  INDEX  fk_answer_mq_question_question_id_idx  ( question_id  ASC),
  CONSTRAINT  fk_answer_mq_question_question_id
  FOREIGN KEY ( question_id )
  REFERENCES   question  ( question_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   answer_sq
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   answer_sq  (
  answer_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  element  VARCHAR(200) NOT NULL,
  element_order  TINYINT(2) UNSIGNED NOT NULL,
  question_id  INT UNSIGNED NOT NULL,
  is_deleted  TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY ( answer_id ),
  INDEX  fk_answer_sq_question_question_id_idx  ( question_id  ASC),
  CONSTRAINT  fk_answer_sq_question_question_id
  FOREIGN KEY ( question_id )
  REFERENCES   question  ( question_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   help
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS help (
  help_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  text VARCHAR(5000) NOT NULL,
  staff_id INT UNSIGNED NOT NULL,
  PRIMARY KEY (help_id),
  INDEX fk_help_staff_staff_id_idx (staff_id ASC),
  CONSTRAINT fk_help_staff_staff_id
  FOREIGN KEY (staff_id)
  REFERENCES staff (staff_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `ratos3`.`question_help`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS question_help (
  question_id INT UNSIGNED NOT NULL,
  help_id INT UNSIGNED NOT NULL,
  PRIMARY KEY (question_id, help_id),
  INDEX fk_question_help_help_id_idx (help_id ASC),
  CONSTRAINT fk_question_help_question_id
  FOREIGN KEY (question_id)
  REFERENCES question (question_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_question_help_help_id
  FOREIGN KEY (help_id)
  REFERENCES help (help_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table   resource
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   resource  (
  resource_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  hyperlink  VARCHAR(200) NOT NULL,
  description  VARCHAR(200) NOT NULL,
  staff_id  INT UNSIGNED NOT NULL,
  last_used TIMESTAMP NOT NULL,
  PRIMARY KEY ( resource_id ),
  UNIQUE INDEX  resource_link_UNIQUE  ( hyperlink  ASC),
  INDEX  fk_resource_staff_staff_id_idx  ( staff_id  ASC),
  CONSTRAINT  fk_resource_staff_staff_id
  FOREIGN KEY ( staff_id )
  REFERENCES   staff  ( staff_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   accepted_phrase
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   accepted_phrase  (
  phrase_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  phrase  VARCHAR(100) NOT NULL,
  staff_id  INT UNSIGNED NOT NULL,
  last_used TIMESTAMP NOT NULL,
  PRIMARY KEY ( phrase_id ),
  UNIQUE INDEX  phrase_staff_UNIQUE  ( phrase  ASC,  staff_id  ASC),
  INDEX  fk_accepted_phrase_staff_id_idx  ( staff_id  ASC),
  CONSTRAINT  fk_accepted_phrase_staff_staff_id
  FOREIGN KEY ( staff_id )
  REFERENCES   staff  ( staff_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   fbsq_phrase
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   fbsq_phrase  (
  phrase_id  INT UNSIGNED NOT NULL,
  answer_id  INT UNSIGNED NOT NULL,
  INDEX  fk_bfsq_phrase_accepted_phrase_phrase_id_idx  ( phrase_id  ASC),
  INDEX  fk_fbsq_phrase_answer_fbsq_answer_id_idx  ( answer_id  ASC),
  PRIMARY KEY ( answer_id ,  phrase_id ),
  CONSTRAINT  fk_bfsq_phrase_accepted_phrase_phrase_id
  FOREIGN KEY ( phrase_id )
  REFERENCES   accepted_phrase  ( phrase_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_fbsq_phrase_answer_fbsq_answer_id
  FOREIGN KEY ( answer_id )
  REFERENCES   answer_fbsq  ( answer_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   answer_fbmq
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   answer_fbmq  (
  answer_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  phrase  VARCHAR(100) NOT NULL,
  occurrence  INT NOT NULL,
  set_id  INT UNSIGNED NOT NULL,
  question_id  INT UNSIGNED NOT NULL,
  is_deleted  TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY ( answer_id ),
  INDEX  fk_answer_fbmq_settings_set_id_idx  ( set_id  ASC),
  INDEX  fk_answer_fbmq_question_question_id_idx  ( question_id  ASC),
  CONSTRAINT  fk_answer_fbmq_settings_set_id
  FOREIGN KEY ( set_id )
  REFERENCES   settings_fbq  ( set_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_answer_fbmq_question_question_id
  FOREIGN KEY ( question_id )
  REFERENCES   question  ( question_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   fbmq_phrase
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   fbmq_phrase  (
  answer_id  INT UNSIGNED NOT NULL,
  phrase_id  INT UNSIGNED NOT NULL,
  INDEX  fk_fbmq_phrase_answer_fbmq_answer_id_idx  ( answer_id  ASC),
  INDEX  fk_fbmq_phrase_accepted_phrase_phrase_id_idx  ( phrase_id  ASC),
  PRIMARY KEY ( answer_id ,  phrase_id ),
  CONSTRAINT  fk_fbmq_phrase_answer_fbmq_answer_id
  FOREIGN KEY ( answer_id )
  REFERENCES   answer_fbmq  ( answer_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_fbmq_phrase_accepted_phrase_phrase_id
  FOREIGN KEY ( phrase_id )
  REFERENCES   accepted_phrase  ( phrase_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   help_resource
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   help_resource  (
  help_id  INT UNSIGNED NOT NULL,
  resource_id  INT UNSIGNED NOT NULL,
  PRIMARY KEY ( help_id ,  resource_id ),
  INDEX  fk_help_resource_resource_id_idx  ( resource_id  ASC),
  CONSTRAINT  fk_help_resource_help_help_id
  FOREIGN KEY ( help_id )
  REFERENCES   help  ( help_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_help_resource_resource_resource_id
  FOREIGN KEY ( resource_id )
  REFERENCES   resource  ( resource_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   answer_mq_resource
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   answer_mq_resource  (
  answer_id  INT UNSIGNED NOT NULL,
  resource_id  INT UNSIGNED NOT NULL,
  PRIMARY KEY ( answer_id ,  resource_id ),
  INDEX  fk_answer_mq_resource_resource_resource_id_idx  ( resource_id  ASC),
  CONSTRAINT  fk_answer_mq_resource_answer_mq_answer_id
  FOREIGN KEY ( answer_id )
  REFERENCES   answer_mq  ( answer_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_answer_mq_resource_resource_resource_id
  FOREIGN KEY ( resource_id )
  REFERENCES   resource  ( resource_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   answer_sq_resource
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   answer_sq_resource  (
  answer_id  INT UNSIGNED NOT NULL,
  resource_id  INT UNSIGNED NOT NULL,
  PRIMARY KEY ( answer_id ,  resource_id ),
  INDEX  fk_answer_sq_resource_resource_resource_id_idx  ( resource_id  ASC),
  CONSTRAINT  fk_answer_sq_resource_answer_sq_answer_id
  FOREIGN KEY ( answer_id )
  REFERENCES   answer_sq  ( answer_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_answer_sq_resource_resource_resource_id
  FOREIGN KEY ( resource_id )
  REFERENCES   resource  ( resource_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   question_resource
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   question_resource  (
  question_id  INT UNSIGNED NOT NULL,
  resource_id  INT UNSIGNED NOT NULL,
  PRIMARY KEY ( question_id ,  resource_id ),
  INDEX  fk_question_resource_resource_resource_id_idx  ( resource_id  ASC),
  CONSTRAINT  fk_question_resource_question_question_id
  FOREIGN KEY ( question_id )
  REFERENCES   question  ( question_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_question_resource_resource_resource_id
  FOREIGN KEY ( resource_id )
  REFERENCES   resource  ( resource_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   answer_mcq_resource
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   answer_mcq_resource  (
  answer_id  INT UNSIGNED NOT NULL,
  resource_id  INT UNSIGNED NOT NULL,
  PRIMARY KEY ( answer_id ,  resource_id ),
  INDEX  fk_answer_mcq_resource_resource_resource_id_idx  ( resource_id  ASC),
  CONSTRAINT  fk_answer_mcq_resource_answer_mcq_answer_id
  FOREIGN KEY ( answer_id )
  REFERENCES   answer_mcq  ( answer_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_answer_mcq_resource_resource_resource_id
  FOREIGN KEY ( resource_id )
  REFERENCES   resource  ( resource_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   strategy
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   strategy  (
  str_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name  VARCHAR(100) NOT NULL,
  description  TEXT(500) NOT NULL,
  PRIMARY KEY ( str_id ))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   settings
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   settings  (
  set_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name  VARCHAR(100) NOT NULL,
  staff_id  INT UNSIGNED NOT NULL,
  seconds_per_question  INT UNSIGNED NOT NULL DEFAULT 60,
  questions_per_sheet  INT UNSIGNED NOT NULL DEFAULT 1,
  days_keep_result_details  INT UNSIGNED NOT NULL DEFAULT 1,
  threshold_3  INT UNSIGNED NOT NULL DEFAULT 50,
  threshold_4  INT UNSIGNED NOT NULL DEFAULT 75,
  threshold_5  INT UNSIGNED NOT NULL DEFAULT 90,
  level_2_coefficient  FLOAT UNSIGNED NOT NULL DEFAULT 1,
  level_3_coefficient  FLOAT UNSIGNED NOT NULL DEFAULT 1,
  display_percent  TINYINT(1) NOT NULL DEFAULT 1,
  display_mark  TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY ( set_id ),
  INDEX  fk_settings_staff_staff_id_idx  ( staff_id  ASC),
  UNIQUE INDEX  settings_name_staff_UNIQUE  ( name  ASC,  staff_id  ASC),
  CONSTRAINT  fk_settings_staff_staff_id
  FOREIGN KEY ( staff_id )
  REFERENCES   staff  ( staff_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   mode
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   mode  (
  mode_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name  VARCHAR(100) NOT NULL,
  staff_id  INT UNSIGNED NOT NULL,
  is_helpable  TINYINT(1) NOT NULL DEFAULT 0,
  is_pyramid  TINYINT(1) NOT NULL DEFAULT 0,
  is_skipable  TINYINT(1) NOT NULL DEFAULT 0,
  is_rightans  TINYINT(1) NOT NULL DEFAULT 0,
  is_resultdetails  TINYINT(1) NOT NULL DEFAULT 0,
  is_pauseable  TINYINT(1) NOT NULL DEFAULT 0,
  is_preservable  TINYINT(1) NOT NULL DEFAULT 0,
  is_reportable  TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY ( mode_id ),
  INDEX  fk_mode_staff_staff_id_idx  ( staff_id  ASC),
  UNIQUE INDEX  mode_name_staff_UNIQUE  ( name  ASC,  staff_id  ASC),
  CONSTRAINT  fk_mode_staff_staff_id
  FOREIGN KEY ( staff_id )
  REFERENCES   staff  ( staff_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   scheme
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   scheme  (
  scheme_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name  VARCHAR(400) NOT NULL,
  is_active  TINYINT(1) NOT NULL,
  strategy_id  INT UNSIGNED NOT NULL,
  settings_id  INT UNSIGNED NOT NULL,
  mode_id  INT UNSIGNED NOT NULL,
  course_id  INT UNSIGNED NOT NULL,
  created  TIMESTAMP NOT NULL,
  created_by  INT UNSIGNED NOT NULL,
  is_deleted  TINYINT(1) NOT NULL DEFAULT 0,
  is_completed  TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY ( scheme_id ),
  INDEX  fk_scheme_staff_created_by_idx  ( created_by  ASC),
  INDEX  fk_scheme_strategy_strategy_id_idx  ( strategy_id  ASC),
  INDEX  fk_scheme_settings_settings_id_idx  ( settings_id  ASC),
  INDEX  fk_scheme_mode_mode_id_idx  ( mode_id  ASC),
  INDEX  fk_scheme_course_course_id_idx  ( course_id  ASC),
  UNIQUE INDEX  name_course_UNIQUE  ( name  ASC,  course_id  ASC),
  CONSTRAINT  fk_scheme_staff_created_by
  FOREIGN KEY ( created_by )
  REFERENCES   staff  ( staff_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_scheme_strategy_strategy_id
  FOREIGN KEY ( strategy_id )
  REFERENCES   strategy  ( str_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_scheme_settings_settings_id
  FOREIGN KEY ( settings_id )
  REFERENCES   settings  ( set_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_scheme_mode_mode_id
  FOREIGN KEY ( mode_id )
  REFERENCES   mode  ( mode_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_scheme_course_course_id
  FOREIGN KEY ( course_id )
  REFERENCES   course  ( course_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   scheme_theme
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   scheme_theme  (
  scheme_theme_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  scheme_id  INT UNSIGNED NOT NULL,
  theme_id  INT UNSIGNED NOT NULL,
  theme_order  TINYINT(2) NOT NULL,
  PRIMARY KEY ( scheme_theme_id ),
  INDEX  fk_scheme_theme_theme_theme_id_idx  ( theme_id  ASC),
  INDEX  fk_scheme_theme_scheme_scheme_id_idx  ( scheme_id  ASC),
  CONSTRAINT  fk_scheme_theme_theme_theme_id
  FOREIGN KEY ( theme_id )
  REFERENCES   theme  ( theme_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_scheme_theme_scheme_scheme_id
  FOREIGN KEY ( scheme_id )
  REFERENCES   scheme  ( scheme_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   type_level
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   type_level  (
  type_level_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  scheme_theme_id  INT UNSIGNED NOT NULL,
  type_id  INT UNSIGNED NOT NULL,
  level_1  TINYINT(3) NOT NULL,
  level_2  TINYINT(3) NOT NULL,
  level_3  TINYINT(3) NOT NULL,
  PRIMARY KEY ( type_level_id ),
  INDEX  fk_type_level_scheme_theme_scheme_theme_id_idx  ( scheme_theme_id  ASC),
  INDEX  fk_type_level_question_type_type_id_idx  ( type_id  ASC),
  CONSTRAINT  fk_type_level_scheme_theme_scheme_theme_id
  FOREIGN KEY ( scheme_theme_id )
  REFERENCES   scheme_theme  ( scheme_theme_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_type_level_question_type_type_id
  FOREIGN KEY ( type_id )
  REFERENCES   question_type  ( type_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   result
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   result  (
  result_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  scheme_id  INT UNSIGNED NOT NULL,
  user_id  INT UNSIGNED NOT NULL,
  percent  FLOAT UNSIGNED NOT NULL,
  mark  TINYINT(1) UNSIGNED NOT NULL,
  ip_address  VARCHAR(15) NOT NULL,
  session_begin  TIMESTAMP NULL,
  session_end  TIMESTAMP NULL,
  is_timeouted  TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY ( result_id ),
  INDEX  fk_result_scheme_scheme_id_idx  ( scheme_id  ASC),
  INDEX  fk_result_user_user_id_idx  ( user_id  ASC),
  CONSTRAINT  fk_result_scheme_scheme_id
  FOREIGN KEY ( scheme_id )
  REFERENCES   scheme  ( scheme_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_result_user_user_id
  FOREIGN KEY ( user_id )
  REFERENCES   user  ( user_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   result_theme
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   result_theme  (
  result_id  INT UNSIGNED NOT NULL,
  theme_id  INT UNSIGNED NOT NULL,
  percent  FLOAT UNSIGNED NOT NULL,
  PRIMARY KEY ( result_id ,  theme_id ),
  INDEX  fk_result_theme_theme_theme_id_idx  ( theme_id  ASC),
  CONSTRAINT  fk_result_theme_result_result_id
  FOREIGN KEY ( result_id )
  REFERENCES   result  ( result_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_result_theme_theme_theme_id
  FOREIGN KEY ( theme_id )
  REFERENCES   theme  ( theme_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   result_details
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   result_details  (
  detail_id  INT UNSIGNED NOT NULL,
  data  TEXT NOT NULL,
  when_remove  TIMESTAMP NOT NULL,
  PRIMARY KEY ( detail_id ),
  INDEX  fk_result_details_result_details_id_idx  ( detail_id  ASC),
  CONSTRAINT  fk_result_details_result_details_id
  FOREIGN KEY ( detail_id )
  REFERENCES   result  ( result_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   session_preserved
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   session_preserved  (
  uuid  VARCHAR(61) NOT NULL,
  scheme_id  INT UNSIGNED NOT NULL,
  user_id  INT UNSIGNED NOT NULL,
  data  TEXT NOT NULL,
  when_preserved  TIMESTAMP NOT NULL,
  PRIMARY KEY ( uuid ),
  INDEX  fk_session_preserved_scheme_scheme_id_idx  ( scheme_id  ASC),
  INDEX  fk_session_preserved_user_user_id_idx  ( user_id  ASC),
  CONSTRAINT  fk_session_preserved_scheme_scheme_id
  FOREIGN KEY ( scheme_id )
  REFERENCES   scheme  ( scheme_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_session_preserved_user_user_id
  FOREIGN KEY ( user_id )
  REFERENCES   user  ( user_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   student
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   student  (
  stud_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  entrance_year  YEAR NOT NULL ,
  class  VARCHAR(50) NOT NULL,
  user_id  INT UNSIGNED NOT NULL,
  is_active  TINYINT(1) NOT NULL,
  fac_id  INT UNSIGNED NOT NULL,
  PRIMARY KEY ( stud_id ),
  INDEX  fk_student_user_user_id_idx  ( user_id  ASC),
  INDEX  fk_student_faculty_fac_id_idx  ( fac_id  ASC),
  CONSTRAINT  fk_student_user_user_id
  FOREIGN KEY ( user_id )
  REFERENCES   user  ( user_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_student_faculty_fac_id
  FOREIGN KEY ( fac_id )
  REFERENCES   faculty  ( fac_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Placeholder table for view   theme_type
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   theme_type  (org_id  INT, fac_id INT, dep_id  INT, course_id  INT,  theme_id  INT,  type_id  INT,  theme  INT,  type  INT,  L1  INT,  L2  INT,  L3  INT,  total  INT);

-- -----------------------------------------------------
-- View   theme_type
-- -----------------------------------------------------

CREATE OR REPLACE VIEW theme_type_view AS
  select o.org_id as org_id, f.fac_id as fac_id, d.dep_id as dep_id, c.course_id as course_id, t.theme_id as theme_id, question.type_id as type_id,
    o.name as organisation, f.name as faculty, d.name as department, c.name as course, t.name as theme, question_type.eng_abbreviation as type,
         sum(question.level=1) as L1, sum(question.level=3) as L2, sum(question.level=3) as L3, count(question.type_id) as total
  from question
    inner join question_type on question.type_id=question_type.type_id
    inner join theme t on question.theme_id=t.theme_id
    inner join course c on t.course_id=c.course_id
    inner join department d on c.dep_id=d.dep_id
    inner join faculty f on d.fac_id=f.fac_id
    inner join organisation o on f.org_id=o.org_id
  group by type_id, course_id;