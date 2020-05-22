package com.example.student;

import com.linkedin.common.callback.FutureCallback;
import com.linkedin.common.util.None;
import com.linkedin.r2.transport.common.Client;
import com.linkedin.r2.transport.common.bridge.client.TransportClient;
import com.linkedin.r2.transport.common.bridge.client.TransportClientAdapter;
import com.linkedin.r2.transport.http.client.HttpClientFactory;
import com.linkedin.restli.client.BatchGetEntityRequest;
import com.linkedin.restli.client.BatchGetRequest;
import com.linkedin.restli.client.BatchRequest;
import com.linkedin.restli.client.Request;
import com.linkedin.restli.client.Response;
import com.linkedin.restli.client.ResponseFuture;
import com.linkedin.restli.client.RestClient;
import com.linkedin.restli.client.RestLiResponseException;
import com.linkedin.restli.client.response.BatchKVResponse;
import com.linkedin.restli.common.BatchResponse;
import com.linkedin.restli.common.EntityResponse;
import com.example.student.StudentsRequestBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class RestLiStudentsClient {

    /* Read user input for API choice */
    private final static Scanner scanner = new Scanner(System.in);

    // TODO: read up on the Builder pattern
    private final static StudentsRequestBuilders requestBuilder =
            new StudentsRequestBuilders();

    /**
     * Gets student from server.
     *
     * @param http the factory that provides the GET client
     */
    private static void getStudent(HttpClientFactory http) throws Exception {
        
        // 1) create restli transport client
        final Client r2Client = new TransportClientAdapter(
                http.getClient(Collections.<String, String>emptyMap()));

        System.out.println("Enter a student ID: ");
        int sid = scanner.nextInt();

        // 2) create the GET request
        StudentsGetRequestBuilder getBuilder = requestBuilder.get();
        Request<Student> getRequest = getBuilder.id(sid).build();

        // 3) send the GET request TODO: do we need to make multiple of these?
        RestClient restClient = new RestClient(r2Client, "http://localhost:8080/");

        // 4) get the response of request back
        final ResponseFuture<Student> getFuture = restClient.sendRequest(getRequest);
        final Response<Student> response = getFuture.getResponse();

        // print results
        System.out.println(response.getEntity().getName());
        System.out.println(response.getEntity().getMajor());
        System.out.println(response.getEntity().getClassYear());

        // shut down the client TODO: can I move this outside
        restClient.shutdown(new FutureCallback<None>());
        http.shutdown(new FutureCallback<None>());
    }

    /**
     * Gets a collection of students.
     *
     * @param http the factory that provides the BATCH_GET client
     */
    private static void batchGetStudents (HttpClientFactory http) throws Exception {

        final Client r2Client = new TransportClientAdapter(
                http.getClient(Collections.emptyMap()));

        System.out.println("Enter the ids of students you wish to retrieve (space separated):");

        // list of string ids
        String rawInput = scanner.nextLine();
        List<String> rawIds = Arrays.asList(rawInput.split(" "));

        // convert to set of int ids
        Set<Integer> ids = rawIds.stream()
                                    .map(id -> Integer.valueOf(id))
                                    .collect(Collectors.toSet());

        // TODO: docs say use buildKV since build is deprecated.
        // buildKV is in BatchGetRequestBuilder, a deprecated class
        // should use BatchGetEntityRequestBuilder, has a method build()
        StudentsBatchGetRequestBuilder batchGetBuilder = requestBuilder.batchGet();
        BatchGetEntityRequest<Integer, Student> batchGetRequest = batchGetBuilder.ids(ids).build();

        // 3) send the GET request
        RestClient restClient = new RestClient(r2Client, "http://localhost:8080/");

        // TODO: use BatchKVResponse<K,V> instead of BatchResponse<T> since
        // BatchGetEntityRequest uses key-value, and BatchGetRequest<T> is
        // deprecated. Why is BatchResponse not also deprecated?
        Response<BatchKVResponse<Integer, EntityResponse<Student>>> response
                = restClient.sendRequest(batchGetRequest).getResponse();

        Map<Integer, EntityResponse<Student>> students
                = response.getEntity().getResults();

        // TODO: how to the errors found in BatchResult from the server?
        students.entrySet().stream()
                .forEach(entry -> {
                    Integer id = entry.getKey();
                    Student student = entry.getValue().getEntity();
                    if (student == null)
                        System.out.println("No record found for SID " + id);
                    else {
                        System.out.println("ID No. " + id);
                        System.out.println(student.getName());
                        System.out.println(student.getMajor());
                        System.out.println(student.getClassYear());
                    }
                    System.out.println("-----------------------");
                });

        restClient.shutdown(new FutureCallback<None>());
        http.shutdown(new FutureCallback<None>());

    }

    public static void main(String[] args) throws Exception {

        // for http requests
        final HttpClientFactory http = new HttpClientFactory.Builder().build();

        // get user option
        System.out.println("------------ TEST ------------");
        System.out.println("1) GET");
        System.out.println("2) BATCH_GET");
        System.out.println("3) CREATE");
        System.out.print("Enter choice: ");
        
        // need to use parseInt nextLine otherwise the next nextLine won't work
        switch (Integer.parseInt(scanner.nextLine())) {
             case 1: // GET request
                 try {
                     getStudent(http);
                 } catch (RestLiResponseException e) {
                     System.out.println("No records match that student id!");
                 }
                 break;
             case 2: // BATCH_GET
                 try {
                     batchGetStudents(http);
                 } catch (RestLiResponseException e) {
                     //TODO: handle exception
                 }
             default:
                 break;
         } 

        
    }
}

