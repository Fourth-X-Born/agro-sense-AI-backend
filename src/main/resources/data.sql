-- Seed Crops
INSERT IGNORE INTO crops (name) VALUES ('Rice');
INSERT IGNORE INTO crops (name) VALUES ('Maize');
INSERT IGNORE INTO crops (name) VALUES ('Tomato');

-- Seed Districts
INSERT IGNORE INTO districts (name) VALUES ('Colombo');
INSERT IGNORE INTO districts (name) VALUES ('Gampaha');
INSERT IGNORE INTO districts (name) VALUES ('Kalutara');

-- Seed a test farmer (for Profile API testing)
-- Only insert if no farmer exists with id=1
INSERT IGNORE INTO farmers (id, name, email, phone, password_hash, district_id, crop_id)
SELECT 1, 'Test Farmer', 'test@example.com', '0771234567', 'test123hash', 1, 1
WHERE NOT EXISTS (SELECT 1 FROM farmers WHERE id = 1);
