package io.sumac.propertyhelper.model;

import io.sumac.propertyhelper.annotations.Property;

public class Model1 {

    @Property(name = "test.found.string")
    private String foundString;
    @Property(name = "test.found.int")
    private Integer foundInteger;
    @Property(name = "test.found.int")
    private int foundIntegerPrimitive;
    @Property(name = "test.found.long")
    private Long foundLong;
    @Property(name = "test.found.long")
    private long foundLongPrimitive;
    @Property(name = "test.found.double")
    private Double foundDouble;
    @Property(name = "test.found.double")
    private double foundDoublePrimitive;
    @Property(name = "test.found.float")
    private Float foundFloat;
    @Property(name = "test.found.float")
    private float foundFloatPrimitive;
    @Property(name = "test.found.boolean")
    private Boolean foundBoolean;
    @Property(name = "test.found.boolean")
    private boolean foundBooleanPrimitive;

    public String getFoundString() {
        return foundString;
    }

    public void setFoundString(String foundString) {
        this.foundString = foundString;
    }

    public Integer getFoundInteger() {
        return foundInteger;
    }

    public void setFoundInteger(Integer foundInteger) {
        this.foundInteger = foundInteger;
    }

    public int getFoundIntegerPrimitive() {
        return foundIntegerPrimitive;
    }

    public void setFoundIntegerPrimitive(int foundIntegerPrimitive) {
        this.foundIntegerPrimitive = foundIntegerPrimitive;
    }

    public Long getFoundLong() {
        return foundLong;
    }

    public void setFoundLong(Long foundLong) {
        this.foundLong = foundLong;
    }

    public long getFoundLongPrimitive() {
        return foundLongPrimitive;
    }

    public void setFoundLongPrimitive(long foundLongPrimitive) {
        this.foundLongPrimitive = foundLongPrimitive;
    }

    public Double getFoundDouble() {
        return foundDouble;
    }

    public void setFoundDouble(Double foundDouble) {
        this.foundDouble = foundDouble;
    }

    public double getFoundDoublePrimitive() {
        return foundDoublePrimitive;
    }

    public void setFoundDoublePrimitive(double foundDoublePrimitive) {
        this.foundDoublePrimitive = foundDoublePrimitive;
    }

    public Float getFoundFloat() {
        return foundFloat;
    }

    public void setFoundFloat(Float foundFloat) {
        this.foundFloat = foundFloat;
    }

    public float getFoundFloatPrimitive() {
        return foundFloatPrimitive;
    }

    public void setFoundFloatPrimitive(float foundFloatPrimitive) {
        this.foundFloatPrimitive = foundFloatPrimitive;
    }

    public Boolean getFoundBoolean() {
        return foundBoolean;
    }

    public void setFoundBoolean(Boolean foundBoolean) {
        this.foundBoolean = foundBoolean;
    }

    public boolean getFoundBooleanPrimitive() {
        return foundBooleanPrimitive;
    }

    public void setFoundBooleanPrimitive(boolean foundBooleanPrimitive) {
        this.foundBooleanPrimitive = foundBooleanPrimitive;
    }

    @Override
    public String toString() {
        return "Model1 [foundString=" + foundString + ", foundInteger=" + foundInteger + ", foundIntegerPrimitive="
                + foundIntegerPrimitive + ", foundLong=" + foundLong + ", foundLongPrimitive=" + foundLongPrimitive
                + ", foundDouble=" + foundDouble + ", foundDoublePrimitive=" + foundDoublePrimitive + ", foundFloat="
                + foundFloat + ", foundFloatPrimitive=" + foundFloatPrimitive + ", foundBoolean=" + foundBoolean
                + ", foundBooleanPrimitive=" + foundBooleanPrimitive + "]";
    }

}