CREATE TABLE room_class
(
    id                              SERIAL PRIMARY KEY,
    room_class_type                 VARCHAR(50),
    description                     VARCHAR(255),
    is_active                       BOOLEAN,
    is_local_booking_active         BOOLEAN,
    is_international_booking_active BOOLEAN,
    price_local                     NUMERIC(10, 2),
    price_local_currency            VARCHAR(10),
    price_international             NUMERIC(10, 2),
    price_international_currency    VARCHAR(10),
    created_at                      TIMESTAMP,
    updated_at                      TIMESTAMP
);

CREATE TABLE room
(
    id                              SERIAL PRIMARY KEY,
    room_class_id                   INTEGER,
    room_number                     VARCHAR(50),
    is_active                       BOOLEAN,
    is_local_booking_active         BOOLEAN,
    is_international_booking_active BOOLEAN,
    created_at                      TIMESTAMP,
    updated_at                      TIMESTAMP,
    CONSTRAINT fk_room_room_class
        FOREIGN KEY (room_class_id)
            REFERENCES room_class (id)
);

CREATE TABLE room_class_config
(
    id                              SERIAL PRIMARY KEY,
    room_class_id                   INTEGER,
    is_active                       BOOLEAN,
    is_local_booking_active         BOOLEAN,
    is_international_booking_active BOOLEAN,
    active_start_date               DATE,
    active_end_date                 DATE,
    price_local                     NUMERIC(10, 2),
    price_local_currency            VARCHAR(10),
    price_international             NUMERIC(10, 2),
    price_international_currency    VARCHAR(10),
    created_at                      TIMESTAMP,
    updated_at                      TIMESTAMP,
    CONSTRAINT fk_room_class_config_room_class
        FOREIGN KEY (room_class_id)
            REFERENCES room_class (id)
);

CREATE TABLE room_config
(
    id                              BIGSERIAL PRIMARY KEY,
    room_id                         BIGINT,
    is_active                       BOOLEAN,
    is_local_booking_active         BOOLEAN,
    is_international_booking_active BOOLEAN,
    start_date                      DATE,
    end_date                        DATE,
    created_at                      TIMESTAMP,
    updated_at                      TIMESTAMP,
    CONSTRAINT fk_room_config_room
        FOREIGN KEY (room_id)
            REFERENCES room (id)
);