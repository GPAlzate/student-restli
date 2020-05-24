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
import com.linkedin.restli.client.CreateIdRequest;
import com.linkedin.restli.client.CreateRequest;
import com.linkedin.restli.client.Request;
import com.linkedin.restli.client.Response;
import com.linkedin.restli.client.ResponseFuture;
import com.linkedin.restli.client.RestClient;
import com.linkedin.restli.client.RestLiResponseException;
import com.linkedin.restli.client.response.BatchKVResponse;
import com.linkedin.restli.common.BatchResponse;
import com.linkedin.restli.common.EntityResponse;
import com.linkedin.restli.common.ErrorResponse;
import com.linkedin.restli.common.HttpStatus;
import com.linkedin.restli.common.IdResponse;

import io.netty.handler.codec.http.HttpRequest;

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

    /* for http requests */
    private final static HttpClientFactory http = new HttpClientFactory.Builder().build();


    /* create restli transport client */
    private final static Client r2Client = new TransportClientAdapter(
            http.getClient(Collections.<String, String>emptyMap()));

    /* client with the specified uri */
    private final static RestClient restClient
            = new RestClient(r2Client, "http://localhost:8080/");

    // TODO: read up on the Builder pattern
    private final static StudentsRequestBuilders requestBuilder =
            new StudentsRequestBuilders();

    /**
     * Gets student from server.
     *
     * @param http the factory that provides the GET client
     */
    private static Student getStudent(int sid) throws Exception {

        // create the GET request
        StudentsGetRequestBuilder getBuilder = requestBuilder.get();
        Request<Student> getRequest = getBuilder.id(sid).build();

        // get the response of request back
        final ResponseFuture<Student> getFuture = restClient.sendRequest(getRequest);
        return getFuture.getResponse().getEntity();

    }

    /**
     * Gets a collection of students.
     *
     * @param http the factory that provides the BATCH_GET client
     */
    private static void batchGetStudents() throws Exception {

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

        // TODO: use BatchKVResponse<K,V> instead of BatchResponse<T> since
        // BatchGetEntityRequest uses key-value, and BatchGetRequest<T> is
        // deprecated. Why is BatchResponse not also deprecated?
        Response<BatchKVResponse<Integer, EntityResponse<Student>>> response
                = restClient.sendRequest(batchGetRequest).getResponse();

        Map<Integer, EntityResponse<Student>> students
                = response.getEntity().getResults();

        if (students.isEmpty())
            throw new RestLiResponseException(
                    new ErrorResponse().setCode("NOT_FOUND").setStatus(404));

        // TODO: how to use the errors found in BatchResult from the server?
        // use getErrors()?
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

    }

    
    private static IdResponse<Integer> createStudent() throws Exception {

        System.out.println("Enter the student's id number:");
        int sid = Integer.valueOf(scanner.nextLine());
        System.out.print("Checking id availability ... ");

        // throw exception if student is found (not found means we can create
        // this student)
        try {
            Student student = getStudent(sid);
        } catch (RestLiResponseException e) { 

            // TODO I should make the exceptions client side, but i'll do it
            // later when I haaaave to
            System.out.print("ID available! Enter the student's name:");
            String name = scanner.nextLine();

            System.out.print("\nEnter the student's major:");
            String major = scanner.nextLine();

            System.out.print("\nEnter the student's graduation year:");
            int classYear = Integer.parseInt(scanner.nextLine());

            System.out.println("Creating student...");

            // make request ...
            CreateIdRequest<Integer, Student> createRequest 
                    = requestBuilder.create()
                                    .input(new Student().setName(name)
                                                        .setMajor(major)
                                                        .setClassYear(classYear))
                                    .addParam("sid", sid)
                                    .build();

            // ... and get response
            Response<IdResponse<Integer>> response
                    = restClient.sendRequest(createRequest).getResponse();

            if (response.hasError())
                throw response.getError();

            return response.getEntity();

        }

        throw new RestLiResponseException(
                new ErrorResponse().setStatus(
                        HttpStatus.S_409_CONFLICT.getCode()
                    ).setCode("SID_CONFLICT").setMessage("ID already taken!")
                );

    }

    public static void main(String[] args) throws Exception {

        int choice;
        do {

            // need to use parseInt nextLine otherwise the next nextLine won't work
            System.out.println("------------ TEST ------------");
            System.out.println("0) Quit");
            System.out.println("1) GET");
            System.out.println("2) BATCH_GET");
            System.out.println("3) CREATE");
            System.out.print("Enter choice: ");
            choice = Integer.parseInt(scanner.nextLine());
            
            switch (choice) {
                case 1: // GET request
                    try {
                        System.out.println("Enter a student ID: ");
                        Student student = getStudent(
                                Integer.parseInt(scanner.nextLine()));
                        System.out.println("----------------------------");
                        System.out.println(student.getName());
                        System.out.println(student.getMajor());
                        System.out.println(student.getClassYear());
                        System.out.println("----------------------------");
                     } catch (RestLiResponseException e) {
                         System.out.println("No records match that student id!");
                     }
                     break;
                 case 2: // BATCH_GET
                     try {
                         System.out.println("Enter the ids of students you wish to retrieve (space separated):");
                         batchGetStudents();
                     } catch (RestLiResponseException e) {
                         System.out.println("No records exist for the specified set of ids!");
                     }
                     break;
                 case 3: // CREATE
                     try {
                         createStudent();
                         System.out.println("Student successfully added to database!");
                     } catch (RestLiResponseException e) {
                         System.out.println(e.getMessage());
                     }
                     break;
                 default: // 0 = Quit
                     System.out.println("Goodbye!");
                     break;
             } 
        } while (choice != 0);

        // shut down the client
        restClient.shutdown(new FutureCallback<None>());
        http.shutdown(new FutureCallback<None>());

        
    }
}

