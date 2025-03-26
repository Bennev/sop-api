package com.benevides.sop_api.domain.expense;

public enum ExpenseStatus {
    WAITING_COMMITMENT("WAITING_COMMITMENT"),
    PARTIALLY_COMMITTED("PARTIALLY_COMMITTED"),
    WAITING_PAYMENT("WAITING_PAYMENT"),
    PARTIALLY_PAID("PARTIALLY_PAID"),
    PAID("PAID");

    private String status;

    ExpenseStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}
