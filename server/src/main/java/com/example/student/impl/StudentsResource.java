package com.example.student.impl;

import com.linkedin.restli.server.annotations.RestLiCollection;
import com.linkedin.restli.server.resources.CollectionResourceTemplate;
import com.example.student.Student;
import java.util.HashMap;
import java.util.Map;

/**
 * Sample Rest.li resource for a student. Identifies student by their ID and
 * retrieves the corresponding student info
 *
 * TODO:
 * - Explore differences between resource annotations, e.g. RestLiCollection,
 *   RestLiSimpleResource, etc.
 *
 *      https://linkedin.github.io/rest.li/user_guide/restli_server#runtimes
 *
 * - Read about resource conventions
 *
 *      https://linkedin.github.io/rest.li/modeling/modeling#collection
 */
@RestLiCollection(name = "students", namespace = "com.example.student", keyName="studentID")
public class StudentsResource extends CollectionResourceTemplate<Integer, Student> {

    /**
     * Populate with sample students
     */
    private static Map<Integer, String> students
            = new HashMap<Integer, String>();

    private static Map<String, Map<String, Object>> studentInfo 
            = new HashMap<String, Map<String, Object>>();

    static {
        students.put(10357854, "Gabriel Alzate");
        studentInfo.put("Gabriel Alzate", new HashMap<String, Object>(){{
                put("major", "Computer Science");
                put("classYear", 2021);
            }}
        );
    }


    /**
     * Gets a student from the 'database'
     *
     * @param sid the unique student id
     * @return the student with all
     */
    @Override
    public Student get(Integer sid) {

        // TODO: maybe we can use optionals here
        String name = students.get(sid);
        if (name == null)
            return null; // null returns 404
        
        Map<String, Object> info = studentInfo.get(name);
        return new Student().setName(name)
                            .setMajor((String) info.get("major"))
                            .setClassYear((Integer) info.get("classYear"));
        
    }
}
