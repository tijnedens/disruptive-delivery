package com.api;

public class Orders {
    private int id;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    private int order_id;

    public int getPrice_in_cents() {
        return price_in_cents;
    }

    public void setPrice_in_cents(int price_in_cents) {
        this.price_in_cents = price_in_cents;
    }

    public String getExpected_delivery_datetime() {
        return expected_delivery_datetime;
    }

    public void setExpected_delivery_datetime(String expected_delivery_daytime) {
        this.expected_delivery_datetime = expected_delivery_daytime;
    }

    private int price_in_cents;
    private String expected_delivery_datetime;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



}
