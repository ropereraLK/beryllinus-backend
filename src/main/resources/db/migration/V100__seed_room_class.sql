-- REVIEW REQUIRED: Add created_at and updated_at columns with CURRENT_TIMESTAMP
-- Example value: CURRENT_TIMESTAMP, CURRENT_TIMESTAMP


INSERT INTO room_class (
    room_class_type,
    description,
    is_active,
    price_local,
    price_local_currency,
    is_local_booking_active,
    price_international,
    price_international_currency,
    is_international_booking_active
)
VALUES
('STANDARD','Standard Room',true,10000,'LKR',true,50,'USD',true),
('EXECUTIVE','Executive Room',true,15000,'LKR',true,75,'USD',true),
('LUXURY','Luxury Room',true,25000,'LKR',true,125,'USD',true);
