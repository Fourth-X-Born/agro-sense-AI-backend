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
