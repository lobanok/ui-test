package com.testproject.model;

public enum PaymentFrequency {

    MONTHLY(0),
    BIWEEKLY(1),
    BIWEEKLY_PLUS(2),
    WEEKLY(3),
    WEEKLY_PLUS(4);

    private int elementIndex;

    PaymentFrequency(int elementIndex) {
        this.elementIndex = elementIndex;
    }

    public int getElementIndex() {
        return elementIndex;
    }
}
