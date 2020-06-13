package io.sumac.propertyutils.model;


import io.sumac.propertyutils.annotations.Property;

public class Model3 {

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

    public Model3(@Property(name = "test.found.string") String foundString,
                  @Property(name = "test.found.int") Integer foundInteger,
                  @Property(name = "test.found.int") int foundIntegerPrimitive,
                  @Property(name = "test.found.long") Long foundLong,
                  @Property(name = "test.found.long") long foundLongPrimitive,
                  @Property(name = "test.found.double") Double foundDouble,
                  @Property(name = "test.found.double") double foundDoublePrimitive,
                  @Property(name = "test.found.float") Float foundFloat,
                  @Property(name = "test.found.float") float foundFloatPrimitive,
                  @Property(name = "test.found.boolean") Boolean foundBoolean,
                  @Property(name = "test.found.boolean") boolean foundBooleanPrimitive) {
        super();
        this.foundString = foundString;
        this.foundInteger = foundInteger;
        this.foundIntegerPrimitive = foundIntegerPrimitive;
        this.foundLong = foundLong;
        this.foundLongPrimitive = foundLongPrimitive;
        this.foundDouble = foundDouble;
        this.foundDoublePrimitive = foundDoublePrimitive;
        this.foundFloat = foundFloat;
        this.foundFloatPrimitive = foundFloatPrimitive;
        this.foundBoolean = foundBoolean;
        this.foundBooleanPrimitive = foundBooleanPrimitive;
    }

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