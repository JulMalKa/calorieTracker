TRUNCATE TABLE meal_entry, meal, product, users RESTART IDENTITY CASCADE;

INSERT INTO users (id, username, email, password, age, weight, height, daily_calorie_goal, daily_protein_goal, daily_carbs_goal, daily_fat_goal) VALUES
    (1, 'Julia', 'julia@example.com', 'password123', 25, 62.0, 168.0, 2000, 120.0, 200.0, 60.0),
    (2, 'Adam', 'adam@example.com', 'password321', 30, 82.5, 182.0, 2600, 150.0, 280.0, 85.0);

INSERT INTO product (id, name, calories_per100g, protein, carbs, fat) VALUES
    (1, 'rice', 344.0, 6.7, 78.9, 0.7),
    (2, 'chicken breast', 99.0, 21.5, 0.0, 1.3),
    (3, 'olive oil', 884.0, 0.0, 0.0, 100.0),
    (4, 'egg', 143.0, 12.6, 0.7, 9.5),
    (5, 'oats', 379.0, 13.2, 67.7, 6.5),
    (6, 'milk 2%', 50.0, 3.3, 4.8, 2.0),
    (7, 'banana', 89.0, 1.1, 22.8, 0.3),
    (8, 'bread', 259.0, 8.5, 48.3, 3.3),
    (9, 'butter', 717.0, 0.8, 0.1, 81.0),
    (10, 'cheese', 356.0, 24.9, 2.2, 27.4),
    (11, 'apple', 52.0, 0.3, 13.8, 0.2),
    (12, 'tomato', 18.0, 0.9, 3.9, 0.2),
    (13, 'potato', 77.0, 2.0, 17.5, 0.1),
    (14, 'salmon', 208.0, 20.4, 0.0, 13.6),
    (15, 'avocado', 160.0, 2.0, 8.5, 14.7);

INSERT INTO meal (id, meal_type, date, user_id) VALUES
    (1, 0, '2026-08-28', 1),
    (2, 1, '2026-08-28', 1),
    (3, 2, '2026-08-28', 1),
    (4, 0, '2026-08-29', 1),
    (5, 0, '2026-08-28', 2),
    (6, 2, '2026-08-28', 2);

INSERT INTO meal_entry (id, meal_id, product_id, amount_in_grams) VALUES
    (1, 1, 5, 50.0),
    (2, 1, 6, 200.0),
    (3, 1, 7, 120.0),
    (4, 2, 1, 100.0),
    (5, 2, 2, 150.0),
    (6, 2, 3, 10.0),
    (7, 2, 12, 150.0),
    (8, 3, 8, 100.0),
    (9, 3, 9, 10.0),
    (10, 3, 10, 40.0),
    (11, 3, 12, 50.0),
    (12, 4, 4, 120.0),
    (13, 4, 9, 10.0),
    (14, 4, 8, 50.0),
    (15, 5, 8, 150.0),
    (16, 5, 15, 80.0),
    (17, 5, 4, 60.0),
    (18, 6, 14, 200.0),
    (19, 6, 13, 250.0),
    (20, 6, 3, 15.0);

SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));
SELECT setval('product_id_seq', (SELECT MAX(id) FROM product));
SELECT setval('meal_id_seq', (SELECT MAX(id) FROM meal));
SELECT setval('meal_entry_id_seq', (SELECT MAX(id) FROM meal_entry));