
package com.example.student;

import javax.annotation.Generated;
import com.linkedin.restli.client.RestliRequestOptions;
import com.linkedin.restli.client.base.GetAllRequestBuilderBase;
import com.linkedin.restli.common.ResourceSpec;


/**
 * Retrieves all students from the map.
 * 
 *  Request:
 *       http GET localhost:8080/students
 * 
 * @deprecated
 *     This format of request builder is obsolete. Please use {@link com.example.student.StudentsGetAllRequestBuilder} instead.
 */
@Generated(value = "com.linkedin.pegasus.generator.JavaCodeUtil", comments = "Rest.li Request Builder")
@Deprecated
public class StudentsGetAllBuilder
    extends GetAllRequestBuilderBase<Integer, Student, StudentsGetAllBuilder>
{


    public StudentsGetAllBuilder(String baseUriTemplate, ResourceSpec resourceSpec, RestliRequestOptions requestOptions) {
        super(baseUriTemplate, Student.class, resourceSpec, requestOptions);
    }

}
