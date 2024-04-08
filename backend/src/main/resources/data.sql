INSERT INTO user (username, password, email) VALUES ('user1', '$2a$10$zP7PgGpmPMpGEBfmyHJa2ed.olcD0YZegSP8IhTTbheZ06SHniun6', 'user1@gmail.com');
INSERT INTO user (username, password, email) VALUES ('user2', '$2a$10$zP7PgGpmPMpGEBfmyHJa2ed.olcD0YZegSP8IhTTbheZ06SHniun6', 'user2@mail.no');
INSERT INTO user (username, password, email) VALUES ('user3', '$2a$10$zP7PgGpmPMpGEBfmyHJa2ed.olcD0YZegSP8IhTTbheZ06SHniun6', 'user3@yahoo.com');

INSERT INTO question_type (type_name) VALUES ('Multiple Choice');
INSERT INTO question_type (type_name) VALUES ('True/False');
INSERT INTO question_type (type_name) VALUES ('Fill in the blank');

INSERT INTO category (category_name) VALUES ('Math');
INSERT INTO category (category_name) VALUES ('Science');
INSERT INTO category (category_name) VALUES ('History');
INSERT INTO category (category_name) VALUES ('Random');

INSERT INTO quiz (quiz_name, category_id, creator_id, multimedia, difficulty_level, quiz_description) VALUES ('Math Quiz', 1, 1, 'math.png', 'Easy', 'This is a quiz about math');
INSERT INTO quiz (quiz_name, category_id, creator_id, multimedia, difficulty_level, quiz_description) VALUES ('Science Quiz', 2, 2, 'science.png', 'Medium', 'This is a quiz about science');
INSERT INTO quiz (quiz_name, category_id, creator_id, multimedia, difficulty_level, quiz_description) VALUES ('History Quiz', 3, 1, 'history.png', 'Hard', 'This is a quiz about history');
INSERT INTO quiz (quiz_name, category_id, creator_id, multimedia, difficulty_level, quiz_description) VALUES ('Random Quiz', 4, 1, 'random.png', 'Easy', 'This is a random quiz');

-- Math Quiz Questions
INSERT INTO question (question_text, type_id, quiz_id) VALUES ('What is 2 + 2 = ?', 1, 1);
INSERT INTO question (question_text, type_id, quiz_id) VALUES ('What is 2 * 5 = ?', 1, 1);
INSERT INTO question (question_text, type_id, quiz_id) VALUES ('Is 4 + 4 = 8 ?', 2, 1);
INSERT INTO question (question_text, type_id, quiz_id) VALUES ('Is 2 + 2 = 5 ?', 2, 1);
INSERT INTO question (question_text, type_id, quiz_id) VALUES ('2 * 3 = _ ?', 3, 1);

-- Science Quiz Questions
INSERT INTO question (question_text, type_id, quiz_id) VALUES ('The man _ invented the light bulb?', 3, 2);
INSERT INTO question (question_text, type_id, quiz_id) VALUES ('There are 206 bones in the human body?', 2, 2);
INSERT INTO question (question_text, type_id, quiz_id) VALUES ('How many colors are in the rainbow?', 1, 2);
INSERT INTO question (question_text, type_id, quiz_id) VALUES ('True or false, the tallest grass on earth is bamboo?', 2, 2);
INSERT INTO question (question_text, type_id, quiz_id) VALUES ('What is the largest desert in the world?', 1, 2);

-- History Quiz Questions
INSERT INTO question (question_text, type_id, quiz_id) VALUES ('What is the capital of France?', 1, 3);
INSERT INTO question (question_text, type_id, quiz_id) VALUES ('What is the capital of Norway?', 1, 3);
INSERT INTO question (question_text, type_id, quiz_id) VALUES ('True or False, World War II started in 1940?', 2, 3);
INSERT INTO question (question_text, type_id, quiz_id) VALUES ('Who was the first computer programmer?', 1, 3);

-- Random Quiz Questions
INSERT INTO question (question_text, type_id, quiz_id) VALUES ('The Earth is flat. True or False?', 2, 4);
INSERT INTO question (question_text, type_id, quiz_id) VALUES ('The blue whale is the biggest animal to have ever lived. True or False?', 2, 4);
INSERT INTO question (question_text, type_id, quiz_id) VALUES ('What is not an ingredient in a cake?', 1, 4);
INSERT INTO question (question_text, type_id, quiz_id) VALUES ('What is a group of crows called?', 1, 4);

-- Math Quiz Choices
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('4', '2 + 2 = 4', true, 1);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('3', 'wrong answer', false, 1);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('5', 'wrong answer', false, 1);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('10', '2 * 5 = 10', true, 2);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('8', 'wrong answer', false, 2);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('true', '4 + 4 = 8', true, 3);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('false', 'wrong answer', false, 3);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('true', '2 + 2 = 5', false, 4);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('false', 'wrong answer', true, 4);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('6', '2 * 3 = 6', true, 5);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('5', 'wrong answer', false, 5);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('7', 'wrong answer', false, 5);

-- Science Quiz Choices
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('Thomas Edison', 'Thomas Edison invented the light bulb', true, 6);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('Albert Einstein', 'Albert Einstein did not invent the light bulb', false, 6);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('Thomas Jefferson', 'Thomas Jefferson did not invent the light bulb', true, 7);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('true', 'Correct!', true, 7);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('false', 'There are 206 bones in the human body', false, 7);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('7', 'There are 7 colors in the rainbow', true, 8);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('6', 'This is wrong!', false, 8);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('8', 'This is wrong!', false, 8);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('9', 'This is wrong!', false, 8);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('true', 'Bamboo is the tallest grass on earth', true, 9);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('false', 'This is wrong!', false, 9);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('Sahara', 'It’s not the Sahara, but actually Antarctica!', false, 10);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('Antarctica', 'Correct', true, 10);

-- History Quiz Choices
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('Paris', 'Paris is the capital of France', true, 9);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('London', 'London is not the capital of France', false, 9);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('Oslo', 'Oslo is the capital of Norway', true, 10);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('Stockholm', 'Stockholm is not the capital of Norway', false, 10);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('false', 'World War II started in 1939', false, 11);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('true', 'World War II started in 1939', true, 11);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('Ada Lovelace', 'Ada Lovelace was the first computer programmer, she created a program for an Analytical Engine', true, 12);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('Alan Turing', 'Alan Turing was not the first computer programmer', false, 12);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('Linus Torvalds', 'Linus Torvalds was not the first computer programmer', false, 12);

-- Random Quiz Choices
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('true', 'The Earth is round', false, 13);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('false', 'The Earth is round', true, 13);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('true', 'This is actually true, even bigger than dinosaurs', true, 14);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('false', 'They are the biggest animals to have ever lived', false, 14);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('Flour', 'Flour is an ingredient in a cake', false, 15);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('Sugar', 'Sugar is an ingredient in a cake', false, 15);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('Sement', 'Sement is not an ingredient in a cake', true, 15);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('A killer', 'This is not correct', false, 16);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('A murder', 'Correct! Scary right?', true, 16);
INSERT INTO question_choices (choice, explanation, is_correct_choice, question_id) VALUES ('A pack', 'Wrong answer!', false, 16);