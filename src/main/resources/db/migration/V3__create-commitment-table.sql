CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE commitment (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    date TIMESTAMP NOT NULL,
    value NUMERIC(15,2) NOT NULL,
    note TEXT,
    expense_id UUID NOT NULL,
    CONSTRAINT fk_commitment_expense FOREIGN KEY (expense_id) REFERENCES expense(id) ON DELETE RESTRICT
);