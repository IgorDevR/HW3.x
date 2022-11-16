package ru.hogwarts.school.record;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class StudentRecord {

    private Long id;
    @NotBlank(message = "name cannot be empty")
    private String name;

    @Min(value = 16, message = "Min age student 16 years")
    @Max(value = 25, message = "Max age student 25 years")
    private int age;

    private FacultyRecord faculty;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public FacultyRecord getFaculty() {
        return faculty;
    }

    public void setFaculty(FacultyRecord faculty) {
        this.faculty = faculty;
    }
}
