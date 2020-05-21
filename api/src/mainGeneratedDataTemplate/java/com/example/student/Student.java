
package com.example.student;

import java.util.List;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import com.linkedin.data.DataMap;
import com.linkedin.data.schema.PathSpec;
import com.linkedin.data.schema.RecordDataSchema;
import com.linkedin.data.template.DataTemplateUtil;
import com.linkedin.data.template.GetMode;
import com.linkedin.data.template.RecordTemplate;
import com.linkedin.data.template.SetMode;


/**
 * A student for Rest.li
 * 
 */
@Generated(value = "com.linkedin.pegasus.generator.JavaCodeUtil", comments = "Rest.li Data Template. Generated from api/src/main/pegasus/com/example/student/Student.pdl.")
public class Student
    extends RecordTemplate
{

    private final static Student.Fields _fields = new Student.Fields();
    private final static RecordDataSchema SCHEMA = ((RecordDataSchema) DataTemplateUtil.parseSchema("{\"type\":\"record\",\"name\":\"Student\",\"namespace\":\"com.example.student\",\"doc\":\"A student for Rest.li\",\"fields\":[{\"name\":\"name\",\"type\":\"string\",\"doc\":\"Full name of student\"},{\"name\":\"major\",\"type\":\"string\",\"doc\":\"Student major, e.g. \\\"Mathematics\\\"\"},{\"name\":\"classYear\",\"type\":\"int\",\"doc\":\"Graduation year\"}]}"));
    private final static RecordDataSchema.Field FIELD_Name = SCHEMA.getField("name");
    private final static RecordDataSchema.Field FIELD_Major = SCHEMA.getField("major");
    private final static RecordDataSchema.Field FIELD_ClassYear = SCHEMA.getField("classYear");

    public Student() {
        super(new DataMap(5, 0.75F), SCHEMA);
    }

    public Student(DataMap data) {
        super(data, SCHEMA);
    }

    public static Student.Fields fields() {
        return _fields;
    }

    /**
     * Existence checker for name
     * 
     * @see Student.Fields#name
     */
    public boolean hasName() {
        return contains(FIELD_Name);
    }

    /**
     * Remover for name
     * 
     * @see Student.Fields#name
     */
    public void removeName() {
        remove(FIELD_Name);
    }

    /**
     * Getter for name
     * 
     * @see Student.Fields#name
     */
    public String getName(GetMode mode) {
        return obtainDirect(FIELD_Name, String.class, mode);
    }

    /**
     * Getter for name
     * 
     * @return
     *     Required field. Could be null for partial record.
     * @see Student.Fields#name
     */
    @Nonnull
    public String getName() {
        return obtainDirect(FIELD_Name, String.class, GetMode.STRICT);
    }

    /**
     * Setter for name
     * 
     * @see Student.Fields#name
     */
    public Student setName(String value, SetMode mode) {
        putDirect(FIELD_Name, String.class, String.class, value, mode);
        return this;
    }

    /**
     * Setter for name
     * 
     * @param value
     *     Must not be null. For more control, use setters with mode instead.
     * @see Student.Fields#name
     */
    public Student setName(
        @Nonnull
        String value) {
        putDirect(FIELD_Name, String.class, String.class, value, SetMode.DISALLOW_NULL);
        return this;
    }

    /**
     * Existence checker for major
     * 
     * @see Student.Fields#major
     */
    public boolean hasMajor() {
        return contains(FIELD_Major);
    }

    /**
     * Remover for major
     * 
     * @see Student.Fields#major
     */
    public void removeMajor() {
        remove(FIELD_Major);
    }

    /**
     * Getter for major
     * 
     * @see Student.Fields#major
     */
    public String getMajor(GetMode mode) {
        return obtainDirect(FIELD_Major, String.class, mode);
    }

    /**
     * Getter for major
     * 
     * @return
     *     Required field. Could be null for partial record.
     * @see Student.Fields#major
     */
    @Nonnull
    public String getMajor() {
        return obtainDirect(FIELD_Major, String.class, GetMode.STRICT);
    }

    /**
     * Setter for major
     * 
     * @see Student.Fields#major
     */
    public Student setMajor(String value, SetMode mode) {
        putDirect(FIELD_Major, String.class, String.class, value, mode);
        return this;
    }

    /**
     * Setter for major
     * 
     * @param value
     *     Must not be null. For more control, use setters with mode instead.
     * @see Student.Fields#major
     */
    public Student setMajor(
        @Nonnull
        String value) {
        putDirect(FIELD_Major, String.class, String.class, value, SetMode.DISALLOW_NULL);
        return this;
    }

    /**
     * Existence checker for classYear
     * 
     * @see Student.Fields#classYear
     */
    public boolean hasClassYear() {
        return contains(FIELD_ClassYear);
    }

    /**
     * Remover for classYear
     * 
     * @see Student.Fields#classYear
     */
    public void removeClassYear() {
        remove(FIELD_ClassYear);
    }

    /**
     * Getter for classYear
     * 
     * @see Student.Fields#classYear
     */
    public Integer getClassYear(GetMode mode) {
        return obtainDirect(FIELD_ClassYear, Integer.class, mode);
    }

    /**
     * Getter for classYear
     * 
     * @return
     *     Required field. Could be null for partial record.
     * @see Student.Fields#classYear
     */
    @Nonnull
    public Integer getClassYear() {
        return obtainDirect(FIELD_ClassYear, Integer.class, GetMode.STRICT);
    }

    /**
     * Setter for classYear
     * 
     * @see Student.Fields#classYear
     */
    public Student setClassYear(Integer value, SetMode mode) {
        putDirect(FIELD_ClassYear, Integer.class, Integer.class, value, mode);
        return this;
    }

    /**
     * Setter for classYear
     * 
     * @param value
     *     Must not be null. For more control, use setters with mode instead.
     * @see Student.Fields#classYear
     */
    public Student setClassYear(
        @Nonnull
        Integer value) {
        putDirect(FIELD_ClassYear, Integer.class, Integer.class, value, SetMode.DISALLOW_NULL);
        return this;
    }

    /**
     * Setter for classYear
     * 
     * @see Student.Fields#classYear
     */
    public Student setClassYear(int value) {
        putDirect(FIELD_ClassYear, Integer.class, Integer.class, value, SetMode.DISALLOW_NULL);
        return this;
    }

    @Override
    public Student clone()
        throws CloneNotSupportedException
    {
        return ((Student) super.clone());
    }

    @Override
    public Student copy()
        throws CloneNotSupportedException
    {
        return ((Student) super.copy());
    }

    public static class Fields
        extends PathSpec
    {


        public Fields(List<String> path, String name) {
            super(path, name);
        }

        public Fields() {
            super();
        }

        /**
         * Full name of student
         * 
         */
        public PathSpec name() {
            return new PathSpec(getPathComponents(), "name");
        }

        /**
         * Student major, e.g. "Mathematics"
         * 
         */
        public PathSpec major() {
            return new PathSpec(getPathComponents(), "major");
        }

        /**
         * Graduation year
         * 
         */
        public PathSpec classYear() {
            return new PathSpec(getPathComponents(), "classYear");
        }

    }

}
