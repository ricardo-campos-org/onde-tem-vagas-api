CREATE TABLE state (
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(30) NOT NULL,
    acronym CHAR(2) NOT NULL
);

CREATE TABLE city (
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(30) NOT NULL,
    state_id INTEGER NOT NULL REFERENCES state (id)
);

CREATE TABLE portal (
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(50) NOT NULL,
    jobs_url VARCHAR(300) NOT NULL,
    city_id  INTEGER NOT NULL REFERENCES city (id),
    enabled  BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE portal_job (
    id              SERIAL PRIMARY KEY,
    job_title       VARCHAR(1000) NOT NULL,
    company_name    VARCHAR(1000) NOT NULL,
    job_type        VARCHAR(30),
    job_description VARCHAR(2000) NOT NULL,
    published_at    VARCHAR(30),
    job_url         VARCHAR(2000) NOT NULL,
    portal_id       INTEGER NOT NULL REFERENCES portal (id),
    created_at      TIMESTAMP NOT NULL,
    processed       BOOLEAN NOT NULL
);

CREATE INDEX idx_portal_jobs_url ON portal_job (job_url);

CREATE TABLE user (
    id          SERIAL PRIMARY KEY,
    first_name  VARCHAR(20) NOT NULL,
    last_name   VARCHAR(30) NOT NULL,
    email       VARCHAR(100) UNIQUE NOT NULL,
    city_id     INTEGER NOT NULL REFERENCES city (id),
    enabled     BOOLEAN NOT NULL DEFAULT TRUE,
    updated_at  TIMESTAMP NOT NULL DEFAULT NOW(),
    terms       VARCHAR(1000) NULL
);

CREATE TABLE user_job (
    id             SERIAL PRIMARY KEY,
    person_id      INTEGER NOT NULL REFERENCES person (id),
    portal_job_id  INTEGER NOT NULL REFERENCES portal_job (id)
);

CREATE TABLE crawler_log (
    id         SERIAL PRIMARY KEY,
    portal_id  INTEGER NOT NULL REFERENCES portal (id),
    text       VARCHAR(2000) NOT NULL,
    created_at TIMESTAMP NOT NULL
);