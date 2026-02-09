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
INSERT IGNORE INTO districts (name) VALUES ('Kandy');
INSERT IGNORE INTO districts (name) VALUES ('Kegalle');
INSERT IGNORE INTO districts (name) VALUES ('Kilinochchi');
INSERT IGNORE INTO districts (name) VALUES ('Kurunegala');
INSERT IGNORE INTO districts (name) VALUES ('Mannar');
INSERT IGNORE INTO districts (name) VALUES ('Matale');
INSERT IGNORE INTO districts (name) VALUES ('Matara');
INSERT IGNORE INTO districts (name) VALUES ('Monaragala');
INSERT IGNORE INTO districts (name) VALUES ('Mullaitivu');
INSERT IGNORE INTO districts (name) VALUES ('Nuwara Eliya');
INSERT IGNORE INTO districts (name) VALUES ('Polonnaruwa');
INSERT IGNORE INTO districts (name) VALUES ('Puttalam');
INSERT IGNORE INTO districts (name) VALUES ('Ratnapura');
INSERT IGNORE INTO districts (name) VALUES ('Trincomalee');
INSERT IGNORE INTO districts (name) VALUES ('Vavuniya');

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

-- Seed Fertilizer Recommendations
-- Rice fertilizers (crop_id = 1)
INSERT IGNORE INTO fertilizer_recommendations (id, crop_id, fertilizer_name, fertilizer_type, dosage_per_hectare, application_stage, application_method, notes)
VALUES (1, 1, 'Urea', 'Nitrogen', '100-150 kg/ha', 'Tillering stage', 'Broadcasting', 'Apply in split doses - 50% at tillering, 50% at panicle initiation');

INSERT IGNORE INTO fertilizer_recommendations (id, crop_id, fertilizer_name, fertilizer_type, dosage_per_hectare, application_stage, application_method, notes)
VALUES (2, 1, 'Triple Super Phosphate (TSP)', 'Phosphorus', '50-75 kg/ha', 'Before planting', 'Basal application', 'Apply during land preparation for strong root development');

INSERT IGNORE INTO fertilizer_recommendations (id, crop_id, fertilizer_name, fertilizer_type, dosage_per_hectare, application_stage, application_method, notes)
VALUES (3, 1, 'Muriate of Potash (MOP)', 'Potassium', '50-60 kg/ha', 'Tillering stage', 'Side dressing', 'Improves grain quality and disease resistance');

-- Maize fertilizers (crop_id = 2)
INSERT IGNORE INTO fertilizer_recommendations (id, crop_id, fertilizer_name, fertilizer_type, dosage_per_hectare, application_stage, application_method, notes)
VALUES (4, 2, 'Urea', 'Nitrogen', '150-200 kg/ha', 'V6-V8 stage', 'Side dressing', 'Apply when plants are knee-high for maximum growth');

INSERT IGNORE INTO fertilizer_recommendations (id, crop_id, fertilizer_name, fertilizer_type, dosage_per_hectare, application_stage, application_method, notes)
VALUES (5, 2, 'DAP (Diammonium Phosphate)', 'Phosphorus', '75-100 kg/ha', 'At planting', 'Band placement', 'Place 5cm below and to the side of seed');

INSERT IGNORE INTO fertilizer_recommendations (id, crop_id, fertilizer_name, fertilizer_type, dosage_per_hectare, application_stage, application_method, notes)
VALUES (6, 2, 'Compost', 'Organic', '5-10 tons/ha', 'Before planting', 'Broadcasting', 'Improves soil structure and water retention');

-- Tomato fertilizers (crop_id = 3)
INSERT IGNORE INTO fertilizer_recommendations (id, crop_id, fertilizer_name, fertilizer_type, dosage_per_hectare, application_stage, application_method, notes)
VALUES (7, 3, 'NPK 15-15-15', 'Compound', '300-400 kg/ha', 'Transplanting', 'Basal application', 'Balanced nutrition for early growth');

INSERT IGNORE INTO fertilizer_recommendations (id, crop_id, fertilizer_name, fertilizer_type, dosage_per_hectare, application_stage, application_method, notes)
VALUES (8, 3, 'Calcium Ammonium Nitrate (CAN)', 'Nitrogen', '100-150 kg/ha', 'Flowering stage', 'Foliar spray', 'Prevents blossom end rot and improves fruit quality');

INSERT IGNORE INTO fertilizer_recommendations (id, crop_id, fertilizer_name, fertilizer_type, dosage_per_hectare, application_stage, application_method, notes)
VALUES (9, 3, 'Potassium Sulphate', 'Potassium', '75-100 kg/ha', 'Fruiting stage', 'Fertigation', 'Enhances fruit color and sugar content');

