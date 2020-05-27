package com.example.student;

import com.linkedin.common.callback.FutureCallback;
import com.linkedin.common.util.None;
import com.linkedin.r2.transport.common.Client;
import com.linkedin.r2.transport.common.bridge.client.TransportClientAdapter;
import com.linkedin.r2.transport.http.client.HttpClientFactory;
import com.linkedin.restli.client.BatchGetEntityRequest;
import com.linkedin.restli.client.CreateIdRequest;
import com.linkedin.restli.client.DeleteRequest;
import com.linkedin.restli.client.GetAllRequest;
import com.linkedin.restli.client.GetRequest;
import com.linkedin.restli.client.Response;
import com.linkedin.restli.client.ResponseFuture;
import com.linkedin.restli.client.RestClient;
import com.linkedin.restli.client.RestLiResponseException;
import com.linkedin.restli.client.UpdateRequest;
import com.linkedin.restli.client.response.BatchKVResponse;
import com.linkedin.restli.common.CollectionResponse;
import com.linkedin.restli.common.EmptyRecord;
import com.linkedin.restli.common.EntityResponse;
import com.linkedin.restli.common.ErrorResponse;
import com.linkedin.restli.common.HttpStatus;
import com.linkedin.restli.common.IdResponse;

import org.apache.commons.lang.StringUtils;

