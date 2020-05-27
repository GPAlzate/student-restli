
package com.example.student;

import javax.annotation.Generated;
import com.linkedin.restli.client.RestliRequestOptions;
import com.linkedin.restli.client.base.BatchGetEntityRequestBuilderBase;
import com.linkedin.restli.common.ResourceSpec;


/**
 * Gets a set of students that correspond to the specified ids.
 * 
 *  Request:
 *       http GET localhost:8080/students?<ids=1&ids=2&...>
 * 
 */
@Generated(value = "com.linkedin.pegasus.generator.JavaCodeUtil", comments = "Rest.li Request Builder")
public class StudentsBatchGetRequestBuilder
    extends BatchGetEntityRequestBuilderBase<Integer, Student, StudentsBatchGetRequestBuilder>
{


    public StudentsBatchGetRequestBuilder(String baseUriTemplate, ResourceSpec resourceSpec, RestliRequestOptions requestOptions) {
        super(baseUriTemplate, resourceSpec, requestOptions);
    }

}
