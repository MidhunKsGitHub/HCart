package com.midhun.hawks_hcart.View;

public class ProductSize {

    private String id;

    public ProductSize(String id, String combination, String product, String attribute, String attributeValue, String priceAttribute, String groupname) {
        this.id = id;
        this.combination = combination;
        this.product = product;
        this.attribute = attribute;
        this.attributeValue = attributeValue;
        this.priceAttribute = priceAttribute;
        this.groupname = groupname;
    }

    private String combination;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCombination() {
        return combination;
    }

    public void setCombination(String combination) {
        this.combination = combination;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public String getPriceAttribute() {
        return priceAttribute;
    }

    public void setPriceAttribute(String priceAttribute) {
        this.priceAttribute = priceAttribute;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    private String product;
    private String attribute;
    private String attributeValue;
    private String priceAttribute;
    private String groupname;
}