import com.example.student.StudentsRequestBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Client app for the student RestLi service.
 */
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
        GetRequest<Student> getRequest = getBuilder.id(sid).build();

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

        // throw new exception if there are no students in the database
        if (students.isEmpty())
            throw new RestLiResponseException(
                    new ErrorResponse().setCode("NOT_FOUND").setStatus(404));

        // TODO: how to use the errors found in BatchResult from the server?
        // they are all in getEntity().getErrors() but what to do...
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

    private static void getAllStudents() throws Exception {
       StudentsGetAllRequestBuilder getAllBuilder = requestBuilder.getAll(); 
       GetAllRequest<Student> getAllRequest = getAllBuilder.build();
       Response<CollectionResponse<Student>> response = restClient.sendRequest(getAllRequest).getResponse();

       // TODO: difference between stream.forEach and Collections.forEach
       response.getEntity()
                .getElements()
                .stream()
                .forEach(System.out::println);

    }

    
    private static IdResponse<Integer> createStudent(int sid) throws Exception {

        System.out.print("Checking id availability ... ");

        // throw exception if student is found (not found means we can create
        // this student)
        try {
            Student student = getStudent(sid);
        } catch (RestLiResponseException e) { // this is where we create

            // TODO I should put exceptions client side, but i'll do it later
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

            // // TODO: is this necessary
            // if (response.hasError())
            //     throw response.getError();

            return response.getEntity();

        }

        // did I use conflict right?
        // TODO: how to write response codes correctly
        throw new RestLiResponseException(
                new ErrorResponse().setStatus(
                        HttpStatus.S_409_CONFLICT.getCode()
                    ).setCode("SID_CONFLICT").setMessage("ID already taken!")
                );

    }

    /**
     * Allows client to update an existing student.
     * TODO: this isn't supposed to have an option, put the choice in
     * PARTIAL_UPDATE
     *
     * @param sid
     */
    private static void updateStudent(int sid) throws Exception {
        System.out.print("Checking if student exists ... ");

        // GET throws exception if not found => can't update
        Student student = getStudent(sid);

        System.out.println("student found!\nPlease enter a new name (enter to skip):");
        String name = scanner.nextLine();

        // naive validity check: there exists a letter and there are no digits
        if (!naiveIsName(name)) {
            throw new RestLiResponseException(
                    new ErrorResponse().setStatus(
                        HttpStatus.S_400_BAD_REQUEST.getCode()
                        ).setMessage("Name has invalid value.")
                    );
        }
        // if not a blank string, then update name
        else if (!name.isEmpty())
            student.setName(name);

        System.out.println("Please enter a new major (enter to skip):");
        String major = scanner.nextLine();

        if (!naiveIsName(major)) {
            throw new RestLiResponseException(
                    new ErrorResponse().setStatus(
                        HttpStatus.S_400_BAD_REQUEST.getCode()
                        ).setMessage("Major has invalid value.")
                    );
        } else if (!major.isEmpty())
            student.setMajor(major);

        // TODO: what does validateInput do?
        UpdateRequest<Student> updateRequest 
                = requestBuilder.update().id(sid).input(student).build();

        // TODO: it says, for example, createRequest uses EmptyRecord for empty
        // body but this has been deprecated (createIdRequest no longer uses
        // it). Why not for update as well?
        Response<EmptyRecord> response = restClient.sendRequest(updateRequest).getResponse();

    }

    /**
     * Checks (naively) if a string can possibly be a name.
     * Def: string is not a valid name if it has no letters or any digits. Empty
     * string is also valid by vacuous truth.
     *
     * @param str the string whose validity to check
     * @return true if a valid name, false otherwise
     */
    private static boolean naiveIsName(String str) {
        return str.isEmpty()
                || (str.chars().anyMatch(Character::isLetter) 
                    && str.chars().noneMatch(Character::isDigit));
    }

    private static void deleteStudent(int sid) throws Exception {

        System.out.print("Checking if student exists ... ");

        // GET throws exception if not found => can't delete
        Student student = getStudent(sid);

        System.out.println("student found!\nDeleting "
                + student.getName() + " from the database ...");

        // finally delete student (will throw error if deletion fails)
        DeleteRequest<Student> deleteRequest = requestBuilder.delete().id(sid).build();
        Response<EmptyRecord> response = restClient.sendRequest(deleteRequest).getResponse();

    }

    public static void main(String[] args) throws Exception {

        int choice;
        do {

            // need to use parseInt nextLine otherwise the next nextLine won't work
            System.out.println("------------ TEST ------------");
            System.out.println("0) Quit");
            System.out.println("1) GET              4) CREATE");
            System.out.println("2) BATCH_GET        5) UPDATE");
            System.out.println("3) GET_ALL          6) DELETE");
            System.out.print("Enter choice: ");

            String rawInput = scanner.nextLine();
            
            // NaN case
            if (!StringUtils.isNumeric(rawInput)) {
                System.out.println("Input NaN; please try again.");
                choice = -1;
                continue;
            }

            choice = Integer.parseInt(rawInput);
            switch (choice) {
                case 0: // QUIT
                    System.out.println("Goodbye!");
                    break;
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
                 case 3: // GET_ALL
                     getAllStudents();
                     break;
                 case 4: // CREATE
                     try {
                         System.out.println("Enter the student's id number:");
                         createStudent(Integer.valueOf(scanner.nextLine()));
                         System.out.println("Student successfully added to database!");
                     } catch (RestLiResponseException e) {
                         System.out.println(e.getMessage());
                     }
                     break;
                 case 5: // UPDATE
                     try {
                         System.out.println("Enter the student ID of whom you wish to update: ");
                         updateStudent(Integer.valueOf(scanner.nextLine()));
                         System.out.println("Student successfully updated!");
                     } catch (RestLiResponseException e) {
                         System.out.println(e.getMessage());
                     }
                     break;
                 case 6: // DELETE
                     try {
                         System.out.println("Enter the student ID of whom you wish to delete: ");
                         deleteStudent(Integer.valueOf(scanner.nextLine()));
                         System.out.println("Student successfully deleted!");
                     } catch (RestLiResponseException e) {
                         System.out.println(e.getServiceErrorMessage());
                     }
                     break;
                 default: // input number that is not a choice
                     System.out.println("Invalid choice. Please try again.");
                     break;
             } 
        } while (choice != 0);

        // shut down the client
        restClient.shutdown(new FutureCallback<None>());
        http.shutdown(new FutureCallback<None>());

        
    }
}

