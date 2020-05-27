
package com.example.student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.linkedin.data.schema.validation.ValidationResult;
import com.linkedin.restli.client.RestliRequestOptions;
import com.linkedin.restli.client.base.CreateIdRequestBuilderBase;
import com.linkedin.restli.common.ResourceMethod;
import com.linkedin.restli.common.ResourceSpec;
import com.linkedin.restli.common.validation.RestLiDataValidator;


/**
 * Adds a student to the database.
 * 
 *  Request:
 *       TODO
 * 
 */
@Generated(value = "com.linkedin.pegasus.generator.JavaCodeUtil", comments = "Rest.li Request Builder")
public class StudentsCreateRequestBuilder
    extends CreateIdRequestBuilderBase<Integer, Student, StudentsCreateRequestBuilder>
{


    public StudentsCreateRequestBuilder(String baseUriTemplate, ResourceSpec resourceSpec, RestliRequestOptions requestOptions) {
        super(baseUriTemplate, Student.class, resourceSpec, requestOptions);
    }

    public static ValidationResult validateInput(Student input) {
        Map<String, List<String>> annotations = new HashMap<String, List<String>>();
        RestLiDataValidator validator = new RestLiDataValidator(annotations, Student.class, ResourceMethod.CREATE);
        return validator.validateInput(input);
    }

}
