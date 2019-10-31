/*Questions Theme #1 (All MCQ) 10 pieces, full equipment*/

INSERT INTO question (title, level, type_id, theme_id, lang_id) VALUES ('Multiple choice question #1 T#1', 1, 1, 1, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id) VALUES ('Multiple choice question #2 T#1', 1, 1, 1, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id) VALUES ('Multiple choice question #3 T#1', 1, 1, 1, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id) VALUES ('Multiple choice question #4 T#1', 1, 1, 1, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id) VALUES ('Multiple choice question #5 T#1', 1, 1, 1, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id) VALUES ('Multiple choice question #6 T#1', 1, 1, 1, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id) VALUES ('Multiple choice question #7 T#1', 1, 1, 1, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id) VALUES ('Multiple choice question #8 T#1', 1, 1, 1, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id) VALUES ('Multiple choice question #9 T#1', 1, 1, 1, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id) VALUES ('Multiple choice question #10 T#1', 1, 1, 1, 1);

INSERT INTO help (name, text, staff_id) VALUES ('Help #1 MCQ#1 T#1', 'Please, refer to section #1 MCQ#1 T#1', 1);
INSERT INTO question_help (question_id, help_id) VALUES (1, 1);
INSERT INTO help (name, text, staff_id) VALUES ('Help #2 MCQ#2 T#1', 'Please, refer to section #2 MCQ#2 T#1', 1);
INSERT INTO question_help (question_id, help_id) VALUES (2, 2);
INSERT INTO help (name, text, staff_id) VALUES ('Help #3 MCQ#3 T#1', 'Please, refer to section #3 MCQ#3 T#1', 1);
INSERT INTO question_help (question_id, help_id) VALUES (3, 3);
INSERT INTO help (name, text, staff_id) VALUES ('Help #4 MCQ#4 T#1', 'Please, refer to section #4 MCQ#4 T#1', 1);
INSERT INTO question_help (question_id, help_id) VALUES (4, 4);
INSERT INTO help (name, text, staff_id) VALUES ('Help #5 MCQ#5 T#1', 'Please, refer to section #5 MCQ#5 T#1', 1);
INSERT INTO question_help (question_id, help_id) VALUES (5, 5);
INSERT INTO help (name, text, staff_id) VALUES ('Help #6 MCQ#6 T#1', 'Please, refer to section #6 MCQ#6 T#1', 1);
INSERT INTO question_help (question_id, help_id) VALUES (6, 6);
INSERT INTO help (name, text, staff_id) VALUES ('Help #7 MCQ#7 T#1', 'Please, refer to section #7 MCQ#7 T#1', 1);
INSERT INTO question_help (question_id, help_id) VALUES (7, 7);
INSERT INTO help (name, text, staff_id) VALUES ('Help #8 MCQ#8 T#1', 'Please, refer to section #8 MCQ#8 T#1', 1);
INSERT INTO question_help (question_id, help_id) VALUES (8, 8);
INSERT INTO help (name, text, staff_id) VALUES ('Help #9 MCQ#9 T#1', 'Please, refer to section #9 MCQ#9 T#1', 1);
INSERT INTO question_help (question_id, help_id) VALUES (9, 9);
INSERT INTO help (name, text, staff_id) VALUES ('Help #10 MCQ#10 T#1', 'Please, refer to section #10 MCQ#10 T#1', 1);
INSERT INTO question_help (question_id, help_id) VALUES (10, 10);

INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#01-MCQ#1-T#1.jpg', 'Resource #1 MCQ#1 T#1', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (1, 1);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#02-MCQ#2-T#1.jpg', 'Resource #2 MCQ#2 T#1', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (2, 2);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#03-MCQ#3-T#1.jpg', 'Resource #3 MCQ#3 T#1', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (3, 3);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#04-MCQ#4-T#1.jpg', 'Resource #4 MCQ#4 T#1', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (4, 4);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#05-MCQ#5-T#1.jpg', 'Resource #5 MCQ#5 T#1', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (5, 5);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#06-MCQ#6-T#1.jpg', 'Resource #6 MCQ#6 T#1', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (6, 6);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#07-MCQ#7-T#1.jpg', 'Resource #7 MCQ#7 T#1', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (7, 7);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#08-MCQ#8-T#1.jpg', 'Resource #8 MCQ#8 T#1', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (8, 8);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#09-MCQ#9-T#1.jpg', 'Resource #9 MCQ#9 T#1', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (9, 9);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#10-MCQ#10-T#1.jpg', 'Resource #10 MCQ#10 T#1', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (10, 10);

INSERT INTO answer_mcq (answer, percent, is_required, question_id)
VALUES ('Answer #1 Q#1 T#1 Correct (100!)', 100, 1, 1);
INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #2 Q#1 T#1', 0, 0, 1);
INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #3 Q#1 T#1', 0, 0, 1);
INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #4 Q#1 T#1', 0, 0, 1);

INSERT INTO answer_mcq (answer, percent, is_required, question_id)
VALUES ('Answer #1 Q#2 T#1 Correct (100!)', 100, 1, 2);
INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #2 Q#2 T#1', 0, 0, 2);
INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #3 Q#2 T#1', 0, 0, 2);
INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #4 Q#2 T#1', 0, 0, 2);

INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #1 Q#3 T#1 Correct (50!)', 50, 1, 3);
INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #2 Q#3 T#1 Correct (50!)', 50, 1, 3);
INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #3 Q#3 T#1', 0, 0, 3);
INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #4 Q#3 T#1', 0, 0, 3);

INSERT INTO answer_mcq (answer, percent, is_required, question_id)
VALUES ('Answer #1 Q#4 T#1 Correct (100!)', 100, 1, 4);
INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #2 Q#4 T#1', 0, 0, 4);
INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #3 Q#4 T#1', 0, 0, 4);
INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #4 Q#4 T#1', 0, 0, 4);

INSERT INTO answer_mcq (answer, percent, is_required, question_id)
VALUES ('Answer #1 Q#5 T#1 Correct (100!)', 100, 1, 5);
INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #2 Q#5 T#1', 0, 0, 5);
INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #3 Q#5 T#1', 0, 0, 5);
INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #4 Q#5 T#1', 0, 0, 5);

INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #1 Q#6 T#1 Correct (33!)', 33, 1, 6);
INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #2 Q#6 T#1 Correct (33!)', 33, 1, 6);
INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #3 Q#6 T#1 Correct (34)', 34, 0, 6);
INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #4 Q#6 T#1', 0, 0, 6);

INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #1 Q#7 T#1 Correct (50)', 50, 0, 7);
INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #2 Q#7 T#1 Correct (50)', 50, 0, 7);
INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #3 Q#7 T#1', 0, 0, 7);
INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #4 Q#7 T#1', 0, 0, 7);

INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #1 Q#8 T#1 Correct (33)', 33, 0, 8);
INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #2 Q#8 T#1 Correct (33)', 33, 0, 8);
INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #3 Q#8 T#1 Correct (34)', 34, 0, 8);
INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #4 Q#8 T#1', 0, 0, 8);

INSERT INTO answer_mcq (answer, percent, is_required, question_id)
VALUES ('Answer #1 Q#9 T#1 Correct (100!)', 100, 1, 9);
INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #2 Q#9 T#1', 0, 0, 9);
INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #3 Q#9 T#1', 0, 0, 9);
INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #4 Q#9 T#1', 0, 0, 9);

