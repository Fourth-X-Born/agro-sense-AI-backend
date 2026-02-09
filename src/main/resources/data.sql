-- Seed Crops (must be first, referenced by other tables)
INSERT IGNORE INTO crops (id, name) VALUES (1, 'Rice');
INSERT IGNORE INTO crops (id, name) VALUES (2, 'Maize');
INSERT IGNORE INTO crops (id, name) VALUES (3, 'Tomato');
INSERT IGNORE INTO crops (id, name) VALUES (4, 'Chili');
INSERT IGNORE INTO crops (id, name) VALUES (5, 'Onion');

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

-- =====================================================
-- CROP GUIDE DATA
-- =====================================================

-- Seed Crop Details
-- Rice (crop_id = 1)
INSERT IGNORE INTO crop_details (crop_id, description, season_type, growth_duration_days, optimal_temperature, water_requirement, soil_ph, image_url)
VALUES (1, 'Rice is the primary staple food in Sri Lanka, grown extensively in wet and dry zones. It requires flooded fields (paddies) during most of its growth cycle and thrives in warm, humid conditions.', 
'Both', 100, '25-32°C', '5-10 cm standing water', '5.5-7.0', NULL);

-- Maize (crop_id = 2)
INSERT IGNORE INTO crop_details (crop_id, description, season_type, growth_duration_days, optimal_temperature, water_requirement, soil_ph, image_url)
VALUES (2, 'Maize (corn) is a versatile crop used for human consumption, livestock feed, and industrial purposes. It grows well in well-drained soils with adequate moisture and warm temperatures.',
'Both', 90, '21-30°C', 'Regular irrigation, 500-800mm', '5.8-7.0', NULL);

-- Tomato (crop_id = 3)
INSERT IGNORE INTO crop_details (crop_id, description, season_type, growth_duration_days, optimal_temperature, water_requirement, soil_ph, image_url)
VALUES (3, 'Tomatoes are one of the most popular vegetables in Sri Lanka, grown throughout the year in different agro-ecological zones. They require moderate temperatures and consistent moisture.',
'Both', 75, '20-27°C', 'Regular drip irrigation', '6.0-6.8', NULL);

-- Seed Growth Stages for Rice
INSERT IGNORE INTO growth_stages (id, crop_id, stage_order, stage_name, start_day, end_day, focus_area, description)
VALUES (1, 1, 1, 'Seedling', 0, 14, 'Root development', 'Seeds germinate and develop into seedlings in the nursery. Focus on proper water management and protection from pests.');

INSERT IGNORE INTO growth_stages (id, crop_id, stage_order, stage_name, start_day, end_day, focus_area, description)
VALUES (2, 1, 2, 'Vegetative', 15, 55, 'Tillering', 'Plants are transplanted and develop tillers. This is the critical growth phase where the plant establishes its structure.');

INSERT IGNORE INTO growth_stages (id, crop_id, stage_order, stage_name, start_day, end_day, focus_area, description)
VALUES (3, 1, 3, 'Reproductive', 56, 85, 'Panicle formation', 'Panicles emerge and flowering occurs. Water management and pest control are critical during this stage.');

INSERT IGNORE INTO growth_stages (id, crop_id, stage_order, stage_name, start_day, end_day, focus_area, description)
VALUES (4, 1, 4, 'Ripening', 86, 100, 'Grain filling', 'Grains mature and ripen. Reduce water gradually and prepare for harvest.');

-- Seed Growth Stages for Maize
INSERT IGNORE INTO growth_stages (id, crop_id, stage_order, stage_name, start_day, end_day, focus_area, description)
VALUES (5, 2, 1, 'Emergence', 0, 10, 'Germination', 'Seeds germinate and emerge from soil. Ensure adequate soil moisture for uniform emergence.');

INSERT IGNORE INTO growth_stages (id, crop_id, stage_order, stage_name, start_day, end_day, focus_area, description)
VALUES (6, 2, 2, 'Vegetative', 11, 50, 'Leaf development', 'Plant develops leaves and grows in height. Critical period for nitrogen application.');

INSERT IGNORE INTO growth_stages (id, crop_id, stage_order, stage_name, start_day, end_day, focus_area, description)
VALUES (7, 2, 3, 'Tasseling', 51, 65, 'Pollination', 'Tassel emerges and pollination occurs. Avoid water stress during this critical period.');

INSERT IGNORE INTO growth_stages (id, crop_id, stage_order, stage_name, start_day, end_day, focus_area, description)
VALUES (8, 2, 4, 'Grain Fill', 66, 90, 'Kernel development', 'Kernels develop and fill with starch. Continue irrigation and monitor for pests.');

-- Seed Growth Stages for Tomato
INSERT IGNORE INTO growth_stages (id, crop_id, stage_order, stage_name, start_day, end_day, focus_area, description)
VALUES (9, 3, 1, 'Transplanting', 0, 14, 'Root establishment', 'Seedlings are transplanted to the field. Provide shade and adequate water for establishment.');

INSERT IGNORE INTO growth_stages (id, crop_id, stage_order, stage_name, start_day, end_day, focus_area, description)
VALUES (10, 3, 2, 'Vegetative', 15, 35, 'Plant growth', 'Plant develops foliage and structure. Stake plants and begin pest monitoring.');

INSERT IGNORE INTO growth_stages (id, crop_id, stage_order, stage_name, start_day, end_day, focus_area, description)
VALUES (11, 3, 3, 'Flowering', 36, 50, 'Flower development', 'Flowers appear and fruit set begins. Ensure proper pollination and calcium nutrition.');

