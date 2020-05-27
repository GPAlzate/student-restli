
package com.example.student;

import javax.annotation.Generated;
import com.linkedin.restli.client.RestliRequestOptions;
import com.linkedin.restli.client.base.BatchGetRequestBuilderBase;
import com.linkedin.restli.common.ResourceSpec;


/**
 * Gets a set of students that correspond to the specified ids.
 * 
 *  Request:
 *       http GET localhost:8080/students?<ids=1&ids=2&...>
 * 
 * @deprecated
 *     This format of request builder is obsolete. Please use {@link com.example.student.StudentsBatchGetRequestBuilder} instead.
 */
@Generated(value = "com.linkedin.pegasus.generator.JavaCodeUtil", comments = "Rest.li Request Builder")
@Deprecated
public class StudentsBatchGetBuilder
    extends BatchGetRequestBuilderBase<Integer, Student, StudentsBatchGetBuilder>
{


    public StudentsBatchGetBuilder(String baseUriTemplate, ResourceSpec resourceSpec, RestliRequestOptions requestOptions) {
        super(baseUriTemplate, Student.class, resourceSpec, requestOptions);
    }

}
