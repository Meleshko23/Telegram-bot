-- liquibase formatted sql

-- changeset ynmelesh: 1
create table notification_task(
    id          SERIAL,
    chart_id    INTEGER,
    message     text,
    date_time   TIMESTAMP
)