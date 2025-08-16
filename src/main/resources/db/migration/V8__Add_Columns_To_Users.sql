ALTER TABLE users
ADD full_name VARCHAR(255) DEFAULT NULL;

ALTER TABLE users
ADD account_non_expired bit(1) DEFAULT 1;

ALTER TABLE users
ADD account_non_locked bit(1) DEFAULT 1;

ALTER TABLE users
ADD credentials_non_expired bit(1) DEFAULT 1;

ALTER TABLE users
ADD enabled bit(1) DEFAULT 1;