-- V1__init.sql

DO $$ BEGIN
  IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'users') THEN
    CREATE TABLE users (
      id UUID PRIMARY KEY,
      username VARCHAR(255) NOT NULL,
      email VARCHAR(255) NOT NULL,
      password VARCHAR(255) NOT NULL,
      is_deleted BOOLEAN NOT NULL DEFAULT false,
      role VARCHAR(255),

      CONSTRAINT uk_username UNIQUE (username),
      CONSTRAINT uk_email UNIQUE (email)
    );

    ALTER TABLE users ADD CONSTRAINT chk_role CHECK (role IN ('ADMIN', 'USER'));

    -- Puedes añadir índices adicionales si es necesario
    -- CREATE INDEX idx_users_email ON users(email);
  END IF;
END $$;
