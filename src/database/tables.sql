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

INSERT INTO room_class (room_class_type,
                        description,
                        is_active,
                        is_local_booking_active,
                        is_international_booking_active,
                        price_local,
                        price_local_currency,
                        price_international,
                        price_international_currency,
                        created_at,
                        updated_at)
VALUES ('STANDARD',
        'Standard Room',
        true,
        true,
        true,
        12000.00,
        'LKR',
        45.00,
        'USD',
        NOW(),
        NOW()),
       ('DELUXE',
        'Deluxe Room',
        true,
        true,
        true,
        18000.00,
        'LKR',
        65.00,
        'USD',
        NOW(),
        NOW()),
       ('EXECUTIVE',
        'Executive Suite',
        true,
        true,
        true,
        30000.00,
        'LKR',
        110.00,
        'USD',
        NOW(),
        NOW());

INSERT INTO room (
    room_class_id,
    room_number,
    is_active,
    is_local_booking_active,
    is_international_booking_active,
    created_at,
    updated_at
) VALUES
      (
          1,
          '101',
          true,
          true,
          true,
          NOW(),
          NOW()
      ),
      (
          1,
          '102',
          true,
          true,
          false,
          NOW(),
          NOW()
      ),
      (
          2,
          '201',
          true,
          true,
          true,
          NOW(),
          NOW()
      ),
      (
          3,
          '401',
          true,
          true,
          true,
          NOW(),
          NOW()
      );

INSERT INTO room_class_config (
    room_class_id,
    is_active,
    is_local_booking_active,
    is_international_booking_active,
    active_start_date,
    active_end_date,
    price_local,
    price_local_currency,
    price_international,
    price_international_currency,
    created_at,
    updated_at
) VALUES
      (
          2,
          true,
          true,
          true,
          '2026-12-20',
          '2027-01-05',
          22000.00,
          'LKR',
          80.00,
          'USD',
          NOW(),
          NOW()
      ),
      (
          3,
          true,
          true,
          true,
          '2026-12-20',
          '2027-01-05',
          38000.00,
          'LKR',
          140.00,
          'USD',
          NOW(),
          NOW()
      );

INSERT INTO room_config (
    room_id,
    is_active,
    is_local_booking_active,
    is_international_booking_active,
    start_date,
    end_date,
    created_at,
    updated_at
) VALUES
      (
          4,
          false,
          false,
          false,
          '2026-10-01',
          '2026-10-10',
          NOW(),
          NOW()
      ),
      (
          2,
          true,
          true,
          false,
          '2026-11-01',
          '2026-11-15',
          NOW(),
          NOW()
      );