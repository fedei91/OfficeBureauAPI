-- V2__init_data.sql

INSERT INTO users (id, username, email, password, role)
VALUES
    ('38b353ca-0d53-48ee-996e-9701d059bb4f', 'admin', 'admin@example.com', '$2a$12$3YFwwlA1HiV9Fyp1Q.P6L.WW8fF5CuqICxEqYGrex5gPVjIZbOdeS', 'ADMIN');

INSERT INTO users (id, username, email, password, role)
VALUES
    ('22729d8f-7f30-4c70-84bb-64d8f2f812ea', 'user1', 'federico@example.com', '$2a$12$.MxvfRt1lgfotvsy2UXtXOk7w1i2ecwWPAROBO2DlsasY4cxj0b5q', 'USER'),
    ('8db2b6c3-288c-48df-a35d-fe0ddbaea048', 'user2', 'gonzalo@example.com', '$2a$12$jN0FiugFy/jAtT1xoQbcu.Biga7IDNmcr9ujIyE3Tj.lj3zvhRSna', 'USER');
