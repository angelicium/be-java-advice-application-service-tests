-- liquibase formatted sql
-- changeset Anna Lesnix:2025-07-10-create-table-application-status-log
CREATE TABLE IF NOT EXISTS application_status_log (
    id UUID PRIMARY KEY NOT NULL,
    application_id UUID NOT NULL REFERENCES applications(id),
    status_id INTEGER NOT NULL REFERENCES application_status(id),
    changed_by UUID,
    changed_at TIMESTAMP NOT NULL DEFAULT now()
);