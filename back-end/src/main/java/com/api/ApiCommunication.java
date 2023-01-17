package com.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class ApiCommunication {

    private static int offeredPrice = 10;
    public static void main(String[] args) throws Exception {

        Transcript transcript = new Transcript();

        HttpRequest getOrders = HttpRequest.newBuilder()
                .uri(new URI("https://pasd-webshop-API.onrender.com/api/order/"))
                .header("x-api-key", "4jcJwdbKqtccLyVhitEj")
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> orderResponse = httpClient.send(getOrders, HttpResponse.BodyHandlers.ofString());

        System.out.println(orderResponse.body());

        Gson gson = new Gson();
        transcript = gson.fromJson(orderResponse.body(), Transcript.class);


        System.out.println(transcript.getOrders().get(1).getId());
        int id = transcript.getOrders().get(1).getId();


        Orders send_order = new Orders();
        send_order.setOrder_id(id);
        send_order.setPrice_in_cents(offeredPrice);
        send_order.setExpected_delivery_datetime("2022-12-15T21:28:22.159Z");

        // might not be necessary to create a new one
        Gson gson1 = new Gson();
        String jsonRequest = gson1.toJson(send_order);
        System.out.println(jsonRequest);

        HttpRequest postDelivery = HttpRequest.newBuilder()
                .uri(new URI("https://pasd-webshop-API.onrender.com/api/delivery/"))
                .header("x-api-key", "4jcJwdbKqtccLyVhitEj")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        HttpResponse<String> offerResponse = httpClient.send(postDelivery, HttpResponse.BodyHandlers.ofString());

        System.out.println(offerResponse.body());

        System.out.println(transcript.getOrders().get(0).getId());

        Delivery delivery1 = gson.fromJson(offerResponse.body(), Delivery.class);

        System.out.println(delivery1.getOrder_id());
        System.out.println(delivery1.getId());

//        while (delivery1.getStatus().equals("REJ")) {
//            offeredPrice -= 10;
//            send_order.setPrice_in_cents(offeredPrice);
//            jsonRequest = gson1.toJson(send_order);
//            // maybe semi-automatic if I figure out how to rewrite the request
//        }



        // Does not work for some reason
        // How to include the delivery_id?
        HttpRequest getSpecificDelivery = HttpRequest.newBuilder()
                .uri(new URI("https://pasd-webshop-API.onrender.com/api/delivery/7488"))
                .header("x-api-key", "4jcJwdbKqtccLyVhitEj")
                .GET()
                .build();

        HttpResponse<String> deliveryDetails = httpClient.send(getSpecificDelivery, HttpResponse.BodyHandlers.ofString());

        System.out.println(deliveryDetails.body());




        // Do the label
//        Gson label = new Gson();
//        String jsonRequest = gson1.toJson(send_order);
//        System.out.println(jsonRequest);
//
//        HttpRequest postDelivery = HttpRequest.newBuilder()
//                .uri(new URI("https://pasd-webshop-API.onrender.com/api/delivery/"))
//                .header("x-api-key", "4jcJwdbKqtccLyVhitEj")
//                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
//                .build();
//



    }

    // Probably separate functions for Updating the delivery

    // Do we just pretend that now the delivery was collected? -> Update the delivery, change in database, notification

    // We should also store them in the database - maybe we can store the Orders objects? possibly should include
    // more things than just the id


}
