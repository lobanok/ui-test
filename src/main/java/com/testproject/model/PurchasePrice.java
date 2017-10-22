package com.testproject.model;

public enum PurchasePrice {

    VALUE_ZERO("0"),
    VALUE_250K("250000"),
    VALUE_500K("500000"),
    VALUE_750K("750000"),
    VALUE_1M("1000000"),
    VALUE_1_25M("1250000"),
    VALUE_1_5M("1500000"),
    VALUE_1_75M("1750000"),
    VALUE_2M("2000000");

    private String value;

    PurchasePrice(String value) {
        this.value = value;
    }

    public String getPrice() {
        return value;
    }

    public long getNumericPrice() {
        return Long.parseLong(value);
    }
}
