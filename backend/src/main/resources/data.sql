INSERT INTO user (username, password, email) VALUES ("malinholi", "$2a$10$IzpQoMyJg.EIUOuR87I4COMLIIrBVnEuuB0SKJn3T.ZKZYtd3Bo9y", "malin.holi@gmail.com");
INSERT INTO user (username, password, email) VALUES ("inamartini", "$2a$10$05BpILHAL10VqkNf5mjsaeE0KfrZmilpaNP0xBEHXJJWBLdZQQH9G", "ina.martini@gmail.com");
INSERT INTO user (username, password, email) VALUES ("elineevje", "$2a$10$05BpILHAL10VqkNf5mjsaeE0KfrZmilpaNP0xBEHXJJWBLdZQQH9G", "eline.evje@gmail.com");

INSERT INTO question_type (type_name) VALUES ("Multiple Choice");
INSERT INTO question_type (type_name) VALUES ("True/False");
INSERT INTO question_type (type_name) VALUES ("Fill in the blank");

INSERT INTO category (category_name) VALUES ("Math");
INSERT INTO category (category_name) VALUES ("Science");
INSERT INTO category (category_name) VALUES ("History");
INSERT INTO category (category_name) VALUES ("Random");

INSERT INTO quiz (quiz_name, category_id, creator_id, multimedia, difficulty_level, quiz_description) VALUES ("Math Quiz", 1, 1, "", "Easy", "This is a quiz about math");
INSERT INTO quiz (quiz_name, category_id, creator_id, multimedia, difficulty_level, quiz_description) VALUES ("Science Quiz", 2, 2, "", "Medium", "This is a quiz about science");
INSERT INTO quiz (quiz_name, category_id, creator_id, multimedia, difficulty_level, quiz_description) VALUES ("History Quiz", 3, 3, "", "Hard", "This is a quiz about history");
INSERT INTO quiz (quiz_name, category_id, creator_id, multimedia, difficulty_level, quiz_description) VALUES ("Random Quiz", 4, 1, "", "Easy", "This is a random quiz");

-- Math Quiz Questions
INSERT INTO question (question_text, question_type_id, quiz_id) VALUES ("What is 2 + 2 = ?", 1, 1);
INSERT INTO question (question_text, question_type_id, quiz_id) VALUES ("What is 2 * 5 = ?", 1, 1);
INSERT INTO question (question_text, question_type_id, quiz_id) VALUES ("Is 4 + 4 = 8 ?", 2, 1);
INSERT INTO question (question_text, question_type_id, quiz_id) VALUES ("Is 2 + 2 = 5 ?", 2, 1);
INSERT INTO question (question_text, question_type_id, quiz_id) VALUES ("What is 2 * 3 = ?", 3, 1);

-- Science Quiz Questions
INSERT INTO question (question_text, question_type_id, quiz_id) VALUES ("Who invented the light bulb?", 3, 2);
INSERT INTO question (question_text, question_type_id, quiz_id) VALUES ("There are 206 bones in the human body?", 2, 2);

-- History Quiz Questions
INSERT INTO question (question_text, question_type_id, quiz_id) VALUES ("What is the capital of France?", 1, 3);
INSERT INTO question (question_text, question_type_id, quiz_id) VALUES ("What is the capital of Norway?", 1, 3);

-- Random Quiz Questions
INSERT INTO question (question_text, question_type_id, quiz_id) VALUES ("The Earth is flat. True or False?", 2, 4);
INSERT INTO question (question_text, question_type_id, quiz_id) VALUES ("The blue whale is the biggest animal to have ever lived. True or False?", 2, 4);

-- Math Quiz Choices
INSERT INTO question_choices (choice_text, explanation, is_correct, question_id) VALUES ("4", "2 + 2 = 4", true, 1);
INSERT INTO question_choices (choice_text, explanation, is_correct, question_id) VALUES ("3", "wrong answer", false, 1);
INSERT INTO question_choices (choice_text, explanation, is_correct, question_id) VALUES ("5", "wrong answer", false, 1);
INSERT INTO question_choices (choice_text, explanation, is_correct, question_id) VALUES ("10", "2 * 5 = 10", true, 2);
INSERT INTO question_choices (choice_text, explanation, is_correct, question_id) VALUES ("8", "wrong answer", false, 2);
INSERT INTO question_choices (choice_text, explanation, is_correct, question_id) VALUES ("true", "4 + 4 = 8", true, 3);
INSERT INTO question_choices (choice_text, explanation, is_correct, question_id) VALUES ("false", "wrong answer", false, 3);
INSERT INTO question_choices (choice_text, explanation, is_correct, question_id) VALUES ("true", "2 + 2 = 5", false, 4);
INSERT INTO question_choices (choice_text, explanation, is_correct, question_id) VALUES ("false", "wrong answer", true, 4);
INSERT INTO question_choices (choice_text, explanation, is_correct, question_id) VALUES ("6", "2 * 3 = 6", true, 5);

