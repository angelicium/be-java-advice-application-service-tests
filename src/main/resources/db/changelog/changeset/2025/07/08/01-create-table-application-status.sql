-- liquibase formatted sql
-- changeset NasonovIgor:2025-07-08-create-table-application-status
CREATE TABLE application_status
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    description TEXT
);

INSERT INTO application_status (name, description)
VALUES ('CREATED', 'Заявка создана'),
       ('PENDING', 'Заявка на рассмотрении'),
       ('ACCEPTED', 'Заявка принята'),
       ('REJECTED', 'Заявка отклонена');