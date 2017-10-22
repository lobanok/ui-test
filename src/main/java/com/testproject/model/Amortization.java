package com.testproject.model;

public enum Amortization {

    YEARS_15(0),
    YEARS_16(1),
    YEARS_17(2),
    YEARS_18(3),
    YEARS_19(4),
    YEARS_20(6),
    YEARS_21(7),
    YEARS_22(8),
    YEARS_23(9),
    YEARS_24(10),
    YEARS_25(11),
    YEARS_30(13);

    private int elementIndex;

    Amortization(int elementIndex) {
        this.elementIndex = elementIndex;
    }

    public int getElementIndex() {
        return elementIndex;
    }
}
