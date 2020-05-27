
package com.example.student;

import java.util.EnumSet;
import java.util.HashMap;
import javax.annotation.Generated;
import com.linkedin.data.template.DynamicRecordMetadata;
import com.linkedin.restli.client.OptionsRequestBuilder;
import com.linkedin.restli.client.RestliRequestOptions;
import com.linkedin.restli.common.ResourceMethod;
import com.linkedin.restli.common.ResourceSpec;
import com.linkedin.restli.common.ResourceSpecImpl;
import com.linkedin.restli.internal.common.URIParamUtils;


/**
 * Sample Rest.li resource for a student. Identifies student by their ID and
 *  retrieves the corresponding student info
 * 
 *  TODO:
 *  - Explore differences between resource annotations, e.g. RestLiCollection,
 *    RestLiSimpleResource, etc.
 * 
 *       https://linkedin.github.io/rest.li/user_guide/restli_server#runtimes
 * 
 *  - Read about resource conventions
 * 
 *       https://linkedin.github.io/rest.li/modeling/modeling#collection
 * 
 *  - Learn how to use RestLiConfig. Where do we specify this?
 * 
 *  - Put the id as a field in the PDL?
 * 
 * generated from: com.example.student.impl.StudentsResource
 * 
 * @deprecated
 *     This format of request builder is obsolete. Please use {@link com.example.student.StudentsRequestBuilders} instead.
 */
@Generated(value = "com.linkedin.pegasus.generator.JavaCodeUtil", comments = "Rest.li Request Builder. Generated from api/src/main/idl/com.example.student.students.restspec.json.")
@Deprecated
public class StudentsBuilders {

    private final String _baseUriTemplate;
    private RestliRequestOptions _requestOptions;
    private final static String ORIGINAL_RESOURCE_PATH = "students";
    private final static ResourceSpec _resourceSpec;

    static {
        HashMap<String, DynamicRecordMetadata> requestMetadataMap = new HashMap<String, DynamicRecordMetadata>();
        HashMap<String, DynamicRecordMetadata> responseMetadataMap = new HashMap<String, DynamicRecordMetadata>();
        HashMap<String, com.linkedin.restli.common.CompoundKey.TypeInfo> keyParts = new HashMap<String, com.linkedin.restli.common.CompoundKey.TypeInfo>();
        _resourceSpec = new ResourceSpecImpl(EnumSet.of(ResourceMethod.GET, ResourceMethod.BATCH_GET, ResourceMethod.CREATE, ResourceMethod.UPDATE, ResourceMethod.DELETE, ResourceMethod.GET_ALL), requestMetadataMap, responseMetadataMap, Integer.class, null, null, Student.class, keyParts);
    }

    public StudentsBuilders() {
        this(RestliRequestOptions.DEFAULT_OPTIONS);
    }

    public StudentsBuilders(RestliRequestOptions requestOptions) {
        _baseUriTemplate = ORIGINAL_RESOURCE_PATH;
        _requestOptions = assignRequestOptions(requestOptions);
    }

    public StudentsBuilders(String primaryResourceName) {
        this(primaryResourceName, RestliRequestOptions.DEFAULT_OPTIONS);
    }

    public StudentsBuilders(String primaryResourceName, RestliRequestOptions requestOptions) {
        _baseUriTemplate = primaryResourceName;
        _requestOptions = assignRequestOptions(requestOptions);
    }

    private String getBaseUriTemplate() {
        return _baseUriTemplate;
    }

    public RestliRequestOptions getRequestOptions() {
        return _requestOptions;
    }

    public String[] getPathComponents() {
        return URIParamUtils.extractPathComponentsFromUriTemplate(_baseUriTemplate);
    }

    private static RestliRequestOptions assignRequestOptions(RestliRequestOptions requestOptions) {
        if (requestOptions == null) {
            return RestliRequestOptions.DEFAULT_OPTIONS;
        } else {
            return requestOptions;
        }
    }

    public static String getPrimaryResource() {
        return ORIGINAL_RESOURCE_PATH;
    }

    public OptionsRequestBuilder options() {
        return new OptionsRequestBuilder(getBaseUriTemplate(), getRequestOptions());
    }

    /**
     * Retrieves all students from the map.
     * 
     *  Request:
     *       http GET localhost:8080/students
     * 
     * @return
     *     builder for the resource method
     */
    public StudentsGetAllBuilder getAll() {
        return new StudentsGetAllBuilder(getBaseUriTemplate(), _resourceSpec, getRequestOptions());
    }

    /**
     * Adds a student to the database.
     * 
     *  Request:
     *       TODO
     * 
     * @return
     *     builder for the resource method
     */
    public StudentsCreateBuilder create() {
        return new StudentsCreateBuilder(getBaseUriTemplate(), _resourceSpec, getRequestOptions());
    }

    /**
     * Gets a set of students that correspond to the specified ids.
     * 
     *  Request:
     *       http GET localhost:8080/students?<ids=1&ids=2&...>
     * 
     * @return
     *     builder for the resource method
     */
    public StudentsBatchGetBuilder batchGet() {
        return new StudentsBatchGetBuilder(getBaseUriTemplate(), _resourceSpec, getRequestOptions());
    }

    /**
     * Gets a student from in-memory store. 
     * 
     *  Request:
     *       http GET localhost:8080/students/<id>
     * 
     * @return
     *     builder for the resource method
     */
    public StudentsGetBuilder get() {
        return new StudentsGetBuilder(getBaseUriTemplate(), _resourceSpec, getRequestOptions());
    }

    /**
     * Updates a student by replacing the entire entity.
     * 
     * @return
     *     builder for the resource method
     */
    public StudentsUpdateBuilder update() {
        return new StudentsUpdateBuilder(getBaseUriTemplate(), _resourceSpec, getRequestOptions());
    }

    /**
     * Deletes a student from the database.
     * 
     * @return
     *     builder for the resource method
     */
    public StudentsDeleteBuilder delete() {
        return new StudentsDeleteBuilder(getBaseUriTemplate(), _resourceSpec, getRequestOptions());
    }

}