INSERT INTO answer_mcq (answer, percent, is_required, question_id)
VALUES ('Answer #1 Q#10 T#1 Correct (100!)', 100, 1, 10);
INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #2 Q#10 T#1', 0, 0, 10);
INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #3 Q#10 T#1', 0, 0, 10);
INSERT INTO answer_mcq (answer, percent, is_required, question_id) VALUES ('Answer #4 Q#10 T#1', 0, 0, 10);

INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#11-Answer#1-MCQ#1-T#1.jpg', 'Resource #11 Answer#1 MCQ#1 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (1, 11);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#12-Answer#2-MCQ#1-T#1.jpg', 'Resource #12 Answer#2 MCQ#1 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (2, 12);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#13-Answer#3-MCQ#1-T#1.jpg', 'Resource #13 Answer#3 MCQ#1 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (3, 13);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#14-Answer#4-MCQ#1-T#1.jpg', 'Resource #14 Answer#4 MCQ#1 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (4, 14);

INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#15-Answer#1-MCQ#2-T#1.jpg', 'Resource #15 Answer#1 MCQ#2 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (5, 15);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#16-Answer#2-MCQ#2-T#1.jpg', 'Resource #16 Answer#2 MCQ#2 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (6, 16);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#17-Answer#3-MCQ#2-T#1.jpg', 'Resource #17 Answer#3 MCQ#2 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (7, 17);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#18-Answer#4-MCQ#2-T#1.jpg', 'Resource #18 Answer#4 MCQ#2 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (8, 18);

INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#19-Answer#1-MCQ#3-T#1.jpg', 'Resource #19 Answer#1 MCQ#3 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (9, 19);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#20-Answer#2-MCQ#3-T#1.jpg', 'Resource #20 Answer#2 MCQ#3 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (10, 20);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#21-Answer#3-MCQ#3-T#1.jpg', 'Resource #20 Answer#3 MCQ#3 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (11, 21);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#22-Answer#4-MCQ#3-T#1.jpg', 'Resource #22 Answer#4 MCQ#3 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (12, 22);

INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#23-Answer#1-MCQ#4-T#1.jpg', 'Resource #23 Answer#1 MCQ#4 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (13, 23);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#24-Answer#2-MCQ#4-T#1.jpg', 'Resource #24 Answer#2 MCQ#4 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (14, 24);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#25-Answer#3-MCQ#4-T#1.jpg', 'Resource #24 Answer#3 MCQ#4 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (15, 25);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#26-Answer#4-MCQ#4-T#1.jpg', 'Resource #26 Answer#4 MCQ#4 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (16, 26);

INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#27-Answer#1-MCQ#5-T#1.jpg', 'Resource #27 Answer#1 MCQ#5 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (17, 27);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#28-Answer#2-MCQ#5-T#1.jpg', 'Resource #28 Answer#2 MCQ#5 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (18, 28);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#29-Answer#3-MCQ#5-T#1.jpg', 'Resource #29 Answer#3 MCQ#5 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (19, 29);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#30-Answer#4-MCQ#5-T#1.jpg', 'Resource #30 Answer#4 MCQ#5 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (20, 30);

INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#31-Answer#1-MCQ#6-T#1.jpg', 'Resource #31 Answer#1 MCQ#6 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (21, 31);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#31-Answer#2-MCQ#6-T#1.jpg', 'Resource #32 Answer#2 MCQ#6 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (22, 32);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#33-Answer#3-MCQ#6-T#1.jpg', 'Resource #33 Answer#3 MCQ#6 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (23, 33);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#34-Answer#4-MCQ#6-T#1.jpg', 'Resource #34 Answer#4 MCQ#6 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (24, 34);

INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#35-Answer#1-MCQ#7-T#1.jpg', 'Resource #35 Answer#1 MCQ#7 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (25, 35);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#36-Answer#2-MCQ#7-T#1.jpg', 'Resource #36 Answer#2 MCQ#7 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (26, 36);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#37-Answer#3-MCQ#7-T#1.jpg', 'Resource #37 Answer#3 MCQ#7 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (27, 37);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#38-Answer#4-MCQ#7-T#1.jpg', 'Resource #38 Answer#4 MCQ#7 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (28, 38);

INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#39-Answer#1-MCQ#8-T#1.jpg', 'Resource #39 Answer#1 MCQ#8 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (29, 39);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#40-Answer#2-MCQ#8-T#1.jpg', 'Resource #40 Answer#2 MCQ#8 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (30, 40);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#41-Answer#3-MCQ#8-T#1.jpg', 'Resource #41 Answer#3 MCQ#8 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (31, 41);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#42-Answer#4-MCQ#8-T#1.jpg', 'Resource #42 Answer#4 MCQ#8 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (32, 42);

INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#43-Answer#1-MCQ#9-T#1.jpg', 'Resource #43 Answer#1 MCQ#9 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (33, 43);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#44-Answer#2-MCQ#9-T#1.jpg', 'Resource #44 Answer#2 MCQ#9 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (34, 44);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#45-Answer#3-MCQ#9-T#1.jpg', 'Resource #45 Answer#3 MCQ#9 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (35, 45);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#46-Answer#4-MCQ#9-T#1.jpg', 'Resource #46 Answer#4 MCQ#9 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (36, 46);

INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#47-Answer#1-MCQ#10-T#1.jpg', 'Resource #47 Answer#1 MCQ#10 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (37, 47);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#48-Answer#2-MCQ#10-T#1.jpg', 'Resource #48 Answer#2 MCQ#10 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (38, 48);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#49-Answer#3-MCQ#10-T#1.jpg', 'Resource #49 Answer#3 MCQ#10 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (39, 49);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#50-Answer#4-MCQ#10-T#1.jpg', 'Resource #50 Answer#4 MCQ#10 T#1', 1);
INSERT INTO answer_mcq_resource (answer_id, resource_id) VALUES (40, 50);


/*Questions Theme #2 (All FBSQ) 10 pieces, full equipment*/

INSERT INTO question (title, level, type_id, theme_id, lang_id)
VALUES ('Fill blank single question #1 T#2 (phrase = wolf)', 1, 2, 2, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id)
VALUES ('Fill blank single question #2 T#2 (phrase = bear)', 1, 2, 2, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id)
VALUES ('Fill blank single question #3 T#2 (phrase = duck)', 1, 2, 2, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id)
VALUES ('Fill blank single question #4 T#2 (phrase = cow)', 1, 2, 2, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id)
VALUES ('Fill blank single question #5 T#2 (phrase = dog)', 1, 2, 2, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id)
VALUES ('Fill blank single question #6 T#2 (phrase = lion)', 1, 2, 2, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id)
VALUES ('Fill blank single question #7 T#2 (phrase = hypo)', 1, 2, 2, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id)
VALUES ('Fill blank single question #8 T#2 (phrase = tiger)', 1, 2, 2, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id)
VALUES ('Fill blank single question #9 T#2 (phrase = cat)', 1, 2, 2, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id)
VALUES ('Fill blank single question #10 T#2 (phrase = fox)', 1, 2, 2, 1);

