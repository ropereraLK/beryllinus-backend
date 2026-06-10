TRUNCATE TABLE
    room_setting,
    room_config,
    room_class_config,
    room
RESTART IDENTITY CASCADE;

TRUNCATE TABLE
    room_class
    RESTART IDENTITY CASCADE;

commit;

ALTER TABLE room_setting
ALTER COLUMN calc_price_local_currency TYPE VARCHAR(20);

ALTER TABLE room_setting
ALTER COLUMN calc_price_international_currency TYPE VARCHAR(20);

DROP TABLE room_setting CASCADE;

SELECT * FROM flyway_schema_history ORDER BY installed_rank;

DROP TABLE flyway_schema_history;