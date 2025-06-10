CREATE TABLE users(
                      id BIGSERIAL PRIMARY KEY,
                      username VARCHAR(30) NOT NULL UNIQUE,
                      password VARCHAR(100) NOT NULL
);

CREATE TABLE roles(
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(50) NOT NULL
);

CREATE TABLE users_roles (
                             user_id BIGINT REFERENCES users(id),
                             role_id INTEGER REFERENCES roles(id),
                             PRIMARY KEY (user_id, role_id)
);

CREATE TABLE tasks(
    id BIGSERIAL PRIMARY KEY,
    description VARCHAR(256) NOT NULL,
    time TIMESTAMP(0) WITHOUT TIME ZONE NOT NULL,
    user_id BIGINT REFERENCES users(id)
);