INSERT INTO help (name, text, staff_id) VALUES ('Help #11 FBSQ#1 T#2', 'Please, refer to section #1 FBSQ#1 T#2', 1);
INSERT INTO question_help (question_id, help_id) VALUES (11, 11);
INSERT INTO help (name, text, staff_id) VALUES ('Help #12 FBSQ#2 T#2', 'Please, refer to section #2 FBSQ#2 T#2', 1);
INSERT INTO question_help (question_id, help_id) VALUES (12, 12);
INSERT INTO help (name, text, staff_id) VALUES ('Help #13 FBSQ#3 T#2', 'Please, refer to section #3 FBSQ#3 T#2', 1);
INSERT INTO question_help (question_id, help_id) VALUES (13, 13);
INSERT INTO help (name, text, staff_id) VALUES ('Help #14 FBSQ#4 T#2', 'Please, refer to section #4 FBSQ#4 T#2', 1);
INSERT INTO question_help (question_id, help_id) VALUES (14, 14);
INSERT INTO help (name, text, staff_id) VALUES ('Help #15 FBSQ#5 T#2', 'Please, refer to section #5 FBSQ#5 T#2', 1);
INSERT INTO question_help (question_id, help_id) VALUES (15, 15);
INSERT INTO help (name, text, staff_id) VALUES ('Help #16 FBSQ#6 T#2', 'Please, refer to section #6 FBSQ#6 T#2', 1);
INSERT INTO question_help (question_id, help_id) VALUES (16, 16);
INSERT INTO help (name, text, staff_id) VALUES ('Help #17 FBSQ#7 T#2', 'Please, refer to section #7 FBSQ#7 T#2', 1);
INSERT INTO question_help (question_id, help_id) VALUES (17, 17);
INSERT INTO help (name, text, staff_id) VALUES ('Help #18 FBSQ#8 T#2', 'Please, refer to section #8 FBSQ#8 T#2', 1);
INSERT INTO question_help (question_id, help_id) VALUES (18, 18);
INSERT INTO help (name, text, staff_id) VALUES ('Help #19 FBSQ#9 T#2', 'Please, refer to section #9 FBSQ#9 T#2', 1);
INSERT INTO question_help (question_id, help_id) VALUES (19, 19);
INSERT INTO help (name, text, staff_id) VALUES ('Help #20 FBSQ#100 T#2', 'Please, refer to section #10 FBSQ#10 T#2', 1);
INSERT INTO question_help (question_id, help_id) VALUES (20, 20);

INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#51-FBSQ#1-T#2.jpg', 'Resource #51 FBSQ#1 T#2', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (11, 51);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#52-FBSQ#2-T#2.jpg', 'Resource #51 FBSQ#2 T#2', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (12, 52);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#53-FBSQ#3-T#2.jpg', 'Resource #51 FBSQ#3 T#2', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (13, 53);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#54-FBSQ#4-T#2.jpg', 'Resource #51 FBSQ#4 T#2', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (14, 54);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#55-FBSQ#5-T#2.jpg', 'Resource #51 FBSQ#5 T#2', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (15, 55);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#56-FBSQ#6-T#2.jpg', 'Resource #51 FBSQ#6 T#2', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (16, 56);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#57-FBSQ#7-T#2.jpg', 'Resource #51 FBSQ#7 T#2', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (17, 57);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#58-FBSQ#8-T#2.jpg', 'Resource #51 FBSQ#8 T#2', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (18, 58);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#59-FBSQ#9-T#2.jpg', 'Resource #51 FBSQ#9 T#2', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (19, 59);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#60-FBSQ#10-T#2.jpg', 'Resource #51 FBSQ#10 T#2', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (20, 60);

