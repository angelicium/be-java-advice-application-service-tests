-- liquibase formatted sql
-- changeset Miloshevich Alexandr:2025-07-09-create-table-applications

CREATE TABLE IF NOT EXISTS applications (
    id UUID PRIMARY KEY NOT NULL,
    user_id UUID NOT NULL,
    specialization VARCHAR(255) NOT NULL,
    skills TEXT NOT NULL,
    experience TEXT NOT NULL,
    status_id INTEGER NOT NULL DEFAULT 1,
    comment TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,

    CONSTRAINT fk_applications_applicationStatus
    FOREIGN KEY (status_id)
    REFERENCES application_status(id)
    ON DELETE CASCADE
    );