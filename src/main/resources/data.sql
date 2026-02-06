INSERT IGNORE INTO districts (name) VALUES ('Ampara');
INSERT IGNORE INTO districts (name) VALUES ('Anuradhapura');
INSERT IGNORE INTO districts (name) VALUES ('Badulla');
INSERT IGNORE INTO districts (name) VALUES ('Batticaloa');
INSERT IGNORE INTO districts (name) VALUES ('Colombo');
INSERT IGNORE INTO districts (name) VALUES ('Galle');
INSERT IGNORE INTO districts (name) VALUES ('Gampaha');
INSERT IGNORE INTO districts (name) VALUES ('Hambantota');
INSERT IGNORE INTO districts (name) VALUES ('Jaffna');
INSERT IGNORE INTO districts (name) VALUES ('Kalutara');

-- Seed a test farmer (for Profile API testing)
-- COMMENTED OUT: This used a plain-text password hash which breaks BCrypt login
-- Use the /api/auth/register endpoint to create users with proper BCrypt passwords
-- INSERT IGNORE INTO farmers (id, name, email, phone, password_hash, district_id, crop_id)
-- SELECT 1, 'Test Farmer', 'test@example.com', '0771234567', 'test123hash', 1, 1
-- WHERE NOT EXISTS (SELECT 1 FROM farmers WHERE id = 1);

-- Seed Market Prices (crop_id, district_id, price_per_kg, price_date)
-- Rice prices (crop_id = 1)
INSERT IGNORE INTO market_prices (id, crop_id, district_id, price_per_kg, price_date) VALUES (1, 1, 1, 150.00, CURDATE());
INSERT IGNORE INTO market_prices (id, crop_id, district_id, price_per_kg, price_date) VALUES (2, 1, 2, 145.00, CURDATE());
INSERT IGNORE INTO market_prices (id, crop_id, district_id, price_per_kg, price_date) VALUES (3, 1, 3, 148.00, CURDATE());

-- Maize prices (crop_id = 2)
INSERT IGNORE INTO market_prices (id, crop_id, district_id, price_per_kg, price_date) VALUES (4, 2, 1, 85.00, CURDATE());
INSERT IGNORE INTO market_prices (id, crop_id, district_id, price_per_kg, price_date) VALUES (5, 2, 2, 82.00, CURDATE());
INSERT IGNORE INTO market_prices (id, crop_id, district_id, price_per_kg, price_date) VALUES (6, 2, 3, 84.00, CURDATE());

-- Tomato prices (crop_id = 3)
INSERT IGNORE INTO market_prices (id, crop_id, district_id, price_per_kg, price_date) VALUES (7, 3, 1, 220.00, CURDATE());
INSERT IGNORE INTO market_prices (id, crop_id, district_id, price_per_kg, price_date) VALUES (8, 3, 2, 215.00, CURDATE());
INSERT IGNORE INTO market_prices (id, crop_id, district_id, price_per_kg, price_date) VALUES (9, 3, 3, 218.00, CURDATE());
