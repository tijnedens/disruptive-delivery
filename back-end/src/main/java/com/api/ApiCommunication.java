package com.api;

import com.api.sql.DatabaseConnection;
import com.google.gson.Gson;

import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Iterator;

public class ApiCommunication {

    private int offeredPrice = 10;

    private final String order_picked_up = "TRN";
    private final String order_delivered = "DEL";

    private HttpClient httpClient = HttpClient.newHttpClient();
    private Transcript transcript = new Transcript();
    private Gson gson = new Gson();

    private final ArrayList<Delivery> acceptedDeliveries = new ArrayList();

    private int id;
    private String sender_name, sender_address, receiver_name, receiver_address, status, exp_datetime;



    // Get the orders from the API
    private void getOrders() throws Exception{

        // Make the request for orders
        HttpRequest getOrders = HttpRequest.newBuilder()
                .uri(new URI("https://pasd-webshop-API.onrender.com/api/order/"))
                .header("x-api-key", "4jcJwdbKqtccLyVhitEj")
                .GET()
                .build();


        HttpResponse<String> orderResponse = httpClient.send(getOrders, HttpResponse.BodyHandlers.ofString());

        System.out.println(orderResponse.body());

        transcript = gson.fromJson(orderResponse.body(), Transcript.class);

        // Manually set to get the orders
        DatabaseConnection database = new DatabaseConnection();
        // TODO for all orders (for now just one)
        //database.loadDB();
        database.createTable();
        database.loadDB();

        ArrayList<Orders> order = transcript.getOrders();
        int orders_len = transcript.getOrders().size();

        for (int i=0; i != orders_len; i+=1) {
            id = order.get(i).getId();
            sender_name = order.get(i).getName("sender");
            receiver_name = order.get(i).getName("receiver");
            sender_address = order.get(i).getAddress("sender");
            receiver_address = order.get(i).getAddress("receiver");
            exp_datetime = order.get(i).getExpDeliveryTime();
            //act_datetime = order.get(i).getActDeliveryTime();
            status = order.get(i).getStatus();
            database.insertDeliveries(String.valueOf(id), status, sender_name, sender_address,
                    receiver_address, receiver_name, exp_datetime);
        }
        database.loadDB();

    }


    // Makes the offer
    private HttpResponse<String> sendOrders() throws Exception{

        // Prepare the body of request
        Orders send_order = new Orders();
        send_order.setOrder_id(id);
        send_order.setPrice_in_cents(offeredPrice);
        send_order.setExpected_delivery_datetime("2022-12-15T21:28:22.159Z");

        String jsonRequest = gson.toJson(send_order);
        System.out.println(jsonRequest);

        HttpRequest postDelivery = HttpRequest.newBuilder()
                .uri(new URI("https://pasd-webshop-API.onrender.com/api/delivery/"))
                .header("x-api-key", "4jcJwdbKqtccLyVhitEj")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        HttpResponse<String> offerResponse = httpClient.send(postDelivery, HttpResponse.BodyHandlers.ofString());

        System.out.println(offerResponse.body());

        return offerResponse;
    }

    private void createDelivery(HttpResponse<String> offerResponse) {

        if (offerResponse.body().equals("{\"detail\":\"Order is already being delivered\"}")) {
            System.out.println("Order is already being delivered");
            return;
        }

        Delivery delivery = gson.fromJson(offerResponse.body(), Delivery.class);

        // HERE IT CAN BE ADDED TO THE DATABASE
        if (delivery.getStatus().equals("EXP")) {
            System.out.println("Order accepted");
            acceptedDeliveries.add(delivery);
            id = delivery.getId();
            System.out.println(id);
        } else {
            System.out.println("Not accepted");
        }

    }

    // Technically working, but we don't have the label
    private void updateDelivery(int delivery_id, String new_status) throws Exception {

        String url = String.format("https://pasd-webshop-API.onrender.com/api/delivery/%d", delivery_id);
        System.out.println(url);

        Delivery updateStatus = new Delivery();
        updateStatus.setStatus(new_status);

        String jsonRequest = gson.toJson(updateStatus);

        System.out.println(jsonRequest);


        HttpRequest postStatusUpdate = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("x-api-key", "4jcJwdbKqtccLyVhitEj")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        HttpResponse<String> statusUpdateResponse = httpClient.send(postStatusUpdate, HttpResponse.BodyHandlers.ofString());
        System.out.println(statusUpdateResponse.body());

    }

    public static void main(String[] args) throws Exception {

        ApiCommunication demo = new ApiCommunication();

        demo.getOrders();


        HttpResponse<String> offerResponse = demo.sendOrders();

        demo.createDelivery(offerResponse);

        //demo.uploadLabel(7823);

        //demo.updateDelivery(demo.acceptedDeliveries.get(0).getId(), demo.order_picked_up);
        demo.updateDelivery(7823, demo.order_picked_up);
    }
}