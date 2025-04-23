INSERT INTO categories (id, name) VALUES (1, 'Dogs');
INSERT INTO tags (id, name) VALUES (1, 'friendly');
INSERT INTO pets (id, name, status, category_id) VALUES (1, 'TestPet', 'available', 1);
INSERT INTO pet_tags (pet_id, tag_id) VALUES (1, 1);