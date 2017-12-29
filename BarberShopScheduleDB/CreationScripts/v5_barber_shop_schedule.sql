ALTER TABLE session RENAME id TO username;
ALTER TABLE session ALTER COLUMN username TYPE TEXT;
