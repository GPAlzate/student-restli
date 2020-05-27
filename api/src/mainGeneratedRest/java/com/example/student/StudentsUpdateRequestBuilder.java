
package com.example.student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.linkedin.data.schema.validation.ValidationResult;
import com.linkedin.restli.client.RestliRequestOptions;
import com.linkedin.restli.client.base.UpdateRequestBuilderBase;
import com.linkedin.restli.common.ResourceMethod;
import com.linkedin.restli.common.ResourceSpec;
import com.linkedin.restli.common.validation.RestLiDataValidator;


/**
 * Updates a student by replacing the entire entity.
 * 
 */
@Generated(value = "com.linkedin.pegasus.generator.JavaCodeUtil", comments = "Rest.li Request Builder")
public class StudentsUpdateRequestBuilder
    extends UpdateRequestBuilderBase<Integer, Student, StudentsUpdateRequestBuilder>
{


    public StudentsUpdateRequestBuilder(String baseUriTemplate, ResourceSpec resourceSpec, RestliRequestOptions requestOptions) {
        super(baseUriTemplate, Student.class, resourceSpec, requestOptions);
    }

    public static ValidationResult validateInput(Student input) {
        Map<String, List<String>> annotations = new HashMap<String, List<String>>();
        RestLiDataValidator validator = new RestLiDataValidator(annotations, Student.class, ResourceMethod.UPDATE);
        return validator.validateInput(input);
    }

}
