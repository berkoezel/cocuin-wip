package com.hoftingale.cocuin.Utils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Requester {
    private final HttpClient client;
    private final HttpRequest request;

    public Requester(String apiUrl){

        client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        request = HttpRequest.newBuilder()
                 .uri(URI.create(apiUrl))
                 .timeout(Duration.ofSeconds(10))
                 .GET()
                 .build();
    }


    public String getApiData() throws NullPointerException {
        String response = null;
        response = makeRequest();
        if(response == null) throw new NullPointerException();
        return response;
    }

    private String makeRequest() {
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (ConnectException e){
            return null;
         } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return response.body();
    }

}
