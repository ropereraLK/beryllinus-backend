
INSERT INTO room (
    room_class_id,
    room_number,
    is_active
)
SELECT
    rc.id,
    rc.room_class_type || '-' || LPAD(gs::text, 3, '0'),
    true
FROM room_class rc
CROSS JOIN generate_series(1,10) gs;
