package com.example.student;

import com.linkedin.common.callback.FutureCallback;
import com.linkedin.common.util.None;
import com.linkedin.r2.transport.common.Client;
import com.linkedin.r2.transport.common.bridge.client.TransportClient;
import com.linkedin.r2.transport.common.bridge.client.TransportClientAdapter;
import com.linkedin.r2.transport.http.client.HttpClientFactory;
import com.linkedin.restli.client.Request;
import com.linkedin.restli.client.Response;
import com.linkedin.restli.client.ResponseFuture;
import com.linkedin.restli.client.RestClient;
import com.linkedin.restli.client.RestLiResponseException;
import com.example.student.StudentsRequestBuilders;
import java.util.Collections;
import java.util.Scanner;

public class RestLiStudentsClient {

    // TODO: read up on the Builder pattern
    private final static StudentsRequestBuilders requestBuilder =
            new StudentsRequestBuilders();

    public static void main(String[] args) throws Exception {

        // for http requests
        final HttpClientFactory http = new HttpClientFactory.Builder().build();

        // get user option
        Scanner scanner = new Scanner(System.in);
        System.out.println("------------ TEST ------------");
        System.out.println("1) GET");
        System.out.println("2) BATCH_GET");
        int choice = scanner.nextInt();

        final Client r2Client = new TransportClientAdapter(
                http.getClient(Collections.<String, String>emptyMap()));

        System.out.println("Enter a student ID: ");
        int sid = scanner.nextInt();

        // create the GET request
        StudentsGetRequestBuilder getBuilder = requestBuilder.get();
        Request<Student> getRequest = getBuilder.id(sid).build();

        // send the GET request
        RestClient restClient = new RestClient(r2Client, "http://localhost:8080/");

        try {
            final ResponseFuture<Student> getFuture = restClient.sendRequest(getRequest);
            final Response<Student> response = getFuture.getResponse();

            System.out.println(response.getEntity().getName());
            System.out.println(response.getEntity().getMajor());
            System.out.println(response.getEntity().getClassYear());
        } catch (RestLiResponseException e) { // 404 response

            System.out.println("No records match that student id!");

        } finally {
            restClient.shutdown(new FutureCallback<None>());
            http.shutdown(new FutureCallback<None>());
        }
        
    }
}

