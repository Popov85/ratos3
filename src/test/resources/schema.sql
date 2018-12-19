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
  password VARCHAR(70) NOT NULL,
  email VARCHAR(200) NOT NULL,
  is_active TINYINT(1) NOT NULL,
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
-- Table  class
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS  class (
  class_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(200) NOT NULL,
  fac_id INT UNSIGNED NOT NULL,
  PRIMARY KEY (class_id),
  INDEX fk_class_faculty_fac_id_idx (fac_id ASC),
  CONSTRAINT fk_class_faculty_fac_id
  FOREIGN KEY (fac_id)
  REFERENCES  faculty (fac_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table  student
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS  student (
  stud_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  user_id INT UNSIGNED NOT NULL,
  class_id INT UNSIGNED NOT NULL,
  entrance_year YEAR NOT NULL COMMENT 'Year when this student/employee has entered the educational organisation',
  PRIMARY KEY (stud_id),
  INDEX fk_student_user_user_id_idx (user_id ASC),
  INDEX fk_student_class_class_id_idx (class_id ASC),
  CONSTRAINT fk_student_user_user_id
  FOREIGN KEY (user_id)
  REFERENCES  user (user_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_student_class_class_id
  FOREIGN KEY (class_id)
  REFERENCES  class (class_id)
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
-- Table user_role
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS user_role (
  role_id INT UNSIGNED NOT NULL,
  user_id INT UNSIGNED NOT NULL,
  PRIMARY KEY (role_id, user_id),
  INDEX fk_user_role_user_user_id_idx (user_id ASC),
  CONSTRAINT fk_user_role_role_role_id
  FOREIGN KEY (role_id)
  REFERENCES role (role_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_user_role_user_user_id
  FOREIGN KEY (user_id)
  REFERENCES user (user_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   staff
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS  staff  (
  staff_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  user_id  INT UNSIGNED NOT NULL,
  dep_id  INT UNSIGNED NOT NULL,
  pos_id  INT UNSIGNED NOT NULL,
  PRIMARY KEY ( staff_id ),
  INDEX  fk_staff_user_user_id_idx  ( user_id  ASC),
  INDEX  fk_staff_department_dep_id_idx  ( dep_id  ASC),
  INDEX  fk_staff_position_id_idx  ( pos_id  ASC),
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
  is_partial  TINYINT(1) NOT NULL DEFAULT 0,
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
-- Table   phrase
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   phrase  (
  phrase_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  phrase  VARCHAR(100) NOT NULL,
  staff_id  INT UNSIGNED NOT NULL,
  last_used TIMESTAMP NOT NULL,
  is_deleted  TINYINT(1) NOT NULL DEFAULT 0,
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
-- Table   answer_mq
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS  answer_mq  (
  answer_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  left_phrase_id  INT UNSIGNED NOT NULL,
  right_phrase_id  INT UNSIGNED NOT NULL,
  question_id  INT UNSIGNED NOT NULL,
  is_deleted  TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY ( answer_id ),
  INDEX  fk_answer_mq_question_question_id_idx  ( question_id  ASC),
  INDEX  fk_answer_mq_phrase1_idx  ( left_phrase_id  ASC),
  INDEX  fk_answer_mq_phrase2_idx  ( right_phrase_id  ASC),
  CONSTRAINT  fk_answer_mq_question_question_id
  FOREIGN KEY ( question_id )
  REFERENCES  question  ( question_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_answer_mq_phrase_left_phrase_id
  FOREIGN KEY ( left_phrase_id )
  REFERENCES  phrase  ( phrase_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_answer_mq_phrase_right_phrase_id
  FOREIGN KEY ( right_phrase_id )
  REFERENCES  phrase  ( phrase_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   answer_sq
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS  answer_sq  (
  answer_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  phrase_id  INT UNSIGNED NOT NULL,
  phrase_order  TINYINT(2) UNSIGNED NOT NULL,
  question_id  INT UNSIGNED NOT NULL,
  is_deleted  TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY ( answer_id ),
  INDEX  fk_answer_sq_question_question_id_idx  ( question_id  ASC),
  INDEX  fk_answer_sq_phrase_phrase_id_idx  ( phrase_id  ASC),
  CONSTRAINT  fk_answer_sq_question_question_id
  FOREIGN KEY ( question_id )
  REFERENCES  question  ( question_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_answer_sq_phrase_phrase_id
  FOREIGN KEY ( phrase_id )
  REFERENCES  phrase  ( phrase_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   helpAvailable
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
-- Table   phraseResource
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   resource  (
  resource_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  hyperlink  VARCHAR(200) NOT NULL,
  description  VARCHAR(200) NOT NULL,
  staff_id  INT UNSIGNED NOT NULL,
  last_used TIMESTAMP NOT NULL,
  is_deleted  TINYINT(1) NOT NULL DEFAULT 0,
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
-- Table  ratos3 . phrase_resource
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS  phrase_resource  (
  phrase_id  INT UNSIGNED NOT NULL,
  resource_id  INT UNSIGNED NOT NULL,
  PRIMARY KEY ( phrase_id ),
  INDEX  fk_phrase_resource_resource_resource_id_idx  ( resource_id  ASC),
  CONSTRAINT  fk_phrase_resource_phrase_phrase_id
  FOREIGN KEY ( phrase_id )
  REFERENCES  phrase  ( phrase_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_phrase_resource_resource_resource_id
  FOREIGN KEY ( resource_id )
  REFERENCES  resource  ( resource_id )
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
  REFERENCES   phrase  ( phrase_id )
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
  REFERENCES   phrase  ( phrase_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table   help_resource
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS  help_resource  (
  help_id  INT UNSIGNED NOT NULL,
  resource_id  INT UNSIGNED NOT NULL,
  PRIMARY KEY ( help_id ),
  INDEX  fk_help_resource_resource_id_idx  ( resource_id  ASC),
  CONSTRAINT  fk_help_resource_help_help_id
  FOREIGN KEY ( help_id )
  REFERENCES  help  ( help_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_help_resource_resource_resource_id
  FOREIGN KEY ( resource_id )
  REFERENCES  resource  ( resource_id )
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
CREATE TABLE IF NOT EXISTS  answer_mcq_resource  (
  answer_id  INT UNSIGNED NOT NULL,
  resource_id  INT UNSIGNED NOT NULL,
  PRIMARY KEY ( answer_id ),
  INDEX  fk_answer_mcq_resource_resource_resource_id_idx  ( resource_id  ASC),
  CONSTRAINT  fk_answer_mcq_resource_answer_mcq_answer_id
  FOREIGN KEY ( answer_id )
  REFERENCES  answer_mcq  ( answer_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_answer_mcq_resource_resource_resource_id
  FOREIGN KEY ( resource_id )
  REFERENCES  resource  ( resource_id )
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
  seconds_per_question  INT UNSIGNED NOT NULL DEFAULT -1,
  strict_seconds_per_question TINYINT(1) NOT NULL DEFAULT 0,
  questions_per_sheet  INT UNSIGNED NOT NULL DEFAULT 1,
  days_keep_result_details  INT UNSIGNED NOT NULL DEFAULT 1,
  level_2_coefficient  FLOAT UNSIGNED NOT NULL DEFAULT 1,
  level_3_coefficient  FLOAT UNSIGNED NOT NULL DEFAULT 1,
  display_percent  TINYINT(1) NOT NULL DEFAULT 1,
  display_mark  TINYINT(1) NOT NULL DEFAULT 1,
  display_theme_results  TINYINT(1) NOT NULL DEFAULT 0,
  is_default  TINYINT(1) NOT NULL DEFAULT 0,
  is_deleted  TINYINT(1) NOT NULL DEFAULT 0,
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
  is_starrable  TINYINT(1) NOT NULL DEFAULT 0,
  is_default  TINYINT(1) NOT NULL DEFAULT 0,
  is_deleted  TINYINT(1) NOT NULL DEFAULT 0,
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
-- Table  ratos3 . grading
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS grading  (
  grading_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name  VARCHAR(100) NOT NULL,
  description  TEXT(500) NOT NULL,
  PRIMARY KEY ( grading_id ))
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table   scheme
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS  scheme  (
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
  grading_id  INT UNSIGNED NOT NULL,
  PRIMARY KEY ( scheme_id ),
  INDEX  fk_scheme_staff_created_by_idx  ( created_by  ASC),
  INDEX  fk_scheme_strategy_strategy_id_idx  ( strategy_id  ASC),
  INDEX  fk_scheme_settings_settings_id_idx  ( settings_id  ASC),
  INDEX  fk_scheme_mode_mode_id_idx  ( mode_id  ASC),
  INDEX  fk_scheme_course_course_id_idx  ( course_id  ASC),
  UNIQUE INDEX  name_course_UNIQUE  ( name  ASC,  course_id  ASC),
  INDEX  fk_scheme_grading_grading_id_idx  ( grading_id  ASC),
  CONSTRAINT  fk_scheme_staff_created_by
  FOREIGN KEY ( created_by )
  REFERENCES  staff  ( staff_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_scheme_strategy_strategy_id
  FOREIGN KEY ( strategy_id )
  REFERENCES  strategy  ( str_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_scheme_settings_settings_id
  FOREIGN KEY ( settings_id )
  REFERENCES  settings  ( set_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_scheme_mode_mode_id
  FOREIGN KEY ( mode_id )
  REFERENCES  mode  ( mode_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_scheme_course_course_id
  FOREIGN KEY ( course_id )
  REFERENCES  course  ( course_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_scheme_grading_grading_id
  FOREIGN KEY ( grading_id )
  REFERENCES  grading  ( grading_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table  ratos3 . four_point
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS  four_point  (
  four_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name  VARCHAR(100) NOT NULL,
  threshold_3  INT NOT NULL,
  threshold_4  INT NOT NULL,
  threshold_5  INT NOT NULL,
  staff_id  INT UNSIGNED NOT NULL,
  is_default  TINYINT(1) NOT NULL,
  is_deleted  TINYINT(1) NOT NULL,
  grading_id  INT UNSIGNED NOT NULL,
  PRIMARY KEY ( four_id ),
  INDEX  fk_four_point_staff_staff_id_idx  ( staff_id  ASC),
  INDEX  fk_four_point_grading_grading_id_idx  ( grading_id  ASC),
  CONSTRAINT  fk_four_point_staff_staff_id
  FOREIGN KEY ( staff_id )
  REFERENCES  staff  ( staff_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_four_point_grading_grading_id
  FOREIGN KEY ( grading_id )
  REFERENCES  grading  ( grading_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table  ratos3 . scheme_four_point
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS  scheme_four_point  (
  scheme_id  INT UNSIGNED NOT NULL,
  four_point_id  INT UNSIGNED NOT NULL,
  PRIMARY KEY ( scheme_id ),
  INDEX  fk_scheme_four_point_four_point_id_idx  ( four_point_id  ASC),
  CONSTRAINT  fk_scheme_four_point_scheme_id
  FOREIGN KEY ( scheme_id )
  REFERENCES  scheme  ( scheme_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_scheme_four_point_four_point_id
  FOREIGN KEY ( four_point_id )
  REFERENCES  four_point  ( four_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table  ratos3 . two_point
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS two_point  (
  two_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name  VARCHAR(100) NOT NULL,
  threshold  INT NOT NULL,
  is_default  TINYINT(1) NOT NULL,
  is_deleted  TINYINT(1) NOT NULL,
  grading_id  INT UNSIGNED NOT NULL,
  staff_id  INT UNSIGNED NOT NULL,
  PRIMARY KEY ( two_id ),
  INDEX  fk_two_point_grading_grading_id_idx  ( grading_id  ASC),
  INDEX  fk_two_point_staff_staff_id_idx  ( staff_id  ASC),
  CONSTRAINT  fk_two_point_grading_grading_id
  FOREIGN KEY ( grading_id )
  REFERENCES  grading  ( grading_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_two_point_staff_staff_id
  FOREIGN KEY ( staff_id )
  REFERENCES  staff  ( staff_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table  ratos3 . scheme_two_point
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS  scheme_two_point  (
  scheme_id  INT UNSIGNED NOT NULL,
  two_point_id  INT UNSIGNED NOT NULL,
  PRIMARY KEY ( scheme_id ),
  INDEX  fk_scheme_two_point_scheme_scheme_id_idx  ( scheme_id  ASC),
  CONSTRAINT  fk_scheme_two_point_two_point_two_point_id
  FOREIGN KEY ( two_point_id )
  REFERENCES  two_point  ( two_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_scheme_two_point_scheme_scheme_id
  FOREIGN KEY ( scheme_id )
  REFERENCES  scheme  ( scheme_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table  ratos3 . free_point
-- ----------------------------------------------------
CREATE TABLE IF NOT EXISTS free_point  (
  free_id  INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name  VARCHAR(100) NOT NULL,
  min_value  INT NOT NULL,
  pass_value  INT NOT NULL,
  max_value  INT NOT NULL,
  staff_id  INT UNSIGNED NOT NULL,
  grading_id  INT UNSIGNED NOT NULL,
  PRIMARY KEY ( free_id ),
  INDEX  fk_free_point_staff_staff_id_idx  ( staff_id  ASC),
  INDEX  fk_free_point_grading_grading_id_idx  ( grading_id  ASC),
  CONSTRAINT  fk_free_point_staff_staff_id
  FOREIGN KEY ( staff_id )
  REFERENCES  staff  ( staff_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_free_point_grading_grading_id
  FOREIGN KEY ( grading_id )
  REFERENCES  grading  ( grading_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table  ratos3 . scheme_free_point
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS  scheme_free_point  (
  scheme_id  INT UNSIGNED NOT NULL,
  free_point_id  INT UNSIGNED NOT NULL,
  PRIMARY KEY ( scheme_id ),
  INDEX  fk_scheme_free_point_scheme_scheme_id_idx  ( scheme_id  ASC),
  CONSTRAINT  fk_scheme_free_point_free_point_id
  FOREIGN KEY ( free_point_id )
  REFERENCES  free_point  ( free_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_scheme_free_point_scheme_id
  FOREIGN KEY ( scheme_id )
  REFERENCES  scheme  ( scheme_id )
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
-- Table  lti_version
-- -----------------------------------------------------
DROP TABLE IF EXISTS  lti_version ;

CREATE TABLE IF NOT EXISTS  lti_version (
  version_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  version VARCHAR(20) NOT NULL,
  PRIMARY KEY (version_id))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table  lti1p0_credentials
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS  lti1p0_credentials (
  credentials_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  lti_consumer_key VARCHAR(1000) NOT NULL,
  lti_client_secret VARCHAR(1000) NOT NULL,
  PRIMARY KEY (credentials_id))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table  lms
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS  lms (
  lms_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(200) NOT NULL,
  lti_version_id INT UNSIGNED NOT NULL,
  org_id INT UNSIGNED NOT NULL,
  credentials_id INT UNSIGNED NULL,
  PRIMARY KEY (lms_id),
  INDEX fk_lms_lti_version_version_id_idx (lti_version_id ASC),
  INDEX fk_lms_organisation_org_id_idx (org_id ASC),
  INDEX fk_lms_oauthv1p0_credentials_credentials_id_idx (credentials_id ASC),
  CONSTRAINT fk_lms_lti_version_version_id
  FOREIGN KEY (lti_version_id)
  REFERENCES  lti_version (version_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_lms_organisation_org_id
  FOREIGN KEY (org_id)
  REFERENCES  organisation (org_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_lms_oauthv1p0_credentials_credentials_id
  FOREIGN KEY (credentials_id)
  REFERENCES  lti1p0_credentials (credentials_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table  lms_origin
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS  lms_origin (
  origin_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  link VARCHAR(500) NOT NULL,
  lms_id INT UNSIGNED NOT NULL,
  PRIMARY KEY (origin_id),
  INDEX fk_lms_origin_lms_lms_id_idx (lms_id ASC),
  CONSTRAINT fk_lms_origin_lms_lms_id
  FOREIGN KEY (lms_id)
  REFERENCES  lms (lms_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table  lms_course
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS  lms_course (
  lms_course_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  course_id INT UNSIGNED NOT NULL,
  lms_id INT UNSIGNED NOT NULL,
  PRIMARY KEY (lms_course_id),
  INDEX fk_lms_course_course_course_id_idx (course_id ASC),
  INDEX fk_lms_course_lms_lms_id_idx (lms_id ASC),
  CONSTRAINT fk_lms_course_course_course_id
  FOREIGN KEY (course_id)
  REFERENCES  course (course_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_lms_course_lms_lms_id
  FOREIGN KEY (lms_id)
  REFERENCES  lms (lms_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table  result
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS  result (
  result_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  scheme_id INT UNSIGNED NOT NULL,
  user_id INT UNSIGNED NOT NULL,
  percent DOUBLE UNSIGNED NOT NULL,
  grade DOUBLE UNSIGNED NOT NULL,
  is_passed TINYINT(1) NOT NULL DEFAULT 0,
  session_ended TIMESTAMP NOT NULL,
  session_lasted INT NOT NULL,
  is_timeouted TINYINT(1) NOT NULL DEFAULT 0,
  lms_id INT UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (result_id),
  INDEX fk_result_scheme_scheme_id_idx (scheme_id ASC),
  INDEX fk_result_user_user_id_idx (user_id ASC),
  INDEX fk_result_lms1_idx (lms_id ASC),
  CONSTRAINT fk_result_scheme_scheme_id
  FOREIGN KEY (scheme_id)
  REFERENCES  scheme (scheme_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_result_user_user_id
  FOREIGN KEY (user_id)
  REFERENCES  user (user_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_result_lms1
  FOREIGN KEY (lms_id)
  REFERENCES  lms (lms_id)
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
  result_id  INT UNSIGNED NOT NULL,
  json_data  TEXT NOT NULL,
  when_remove  TIMESTAMP NOT NULL,
  PRIMARY KEY ( result_id ),
  INDEX  fk_result_details_result_result_id_idx  ( result_id  ASC),
  CONSTRAINT  fk_result_details_result_id
  FOREIGN KEY ( result_id )
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
-- Placeholder table for view   theme_type
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   theme_type  (org_id  INT, fac_id INT, dep_id  INT, course_id  INT,  theme_id  INT,  type_id  INT,  theme  INT,  type  INT,  L1  INT,  L2  INT,  L3  INT,  total  INT);

-- -----------------------------------------------------
-- Table   user_question_starred
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS   user_question_starred  (
  user_id  INT UNSIGNED NOT NULL,
  question_id  INT UNSIGNED NOT NULL,
  star  INT NOT NULL DEFAULT 0,
  PRIMARY KEY ( user_id ,  question_id ),
  INDEX  fk_user_question_starred_user_id_idx  ( user_id  ASC),
  CONSTRAINT  fk_user_question_starred_question_id
  FOREIGN KEY ( question_id )
  REFERENCES   question  ( question_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT  fk_user_question_starred_user_id
  FOREIGN KEY ( user_id )
  REFERENCES   user  ( user_id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table  group
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS  groups (
  group_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(200) NOT NULL,
  staff_id INT UNSIGNED NOT NULL,
  PRIMARY KEY (group_id),
  INDEX fk_group_staff_staff_id_idx (staff_id ASC),
  CONSTRAINT fk_group_staff_staff_id
  FOREIGN KEY (staff_id)
  REFERENCES  staff (staff_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table  student_group
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS  student_group (
  stud_id INT UNSIGNED NOT NULL,
  group_id INT UNSIGNED NOT NULL,
  PRIMARY KEY (stud_id, group_id),
  INDEX fk_student_group_group_group_id_idx (group_id ASC),
  CONSTRAINT fk_student_group_student_student_id
  FOREIGN KEY (stud_id)
  REFERENCES  student (stud_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_student_group_group_student_id
  FOREIGN KEY (group_id)
  REFERENCES  groups (group_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table  group_scheme
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS  group_scheme (
  group_id INT UNSIGNED NOT NULL,
  scheme_id INT UNSIGNED NOT NULL,
  is_enabled TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (group_id, scheme_id),
  INDEX fk_group_scheme_scheme_scheme_id_idx (scheme_id ASC),
  CONSTRAINT fk_group_scheme_group_group_id
  FOREIGN KEY (group_id)
  REFERENCES  groups (group_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_group_scheme_scheme_group_id
  FOREIGN KEY (scheme_id)
  REFERENCES  scheme (scheme_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB;

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