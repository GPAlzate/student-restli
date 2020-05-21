package com.example.student.impl;

import com.linkedin.restli.server.CreateResponse;
import com.linkedin.restli.server.annotations.RestLiCollection;
import com.linkedin.restli.server.resources.CollectionResourceTemplate;
import com.example.student.Student;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private static Map<Integer, Student> students
            = new HashMap<Integer, Student>();

    static {
        students.put(1, new Student().setName("Gabe Alzate")
                                            .setMajor("Computer Science")
                                            .setClassYear(2021));
        students.put(2, new Student().setName("Srinivasa Ramanujan")
                                            .setMajor("Mathematics")
                                            .setClassYear(2021));
        students.put(3, new Student().setName("Noam Chomsky")
                                            .setMajor("Linguistics")
                                            .setClassYear(2021));
        students.put(4, new Student().setName("Pyotr Ilyich Tchaikovsky")
                                            .setMajor("Music")
                                            .setClassYear(2021));
        students.put(5, new Student().setName("Jackson Pollock")
                                            .setMajor("Art")
                                            .setClassYear(2021));
        students.put(6, new Student().setName("Galileo")
                                            .setMajor("Physics")
                                            .setClassYear(2021));
        students.put(7, new Student().setName("Rick Riordan")
                                            .setMajor("English")
                                            .setClassYear(2021));
        students.put(8, new Student().setName("Andrew Yang")
                                            .setMajor("Asian American Studies")
                                            .setClassYear(2021));
        students.put(9, new Student().setName("Quentin Tarantino")
                                            .setMajor("Media Studies")
                                            .setClassYear(2021));
        students.put(10, new Student().setName("John Maynard Keynes")
                                            .setMajor("Economics")
                                            .setClassYear(2021));
    }

    /**
     * Gets a student from in-memory store. 
     *
     * @param sid the unique student id
     *
     * @return the student (or null if the student doesn't exist, in which case
     *         rest.li sends a 404)
     */
    @Override
    public Student get(Integer sid) {
        return students.get(sid);
    }

    /**
     * Gets a set of students that correspond to the specified ids.
     *
     * @param ids the ids of the students to get
     * @return a map of student ids to students who exist
     */
    @Override
    public Map<Integer, Student> batchGet(Set<Integer> ids) {
        return ids.stream()
                    .collect(Collectors
                    .toMap(id -> (Integer) id,
                            id -> (Student) students.get(id)));
    }

    /**
     * Adds a student to the database.
     *
     * @param entity the Student we're adding
     * @return response object  HTTP status code
     */
    @Override
    public CreateResponse create(Student entity) {
        return super.create(entity);
    }

}
