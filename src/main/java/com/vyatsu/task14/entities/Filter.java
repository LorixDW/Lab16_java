package com.vyatsu.task14.entities;

import java.util.ArrayList;

public class Filter {
    private String pattern = "";
    private String priceF = "";
    private int max = Integer.MAX_VALUE;
    private int min = -1;
    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getPriceF() {
        return priceF;
    }

    public void setPriceF(String priceF) {
        this.priceF = priceF;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setMin(int min) {
        this.min = min;
    }
}