INSERT IGNORE INTO growth_stages (id, crop_id, stage_order, stage_name, start_day, end_day, focus_area, description)
VALUES (12, 3, 4, 'Fruiting', 51, 75, 'Fruit development', 'Fruits develop and ripen. Regular harvesting encourages continued production.');

-- Seed Crop Guidelines
-- Rice DOs
INSERT IGNORE INTO crop_guidelines (id, crop_id, stage_id, guideline_type, description, priority)
VALUES (1, 1, NULL, 'DO', 'Maintain 5-10 cm standing water during vegetative and reproductive stages', 1);

INSERT IGNORE INTO crop_guidelines (id, crop_id, stage_id, guideline_type, description, priority)
VALUES (2, 1, NULL, 'DO', 'Apply fertilizer when soil is moist but not flooded for better absorption', 2);

INSERT IGNORE INTO crop_guidelines (id, crop_id, stage_id, guideline_type, description, priority)
VALUES (3, 1, NULL, 'DO', 'Use certified seeds from approved sources for better germination and yield', 3);

INSERT IGNORE INTO crop_guidelines (id, crop_id, stage_id, guideline_type, description, priority)
VALUES (4, 1, NULL, 'DO', 'Practice crop rotation to break pest and disease cycles', 4);

-- Rice DONTs
INSERT IGNORE INTO crop_guidelines (id, crop_id, stage_id, guideline_type, description, priority)
VALUES (5, 1, NULL, 'DONT', 'Do not apply Urea if heavy rain is expected within 24 hours', 1);

INSERT IGNORE INTO crop_guidelines (id, crop_id, stage_id, guideline_type, description, priority)
VALUES (6, 1, NULL, 'DONT', 'Avoid draining fields completely during tillering stage', 2);

INSERT IGNORE INTO crop_guidelines (id, crop_id, stage_id, guideline_type, description, priority)
VALUES (7, 1, NULL, 'DONT', 'Do not over-apply nitrogen fertilizer as it promotes pest attack', 3);

-- Maize DOs
INSERT IGNORE INTO crop_guidelines (id, crop_id, stage_id, guideline_type, description, priority)
VALUES (8, 2, NULL, 'DO', 'Plant in rows with proper spacing (75cm x 25cm) for optimal growth', 1);

INSERT IGNORE INTO crop_guidelines (id, crop_id, stage_id, guideline_type, description, priority)
VALUES (9, 2, NULL, 'DO', 'Apply side dressing of nitrogen when plants are knee-high', 2);

INSERT IGNORE INTO crop_guidelines (id, crop_id, stage_id, guideline_type, description, priority)
VALUES (10, 2, NULL, 'DO', 'Control weeds early as maize is very sensitive to weed competition', 3);

-- Maize DONTs
INSERT IGNORE INTO crop_guidelines (id, crop_id, stage_id, guideline_type, description, priority)
VALUES (11, 2, NULL, 'DONT', 'Do not allow water stress during tasseling and silking stages', 1);

INSERT IGNORE INTO crop_guidelines (id, crop_id, stage_id, guideline_type, description, priority)
VALUES (12, 2, NULL, 'DONT', 'Avoid planting in waterlogged soils', 2);

-- Tomato DOs
INSERT IGNORE INTO crop_guidelines (id, crop_id, stage_id, guideline_type, description, priority)
VALUES (13, 3, NULL, 'DO', 'Stake or cage plants to support fruit weight and improve air circulation', 1);

INSERT IGNORE INTO crop_guidelines (id, crop_id, stage_id, guideline_type, description, priority)
VALUES (14, 3, NULL, 'DO', 'Apply calcium to prevent blossom end rot', 2);

INSERT IGNORE INTO crop_guidelines (id, crop_id, stage_id, guideline_type, description, priority)
VALUES (15, 3, NULL, 'DO', 'Harvest fruits regularly to encourage continued production', 3);

-- Tomato DONTs
INSERT IGNORE INTO crop_guidelines (id, crop_id, stage_id, guideline_type, description, priority)
VALUES (16, 3, NULL, 'DONT', 'Do not wet foliage when irrigating to prevent fungal diseases', 1);

INSERT IGNORE INTO crop_guidelines (id, crop_id, stage_id, guideline_type, description, priority)
VALUES (17, 3, NULL, 'DONT', 'Avoid planting in fields where solanaceous crops were grown recently', 2);

INSERT IGNORE INTO crop_guidelines (id, crop_id, stage_id, guideline_type, description, priority)
VALUES (18, 3, NULL, 'DONT', 'Do not over-irrigate as it causes root diseases and reduces fruit quality', 3);

-- ==========================================
-- ADMIN TABLE SEED DATA
-- ==========================================
-- Note: The 'admins' table is auto-created by JPA/Hibernate from Admin.java entity
-- Default admin password is 'admin123' (BCrypt hashed)
-- You can register new admins via /api/admin/auth/register endpoint

-- Create admins table if not exists (for MySQL compatibility)
CREATE TABLE IF NOT EXISTS admins (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(255) UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    created_at DATETIME,
    updated_at DATETIME
);

-- Seed default super admin (password: admin123)
-- BCrypt hash for 'admin123': $2a$10$N9qo8uLOickgx2ZMRZoMye1eYkVHQl.V8HvT.q4r8Cw8/Tp3q8jUO
INSERT IGNORE INTO admins (id, name, email, phone, password_hash, role, created_at, updated_at)
VALUES (1, 'Super Admin', 'admin@agrosense.com', '0771234567', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIH7YxTXjdJj0xrFfWzqQJx7FqQ.WNHK', 'SUPER_ADMIN', NOW(), NOW());
