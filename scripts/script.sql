DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS statuses CASCADE;
DROP TABLE IF EXISTS  vendors CASCADE;
DROP TABLE IF EXISTS users_cards CASCADE;
/*CREATE TABLE roles (
                       role_id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                       role_name VARCHAR(255) NOT NULL UNIQUE
);

INSERT INTO roles(role_name) VALUES ('USER');
INSERT INTO roles(role_name) VALUES ('ADMIN');

CREATE TABLE statuses (
                          status_id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                          status_name VARCHAR(255) NOT NULL UNIQUE
);

INSERT INTO statuses(status_name) VALUES ('ACTIVE');
INSERT INTO statuses(status_name) VALUES ('BANNED');*/

CREATE TABLE users (
                       user_id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       phone VARCHAR(255) NOT NULL UNIQUE,
                       first_name VARCHAR(255) NOT NULL,
                       second_name VARCHAR(255) NOT NULL,
                       patronymic_name VARCHAR(255),
                       role VARCHAR(255) DEFAULT('USER'),
                       status VARCHAR(255) DEFAULT('ACTIVE'),
                       created_at timestamptz NOT NULL,
                       last_login timestamptz
);

CREATE TABLE vendors (
        vendor_id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
        vendor_name varchar(50) NOT NULL
);

CREATE TABLE users_cards (
        card_id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
        user_id integer REFERENCES users(user_id),
        vendor_id integer REFERENCES vendors(vendor_id),
        created_at timestamptz NOT NULL,
        expire_at timestamptz NOT NULL,
        cvv varchar(3) NOT NULL,
        balance NUMERIC(9, 2)
);

/*CREATE TABLE users (
                       user_id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                       email VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       phone VARCHAR(255) NOT NULL,
                       first_name VARCHAR(255) NOT NULL,
                       second_name VARCHAR(255) NOT NULL,
                       patronymic_name VARCHAR(255),
                       role VARCHAR(255) DEFAULT ('USER'),
                       status VARCHAR(255) DEFAULT('ACTIVE'),
                       created_at timestamptz NOT NULL,
                       last_login timestamptz
);

  DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS statuses CASCADE;

CREATE TABLE roles (
    role_id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    role_name VARCHAR(255) NOT NULL UNIQUE
);

INSERT INTO roles(role_name) VALUES ('USER');
INSERT INTO roles(role_name) VALUES ('ADMIN');

CREATE TABLE statuses (
    status_id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    status_name VARCHAR(255) NOT NULL UNIQUE
);

INSERT INTO statuses(status_name) VALUES ('ACTIVE');
INSERT INTO statuses(status_name) VALUES ('BANNED');

CREATE TABLE users (
    user_id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    second_name VARCHAR(255) NOT NULL,
    patronymic_name VARCHAR(255),
    role_id integer REFERENCES roles(role_id) DEFAULT (1),
    status_id integer REFERENCES statuses(status_id) DEFAULT (1),
    created_at timestamptz NOT NULL,
    last_login timestamptz
);

*/