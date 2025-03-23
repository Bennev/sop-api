package com.benevides.sop_api.domain.commitment;

import java.util.Date;

public record CreateCommitmentDTO(Date date, float value, String note, long expense_id) {
}
