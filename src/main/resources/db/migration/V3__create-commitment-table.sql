CREATE TABLE commitment (
    id BIGSERIAL PRIMARY KEY,
    date TIMESTAMP NOT NULL,
    value NUMERIC(15,2) NOT NULL,
    note TEXT,
    expense_id BIGINT NOT NULL,
    CONSTRAINT fk_commitment_expense FOREIGN KEY (expense_id) REFERENCES expense(id) ON DELETE RESTRICT
);