package com.example.student.impl;

import com.linkedin.restli.common.HttpStatus;
import com.linkedin.restli.server.BatchResult;
import com.linkedin.restli.server.CreateKVResponse;
import com.linkedin.restli.server.CreateResponse;
import com.linkedin.restli.server.ErrorResponseFormat;
import com.linkedin.restli.server.PagingContext;
import com.linkedin.restli.server.ResourceContext;
import com.linkedin.restli.server.RestLiConfig;
import com.linkedin.restli.server.RestLiServiceException;
import com.linkedin.restli.server.annotations.RestLiCollection;
import com.linkedin.restli.server.resources.CollectionResourceTemplate;
import com.example.student.Student;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collector;
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
 *
 * - Learn how to use RestLiConfig. Where do we specify this?
 */
@RestLiCollection(name = "students", namespace = "com.example.student", keyName="studentID")
public class StudentsResource extends CollectionResourceTemplate<Integer, Student> {

    /**
     * Populate with sample students
     */
    private static Map<Integer, Student> students = new HashMap<Integer, Student>();

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

    @Override
    public List<Student> getAll(PagingContext pagingContext) {
        return super.getAll(pagingContext);
    }

    /**
     * Gets a set of students that correspond to the specified ids.
     *
     * @param ids the ids of the students to get
     * @return a map of student ids to students who exist
     */
    @Override
    public BatchResult<Integer, Student> batchGet(Set<Integer> ids) {

        Map<Integer, Student> batch = new HashMap<Integer, Student>();
        Map<Integer, RestLiServiceException> errors = new HashMap<Integer, RestLiServiceException>();

        // put a student in batch if they exist, create an error otherwise
        ids.stream()
            .forEach(id -> {
                Student student = students.get(id);
                if (student != null) {
                    batch.put(id, student);
                }
                else {
                    errors.put(id,
                            new RestLiServiceException(
                                HttpStatus.S_404_NOT_FOUND,
                                "No matches found for this sid."));
                }
            });

        return new BatchResult<Integer, Student>(batch, errors);

    }

    /**
     * Adds a student to the database.
     *
     * @param entity the Student we're adding
     * @return response object with HTTP status code (default is 200)
     */
    @Override
    public CreateResponse create(Student entity) {

        // added a parameter so we can put this new student in the database.
        // TODO: should we just add id to the Student.pdl?
        int sid = Integer.valueOf(getContext().getParameter("sid"));

        // illegal name
        if (!entity.hasName() || entity.getName().isEmpty())
            return new CreateResponse(
                    new RestLiServiceException(HttpStatus.S_400_BAD_REQUEST,
                            "The student has no name."));

        // empty major (no major has value "undeclared")
        if (!entity.hasMajor())
            return new CreateResponse(
                    new RestLiServiceException(HttpStatus.S_400_BAD_REQUEST,
                            "Major field cannot be empty.")
                    );

        // illegal class year
        if (!entity.hasClassYear() || entity.getClassYear() < 1887)
            return new CreateResponse(
                    new RestLiServiceException(HttpStatus.S_400_BAD_REQUEST,
                            "Class year must be later than 1887.")
                    );

        students.put(sid, entity);
        return new CreateResponse(sid);

    }

}
