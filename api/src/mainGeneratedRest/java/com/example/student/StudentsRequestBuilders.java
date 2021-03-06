
package com.example.student;

import java.util.EnumSet;
import java.util.HashMap;
import javax.annotation.Generated;
import com.linkedin.data.template.DynamicRecordMetadata;
import com.linkedin.restli.client.OptionsRequestBuilder;
import com.linkedin.restli.client.RestliRequestOptions;
import com.linkedin.restli.client.base.BuilderBase;
import com.linkedin.restli.common.ResourceMethod;
import com.linkedin.restli.common.ResourceSpec;
import com.linkedin.restli.common.ResourceSpecImpl;


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
 * generated from: com.example.student.impl.StudentsResource
 * 
 */
@Generated(value = "com.linkedin.pegasus.generator.JavaCodeUtil", comments = "Rest.li Request Builder. Generated from api/src/main/idl/com.example.student.students.restspec.json.")
public class StudentsRequestBuilders
    extends BuilderBase
{

    private final static String ORIGINAL_RESOURCE_PATH = "students";
    private final static ResourceSpec _resourceSpec;

    static {
        HashMap<String, DynamicRecordMetadata> requestMetadataMap = new HashMap<String, DynamicRecordMetadata>();
        HashMap<String, DynamicRecordMetadata> responseMetadataMap = new HashMap<String, DynamicRecordMetadata>();
        HashMap<String, com.linkedin.restli.common.CompoundKey.TypeInfo> keyParts = new HashMap<String, com.linkedin.restli.common.CompoundKey.TypeInfo>();
        _resourceSpec = new ResourceSpecImpl(EnumSet.of(ResourceMethod.GET, ResourceMethod.BATCH_GET, ResourceMethod.CREATE), requestMetadataMap, responseMetadataMap, Integer.class, null, null, Student.class, keyParts);
    }

    public StudentsRequestBuilders() {
        this(RestliRequestOptions.DEFAULT_OPTIONS);
    }

    public StudentsRequestBuilders(RestliRequestOptions requestOptions) {
        super(ORIGINAL_RESOURCE_PATH, requestOptions);
    }

    public StudentsRequestBuilders(String primaryResourceName) {
        this(primaryResourceName, RestliRequestOptions.DEFAULT_OPTIONS);
    }

    public StudentsRequestBuilders(String primaryResourceName, RestliRequestOptions requestOptions) {
        super(primaryResourceName, requestOptions);
    }

    public static String getPrimaryResource() {
        return ORIGINAL_RESOURCE_PATH;
    }

    public OptionsRequestBuilder options() {
        return new OptionsRequestBuilder(getBaseUriTemplate(), getRequestOptions());
    }

    /**
     * Gets a student from in-memory store.
     * 
     * @return
     *     builder for the resource method
     */
    public StudentsGetRequestBuilder get() {
        return new StudentsGetRequestBuilder(getBaseUriTemplate(), _resourceSpec, getRequestOptions());
    }

    /**
     * Gets a set of students that correspond to the specified ids.
     * 
     * @return
     *     builder for the resource method
     */
    public StudentsBatchGetRequestBuilder batchGet() {
        return new StudentsBatchGetRequestBuilder(getBaseUriTemplate(), _resourceSpec, getRequestOptions());
    }

    /**
     * TODO
     *  Adds a student to the database.
     * 
     * @return
     *     builder for the resource method
     */
    public StudentsCreateRequestBuilder create() {
        return new StudentsCreateRequestBuilder(getBaseUriTemplate(), _resourceSpec, getRequestOptions());
    }

}
