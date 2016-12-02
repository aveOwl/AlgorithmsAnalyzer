package com.analyzer.util;

public class ArraySpawnStrategy {
    private Integer minValue;
    private Integer maxValue;
    private Integer step;

    public ArraySpawnStrategy(Integer minValue, Integer maxValue, Integer step) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.step = step;
    }

    public Integer minValue() {
        return this.minValue;
    }

    public Integer maxValue() {
        return this.maxValue;
    }

    public Integer step() {
        return this.step;
    }

    @Override
    public String toString() {
        return "ArraySpawnStrategy{" + "minValue=" + minValue + ", maxValue=" + maxValue + ", step=" + step + '}';
    }
}
