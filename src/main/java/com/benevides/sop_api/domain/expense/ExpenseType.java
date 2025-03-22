package com.benevides.sop_api.domain.expense;

public enum ExpenseType {
    BUILDING_CONSTRUCTION("BUILDING_CONSTRUCTION"),
    ROAD_CONSTRUCTION("ROAD_CONSTRUCTION"),
    OTHERS("OTHERS");

    private String type;

    ExpenseType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
