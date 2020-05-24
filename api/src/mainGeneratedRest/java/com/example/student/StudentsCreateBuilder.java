
package com.example.student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.linkedin.data.schema.validation.ValidationResult;
import com.linkedin.restli.client.RestliRequestOptions;
import com.linkedin.restli.client.base.CreateRequestBuilderBase;
import com.linkedin.restli.common.ResourceMethod;
import com.linkedin.restli.common.ResourceSpec;
import com.linkedin.restli.common.validation.RestLiDataValidator;


/**
 * TODO
 *  Adds a student to the database.
 * 
 * @deprecated
 *     This format of request builder is obsolete. Please use {@link com.example.student.StudentsCreateRequestBuilder} instead.
 */
@Generated(value = "com.linkedin.pegasus.generator.JavaCodeUtil", comments = "Rest.li Request Builder")
@Deprecated
public class StudentsCreateBuilder
    extends CreateRequestBuilderBase<Integer, Student, StudentsCreateBuilder>
{


    public StudentsCreateBuilder(String baseUriTemplate, ResourceSpec resourceSpec, RestliRequestOptions requestOptions) {
        super(baseUriTemplate, Student.class, resourceSpec, requestOptions);
    }

    public static ValidationResult validateInput(Student input) {
        Map<String, List<String>> annotations = new HashMap<String, List<String>>();
        RestLiDataValidator validator = new RestLiDataValidator(annotations, Student.class, ResourceMethod.CREATE);
        return validator.validateInput(input);
    }

}
