package com.benevides.sop_api.domain.expense;

public enum ExpenseType {
    BUILDING_CONSTRUCTION("building_construction"),
    ROAD_CONSTRUCTION("road_construction"),
    OTHERS("others");

    private String type;

    ExpenseType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
