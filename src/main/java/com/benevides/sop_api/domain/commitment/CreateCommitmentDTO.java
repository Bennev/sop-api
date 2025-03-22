package com.benevides.sop_api.domain.commitment;

import java.util.Date;
import java.util.UUID;

public record CreateCommitmentDTO(Date date, float value, String note, UUID expense_id) {
}
