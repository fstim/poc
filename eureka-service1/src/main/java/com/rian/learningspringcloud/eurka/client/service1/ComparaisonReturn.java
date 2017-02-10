package com.rian.learningspringcloud.eurka.client.service1;

class ComparaisonReturn {

    private Integer initialValue;
    private Integer strangeValue;
    private Boolean comparaisonResult;


    Boolean getComparaisonResult() {
        return comparaisonResult;
    }

    void setComparaisonResult(Boolean comparaisonResult) {
        this.comparaisonResult = comparaisonResult;
    }

    Integer getStrangeValue() {
        return strangeValue;
    }

    Integer getInitialValue() {
        return initialValue;
    }

    void setInitialValue(Integer initialValue) {
        this.initialValue = initialValue;
    }

    void setStrangeValue(Integer strangeValue) {
        this.strangeValue = strangeValue;
    }
}
