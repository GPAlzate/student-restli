
package com.example.student;

import javax.annotation.Generated;
import com.linkedin.restli.client.RestliRequestOptions;
import com.linkedin.restli.client.base.DeleteRequestBuilderBase;
import com.linkedin.restli.common.ResourceSpec;


/**
 * Deletes a student from the database.
 * 
 * @deprecated
 *     This format of request builder is obsolete. Please use {@link com.example.student.StudentsDeleteRequestBuilder} instead.
 */
@Generated(value = "com.linkedin.pegasus.generator.JavaCodeUtil", comments = "Rest.li Request Builder")
@Deprecated
public class StudentsDeleteBuilder
    extends DeleteRequestBuilderBase<Integer, Student, StudentsDeleteBuilder>
{


    public StudentsDeleteBuilder(String baseUriTemplate, ResourceSpec resourceSpec, RestliRequestOptions requestOptions) {
        super(baseUriTemplate, Student.class, resourceSpec, requestOptions);
    }

}
