CREATE TABLE expense (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    protocol_date TIMESTAMP NOT NULL,
    due_date TIMESTAMP NOT NULL,
    creditor VARCHAR(255) NOT NULL,
    description TEXT,
    value NUMERIC(15,2) NOT NULL
);