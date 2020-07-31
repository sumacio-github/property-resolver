package io.sumac.devtools.propertyresolver.model;

import io.sumac.devtools.propertyresolver.annotations.Property;

public class Model2 {

    private String foundString;

    private Integer foundInteger;

    private int foundIntegerPrimitive;

    private Long foundLong;

    private long foundLongPrimitive;

    private Double foundDouble;

    private double foundDoublePrimitive;

    private Float foundFloat;

    private float foundFloatPrimitive;

    private Boolean foundBoolean;

    private boolean foundBooleanPrimitive;

    public String getFoundString() {
        return foundString;
    }

    @Property(name = "test.found.string")
    public void setFoundString(String foundString) {
        this.foundString = foundString;
    }

    public Integer getFoundInteger() {
        return foundInteger;
    }

    @Property(name = "test.found.int")
    public void setFoundInteger(Integer foundInteger) {
        this.foundInteger = foundInteger;
    }

    public int getFoundIntegerPrimitive() {
        return foundIntegerPrimitive;
    }

    @Property(name = "test.found.int")
    public void setFoundIntegerPrimitive(int foundIntegerPrimitive) {
        this.foundIntegerPrimitive = foundIntegerPrimitive;
    }

    public Long getFoundLong() {
        return foundLong;
    }

    @Property(name = "test.found.long")
    public void setFoundLong(Long foundLong) {
        this.foundLong = foundLong;
    }

    public long getFoundLongPrimitive() {
        return foundLongPrimitive;
    }

    @Property(name = "test.found.long")
    public void setFoundLongPrimitive(long foundLongPrimitive) {
        this.foundLongPrimitive = foundLongPrimitive;
    }

    public Double getFoundDouble() {
        return foundDouble;
    }

    @Property(name = "test.found.double")
    public void setFoundDouble(Double foundDouble) {
        this.foundDouble = foundDouble;
    }

    public double getFoundDoublePrimitive() {
        return foundDoublePrimitive;
    }

    @Property(name = "test.found.double")
    public void setFoundDoublePrimitive(double foundDoublePrimitive) {
        this.foundDoublePrimitive = foundDoublePrimitive;
    }

    public Float getFoundFloat() {
        return foundFloat;
    }

    @Property(name = "test.found.float")
    public void setFoundFloat(Float foundFloat) {
        this.foundFloat = foundFloat;
    }

    public float getFoundFloatPrimitive() {
        return foundFloatPrimitive;
    }

    @Property(name = "test.found.float")
    public void setFoundFloatPrimitive(float foundFloatPrimitive) {
        this.foundFloatPrimitive = foundFloatPrimitive;
    }

    public Boolean getFoundBoolean() {
        return foundBoolean;
    }

    @Property(name = "test.found.boolean")
    public void setFoundBoolean(Boolean foundBoolean) {
        this.foundBoolean = foundBoolean;
    }

    public boolean getFoundBooleanPrimitive() {
        return foundBooleanPrimitive;
    }

    @Property(name = "test.found.boolean")
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
