package com.api;

import java.util.*;

public class Orders {

    // TODO: reorganize the data table, delete some uneccesary columns
    // TODO: add data after accepting the request (fyi accepting works)
    // TODO: sending the label and update??

    private int id;
    private int order_id;
    private String send_date;
    private Map<String, String> sender_info;
    private Map<String, String> receiver_info;
    private int price_in_cents;
    private String expected_delivery_datetime;

    public Map setMap(String mapType) {
        Map<String, String> map = new TreeMap<String, String>();
        if (mapType.equals("sender")) {
            map = sender_info;
        } else if (mapType.equals("receiver")) {
            map = receiver_info;
        }
        return map;
    }

    public String getName(String userType) {
        String name = "";
        Map<String, String> map = new TreeMap<String, String>();
        map = setMap(userType);

        for (Map.Entry<String, String>  entry : map.entrySet()) {
            if (entry.getKey().equals("name")) {
                name = entry.getValue();
            }
        }
        return name;
    }

    public String getAddress(String userType) {
        String address = "";
        Map<String, String> map = new TreeMap<String, String>();
        map = setMap(userType);

        for (Map.Entry<String, String>  entry : map.entrySet()) {
            if (!entry.getKey().equals("name")) {
                address += entry.getValue() + " ";
            }
        }
        return address;
    }

    public String getExpDeliveryTime() {
        String exp_datetime = "";
        Map<String, String> map = new TreeMap<String, String>();

        for (Map.Entry<String, String>  entry : map.entrySet()) {
            if (entry.getKey().equals("expected_deliver_datetime")) {
                exp_datetime += entry.getValue() + " ";
            }
        }
        return exp_datetime;
    }

    public String getActDeliveryTime() {
        String act_datetime = "";
        Map<String, String> map = new TreeMap<String, String>();

        for (Map.Entry<String, String>  entry : map.entrySet()) {
            if (entry.getKey().equals("expected_deliver_datetime")) {
                act_datetime += entry.getValue() + " ";
            }
        }
        return act_datetime;
    }

    public String getStatus() {
        String status = "";
        Map<String, String> map = new TreeMap<String, String>();

        for (Map.Entry<String, String>  entry : map.entrySet()) {
            if (entry.getKey().equals("expected_deliver_datetime")) {
                status += entry.getValue() + " ";
            }
        }
        return status;
    }


    public void setSender_info(Map<String, String> sender_info) {
        this.sender_info = sender_info;
    }

    public Object getSender_info() {
        return sender_info;
    }

    public Object getReceiver_info() {
        return receiver_info;
    }

    public void setReceiver_info(Map<String, String> receiver_info) {
        this.receiver_info = receiver_info;
    }

    public String getSend_date() {
        return send_date;
    }

    public void setSend_date(String send_date) {
        this.send_date = send_date;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



}
