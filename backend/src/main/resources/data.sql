INSERT INTO user (username, password, email) VALUES ("malinholi", "$2a$10$IzpQoMyJg.EIUOuR87I4COMLIIrBVnEuuB0SKJn3T.ZKZYtd3Bo9y", "malin.holi@gmail.com");
INSERT INTO user (username, password, email) VALUES ("inamartini", "$2a$10$05BpILHAL10VqkNf5mjsaeE0KfrZmilpaNP0xBEHXJJWBLdZQQH9G", "ina.martini@gmail.com");
INSERT INTO user (username, password, email) VALUES ("elineevje", "$2a$10$05BpILHAL10VqkNf5mjsaeE0KfrZmilpaNP0xBEHXJJWBLdZQQH9G", "eline.evje@gmail.com");

INSERT INTO question_type (type_name) VALUES ("Multiple Choice");
INSERT INTO question_type (type_name) VALUES ("True/False");
INSERT INTO question_type (type_name) VALUES ("Fill in the blank");

INSERT INTO category (category_name) VALUES ("Math");
INSERT INTO category (category_name) VALUES ("Science");
INSERT INTO category (category_name) VALUES ("History");

INSERT INTO quiz (quiz_name, category_id, creator_id, multimedia, difficulty_level, quiz_description) VALUES ("Math Quiz", 1, 5, "", "Easy", "This is a quiz about math");
INSERT INTO quiz (quiz_name, category_id, creator_id, multimedia, difficulty_level, quiz_description) VALUES ("Science Quiz", 2, 5, "", "Medium", "This is a quiz about science");
INSERT INTO quiz (quiz_name, category_id, creator_id, multimedia, difficulty_level, quiz_description) VALUES ("History Quiz", 3, 5, "", "Hard", "This is a quiz about history");