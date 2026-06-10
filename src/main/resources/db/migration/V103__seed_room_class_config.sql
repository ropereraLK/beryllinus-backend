-- REVIEW REQUIRED: Add created_at and updated_at columns with CURRENT_TIMESTAMP
-- Example value: CURRENT_TIMESTAMP, CURRENT_TIMESTAMP


INSERT INTO room_class_config (
    room_class_id,
    is_active,
    start_date,
    end_date,
    price_local,
    price_local_currency,
    is_local_booking_active,
    price_international,
    price_international_currency,
    is_international_booking_active
)
SELECT
    rc.id,
    true,
    DATE '2026-12-20',
    DATE '2027-01-05',
    rc.price_local * 1.25,
    rc.price_local_currency,
    true,
    rc.price_international * 1.25,
    rc.price_international_currency,
    true
FROM room_class rc;
