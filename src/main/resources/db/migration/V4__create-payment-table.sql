CREATE TABLE payment (
    id BIGSERIAL PRIMARY KEY,
    date TIMESTAMP NOT NULL,
    value NUMERIC(15,2) NOT NULL,
    note TEXT,
    commitment_id BIGINT NOT NULL,
    CONSTRAINT fk_payment_commitment FOREIGN KEY (commitment_id) REFERENCES commitment(id) ON DELETE RESTRICT
);