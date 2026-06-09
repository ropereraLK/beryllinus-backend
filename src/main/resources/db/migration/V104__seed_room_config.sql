
INSERT INTO room_config (
    room_id,
    is_active,
    start_date,
    end_date
)
SELECT
    r.id,
    false,
    DATE '2026-08-01',
    DATE '2026-08-15'
FROM room r
WHERE MOD(r.id,5)=0;