-- Science Quiz Choices
INSERT INTO question_choices (choice_text, explanation, is_correct, question_id) VALUES ("Thomas Edison", "Thomas Edison invented the light bulb", true, 6);
INSERT INTO question_choices (choice_text, explanation, is_correct, question_id) VALUES ("true", "Correct!", true, 7);
INSERT INTO question_choices (choice_text, explanation, is_correct, question_id) VALUES ("false", "There are 206 bones in the human body", false, 7);

-- History Quiz Choices
INSERT INTO question_choices (choice_text, explanation, is_correct, question_id) VALUES ("Paris", "Paris is the capital of France", true, 8);
INSERT INTO question_choices (choice_text, explanation, is_correct, question_id) VALUES ("London", "London is not the capital of France", false, 8);
INSERT INTO question_choices (choice_text, explanation, is_correct, question_id) VALUES ("Oslo", "Oslo is the capital of Norway", true, 9);
INSERT INTO question_choices (choice_text, explanation, is_correct, question_id) VALUES ("Stockholm", "Stockholm is not the capital of Norway", false, 9);

-- Random Quiz Choices
INSERT INTO question_choices (choice_text, explanation, is_correct, question_id) VALUES ("true", "The Earth is round", false, 10);
INSERT INTO question_choices (choice_text, explanation, is_correct, question_id) VALUES ("false", "The Earth is round", true, 10);
INSERT INTO question_choices (choice_text, explanation, is_correct, question_id) VALUES ("true", "This is actually true, even bigger than dinosaurs", true, 11);
INSERT INTO question_choices (choice_text, explanation, is_correct, question_id) VALUES ("false", "They are the biggest animals to have ever lived", false, 11);

INSERT INTO completed_quiz (score, quiz_id, user_id) VALUES (5, 1, 2);
INSERT INTO completed_quiz (score, quiz_id, user_id) VALUES (3, 1, 3);
INSERT INTO completed_quiz (score, quiz_id, user_id) VALUES (1, 2, 1);
INSERT INTO completed_quiz (score, quiz_id, user_id) VALUES (0, 3, 3);
INSERT INTO completed_quiz (score, quiz_id, user_id) VALUES (1, 4, 2);
INSERT INTO completed_quiz (score, quiz_id, user_id) VALUES (2, 4, 1);

INSERT INTO user_answer (completed_quiz_id, quiz_choice_id) VALUES (1, 1);
INSERT INTO user_answer (completed_quiz_id, quiz_choice_id) VALUES (1, 4);
INSERT INTO user_answer (completed_quiz_id, quiz_choice_id) VALUES (2, 6);
INSERT INTO user_answer (completed_quiz_id, quiz_choice_id) VALUES (2, 8);
INSERT INTO user_answer (completed_quiz_id, quiz_choice_id) VALUES (3, 10);
INSERT INTO user_answer (completed_quiz_id, quiz_choice_id) VALUES (2, 2);
INSERT INTO user_answer (completed_quiz_id, quiz_choice_id) VALUES (3, 4);
INSERT INTO user_answer (completed_quiz_id, quiz_choice_id) VALUES (4, 6);
INSERT INTO user_answer (completed_quiz_id, quiz_choice_id) VALUES (5, 9);
INSERT INTO user_answer (completed_quiz_id, quiz_choice_id) VALUES (6, 10);
INSERT INTO user_answer (completed_quiz_id, quiz_choice_id) VALUES (3, 4);
INSERT INTO user_answer (completed_quiz_id, quiz_choice_id) VALUES (3, 6);
INSERT INTO user_answer (completed_quiz_id, quiz_choice_id) VALUES (4, 8);
INSERT INTO user_answer (completed_quiz_id, quiz_choice_id) VALUES (4, 10);
INSERT INTO user_answer (completed_quiz_id, quiz_choice_id) VALUES (5, 11);
INSERT INTO user_answer (completed_quiz_id, quiz_choice_id) VALUES (5, 14);
INSERT INTO user_answer (completed_quiz_id, quiz_choice_id) VALUES (6, 11);
INSERT INTO user_answer (completed_quiz_id, quiz_choice_id) VALUES (6, 13);

