package com.api;

import java.util.ArrayList;
import java.util.Collection;

public class Orders {

    public Object getSender_info() {
        return sender_info;
    }

    public void setSender_info(Object sender_info) {
        this.sender_info = sender_info;
    }

    private Object sender_info;

    public Object getReceiver_info() {
        return receiver_info;
    }

    public void setReceiver_info(Object receiver_info) {
        this.receiver_info = receiver_info;
    }

    private Object receiver_info;

    public String getSend_date() {
        return send_date;
    }

    public void setSend_date(String send_date) {
        this.send_date = send_date;
    }


    private String send_date;

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
