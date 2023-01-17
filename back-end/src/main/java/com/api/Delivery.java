package com.api;

public class Delivery {
    private String expected_deliver_datetime;

    public String getExpected_deliver_datetime() {
        return expected_deliver_datetime;
    }

    public void setExpected_deliver_datetime(String expected_deliver_datetime) {
        this.expected_deliver_datetime = expected_deliver_datetime;
    }

    public String getActual_deliver_datetime() {
        return actual_deliver_datetime;
    }

    public void setActual_deliver_datetime(String actual_deliver_datetime) {
        this.actual_deliver_datetime = actual_deliver_datetime;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getCost_in_cents() {
        return cost_in_cents;
    }

    public void setCost_in_cents(int cost_in_cents) {
        this.cost_in_cents = cost_in_cents;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String actual_deliver_datetime;
    private int order_id;
    private int cost_in_cents;
    private String status;
    private int id;
}
