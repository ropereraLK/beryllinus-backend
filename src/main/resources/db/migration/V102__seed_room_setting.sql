-- REVIEW REQUIRED: Add created_at and updated_at columns with CURRENT_TIMESTAMP
-- Example value: CURRENT_TIMESTAMP, CURRENT_TIMESTAMP

-- V102__seed_room_setting.sql
-- Generates RoomSetting records for all RoomClasses
-- Date Range: 2026-06-09 to 2027-06-09 (inclusive)

INSERT INTO room_setting (
    room_class_id,
    date,
    calculated_is_active,
    calc_price_local,
    calc_price_local_currency,
    cal_is_local_booking_active,
    calc_price_international,
    calc_price_international_currency,
    calc_is_international_booking_active,
    available_rooms
)
SELECT
    rc.id,
    d::date,
    rc.is_active,
    rc.price_local,
    rc.price_local_currency,
    rc.is_local_booking_active,
    rc.price_international,
    rc.price_international_currency,
    rc.is_international_booking_active,
    10
FROM room_class rc
CROSS JOIN generate_series(
    DATE '2026-06-09',
    DATE '2027-06-09',
    INTERVAL '1 day'
) d
ORDER BY rc.id, d;