INSERT INTO answer_fbsq (answer_id, set_id) VALUES (11, 1);
INSERT INTO answer_fbsq (answer_id, set_id) VALUES (12, 1);
INSERT INTO answer_fbsq (answer_id, set_id) VALUES (13, 1);
INSERT INTO answer_fbsq (answer_id, set_id) VALUES (14, 1);
INSERT INTO answer_fbsq (answer_id, set_id) VALUES (15, 1);
INSERT INTO answer_fbsq (answer_id, set_id) VALUES (16, 1);
INSERT INTO answer_fbsq (answer_id, set_id) VALUES (17, 1);
INSERT INTO answer_fbsq (answer_id, set_id) VALUES (18, 1);
INSERT INTO answer_fbsq (answer_id, set_id) VALUES (19, 1);
INSERT INTO answer_fbsq (answer_id, set_id) VALUES (20, 1);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('wolf', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbsq_phrase (phrase_id, answer_id) VALUES (1, 11);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('wolf+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbsq_phrase (phrase_id, answer_id) VALUES (2, 11);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('wolf-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbsq_phrase (phrase_id, answer_id) VALUES (3, 11);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('bear', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbsq_phrase (phrase_id, answer_id) VALUES (4, 12);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('bear+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbsq_phrase (phrase_id, answer_id) VALUES (5, 12);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('bear-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbsq_phrase (phrase_id, answer_id) VALUES (6, 12);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('duck', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbsq_phrase (phrase_id, answer_id) VALUES (7, 13);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('duck+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbsq_phrase (phrase_id, answer_id) VALUES (8, 13);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('duck-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbsq_phrase (phrase_id, answer_id) VALUES (9, 13);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('cow', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbsq_phrase (phrase_id, answer_id) VALUES (10, 14);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('cow+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbsq_phrase (phrase_id, answer_id) VALUES (11, 14);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('cow-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbsq_phrase (phrase_id, answer_id) VALUES (12, 14);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('dog', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbsq_phrase (phrase_id, answer_id) VALUES (13, 15);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('dog+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbsq_phrase (phrase_id, answer_id) VALUES (14, 15);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('dog-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbsq_phrase (phrase_id, answer_id) VALUES (15, 15);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('lion', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbsq_phrase (phrase_id, answer_id) VALUES (16, 16);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('lion+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbsq_phrase (phrase_id, answer_id) VALUES (17, 16);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('lion-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbsq_phrase (phrase_id, answer_id) VALUES (18, 16);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('hypo', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbsq_phrase (phrase_id, answer_id) VALUES (19, 17);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('hypo+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbsq_phrase (phrase_id, answer_id) VALUES (20, 17);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('hypo-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbsq_phrase (phrase_id, answer_id) VALUES (21, 17);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('tiger', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbsq_phrase (phrase_id, answer_id) VALUES (22, 18);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('tiger+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbsq_phrase (phrase_id, answer_id) VALUES (23, 18);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('tiger-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbsq_phrase (phrase_id, answer_id) VALUES (24, 18);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('cat', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbsq_phrase (phrase_id, answer_id) VALUES (25, 19);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('cat+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbsq_phrase (phrase_id, answer_id) VALUES (26, 19);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('cat-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbsq_phrase (phrase_id, answer_id) VALUES (27, 19);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('fox', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbsq_phrase (phrase_id, answer_id) VALUES (28, 20);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('fox+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbsq_phrase (phrase_id, answer_id) VALUES (29, 20);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('fox-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbsq_phrase (phrase_id, answer_id) VALUES (30, 20);


/*Questions Theme #3 (All FBMQ) 10 pieces, full equipment*/

INSERT INTO question (title, level, type_id, theme_id, lang_id)
VALUES ('Fill blank multiple question #1 T#3 (phrases = {tree, grass, bush})', 1, 3, 3, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id)
VALUES ('Fill blank multiple question #2 T#3 (phrases = {sky, wind, rain})', 1, 3, 3, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id)
VALUES ('Fill blank multiple question #3 T#3 (phrases = {water, sun, sand})', 1, 3, 3, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id)
VALUES ('Fill blank multiple question #4 T#3 (phrases = {train, bus, plane})', 1, 3, 3, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id)
VALUES ('Fill blank multiple question #5 T#3 (phrases = {gas, fire, ice})', 1, 3, 3, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id)
VALUES ('Fill blank multiple question #6 T#3 (phrases = {fish, meat, lard})', 1, 3, 3, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id)
VALUES ('Fill blank multiple question #7 T#3 (phrases = {chair, table, stool})', 1, 3, 3, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id)
VALUES ('Fill blank multiple question #8 T#3 (phrases = {candy, sweets, chocolate})', 1, 3, 3, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id)
VALUES ('Fill blank multiple question #9 T#3 (phrases = {bottle, glass, jar})', 1, 3, 3, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id)
VALUES ('Fill blank multiple question #10 T#3 (phrases = {heart, soul, love})', 1, 3, 3, 1);

INSERT INTO help (name, text, staff_id) VALUES ('Help #21 FBMQ#1 T#3', 'Please, refer to section #1 FBMQ#1 T#3', 1);
INSERT INTO question_help (question_id, help_id) VALUES (21, 21);
INSERT INTO help (name, text, staff_id) VALUES ('Help #22 FBMQ#2 T#3', 'Please, refer to section #2 FBMQ#2 T#3', 1);
INSERT INTO question_help (question_id, help_id) VALUES (22, 22);
INSERT INTO help (name, text, staff_id) VALUES ('Help #23 FBMQ#3 T#3', 'Please, refer to section #3 FBMQ#3 T#3', 1);
INSERT INTO question_help (question_id, help_id) VALUES (23, 23);
INSERT INTO help (name, text, staff_id) VALUES ('Help #24 FBMQ#4 T#3', 'Please, refer to section #4 FBMQ#4 T#3', 1);
INSERT INTO question_help (question_id, help_id) VALUES (24, 24);
INSERT INTO help (name, text, staff_id) VALUES ('Help #25 FBMQ#5 T#3', 'Please, refer to section #5 FBMQ#5 T#3', 1);
INSERT INTO question_help (question_id, help_id) VALUES (25, 25);
INSERT INTO help (name, text, staff_id) VALUES ('Help #26 FBMQ#6 T#3', 'Please, refer to section #6 FBMQ#6 T#3', 1);
INSERT INTO question_help (question_id, help_id) VALUES (26, 26);
INSERT INTO help (name, text, staff_id) VALUES ('Help #27 FBMQ#7 T#3', 'Please, refer to section #7 FBMQ#7 T#3', 1);
INSERT INTO question_help (question_id, help_id) VALUES (27, 27);
INSERT INTO help (name, text, staff_id) VALUES ('Help #28 FBMQ#8 T#3', 'Please, refer to section #8 FBMQ#8 T#3', 1);
INSERT INTO question_help (question_id, help_id) VALUES (28, 28);
INSERT INTO help (name, text, staff_id) VALUES ('Help #29 FBMQ#9 T#3', 'Please, refer to section #9 FBMQ#9 T#3', 1);
INSERT INTO question_help (question_id, help_id) VALUES (29, 29);
INSERT INTO help (name, text, staff_id) VALUES ('Help #30 FBMQ#10 T#3', 'Please, refer to section #10 FBMQ#10 T#3', 1);
INSERT INTO question_help (question_id, help_id) VALUES (30, 30);

INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#61-FBMQ#1-T#3.jpg', 'Resource #61 FBMQ#1 T#3', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (21, 61);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#62-FBMQ#2-T#3.jpg', 'Resource #62 FBMQ#2 T#3', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (22, 62);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#63-FBMQ#3-T#3.jpg', 'Resource #63 FBMQ#3 T#3', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (23, 63);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#64-FBMQ#4-T#3.jpg', 'Resource #64 FBMQ#4 T#3', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (24, 64);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#65-FBMQ#5-T#3.jpg', 'Resource #65 FBMQ#5 T#3', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (25, 65);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#66-FBMQ#6-T#3.jpg', 'Resource #66 FBMQ#6 T#3', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (26, 66);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#67-FBMQ#7-T#3.jpg', 'Resource #67 FBMQ#7 T#3', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (27, 67);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#68-FBMQ#8-T#3.jpg', 'Resource #68 FBMQ#8 T#3', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (28, 68);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#69-FBMQ#9-T#3.jpg', 'Resource #69 FBMQ#9 T#3', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (29, 69);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#70-FBMQ#10-T#3.jpg', 'Resource #70 FBMQ#10 T#3', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (30, 70);


INSERT INTO answer_fbmq (phrase, occurrence, set_id, question_id) VALUES ('tree', 1, 1, 21);
INSERT INTO answer_fbmq (phrase, occurrence, set_id, question_id) VALUES ('grass', 1, 1, 21);
INSERT INTO answer_fbmq (phrase, occurrence, set_id, question_id) VALUES ('bush', 1, 1, 21);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('tree-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (31, 1);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('tree+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (32, 1);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('grass-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (33, 2);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('grass+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (34, 2);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('bush-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (35, 3);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('bush+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (36, 3);


INSERT INTO answer_fbmq (phrase, occurrence, set_id, question_id) VALUES ('sky', 1, 1, 22);
INSERT INTO answer_fbmq (phrase, occurrence, set_id, question_id) VALUES ('wind', 1, 1, 22);
INSERT INTO answer_fbmq (phrase, occurrence, set_id, question_id) VALUES ('rain', 1, 1, 22);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('sky-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (37, 4);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('sky+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (38, 4);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('wind-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (39, 5);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('wind+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (40, 5);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('rain-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (41, 6);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('rain+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (42, 6);


INSERT INTO answer_fbmq (phrase, occurrence, set_id, question_id) VALUES ('water', 1, 1, 23);
INSERT INTO answer_fbmq (phrase, occurrence, set_id, question_id) VALUES ('sun', 1, 1, 23);
INSERT INTO answer_fbmq (phrase, occurrence, set_id, question_id) VALUES ('sand', 1, 1, 23);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('water-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (43, 7);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('water+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (44, 7);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('sun-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (45, 8);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('sun+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (46, 8);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('sand-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (47, 9);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('sand+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (48, 9);


INSERT INTO answer_fbmq (phrase, occurrence, set_id, question_id) VALUES ('train', 1, 1, 24);
INSERT INTO answer_fbmq (phrase, occurrence, set_id, question_id) VALUES ('bus', 1, 1, 24);
INSERT INTO answer_fbmq (phrase, occurrence, set_id, question_id) VALUES ('plane', 1, 1, 24);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('train-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (49, 10);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('train+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (50, 10);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('bus-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (51, 11);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('bus+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (52, 11);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('plane-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (53, 12);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('plane+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (54, 12);


INSERT INTO answer_fbmq (phrase, occurrence, set_id, question_id) VALUES ('gas', 1, 1, 25);
INSERT INTO answer_fbmq (phrase, occurrence, set_id, question_id) VALUES ('fire', 1, 1, 25);
INSERT INTO answer_fbmq (phrase, occurrence, set_id, question_id) VALUES ('ice', 1, 1, 25);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('gas-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (55, 13);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('gas+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (56, 13);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('fire-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (57, 14);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('fire+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (58, 14);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('ice-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (59, 15);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('ice+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (60, 15);


INSERT INTO answer_fbmq (phrase, occurrence, set_id, question_id) VALUES ('fish', 1, 1, 26);
INSERT INTO answer_fbmq (phrase, occurrence, set_id, question_id) VALUES ('meat', 1, 1, 26);
INSERT INTO answer_fbmq (phrase, occurrence, set_id, question_id) VALUES ('lard', 1, 1, 26);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('fish-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (61, 16);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('fish+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (62, 16);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('meat-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (63, 17);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('meat+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (64, 17);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('lard-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (65, 18);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('lard+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (66, 18);


INSERT INTO answer_fbmq (phrase, occurrence, set_id, question_id) VALUES ('chair', 1, 1, 27);
INSERT INTO answer_fbmq (phrase, occurrence, set_id, question_id) VALUES ('table', 1, 1, 27);
INSERT INTO answer_fbmq (phrase, occurrence, set_id, question_id) VALUES ('stool', 1, 1, 27);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('chair-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (67, 19);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('chair+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (68, 19);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('table-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (69, 20);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('table+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (70, 20);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('stool-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (71, 21);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('stool+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (72, 21);


INSERT INTO answer_fbmq (phrase, occurrence, set_id, question_id) VALUES ('candy', 1, 1, 28);
INSERT INTO answer_fbmq (phrase, occurrence, set_id, question_id) VALUES ('sweets', 1, 1, 28);
INSERT INTO answer_fbmq (phrase, occurrence, set_id, question_id) VALUES ('chocolate', 1, 1, 28);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('candy-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (73, 22);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('candy+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (74, 22);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('sweets-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (75, 23);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('sweets+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (76, 23);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('chocolate-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (77, 24);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('chocolate+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (78, 24);


INSERT INTO answer_fbmq (phrase, occurrence, set_id, question_id) VALUES ('bottle', 1, 1, 29);
INSERT INTO answer_fbmq (phrase, occurrence, set_id, question_id) VALUES ('glass', 1, 1, 29);
INSERT INTO answer_fbmq (phrase, occurrence, set_id, question_id) VALUES ('jar', 1, 1, 29);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('bottle-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (79, 25);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('bottle+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (80, 25);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('glass-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (81, 26);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('glass+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (82, 26);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('jar-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (83, 27);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('jar+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (84, 27);


INSERT INTO answer_fbmq (phrase, occurrence, set_id, question_id) VALUES ('heart', 1, 1, 30);
INSERT INTO answer_fbmq (phrase, occurrence, set_id, question_id) VALUES ('soul', 1, 1, 30);
INSERT INTO answer_fbmq (phrase, occurrence, set_id, question_id) VALUES ('love', 1, 1, 30);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('heart-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (85, 28);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('heart+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (86, 28);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('soul-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (87, 29);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('soul+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (88, 29);

INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('love-', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (89, 30);
INSERT INTO phrase (phrase, staff_id, last_used) VALUES ('love+', 1, '2018-07-18 10:30:15.999999999');
INSERT INTO fbmq_phrase (phrase_id, answer_id) VALUES (90, 30);


/*Questions Theme #4 (All MQ) 10 pieces, full equipment*/

INSERT INTO question (title, level, type_id, theme_id, lang_id) VALUES ('Matcher question #1 T#4', 1, 4, 4, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id) VALUES ('Matcher question #2 T#4', 1, 4, 4, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id) VALUES ('Matcher question #3 T#4', 1, 4, 4, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id) VALUES ('Matcher question #4 T#4', 1, 4, 4, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id) VALUES ('Matcher question #5 T#4', 1, 4, 4, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id) VALUES ('Matcher question #6 T#4', 1, 4, 4, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id) VALUES ('Matcher question #7 T#4', 1, 4, 4, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id) VALUES ('Matcher question #8 T#4', 1, 4, 4, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id) VALUES ('Matcher question #9 T#4', 1, 4, 4, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id) VALUES ('Matcher question #10 T#4', 1, 4, 4, 1);

INSERT INTO help (name, text, staff_id) VALUES ('Help #31 MQ#1 T#4', 'Please, refer to section #1 MQ#1 T#4', 1);
INSERT INTO question_help (question_id, help_id) VALUES (31, 31);
INSERT INTO help (name, text, staff_id) VALUES ('Help #32 MQ#2 T#4', 'Please, refer to section #2 MQ#2 T#4', 1);
INSERT INTO question_help (question_id, help_id) VALUES (32, 32);
INSERT INTO help (name, text, staff_id) VALUES ('Help #33 MQ#3 T#4', 'Please, refer to section #3 MQ#3 T#4', 1);
INSERT INTO question_help (question_id, help_id) VALUES (33, 33);
INSERT INTO help (name, text, staff_id) VALUES ('Help #34 MQ#4 T#4', 'Please, refer to section #4 MQ#4 T#4', 1);
INSERT INTO question_help (question_id, help_id) VALUES (34, 34);
INSERT INTO help (name, text, staff_id) VALUES ('Help #35 MQ#5 T#4', 'Please, refer to section #5 MQ#5 T#4', 1);
INSERT INTO question_help (question_id, help_id) VALUES (35, 35);
INSERT INTO help (name, text, staff_id) VALUES ('Help #36 MQ#6 T#4', 'Please, refer to section #6 MQ#6 T#4', 1);
INSERT INTO question_help (question_id, help_id) VALUES (36, 36);
INSERT INTO help (name, text, staff_id) VALUES ('Help #37 MQ#7 T#4', 'Please, refer to section #7 MQ#7 T#4', 1);
INSERT INTO question_help (question_id, help_id) VALUES (37, 37);
INSERT INTO help (name, text, staff_id) VALUES ('Help #38 MQ#8 T#4', 'Please, refer to section #8 MQ#8 T#4', 1);
INSERT INTO question_help (question_id, help_id) VALUES (38, 38);
INSERT INTO help (name, text, staff_id) VALUES ('Help #39 MQ#9 T#4', 'Please, refer to section #9 MQ#9 T#4', 1);
INSERT INTO question_help (question_id, help_id) VALUES (39, 39);
INSERT INTO help (name, text, staff_id) VALUES ('Help #40 MQ#10 T#4', 'Please, refer to section #10 MQ#10 T#4', 1);
INSERT INTO question_help (question_id, help_id) VALUES (40, 40);

INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#71-MQ#1-T#4.jpg', 'Resource #71 MQ#1 T#4', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (31, 71);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#72-MQ#2-T#4.jpg', 'Resource #72 MQ#2 T#4', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (32, 72);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#73-MQ#3-T#4.jpg', 'Resource #73 MQ#3 T#4', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (33, 73);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#74-MQ#4-T#4.jpg', 'Resource #74 MQ#4 T#4', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (34, 74);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#75-MQ#5-T#4.jpg', 'Resource #75 MQ#5 T#4', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (35, 75);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#76-MQ#6-T#4.jpg', 'Resource #76 MQ#6 T#4', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (36, 76);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#77-MQ#7-T#4.jpg', 'Resource #77 MQ#7 T#4', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (37, 77);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#78-MQ#8-T#4.jpg', 'Resource #78 MQ#8 T#4', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (38, 78);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#79-MQ#9-T#4.jpg', 'Resource #79 MQ#9 T#4', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (39, 79);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#80-MQ#10-T#4.jpg', 'Resource #80 MQ#10 T#4', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (40, 80);

/* Q#1 answers*/

INSERT INTO phrase (phrase, staff_id) VALUES ('white', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('black', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#81-Answer#1-MQ#1-T#4.jpg', 'Resource #81 Answer#1 MQ#1 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (92, 81);

INSERT INTO phrase (phrase, staff_id) VALUES ('heaven', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('earth', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#82-Answer#2-MQ#1-T#4.jpg', 'Resource #82 Answer#2 MQ#1 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (94, 82);

INSERT INTO phrase (phrase, staff_id) VALUES ('sea', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('ground', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#83-Answer#3-MQ#1-T#4.jpg', 'Resource #83 Answer#3 MQ#1 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (96, 83);

INSERT INTO phrase (phrase, staff_id) VALUES ('air', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('fire', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#84-Answer#4-MQ#1-T#4.jpg', 'Resource #84 Answer#4 MQ#1 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (98, 84);

INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (91, 92, 31);
INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (93, 94, 31);
INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (95, 96, 31);
INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (97, 98, 31);


/* Q#2 answers*/
INSERT INTO phrase (phrase, staff_id) VALUES ('mount', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('plain', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#85-Answer#1-MQ#2-T#4.jpg', 'Resource #85 Answer#1 MQ#2 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (100, 85);

INSERT INTO phrase (phrase, staff_id) VALUES ('fruit', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('vegetable', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#86-Answer#2-MQ#2-T#4.jpg', 'Resource #86 Answer#2 MQ#2 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (102, 86);

INSERT INTO phrase (phrase, staff_id) VALUES ('sweet', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('bitter', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#87-Answer#3-MQ#2-T#4.jpg', 'Resource #87 Answer#3 MQ#2 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (104, 87);

INSERT INTO phrase (phrase, staff_id) VALUES ('man', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('woman', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#88-Answer#4-MQ#2-T#4.jpg', 'Resource #88 Answer#4 MQ#2 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (106, 88);

INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (99, 100, 32);
INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (101, 102, 32);
INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (103, 104, 32);
INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (105, 106, 32);


/* Q#3 answers*/

INSERT INTO phrase (phrase, staff_id) VALUES ('rich', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('poor', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#89-Answer#1-MQ#3-T#4.jpg', 'Resource #89 Answer#1 MQ#3 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (108, 89);

INSERT INTO phrase (phrase, staff_id) VALUES ('big', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('small', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#90-Answer#2-MQ#3-T#4.jpg', 'Resource #90 Answer#2 MQ#3 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (110, 90);

INSERT INTO phrase (phrase, staff_id) VALUES ('cats', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('dogs', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#91-Answer#3-MQ#3-T#4.jpg', 'Resource #91 Answer#3 MQ#3 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (112, 91);

INSERT INTO phrase (phrase, staff_id) VALUES ('weak', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('strong', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#92-Answer#4-MQ#3-T#4.jpg', 'Resource #92 Answer#4 MQ#3 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (114, 92);

INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (107, 108, 33);
INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (109, 110, 33);
INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (111, 112, 33);
INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (113, 114, 33);


/* Q#4 answers*/
INSERT INTO phrase (phrase, staff_id) VALUES ('healthy', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('ill', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#93-Answer#1-MQ#4-T#4.jpg', 'Resource #93 Answer#1 MQ#4 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (116, 93);

INSERT INTO phrase (phrase, staff_id) VALUES ('dark', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('light', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#94-Answer#2-MQ#4-T#4.jpg', 'Resource #94 Answer#2 MQ#4 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (118, 94);

INSERT INTO phrase (phrase, staff_id) VALUES ('sugar', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('salt', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#95-Answer#3-MQ#4-T#4.jpg', 'Resource #95 Answer#3 MQ#4 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (120, 95);

INSERT INTO phrase (phrase, staff_id) VALUES ('kind', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('angry', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#96-Answer#4-MQ#4-T#4.jpg', 'Resource #96 Answer#4 MQ#4 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (122, 96);

INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (115, 116, 34);
INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (117, 118, 34);
INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (119, 120, 34);
INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (121, 122, 34);


/* Q#5 answers*/
INSERT INTO phrase (phrase, staff_id) VALUES ('smart', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('stupid', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#97-Answer#1-MQ#5-T#4.jpg', 'Resource #97 Answer#1 MQ#5 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (124, 97);

INSERT INTO phrase (phrase, staff_id) VALUES ('expensive', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('cheap', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#98-Answer#2-MQ#5-T#4.jpg', 'Resource #98 Answer#2 MQ#5 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (126, 98);

INSERT INTO phrase (phrase, staff_id) VALUES ('fast', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('slow', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#99-Answer#3-MQ#5-T#4.jpg', 'Resource #99 Answer#3 MQ#5 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (128, 99);

INSERT INTO phrase (phrase, staff_id) VALUES ('river', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('lake', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#100-Answer#4-MQ#5-T#4.jpg', 'Resource #100 Answer#4 MQ#5 T#4', 1);

INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (130, 100);

INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (123, 124, 35);
INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (125, 126, 35);
INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (127, 128, 35);
INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (129, 130, 35);


/* Q#6 answers*/
INSERT INTO phrase (phrase, staff_id) VALUES ('city', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('village', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#101-Answer#1-MQ#6-T#4.jpg', 'Resource #101 Answer#1 MQ#6 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (132, 101);

INSERT INTO phrase (phrase, staff_id) VALUES ('dollar', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('euro', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#102-Answer#2-MQ#6-T#4.jpg', 'Resource #102 Answer#2 MQ#6 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (134, 102);

INSERT INTO phrase (phrase, staff_id) VALUES ('heart', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('soul', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#103-Answer#3-MQ#6-T#4.jpg', 'Resource #103 Answer#3 MQ#6 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (136, 103);

INSERT INTO phrase (phrase, staff_id) VALUES ('cold winter', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('hot summer', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#104-Answer#4-MQ#6-T#4.jpg', 'Resource #104 Answer#4 MQ#6 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (138, 104);

INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (131, 132, 36);
INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (133, 134, 36);
INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (135, 136, 36);
INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (137, 138, 36);


/* Q#7 answers*/
INSERT INTO phrase (phrase, staff_id) VALUES ('long', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('short', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#105-Answer#1-MQ#7-T#4.jpg', 'Resource #105 Answer#1 MQ#7 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (140, 105);

INSERT INTO phrase (phrase, staff_id) VALUES ('walk', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('ride', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#106-Answer#2-MQ#7-T#4.jpg', 'Resource #106 Answer#2 MQ#7 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (142, 106);

INSERT INTO phrase (phrase, staff_id) VALUES ('wood', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('steel', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#107-Answer#3-MQ#7-T#4.jpg', 'Resource #107 Answer#3 MQ#7 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (144, 107);

INSERT INTO phrase (phrase, staff_id) VALUES ('art', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('technology', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#108-Answer#4-MQ#7-T#4.jpg', 'Resource #108 Answer#4 MQ#7 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (146, 108);

INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (139, 140, 37);
INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (141, 142, 37);
INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (143, 144, 37);
INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (145, 146, 37);


/* Q#8 answers*/
INSERT INTO phrase (phrase, staff_id) VALUES ('work', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('relax', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#109-Answer#1-MQ#8-T#4.jpg', 'Resource #109 Answer#1 MQ#8 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (148, 109);

INSERT INTO phrase (phrase, staff_id) VALUES ('warm', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('cold', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#110-Answer#2-MQ#8-T#4.jpg', 'Resource #110 Answer#2 MQ#8 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (150, 110);

INSERT INTO phrase (phrase, staff_id) VALUES ('excellent', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('satisfactory', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#111-Answer#3-MQ#8-T#4.jpg', 'Resource #111 Answer#3 MQ#8 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (152, 111);

INSERT INTO phrase (phrase, staff_id) VALUES ('reality', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('imagination', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#112-Answer#4-MQ#8-T#4.jpg', 'Resource #112 Answer#4 MQ#8 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (154, 112);

INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (147, 148, 38);
INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (149, 150, 38);
INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (151, 152, 38);
INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (153, 154, 38);


/* Q#9 answers*/

INSERT INTO phrase (phrase, staff_id) VALUES ('humble', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('aristocratic', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#113-Answer#1-MQ#9-T#4.jpg', 'Resource #113 Answer#1 MQ#9 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (156, 113);

INSERT INTO phrase (phrase, staff_id) VALUES ('wide', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('narrow', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#114-Answer#2-MQ#9-T#4.jpg', 'Resource #114 Answer#2 MQ#9 T#4', 1);

INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (158, 114);

INSERT INTO phrase (phrase, staff_id) VALUES ('west', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('east', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#115-Answer#3-MQ#9-T#4.jpg', 'Resource #115 Answer#3 MQ#9 T#4', 1);

INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (160, 115);

INSERT INTO phrase (phrase, staff_id) VALUES ('glad', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('sad', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#116-Answer#4-MQ#9-T#4.jpg', 'Resource #116 Answer#4 MQ#9 T#4', 1);

INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (162, 116);

INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (155, 156, 39);
INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (157, 158, 39);
INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (159, 160, 39);
INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (161, 162, 39);


/* Q#10 answers*/
INSERT INTO phrase (phrase, staff_id) VALUES ('humid', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('dry', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#117-Answer#1-MQ#10-T#4.jpg', 'Resource #117 Answer#1 MQ#10 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (164, 117);

INSERT INTO phrase (phrase, staff_id) VALUES ('peace', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('war', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#118-Answer#2-MQ#10-T#4.jpg', 'Resource #118 Answer#2 MQ#10 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (166, 118);

INSERT INTO phrase (phrase, staff_id) VALUES ('love', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('hate', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#119-Answer#3-MQ#10-T#4.jpg', 'Resource #119 Answer#3 MQ#10 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (168, 119);

INSERT INTO phrase (phrase, staff_id) VALUES ('day', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('night', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#120-Answer#4-MQ#10-T#4.jpg', 'Resource #120 Answer#4 MQ#10 T#4', 1);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (170, 120);

INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (163, 164, 40);
INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (165, 166, 40);
INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (167, 168, 40);
INSERT INTO answer_mq (left_phrase_id, right_phrase_id, question_id) VALUES (169, 170, 40);


/*Questions Theme #5 (All SQ) 10 pieces, full equipment*/

INSERT INTO question (title, level, type_id, theme_id, lang_id) VALUES ('Sequence question #1 T#5', 1, 5, 5, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id) VALUES ('Sequence question #2 T#5', 1, 5, 5, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id) VALUES ('Sequence question #3 T#5', 1, 5, 5, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id) VALUES ('Sequence question #4 T#5', 1, 5, 5, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id) VALUES ('Sequence question #5 T#5', 1, 5, 5, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id) VALUES ('Sequence question #6 T#5', 1, 5, 5, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id) VALUES ('Sequence question #7 T#5', 1, 5, 5, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id) VALUES ('Sequence question #8 T#5', 1, 5, 5, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id) VALUES ('Sequence question #9 T#5', 1, 5, 5, 1);
INSERT INTO question (title, level, type_id, theme_id, lang_id) VALUES ('Sequence question #10 T#5', 1, 5, 5, 1);

INSERT INTO help (name, text, staff_id) VALUES ('Help #41 SQ#1 T#5', 'Please, refer to section #1 SQ#1 T#5', 1);
INSERT INTO question_help (question_id, help_id) VALUES (41, 41);
INSERT INTO help (name, text, staff_id) VALUES ('Help #42 SQ#2 T#5', 'Please, refer to section #2 SQ#2 T#5', 1);
INSERT INTO question_help (question_id, help_id) VALUES (42, 42);
INSERT INTO help (name, text, staff_id) VALUES ('Help #43 SQ#3 T#5', 'Please, refer to section #3 SQ#3 T#5', 1);
INSERT INTO question_help (question_id, help_id) VALUES (43, 43);
INSERT INTO help (name, text, staff_id) VALUES ('Help #44 SQ#4 T#5', 'Please, refer to section #4 SQ#4 T#5', 1);
INSERT INTO question_help (question_id, help_id) VALUES (44, 44);
INSERT INTO help (name, text, staff_id) VALUES ('Help #45 SQ#5 T#5', 'Please, refer to section #5 SQ#5 T#5', 1);
INSERT INTO question_help (question_id, help_id) VALUES (45, 45);
INSERT INTO help (name, text, staff_id) VALUES ('Help #46 SQ#6 T#5', 'Please, refer to section #6 SQ#6 T#5', 1);
INSERT INTO question_help (question_id, help_id) VALUES (46, 46);
INSERT INTO help (name, text, staff_id) VALUES ('Help #47 SQ#7 T#5', 'Please, refer to section #7 SQ#7 T#5', 1);
INSERT INTO question_help (question_id, help_id) VALUES (47, 47);
INSERT INTO help (name, text, staff_id) VALUES ('Help #48 SQ#8 T#5', 'Please, refer to section #8 SQ#8 T#5', 1);
INSERT INTO question_help (question_id, help_id) VALUES (48, 48);
INSERT INTO help (name, text, staff_id) VALUES ('Help #49 SQ#9 T#5', 'Please, refer to section #9 SQ#9 T#5', 1);
INSERT INTO question_help (question_id, help_id) VALUES (49, 49);
INSERT INTO help (name, text, staff_id) VALUES ('Help #50 SQ#10 T#5', 'Please, refer to section #10 SQ#10 T#5', 1);
INSERT INTO question_help (question_id, help_id) VALUES (50, 50);

INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#121-SQ#1-T#5.jpg', 'Resource #121 SQ#1 T#5', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (41, 121);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#122-SQ#2-T#5.jpg', 'Resource #122 SQ#2 T#5', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (42, 122);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#123-SQ#3-T#5.jpg', 'Resource #123 SQ#3 T#5', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (43, 123);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#124-SQ#4-T#5.jpg', 'Resource #124 SQ#4 T#5', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (44, 124);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#125-SQ#5-T#5.jpg', 'Resource #125 SQ#5 T#5', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (45, 125);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#126-SQ#6-T#5.jpg', 'Resource #126 SQ#6 T#5', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (46, 126);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#127-SQ#7-T#5.jpg', 'Resource #127 SQ#7 T#5', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (47, 127);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#128-SQ#8-T#5.jpg', 'Resource #128 SQ#8 T#5', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (48, 128);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#129-SQ#9-T#5.jpg', 'Resource #129 SQ#9 T#5', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (49, 129);
INSERT INTO resource (hyperlink, description, staff_id)
VALUES ('https://image.slidesharecdn.com/phraseResource-#130-SQ#10-T#5.jpg', 'Resource #130 SQ#10 T#5', 1);
INSERT INTO question_resource (question_id, resource_id) VALUES (50, 130);

/* Q#1 answers*/
INSERT INTO phrase (phrase, staff_id) VALUES ('Monday', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('Tuesday', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('Wednesday', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('Thursday', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('Friday', 1);

INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#131-Answer#1-SQ#1-T#5.jpg', 'Resource #131 Answer#1 SQ#1 T#5', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#132-Answer#2-SQ#1-T#5.jpg', 'Resource #132 Answer#2 SQ#1 T#5', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#133-Answer#3-SQ#1-T#5.jpg', 'Resource #133 Answer#3 SQ#1 T#5', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#134-Answer#4-SQ#1-T#5.jpg', 'Resource #134 Answer#4 SQ#1 T#5', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#135-Answer#5-SQ#1-T#5.jpg', 'Resource #135 Answer#5 SQ#1 T#5', 1);

INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (171, 131);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (172, 132);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (173, 133);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (174, 134);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (175, 135);

INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (171, 0, 41);
INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (172, 1, 41);
INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (173, 2, 41);
INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (174, 3, 41);
INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (175, 4, 41);


/* Q#2 answers*/
INSERT INTO phrase (phrase, staff_id) VALUES ('January', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('February', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('March', 1);

INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#136-Answer#1-SQ#2-T#5.jpg', 'Resource #136 Answer#1 SQ#2 T#5', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#137-Answer#2-SQ#2-T#5.jpg', 'Resource #137 Answer#2 SQ#2 T#5', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#138-Answer#3-SQ#2-T#5.jpg', 'Resource #138 Answer#3 SQ#2 T#5', 1);

INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (176, 136);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (177, 137);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (178, 138);

INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (176, 0, 42);
INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (177, 1, 42);
INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (178, 2, 42);


/* Q#3 answers*/
INSERT INTO phrase (phrase, staff_id) VALUES ('summer', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('autumn', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('winter', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('spring', 1);

INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#139-Answer#1-SQ#3-T#5.jpg', 'Resource #139 Answer#1 SQ#3 T#5', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#140-Answer#2-SQ#3-T#5.jpg', 'Resource #140 Answer#2 SQ#3 T#5', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#141-Answer#3-SQ#3-T#5.jpg', 'Resource #141 Answer#3 SQ#3 T#5', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#142-Answer#4-SQ#3-T#5.jpg', 'Resource #142 Answer#3 SQ#3 T#5', 1);

INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (179, 139);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (180, 140);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (181, 141);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (182, 142);

INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (179, 0, 43);
INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (180, 1, 43);
INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (181, 2, 43);
INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (182, 3, 43);

/* Q#4 answers*/
INSERT INTO phrase (phrase, staff_id) VALUES ('kindergarten', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('school', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('university', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('company', 1);

INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#143-Answer#1-SQ#4-T#5.jpg', 'Resource #143 Answer#1 SQ#4 T#5', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#144-Answer#2-SQ#4-T#5.jpg', 'Resource #144 Answer#2 SQ#4 T#5', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#145-Answer#3-SQ#4-T#5.jpg', 'Resource #145 Answer#3 SQ#4 T#5', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#146-Answer#4-SQ#4-T#5.jpg', 'Resource #146 Answer#4 SQ#4 T#5', 1);

INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (183, 143);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (184, 144);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (185, 145);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (186, 146);

INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (183, 0, 44);
INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (184, 1, 44);
INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (185, 2, 44);
INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (186, 3, 44);


/* Q#5 answers*/
INSERT INTO phrase (phrase, staff_id) VALUES ('birth', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('grow-up', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('marriage', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('death', 1);

INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#147-Answer#1-SQ#5-T#5.jpg', 'Resource #147 Answer#1 SQ#5 T#5', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#148-Answer#2-SQ#5-T#5.jpg', 'Resource #148 Answer#2 SQ#5 T#5', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#149-Answer#3-SQ#5-T#5.jpg', 'Resource #149 Answer#3 SQ#5 T#5', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#150-Answer#4-SQ#5-T#5.jpg', 'Resource #150 Answer#4 SQ#5 T#5', 1);

INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (187, 147);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (188, 148);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (189, 149);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (190, 150);

INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (187, 0, 45);
INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (188, 1, 45);
INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (189, 2, 45);
INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (190, 3, 45);


/* Q#6 answers*/
INSERT INTO phrase (phrase, staff_id) VALUES ('one', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('two', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('three', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('four', 1);

INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#151-Answer#1-SQ#6-T#5.jpg', 'Resource #151 Answer#1 SQ#6 T#5', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#152-Answer#2-SQ#6-T#5.jpg', 'Resource #152 Answer#2 SQ#6 T#5', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#153-Answer#3-SQ#6-T#5.jpg', 'Resource #153 Answer#3 SQ#6 T#5', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#154-Answer#4-SQ#6-T#5.jpg', 'Resource #154 Answer#4 SQ#6 T#5', 1);

INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (191, 151);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (192, 152);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (193, 153);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (194, 154);

INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (191, 0, 46);
INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (192, 1, 46);
INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (193, 2, 46);
INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (194, 3, 46);

/* Q#7 answers*/
INSERT INTO phrase (phrase, staff_id) VALUES ('letter A', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('letter B', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('letter C', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('letter D', 1);

INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#155-Answer#1-SQ#7-T#5.jpg', 'Resource #155 Answer#1 SQ#7 T#5', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#156-Answer#2-SQ#7-T#5.jpg', 'Resource #156 Answer#2 SQ#7 T#5', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#157-Answer#3-SQ#7-T#5.jpg', 'Resource #157 Answer#3 SQ#7 T#5', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#158-Answer#4-SQ#7-T#5.jpg', 'Resource #158 Answer#4 SQ#7 T#5', 1);

INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (195, 155);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (196, 156);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (197, 157);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (198, 158);

INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (195, 0, 47);
INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (196, 1, 47);
INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (197, 2, 47);
INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (198, 3, 47);


/* Q#8 answers*/
INSERT INTO phrase (phrase, staff_id) VALUES ('morning', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('noon', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('afternoon', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('evening', 1);

INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#159-Answer#1-SQ#8-T#5.jpg', 'Resource #159 Answer#1 SQ#8 T#5', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#160-Answer#2-SQ#8-T#5.jpg', 'Resource #160 Answer#2 SQ#8 T#5', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#161-Answer#3-SQ#8-T#5.jpg', 'Resource #161 Answer#3 SQ#8 T#5', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#162-Answer#4-SQ#8-T#5.jpg', 'Resource #162 Answer#4 SQ#8 T#5', 1);

INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (199, 159);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (200, 160);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (201, 161);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (202, 162);

INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (199, 0, 48);
INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (200, 1, 48);
INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (201, 2, 48);
INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (202, 3, 48);


/* Q#9 answers*/
INSERT INTO phrase (phrase, staff_id) VALUES ('2000', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('2001', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('2002', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('2003', 1);

INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#163-Answer#1-SQ#9-T#5.jpg', 'Resource #163 Answer#1 SQ#9 T#5', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#164-Answer#2-SQ#9-T#5.jpg', 'Resource #164 Answer#2 SQ#9 T#5', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#165-Answer#3-SQ#9-T#5.jpg', 'Resource #165 Answer#3 SQ#9 T#5', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#166-Answer#4-SQ#9-T#5.jpg', 'Resource #166 Answer#4 SQ#9 T#5', 1);

INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (203, 163);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (204, 164);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (205, 165);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (206, 166);

INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (203, 0, 49);
INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (204, 1, 49);
INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (205, 2, 49);
INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (206, 3, 49);

/* Q#10 answers*/
INSERT INTO phrase (phrase, staff_id) VALUES ('step 1', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('step 2', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('step 3', 1);
INSERT INTO phrase (phrase, staff_id) VALUES ('step 4', 1);

INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#167-Answer#1-SQ#10-T#5.jpg', 'Resource #167 Answer#1 SQ#10 T#5', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#168-Answer#2-SQ#10-T#5.jpg', 'Resource #168 Answer#2 SQ#10 T#5', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#169-Answer#3-SQ#10-T#5.jpg', 'Resource #169 Answer#3 SQ#10 T#5', 1);
INSERT INTO resource (hyperlink, description, staff_id) VALUES
  ('https://image.slidesharecdn.com/phraseResource-#170-Answer#4-SQ#10-T#5.jpg', 'Resource #170 Answer#4 SQ#10 T#5', 1);

INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (207, 167);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (208, 168);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (209, 169);
INSERT INTO phrase_resource (phrase_id, resource_id) VALUES (210, 170);

INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (207, 0, 50);
INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (208, 1, 50);
INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (209, 2, 50);
INSERT INTO answer_sq (phrase_id, phrase_order, question_id) VALUES (210, 3, 50);
