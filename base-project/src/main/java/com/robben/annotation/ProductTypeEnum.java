package com.robben.annotation;

public enum ProductTypeEnum {
    P("原材料"), M("加工品");

    ProductTypeEnum(String type) {
        this.type = type;
    }

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
