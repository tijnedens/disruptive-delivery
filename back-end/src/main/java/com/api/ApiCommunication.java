package com.api;

import com.google.gson.Gson;

import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class ApiCommunication {

    private int offeredPrice = 10;

    private final String order_picked_up = "TRN";
    private final String order_delivered = "DEL";

    private HttpClient httpClient = HttpClient.newHttpClient();
    private Transcript transcript = new Transcript();
    private Gson gson = new Gson();

    private final ArrayList<Delivery> acceptedDeliveries = new ArrayList();

    private int id;

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
        System.out.println(transcript.getOrders().get(5).getId());
        id = transcript.getOrders().get(5).getId();

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