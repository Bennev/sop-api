CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE payment (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    date TIMESTAMP NOT NULL,
    value NUMERIC(15,2) NOT NULL,
    note TEXT,
    commitment_id UUID NOT NULL,
    CONSTRAINT fk_payment_commitment FOREIGN KEY (commitment_id) REFERENCES commitment(id) ON DELETE RESTRICT
);